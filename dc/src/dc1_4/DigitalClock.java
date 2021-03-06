package dc1_4;

import java.awt.Button;
import java.awt.Choice;

// デジタル時計に次の機能追加を行ってください。
// • メニューを付けて、プロパティダイアログを開ける。
// • プロパティダイアログでは、以下の項目を設定できる。
//     1. フォントの指定
//     2. フォントサイズの指定 -> Sliderはない、JSliderになる
//     3. 文字色の指定
//     4. 時計の背景色の指定
// • 描画に際して、ちらつきをなくすようにダブルバッファリングする。
// • フォントとフォントサイズを変更すると、時計を表示すべきフレームの大きさを適切に自動変更して、
//   正しく表示されるようにする。

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDateTime;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

// 課題 1-4
// 課題 1-2のデジタル時計で、属性をダイアログで指定できるようにしましたが、ダイアログを作り直
// してください。
// • レイアウトマネージャは、GridBagLayout を使用する。
// • プロパティダイアログは、「属性名」との「値の選択リスト」が縦に並ぶようにする。
// 		　　　フォント　フォントの値の選択リスト
// 		フォントサイズ　サイズの値の選択リスト
// 		　　　　文字色　色の値の選択リスト
// 		　　　　背景色　色の値の選択リスト
// この場合「属性名」のラベルは右寄せして、「値の選択リスト」メニューは左寄せにする。
// • ダイアログの下には、「OK」「キャンセル」のボタンをダイアログの右下に寄せて表示し、そ
// れぞれのボタンを実装する。キャンセルされた場合には、正しく、元の値に戻るようにする。
// • java.util.prefs パッケージを使用して、プロパティダイアログの内容の保存と、時計の終
// 了時の位置を保存する。再度、時計を起動した場合に、それらの保存を復元して、デスクトッ
// プの元の位置に表示されるようにする。
// 上記条件は最低限の条件です。上記条件を満たして、自分なりの創意工夫をしたデジタル時計を作っ
// てきてください。

/**
 * デジタル時計を描画する
 * @author p000527465
 *
 */
public class DigitalClock extends Frame implements Runnable, ActionListener{
	Insets insets = getInsets();
	final int DEFAULT_CANVAS_WIDTH = 350;
	final int DEFAULT_CANVAS_HEIGHT = 200;
	final int DEFAULT_FONT_SIZE = 50;
	final int RATIO_WIDTH = DEFAULT_CANVAS_WIDTH/DEFAULT_FONT_SIZE;
	final int RATIO_HEIGHT = DEFAULT_CANVAS_HEIGHT/DEFAULT_FONT_SIZE;

	final String DEFAULT_FONT = Font.MONOSPACED;
	final Color DEFAULT_TXTCOLOR = Color.DARK_GRAY;
	final Color DEFAULT_BGCOLOR = Color.WHITE;

	int canvasWidth = DEFAULT_CANVAS_WIDTH,
		canvasHeight = DEFAULT_CANVAS_HEIGHT;
	Image back;
	Graphics buffer;
	Font canvasFont;
	/**
	 * 時間を保持
	 */
	private LocalDateTime dateTime = LocalDateTime.now();
	private String hhmmss;
	private String yyyymmdd;
	// 設定の保存
	Preferences prefs;

	public static void main(String[] args) {
		DigitalClock dc = new DigitalClock();
		Thread thread = new Thread(dc);
		thread.start();
    }

	public DigitalClock() {
		setVisible(true);
		init();
		DigitalClockListener dcListener = new DigitalClockListener();
		addWindowListener(dcListener);
		addComponentListener(dcListener);

		insets = getInsets();
		setSize(insets.left+canvasWidth+insets.right,
				insets.top+canvasHeight+insets.bottom);
	}

	/**
	 * 初期値の設定
	 */
	protected void init() {
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("デザイン" );
		MenuItem menuItem = new MenuItem("プロパティ");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);
		setMenuBar(menuBar);

		// get now time
		dateTime = LocalDateTime.now();

		// set property
		prefs = Preferences.userNodeForPackage(this.getClass());
		try {
			if (prefs.keys().length > 0)
				setPrefs();
			else
				setProperty();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
//		prefs.put("testKey","testValue");

		setResizable(false);
	}

	/**
	 * スレッドの実行処理
	 */
	@Override
	public void run() {
		while (true) {
			// 日時情報の更新(描画用文字列は更新されない)
			dateTime = LocalDateTime.now();
			repaint();
			try {
				// wait 1/10 second
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 描画メソッド
	 */
	public void paint(Graphics g) {
		back = createImage(insets.left+canvasWidth+insets.right,
							insets.top+canvasHeight+insets.bottom);
		buffer = back.getGraphics();

		// 描画用文字列の更新
		hhmmss = timeToString();
		yyyymmdd = dateToString();
		buffer.setFont(canvasFont);
		int fontSize = buffer.getFontMetrics().getHeight();
		int cw = insets.left+canvasWidth/2,ch = insets.top+(canvasHeight/2);
		drawStringCenter(buffer,hhmmss,   cw, ch-fontSize/2);
		drawStringCenter(buffer,yyyymmdd, cw, ch+fontSize/2);//ascent使った方がいいの？
		g.drawImage(back, 0, 0, this);
	}

	// accessor

	// utility
	/**
	 * 時刻をdateから取得し、hh:mm:ssの形で返す
	 * @return hh:mm:ss
	 */
	protected String timeToString() {
		String hh = convertDigitString(dateTime.getHour(),2);
		String mm = convertDigitString(dateTime.getMinute(),2);
		String ss = convertDigitString(dateTime.getSecond(),2);
		return hh+":"+mm+":"+ss;
	}

	/**
	 * 時刻をdateから取得し、yyyy/mm/ddの形で返す
	 * @return yyyy/mm/dd
	 */
	protected String dateToString() {
		String yyyy = convertDigitString(dateTime.getYear(),4);
		String mm = convertDigitString(dateTime.getMonthValue(),2);
		String dd = convertDigitString(dateTime.getDayOfMonth(),2);
		return yyyy+"/"+mm+"/"+dd;
	}

	/**
	 * 数値numをdigit桁の文字列にして返す
	 * (2019,2)	-> 19
	 * (3,4)	-> 0003
	 * @param num 桁を修正したい整数
	 * @param digit 修正後の桁数
	 * @return 修正したnum
	 */
	public String convertDigitString(int num,int digit) {
		// digitを越えた桁を削除
		num %= (int)( Math.pow(10,digit));
		String format = "%0"+digit+"d";
		return String.format(format, num);
	}

	public final void setPrefs() {
		int x = prefs.getInt("locationx", 0);
		int y = prefs.getInt("locationy", 0);
		setLocation(x, y);
		String font = prefs.get("font", DEFAULT_FONT);
		int fontSize = prefs.getInt("fontsize", DEFAULT_FONT_SIZE);
		int txtColorRGB = prefs.getInt("color", DEFAULT_TXTCOLOR.getRGB());
		int bgColorRGB = prefs.getInt("bgcolor", DEFAULT_BGCOLOR.getRGB());
		System.out.println(font);
		System.out.println(fontSize);
		System.out.println(new Color(txtColorRGB));
		System.out.println(new Color(bgColorRGB));
		setProperty(font, fontSize, new Color(txtColorRGB), new Color(bgColorRGB));
	}

	/**
	 * x,yが中心となるようdrawStringする
	 * @param g 描画に使うGraphics
	 * @param text 描画したい文字
	 * @param x 中心座標
	 * @param y 中心座標
	 */
	public static void drawStringCenter(Graphics g,String text,int x,int y){
		FontMetrics fontMet = g.getFontMetrics();
		Rectangle textRect = fontMet.getStringBounds(text, g).getBounds();
		int cx = x-textRect.width/2;
		int cy = y-textRect.height/2+fontMet.getMaxAscent();

        g.drawString(text, cx, cy);

	}

	/**
	 * フォント、フォントサイズ、文字色、背景色を初期設定にする
	 */
	public void setProperty() {
		setProperty(DEFAULT_FONT, DEFAULT_FONT_SIZE,
					DEFAULT_TXTCOLOR, DEFAULT_BGCOLOR);
	}

	/**
	 * フォント、フォントサイズ、文字色、背景色を設定する
	 * @param font フォント
	 * @param fontSize フォントサイズ
	 * @param txtColor 文字色
	 * @param bgColor 背景色
	 */
	public void setProperty(String font,int fontSize,Color txtColor,Color bgColor) {
		if (font == "") font = DEFAULT_FONT;
		if (fontSize == -1) fontSize = DEFAULT_FONT_SIZE;
		if (txtColor == null) txtColor = DEFAULT_TXTCOLOR;
		if (bgColor == null) bgColor = DEFAULT_BGCOLOR;

		// 1,2. フォント、サイズの指定
		canvasFont = new Font(font,Font.BOLD,fontSize);
		setSize(insets.left+fontSize*RATIO_WIDTH+insets.right,
				insets.top+fontSize*RATIO_HEIGHT+insets.bottom);
		canvasWidth = getSize().width-(insets.left+insets.right);
		canvasHeight = getSize().height-(insets.top+insets.bottom);
		// 3. 文字色の指定
		setForeground(txtColor);
		// 4. 時計の背景色の指定
		setBackground(bgColor);
	}


	// ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		// initialCol,initialSize等を定義しておく
		// 設定を確定させた時点で、ダイアログのinitialを変更し、データを一通り返す
		// 帰ってきた値をもとにGraphicsの中身を上書きする
		SettingDialog dlg = new SettingDialog(this,"プロパティ",ModalityType.DOCUMENT_MODAL);
		dlg.setVisible(true);//windowClosingいれてね

	}

	/**
	 * 設定画面
	 * @author p000527465
	 *
	 */
	private class SettingDialog extends Dialog implements ActionListener,TextListener, WindowListener{
		private Choice fontChoice;
		private Choice fontSizeChoice;
		private ColorTextFields colorTF;
		private ColorTextFields bgColorTF;
		private GridBagLayout gbl;
		private Panel panel;
		private int width = 400;
		private int height = 200;

		public SettingDialog(Window owner, String title, ModalityType modalityType) {
			super(owner, title, modalityType);
			setSize(new Dimension(width,height));
			addWindowListener(this);
			init();
		}

		protected void init() {
			panel = new Panel();
			gbl = new GridBagLayout();
			panel.setLayout(gbl);
			GridBagConstraints gbc = new GridBagConstraints();

			Label fontLabel = new Label("font");
			setGBC(gbc,0,0,1,1,GridBagConstraints.EAST);
			gbl.setConstraints(fontLabel, gbc);
			panel.add(fontLabel);
//			addComponent(new Label("font"),0,0,1,1,GridBagConstraints.EAST);
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        // initial: buffer.getFont().getFamily();
			String[] fontStrs = ge.getAvailableFontFamilyNames();
//	        String[] fontStrs = {Font.MONOSPACED,Font.SANS_SERIF,Font.SERIF,Font.DIALOG,Font.DIALOG_INPUT};
	        fontChoice = getChoice(fontStrs, buffer.getFont().getFamily());
			setGBC(gbc, 0,1,1,1,GridBagConstraints.WEST);
			gbl.setConstraints(fontChoice, gbc);
			panel.add(fontChoice);
//	        addComponent(fontChoice,0,1,1,1,GridBagConstraints.WEST);

			Label fontSizeLabel = new Label("font size");
			setGBC(gbc,1,0,1,1,GridBagConstraints.EAST);
			gbl.setConstraints(fontSizeLabel, gbc);
			panel.add(fontSizeLabel);
//			addComponent(new Label("font size"),1,0,1,1,GridBagConstraints.EAST);
			int minSize = 8;int maxSize = 100;
	        String[] fontSizeStrs = new String[maxSize-minSize];
	        for (int i = 0;i < fontSizeStrs.length;i++) fontSizeStrs[i] = String.valueOf(i+minSize+1);
	        fontSizeChoice = getChoice(fontSizeStrs, String.valueOf(buffer.getFont().getSize()));
			setGBC(gbc,1,1,1,1,GridBagConstraints.WEST);
			gbl.setConstraints(fontSizeChoice, gbc);
			panel.add(fontSizeChoice);
//			addComponent(fontSizeChoice,1,1,1,1,GridBagConstraints.WEST);

			Label fontColorLabel = new Label("font color");
			setGBC(gbc,2,0,1,1,GridBagConstraints.EAST);
			gbl.setConstraints(fontColorLabel, gbc);
			panel.add(fontColorLabel);
//			addComponent(new Label("font color"),2,0,1,1,GridBagConstraints.EAST);
			colorTF = getColorTextFieldsFromColor(getForeground());
			Panel colorPanel = getColorTextFieldsPanel(colorTF);
			setGBC(gbc,2,1,1,1,GridBagConstraints.WEST);
			gbl.setConstraints(colorPanel, gbc);
			panel.add(colorPanel);
//			addComponent(getColorTextFieldsPanel(colorTF),2,1,1,1,GridBagConstraints.WEST);

			Label bgcolorLabel = new Label("background color");
			setGBC(gbc,3,0,1,1,GridBagConstraints.EAST);
			gbl.setConstraints(bgcolorLabel, gbc);
			panel.add(bgcolorLabel);
//			addComponent(bgcolorLabel,3,0,1,1,GridBagConstraints.EAST);
			bgColorTF = getColorTextFieldsFromColor(getBackground());
			Panel bgColorPanel = getColorTextFieldsPanel(bgColorTF);
			setGBC(gbc,3,1,1,1,GridBagConstraints.WEST);
			gbl.setConstraints(bgColorPanel, gbc);
			panel.add(bgColorPanel);
//			addComponent(getColorTextFieldsPanel(bgColorTF),3,1,1,1,GridBagConstraints.WEST);

			Panel p = new Panel();
			Button okButton = new Button("OK");
			okButton.addActionListener(this);
			p.add(okButton);
			Button cancelButton = new Button("キャンセル");
			cancelButton.addActionListener(this);
			p.add(cancelButton);
			setGBC(gbc,4,0,1,2,GridBagConstraints.EAST);
			gbl.setConstraints(p, gbc);
			panel.add(p);
			addComponent(panel, 0, 0, getWidth(), getHeight());
//			addComponent(p,4,0,1,2);
		}

		protected void setGBC(GridBagConstraints gbc, int gridy, int gridx, int gridheight, int gridwidth, int anchor) {
			gbc.gridx = gridx;
			gbc.gridy = gridy;
			gbc.gridwidth = gridwidth;
			gbc.gridheight = gridheight;
			gbc.anchor = anchor;
		}

		protected Choice getChoice(String[] array, String selected) {
	        Choice c = new Choice();
	        for (int i = 0;i < array.length;i++) {
	        	c.add(array[i]);
	        }
	        c.select(selected);
	        return c;
		}

		protected ColorTextFields getColorTextFieldsFromColor(Color c) {
			int tfLength = 2;
			TextField r = new TextField(String.valueOf(c.getRed()),tfLength);
			TextField g = new TextField(String.valueOf(c.getGreen()),tfLength);
			TextField b = new TextField(String.valueOf(c.getBlue()),tfLength);
			r.addTextListener(this);
			g.addTextListener(this);
			b.addTextListener(this);
			ColorTextFields ctf = new ColorTextFields(r, g, b);
			return ctf;
		}

		protected Panel getColorTextFieldsPanel(ColorTextFields ctf) {
			Panel p = new Panel();
			p.setLayout(new GridLayout(1,6));
			p.add(new Label("R"));
			p.add(ctf.getRedTextField());
			p.add(new Label("G"));
			p.add(ctf.getGreenTextField());
			p.add(new Label("B"));
			p.add(ctf.getBlueTextField());
			return p;
		}

		public void setPropertyFromInput() {
			Color color = new Color(Integer.parseInt(colorTF.getRedTextField().getText()),
									Integer.parseInt(colorTF.getGreenTextField().getText()),
									Integer.parseInt(colorTF.getBlueTextField().getText()));
			Color bgColor = new Color(Integer.parseInt(bgColorTF.getRedTextField().getText()),
								Integer.parseInt(bgColorTF.getGreenTextField().getText()),
								Integer.parseInt(bgColorTF.getBlueTextField().getText()));
			String font = fontChoice.getSelectedItem();
			String fontSizeStr = fontSizeChoice.getSelectedItem();
			String colorStr = String.valueOf(color.getRGB());
			String bgcolorStr = String.valueOf(bgColor.getRGB());
			setProperty(font, Integer.parseInt(fontSizeStr),
						color,
						bgColor);
			prefs.put("font", font);
			prefs.put("fontsize", fontSizeStr);
			prefs.put("color", colorStr);
			prefs.put("bgcolor", bgcolorStr);
		}

		public void addComponent(Component c, int row, int col, int h, int w) {
	        addComponent(c, row, col, h, w, GridBagConstraints.CENTER);
	    }
		public void addComponent(Component c, int row, int col, int h, int w,int anchor) {
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        gbc.gridx = col;
	        gbc.gridy = row;
	        gbc.gridwidth = w;
	        gbc.gridheight = h;
	        gbc.anchor = anchor;
	        gbl.setConstraints(c, gbc);
	        add(c);
	    }

		class ColorTextFields{
			private TextField rtf;
			private TextField gtf;
			private TextField btf;
			public ColorTextFields(TextField rtf,TextField gtf,TextField btf) {
				this.rtf = rtf;
				this.gtf = gtf;
				this.btf = btf;
			}
			public TextField getRedTextField() {
				return rtf;
			}
			public TextField getGreenTextField() {
				return gtf;
			}
			public TextField getBlueTextField() {
				return btf;
			}
		}

		// WindowListener
		@Override
		public void windowOpened(WindowEvent e) {}
		@Override
		public void windowClosing(WindowEvent e) {
			int closeOperation = JOptionPane.showConfirmDialog(this, "保存しますか？");
			switch (closeOperation) {
			case JOptionPane.YES_OPTION:
				setPropertyFromInput();
				setVisible(false);
				break;
			case JOptionPane.NO_OPTION:
				setVisible(false);
				break;
			case JOptionPane.CANCEL_OPTION:
			}
		}
		@Override
		public void windowClosed(WindowEvent e) {}
		@Override
		public void windowIconified(WindowEvent e) {}
		@Override
		public void windowDeiconified(WindowEvent e) {}
		@Override
		public void windowActivated(WindowEvent e) {}
		@Override
		public void windowDeactivated(WindowEvent e) {}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof Button) {
				switch (((Button)e.getSource()).getLabel()) {
				case "OK":
					setPropertyFromInput();
					break;
				case "キャンセル":
					setVisible(false);
					break;
				}
			}
		}

		/**
		 *
		 * 0~255の数字しか入ってこない前提！そうでない場合修正すること<br>
		 */
		@Override
		public void textValueChanged(TextEvent e) {
			if (e.getSource() instanceof TextField) {
				TextField t = (TextField)e.getSource();
				String text = t.getText();
				if (text.equals("")) {
					return;
				}
				if (!(text.equals(text.replaceAll("[^0-9]", "")))) {
					// 数字以外を入れさせない
					t.setText(text.replaceAll("[^0-9]", ""));
					return;
				}
				int min = 0,max = 255;
				int col = Integer.parseInt(text);
				if (col < min) t.setText(String.valueOf(min));
				else if (col > max) t.setText(String.valueOf(max));
			}
		}

	}

	/**
	 * WindowListener, ComponentListenerの挙動をまとめたもの
	 * @author p000527465
	 *
	 */
	private class DigitalClockListener implements WindowListener,ComponentListener{

		// WindowListener
		@Override
		public void windowOpened(WindowEvent e) {}
		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
		@Override
		public void windowClosed(WindowEvent e) {}
		@Override
		public void windowIconified(WindowEvent e) {}
		@Override
		public void windowDeiconified(WindowEvent e) {}
		@Override
		public void windowActivated(WindowEvent e) {}
		@Override
		public void windowDeactivated(WindowEvent e) {}

		@Override
		public void componentResized(ComponentEvent e) {
		}
		@Override
		public void componentMoved(ComponentEvent e) {
			Point point = getLocationOnScreen();
			prefs.put("locationx", String.valueOf(point.x));
			prefs.put("locationy", String.valueOf(point.y));
		}
		@Override
		public void componentShown(ComponentEvent e) {}
		@Override
		public void componentHidden(ComponentEvent e) {}

	}

}
