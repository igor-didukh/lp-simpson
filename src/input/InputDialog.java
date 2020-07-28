package input;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import data.Constraint;
import data.Function;
import main.Common;
import main.MainWindow;

public class InputDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final String SAVE = "SAVE";
	private int amount, mode, width = 50, height = 26, space = 5;
	
	private JPanel contentPane;
	private DoubleTextField[] txtCoefs;
	private DoubleTextField txtC;
	private JLabel lblName;
	private JComboBox<String> comboDirection;
	
	// For ADD constraint
	public InputDialog() {
		String[] dirs = new String[] {Common.C_LOE, Common.C_GOE, Common.C_EQU};
		mode = 1;
		initDialog(dirs);
		setTitle("Add Constraint");
		makeName();
	}
	
	// For EDIT function or constraint
	public InputDialog(Function f) {
		String[] dirs;
		String className = f.getClass().getSimpleName(); 
		
		if ( className.equals("Function") ) {
			dirs =  new String[] {Common.F_MIN, Common.F_MAX};
			mode = 0;
		} else {
			dirs = new String[] {Common.C_LOE, Common.C_GOE, Common.C_EQU};
			mode = 1;
		}
		
		initDialog(dirs);
		setTitle("Edit " + className);
		
		// set text field's values
		double[] coefs = f.getCoefs();
		for (int i = 0; i < coefs.length; i++) 
			txtCoefs[i].setText( Common.clip( coefs[i] ) );
		
		if (mode == 1) {
			Constraint cons = (Constraint) f;
			txtC.setText( Common.clip( cons.getC() ) );
		}
		
		// set combo direction value
		int pos = 0;
		String dir = f.getDir();
		for (int i = 0; i < dirs.length; i++)
			if ( dir.equals(dirs[i]) )
				pos = i;
		
		comboDirection.setSelectedIndex(pos);
		
		makeName();
	}
	
	// Make dialog window
	private void initDialog(String[] directions) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		
		amount = MainWindow.objectiveFunction.getVarsAmount();
		setBounds(0, 0, (2 * amount + mode + 1) * (width + space) + 10, 140);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 238, 238));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtCoefs = new DoubleTextField[amount];
		int posX = 5;
		
		for (int i = 0; i < amount; i++) {
			txtCoefs[i] = createTextField();
			txtCoefs[i].setBounds(posX, 5, width, height);
			contentPane.add(txtCoefs[i]);
			posX += width + space;
			
			String lbl = "* x" + (i+1);
			lbl = (i != amount - 1) ? lbl + " +": lbl;
			
			JLabel lblC = new JLabel(lbl);
			lblC.setVerticalAlignment(SwingConstants.CENTER);
			lblC.setHorizontalAlignment(SwingConstants.CENTER);
			lblC.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblC.setBounds(posX, 5, width, height);
			contentPane.add(lblC);
			posX += width + space;
		}
		
		comboDirection = new JComboBox<String>();
		comboDirection.setModel(new DefaultComboBoxModel<String>(directions));
		comboDirection.setBounds(posX, 5, width, height);
		comboDirection.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				makeName();
			}
		});
		comboDirection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				makeName();
			}
		});
		contentPane.add(comboDirection);
		
		// if work with constraint
		if (mode == 1) {
			posX += width + space;
			txtC = createTextField();
			txtC.setBounds(posX, 5, width, height);
			txtC.setRestrictions("not_neg_double");
			contentPane.add(txtC);
		}
		
		JPanel panelName = new JPanel();
		panelName.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelName.setBounds(5, height + 10, (2 * amount + mode + 1) * (width + space) - 5, 35);
		contentPane.add(panelName);
		
		lblName = new JLabel();
		lblName.setForeground(new Color(0, 0, 128));
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		panelName.add(lblName);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBounds(5, height + 47, (2 * amount + mode + 1) * (width + space) - 5, 35);
		contentPane.add(panelButtons);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		btnOK.setActionCommand(SAVE);
		panelButtons.add(btnOK);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		panelButtons.add(btnCancel);
	}
	
	// Create one text field with listeners 
	private DoubleTextField createTextField() {
		DoubleTextField res = new DoubleTextField();
		res.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				makeName();
			}
		});
		res.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				makeName();
			}
		});
		return res;
	}
	
	// Create the name of function or cor constraint 
	private void makeName() {
		double[] c = new double[amount];
		for (int i = 0; i < amount; i++)
			c[i] = txtCoefs[i].getDouble();
		
		String dir = (String) comboDirection.getSelectedItem();
		
		String name;
		if (mode == 0)
			name = Common.makePolynomName(c, " --> " + dir);
		else
			name = Common.makePolynomName(c, dir) + " " + Common.clip(txtC.getDouble());
		
		lblName.setText(name);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == SAVE) {
			int zeroCoefsCount = 0;
			double[] c = new double[amount];
			
			for (int i = 0; i < amount; i++) {
				c[i] = txtCoefs[i].getDouble();
				if (c[i] == 0)
					zeroCoefsCount++;
			}
			
			if (zeroCoefsCount == amount)
				Common.showErrorMessage(this, "All coefs should not be zero!");
			else {
				String dir = (String) comboDirection.getSelectedItem();
				if (mode == 0)
					MainWindow.objectiveFunction = new Function(c, dir);
				else
					MainWindow.constraintFromDialog = new Constraint(c, dir, txtC.getDouble());
				dispose();
			}
		} 
		else
			dispose();
	}

}