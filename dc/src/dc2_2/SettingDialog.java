package dc2_2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingDialog extends JDialog implements ActionListener, ChangeListener{
	private JComboBox<String> fontCombo;
	private JSlider fontSizeSlider;
	private JLabel fontSizeValue;
	private JSpinner[] fgColorSpinner;
	private JPanel fgChip;
	private JSpinner[] bgColorSpinner;
	private JPanel bgChip;
	private GridBagLayout gbl;
	private JPanel panel;
	private int width = 400;
	private int height = 250;

	private ComponentProperty cp;
	public SettingDialog(Window owner,  ComponentProperty cp) {
		super(owner, "プロパティ", ModalityType.DOCUMENT_MODAL);
		this.cp = cp;
		setSize(new Dimension(width,height));
		init();
		setResizable(false);
        setLocationRelativeTo(null);
		setVisible(true);
	}

	public ComponentProperty get() {
		return cp;
	}

	private void init() {
		panel = new JPanel();
		gbl = new GridBagLayout();
		panel.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();

		setPanel("font", getFontComboPanel(), gbc, 0);
		setPanel("font size", getFontSizeComboPanel(), gbc, 1);
		setPanel("font color", getForegroundColorPanel(), gbc, 2);
		setPanel("bg color", getBackgroundColorPanel(), gbc, 3);

		JPanel confirmationPanel = getConfirmationPanel();
		setGBC(gbc,4,0,1,2,GridBagConstraints.EAST);
		gbl.setConstraints(confirmationPanel, gbc);
		panel.add(confirmationPanel);
		//
		addComponent(panel, 0, 0, getWidth(), getHeight());
	}
	private void setPanel(String label, JPanel p, GridBagConstraints gbc, int gridy) {
		Label fontLabel = new Label(label);
		setGBC(gbc,gridy,0,1,1,GridBagConstraints.EAST);
		gbl.setConstraints(fontLabel, gbc);
		panel.add(fontLabel);

		setGBC(gbc,gridy,1,1,1,GridBagConstraints.WEST);
		gbl.setConstraints(p, gbc);
		panel.add(p);
	}
	private JPanel getFontComboPanel() {
		String[] fontStrs = ComponentProperty.getFontFamilyList();
		fontCombo = new JComboBox<String>(fontStrs);
		fontCombo.setSelectedItem(cp.getFontFamily());
		JPanel p = new JPanel();
		p.add(fontCombo);
		return p;
	}
	private JPanel getFontSizeComboPanel() {
		fontSizeSlider = new JSlider(8, 100, cp.getFontSize());
		fontSizeSlider.addChangeListener(this);
		fontSizeValue = new JLabel(String.valueOf(fontSizeSlider.getValue()));
		JPanel p = new JPanel();
		p.add(fontSizeSlider);
		p.add(fontSizeValue);
		return p;
	}
	private JPanel getForegroundColorPanel() {
		fgColorSpinner = new JSpinner[3];
		for (int i = 0;i < 3;i++) {
			fgColorSpinner[i] = new JSpinner();
		}
		fgChip = new JPanel();
		return getColorSpinPanel(cp.getForegroundColor(), fgColorSpinner, fgChip);
	}
	private JPanel getBackgroundColorPanel() {
		bgColorSpinner = new JSpinner[3];
		for (int i = 0;i < 3;i++) {
			bgColorSpinner[i] = new JSpinner();
		}
		bgChip = new JPanel();
		return getColorSpinPanel(cp.getBackgroundColor(), bgColorSpinner, bgChip);
	}
	private JPanel getColorSpinPanel(Color c, JSpinner[] spinners, JPanel chip) {
		JPanel p = new JPanel();
		for (int i = 0;i < 3;i++) {
			spinners[i].setPreferredSize(new Dimension(50, 20));
			spinners[i].addChangeListener(this);
		}
		chip.setSize(new Dimension(25,25));
		chip.setBackground(c);
		p.add(chip);
		spinners[0].setValue(c.getRed());
		p.add(new JLabel("R"));
		p.add(spinners[0]);
		spinners[1].setValue(c.getGreen());
		p.add(new JLabel("G"));
		p.add(spinners[1]);
		spinners[2].setValue(c.getBlue());
		p.add(new JLabel("B"));
		p.add(spinners[2]);
		return p;
	}

	protected void setGBC(GridBagConstraints gbc, int gridy, int gridx, int gridheight, int gridwidth, int anchor) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.anchor = anchor;
	}
	private JPanel getConfirmationPanel() {
		JPanel p = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);
		p.add(okButton);
		JButton cancelButton = new JButton("キャンセル");
		cancelButton.addActionListener(this);
		p.add(cancelButton);
		return p;
	}
	private Color getColorFromSpinners(JSpinner[] spinners) {
		return new Color(
				(int)spinners[0].getValue(),
				(int)spinners[1].getValue(),
				(int)spinners[2].getValue());
	}
	private void addComponent(JComponent c, int row, int col, int h, int w) {
        addComponent(c, row, col, h, w, GridBagConstraints.CENTER);
    }
	public void addComponent(JComponent c, int row, int col, int h, int w,int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setGBC(gbc, col, row, w, h, anchor);
        gbl.setConstraints(c, gbc);
        add(c);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			switch (((JButton)e.getSource()).getText()) {
			case "OK":
				cp = new ComponentProperty(
						(String)fontCombo.getSelectedItem(),
						fontSizeSlider.getValue(),
						getColorFromSpinners(fgColorSpinner),
						getColorFromSpinners(bgColorSpinner)
						);
				setVisible(false);
				break;
			case "キャンセル":
				setVisible(false);
				break;
			}
		}
	}
	class ComponentWithTitle {
		String title;
		JComponent component;
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof JSlider) {
			fontSizeValue.setText(String.valueOf(fontSizeSlider.getValue()));
		} else if (e.getSource() instanceof JSpinner) {
			JSpinner source = (JSpinner)e.getSource();
			if (source == fgColorSpinner[0] || source == fgColorSpinner[1] || source == fgColorSpinner[2]) {
				fgChip.setBackground(getColorFromSpinners(fgColorSpinner));
			} else if (source == bgColorSpinner[0] || source == bgColorSpinner[1] || source == bgColorSpinner[2]) {
				bgChip.setBackground(getColorFromSpinners(bgColorSpinner));
			}
		}
	}
}
