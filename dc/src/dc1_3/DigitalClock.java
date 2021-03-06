package dc1_3;

import java.awt.Button;
import java.awt.CheckboxMenuItem;
import java.awt.Choice;

// 課題 1-2のデジタル時計を、次のように修正してください。
// • Frame ではなく Window クラスを使用して、フレーム枠のないデジタル時計にする。
// • 課題1-２のダイアログで指定できた属性は、マウスの右クリックでポップアップメニューを
//   表示して、カスケード形式で選択出来るようにする（ダイアログは開かない）。
// • 時計内をマウスの左ボタンでクリックしたまま、デスクトップ上でウィンドウを移動させるこ とができるようにする。

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
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

/**
 * デジタル時計を描画する
 * @author p000527465
 *
 */
public class DigitalClock extends Window implements Runnable,ActionListener, MouseListener, MouseMotionListener{
	Insets insets = getInsets();
	final int DEFAULT_CANVAS_WIDTH = 350;
	final int DEFAULT_CANVAS_HEIGHT = 200;
	final int DEFAULT_FONT_SIZE = 50;
	final int RATIO_WIDTH = DEFAULT_CANVAS_WIDTH/DEFAULT_FONT_SIZE;
	final int RATIO_HEIGHT = DEFAULT_CANVAS_HEIGHT/DEFAULT_FONT_SIZE;

	int canvasWidth = DEFAULT_CANVAS_WIDTH,
		canvasHeight = DEFAULT_CANVAS_HEIGHT;
	Image back;
	Graphics buffer;
	Font canvasFont;
	/**
	 * 時間を保持
	 */
	private LocalDateTime dateTime;
	private String hhmmss;
	private String yyyymmdd;

	private MonospacedClock clock;

	PopupMenu popMenu;

	public static void main(String[] args) {
		DigitalClock dc = new DigitalClock();
		Thread thread = new Thread(dc);
		thread.start();
    }

	public DigitalClock() {
		super(new Frame());
		setVisible(true);
		init();
		DigitalClockListener dcListener = new DigitalClockListener();
		addWindowListener(dcListener);
		addComponentListener(dcListener);

		setSize(canvasWidth,canvasHeight);
//		insets = getInsets();
//		setSize(insets.left+canvasWidth+insets.right,
//				insets.top+canvasHeight+insets.bottom);
	}

	MenuItem closeMenu;
	/**
	 * 初期値の設定
	 */
	protected void init() {
		clock = new MonospacedClock();
		addMouseListener(this);
		addMouseMotionListener(this);

		// get now time
		dateTime = LocalDateTime.now();
		setProperty();
//		setResizable(false);
		Menu menu = getMenu();
		popMenu = new PopupMenu();
		popMenu.add(menu);
		closeMenu = new MenuItem("閉じる");
		closeMenu.addActionListener(this);
		popMenu.add(closeMenu);
		add(popMenu);

	}

	CheckboxMenuItem[] fontItems;
	CheckboxMenuItem[] fontSizeItems;
	CheckboxMenuItem[] txtColorItems;
	CheckboxMenuItem[] bgColorItems;
	HashMap<String, Color> colors = new HashMap<String, Color>(){{
			put("BLACK",Color.BLACK);
			put("RED",Color.RED);
			put("GREEN",Color.GREEN);
			put("BLUE",Color.BLUE);
			put("CYAN",Color.CYAN);
			put("MAGENTA",Color.MAGENTA);
			put("YELLOW",Color.YELLOW);
			put("WHITE",Color.WHITE);
			put("DARK_GRAY", Color.DARK_GRAY);
	}};
	Menu getMenu() {
		Menu menu = new Menu("デザイン" );

		Menu fontMenu = new Menu("font");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // initial: buffer.getFont().getFamily();
		String[] fontStrs = ge.getAvailableFontFamilyNames();
		fontItems = new CheckboxMenuItem[fontStrs.length];
		FontItemListener fl = new FontItemListener();
        for (int i = 0;i < fontStrs.length;i++) {
        	CheckboxMenuItem item = new CheckboxMenuItem(fontStrs[i]);
        	if (fontStrs[i].equals(buffer.getFont().getFamily())) item.setState(true);
        	item.addItemListener(fl);
        	fontMenu.add(item);
        	fontItems[i] = item;
        }

		Menu fontSizeMenu = new Menu("font size");
		fontSizeItems = new CheckboxMenuItem[11];
		FontSizeItemListener fsl = new FontSizeItemListener();
		for (int i = 0;i < fontSizeItems.length;i++) {
			int val = (i+1)*10;
        	CheckboxMenuItem item = new CheckboxMenuItem(String.valueOf(val));
        	if (val == buffer.getFont().getSize()) item.setState(true);
        	item.addItemListener(fsl);
        	fontSizeMenu.add(item);
        	fontSizeItems[i] = item;
        }

		Menu txtColorMenu = new Menu("font color");
		txtColorItems = new CheckboxMenuItem[colors.size()];
		TextColorItemListener tcl = new TextColorItemListener();
		int colorIdx = 0;
		for(Iterator<String> iterator = colors.keySet().iterator(); iterator.hasNext();colorIdx++) {
			String key = iterator.next();
			CheckboxMenuItem item = new CheckboxMenuItem(key);
			if (colors.get(key).equals(getForeground())) item.setState(true);
			item.addItemListener(tcl);
			txtColorMenu.add(item);
			txtColorItems[colorIdx] = item;
		}

		Menu bgColorMenu = new Menu("background color");
		bgColorItems = new CheckboxMenuItem[colors.size()];
		BgColorItemListener bcl = new BgColorItemListener();
		colorIdx = 0;
		for(Iterator<String> iterator = colors.keySet().iterator(); iterator.hasNext();colorIdx++) {
			String key = iterator.next();
			CheckboxMenuItem item = new CheckboxMenuItem(key);
		    if (colors.get(key).equals(getBackground())) item.setState(true);
			item.addItemListener(bcl);
			bgColorMenu.add(item);
			bgColorItems[colorIdx] = item;
		}
		menu.add(fontMenu);
		menu.add(fontSizeMenu);
		menu.add(txtColorMenu);
		menu.add(bgColorMenu);
//		menu.add(menuItem);
		return menu;
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
		buffer = back.getGraphics() != null ? back.getGraphics() : g;

		// 描画用文字列の更新
		hhmmss = timeToString();
		yyyymmdd = dateToString();
		buffer.setFont(canvasFont);
		int fontSize = buffer.getFontMetrics().getHeight();
		int cw = insets.left+canvasWidth/2,ch = insets.top+(canvasHeight/2);
		drawStringCenter(buffer,hhmmss,   cw, ch-fontSize/2);
		drawStringCenter(buffer,yyyymmdd, cw, ch+fontSize/2);//ascent使った方がいいの？
//		clock.drawClockCenter(buffer,  cw, ch);

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
		setProperty(Font.MONOSPACED,DEFAULT_FONT_SIZE,Color.DARK_GRAY,Color.WHITE);
	}

	/**
	 * フォント、フォントサイズ、文字色、背景色を設定する
	 * @param font フォント
	 * @param fontSize フォントサイズ
	 * @param txtColor 文字色
	 * @param bgColor 背景色
	 */
	public void setProperty(String font,int fontSize,Color txtColor,Color bgColor) {
		if (font == null) font = canvasFont.getFontName();
		if (fontSize == -1) fontSize = canvasFont.getSize();
		if (txtColor == null) txtColor = getForeground();
		if (bgColor == null) bgColor = getBackground();
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
		if (e.getSource().equals(closeMenu)) {
			System.exit(0);
		}
		// initialCol,initialSize等を定義しておく
		// 設定を確定させた時点で、ダイアログのinitialを変更し、データを一通り返す
		// 帰ってきた値をもとにGraphicsの中身を上書きする
		SettingDialog dlg = new SettingDialog(this,"プロパティ",ModalityType.DOCUMENT_MODAL);
		dlg.setVisible(true);//windowClosingいれてね

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	Point clickedPoint = new Point();
	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				clickedPoint.setLocation(e.getX(), e.getY());
				break;
			case MouseEvent.BUTTON3:
				popMenu.show(this,e.getX(),e.getY());
				break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getLocationOnScreen();
		setLocation(new Point(p.x-clickedPoint.x, p.y-clickedPoint.y));
	}

	@Override
	public void mouseMoved(MouseEvent e) {

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
		private int width = 400;
		private int height = 200;

		public SettingDialog(Window owner, String title, ModalityType modalityType) {
			super(owner, title, modalityType);
			setSize(new Dimension(width,height));
			addWindowListener(this);
			init();
		}

		protected void init() {
			gbl = new GridBagLayout();
			setLayout(gbl);

			addComponent(new Label("font"),0,0,1,1,GridBagConstraints.WEST);
//	        String[] fontStrs = {Font.MONOSPACED,Font.SANS_SERIF,Font.SERIF,Font.DIALOG,Font.DIALOG_INPUT};
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			String[] fontStrs = ge.getAvailableFontFamilyNames();
	        fontChoice = getChoice(fontStrs, buffer.getFont().getFamily());
			addComponent(fontChoice,0,1,1,1,GridBagConstraints.EAST);

			addComponent(new Label("font size"),1,0,1,1,GridBagConstraints.WEST);
			int minSize = 8;int maxSize = 100;
	        String[] fontSizeStrs = new String[maxSize-minSize];
	        for (int i = 0;i < fontSizeStrs.length;i++) fontSizeStrs[i] = String.valueOf(i+minSize+1);
	        fontSizeChoice = getChoice(fontSizeStrs, String.valueOf(buffer.getFont().getSize()));
			addComponent(fontSizeChoice,1,1,1,1,GridBagConstraints.EAST);

			addComponent(new Label("font color"),2,0,1,1,GridBagConstraints.WEST);
			colorTF = getColorTextFieldsFromColor(getForeground());
			addComponent(getColorTextFieldsPanel(colorTF),2,1,1,1,GridBagConstraints.EAST);

			addComponent(new Label("background color"),3,0,1,1,GridBagConstraints.WEST);
			bgColorTF = getColorTextFieldsFromColor(getBackground());
			addComponent(getColorTextFieldsPanel(bgColorTF),3,1,1,1,GridBagConstraints.EAST);

			Panel p = new Panel();
			Button okButton = new Button("OK");
			okButton.addActionListener(this);
			p.add(okButton);
			Button cancelButton = new Button("キャンセル");
			cancelButton.addActionListener(this);
			p.add(cancelButton);
			addComponent(p,4,0,1,2);
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
			setProperty(fontChoice.getSelectedItem(),
						Integer.parseInt(fontSizeChoice.getSelectedItem()),
						color,
						bgColor);
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
		public void componentMoved(ComponentEvent e) {}
		@Override
		public void componentShown(ComponentEvent e) {}
		@Override
		public void componentHidden(ComponentEvent e) {}

	}


	private class FontItemListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			CheckboxMenuItem ci = (CheckboxMenuItem)e.getSource();
			for (int i = 0;i < fontItems.length;i++) {
				CheckboxMenuItem fi = fontItems[i];
				if (ci.equals(fi)) {
					setProperty(fi.getLabel(), -1, null, null);
				}
				else if (fi.getState()) fi.setState(false);
			}
		}
	}
	private class FontSizeItemListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			CheckboxMenuItem ci = (CheckboxMenuItem)e.getSource();
			for (int i = 0;i < fontSizeItems.length;i++) {
				CheckboxMenuItem fi = fontSizeItems[i];
				if (ci.equals(fi)) {
					setProperty(null, Integer.parseInt(fi.getLabel()), null, null);
				}
				else if (fi.getState()) fi.setState(false);
			}
		}
	}

	private class TextColorItemListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			CheckboxMenuItem ci = (CheckboxMenuItem)e.getSource();
			for (int i = 0;i < txtColorItems.length;i++) {
				CheckboxMenuItem ti = txtColorItems[i];
				if (ci.equals(ti)) {
					setProperty(null, -1, colors.get(ti.getLabel()), null);
				}
				else if (ti.getState()) ti.setState(false);
			}
		}
	}
	private class BgColorItemListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			CheckboxMenuItem ci = (CheckboxMenuItem)e.getSource();
			for (int i = 0;i < bgColorItems.length;i++) {
				CheckboxMenuItem bi = bgColorItems[i];
				if (ci.equals(bi)) {
					setProperty(null, -1, null, colors.get(bi.getLabel()));
				}
				else if (bi.getState()) bi.setState(false);
			}
		}
	}

}
