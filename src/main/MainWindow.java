package main;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;

import data.Constraint;
import data.Function;
import data.TaskDataCollection;
import data.TaskDataItem;
import input.InputDialog;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final String EDIT_FUNCTION = "EDIT_FUNCTION", ADD_CONSTRAINT = "ADD_CONSTRAINT", EDIT_CONSTRAINT = "EDIT_CONSTRAINT", DELETE_CONSTRAINT = "DELETE_CONSTRAINT", 
			SET_AMOUNT = "SET_AMOUNT", SOLVE = "SOLVE", EXIT = "EXIT";
	
	public static Constraint constraintFromDialog;
	public static Function objectiveFunction;
	
	private JPanel contentPane, panelMain, panelTitle, panelFunctionName;
	private JTextField txtAmount;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JLabel lblFunctionName;
	private JButton btnAddConstraint, btnEditConstraint, btnDeleteConstraint, btnSolve; 
	
	private final DefaultListModel<Constraint> listModel = new DefaultListModel<Constraint>();
	private JList<Constraint> listConstraints;
	private JComboBox<String> comboProblem;
	private ButtonGroup grpFormat;
	
	// Main window
	public MainWindow() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				closeFrame(evt);
			}
		});
		
		setTitle("Solving of the linear programming problem");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setBounds(100, 100, 565, 528);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 238, 238));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		mnFile = new JMenu("File");
		
		JMenuItem mntmAmount = new JMenuItem("Variables");
		mntmAmount.setActionCommand(SET_AMOUNT);
		mntmAmount.addActionListener(this);
		mnFile.add(mntmAmount);
		
		JMenuItem mntmSolve = new JMenuItem("Solve");
		mntmSolve.setActionCommand(SOLVE);
		mntmSolve.addActionListener(this);
		mnFile.add(mntmSolve);
		
		mnFile.addSeparator();
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setActionCommand(EXIT);
		mntmExit.addActionListener(this);
		mnFile.add(mntmExit);
		
		menuBar.add(mnFile);
		
		panelMain = new JPanel();
		contentPane.add(panelMain);
		panelMain.setLayout(null);
		
		JLabel lblAmount = new JLabel("Number of variables:");
		lblAmount.setBounds(410, 69, 139, 23);
		panelMain.add(lblAmount);
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtAmount = new JTextField();
		txtAmount.setForeground(new Color(128, 0, 0));
		txtAmount.setBounds(410, 95, 57, 23);
		panelMain.add(txtAmount);
		txtAmount.setEditable(false);
		txtAmount.setHorizontalAlignment(SwingConstants.CENTER);
		txtAmount.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtAmount.setColumns(10);
		
		JButton btnSetAmount = new JButton("Set...");
		btnSetAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSetAmount.setBounds(470, 94, 79, 25);
		panelMain.add(btnSetAmount);
		btnSetAmount.setActionCommand(SET_AMOUNT);
		btnSetAmount.addActionListener(this);
		
		panelTitle = new JPanel();
		panelTitle.setBounds(10, 11, 539, 58);
		panelTitle.setLayout(new BorderLayout(0, 0));
		panelMain.add(panelTitle);
		
		JLabel lblTitle = new JLabel("Solving of the linear programming problem");
		lblTitle.setVerticalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(0, 0, 128));
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 28));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblTitle, BorderLayout.CENTER);
		
		JLabel lblMethod = new JLabel("(simplex method)");
		lblMethod.setForeground(new Color(0, 0, 128));
		lblMethod.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMethod.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblMethod, BorderLayout.SOUTH);
		
		JPanel panelFunction = new JPanel();
		panelFunction.setBounds(10, 121, 539, 81);
		panelFunction.setLayout(new BorderLayout(0, 0));
		panelMain.add(panelFunction);
		
		JLabel lblFunctionTitle = new JLabel(" Objective function:");
		lblFunctionTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFunctionTitle.setHorizontalAlignment(SwingConstants.LEFT);
		panelFunction.add(lblFunctionTitle, BorderLayout.NORTH);
		
		panelFunctionName = new JPanel();
		panelFunctionName.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelFunction.add(panelFunctionName, BorderLayout.CENTER);
		
		lblFunctionName = new JLabel();
		panelFunctionName.add(lblFunctionName);
		lblFunctionName.setForeground(new Color(128, 0, 0));
		lblFunctionName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFunctionName.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JPanel panelFunctionButton = new JPanel();
		panelFunction.add(panelFunctionButton, BorderLayout.SOUTH);
		
		JButton btnEditFunction = new JButton("Edit...");
		panelFunctionButton.add(btnEditFunction);
		btnEditFunction.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEditFunction.setActionCommand(EDIT_FUNCTION);
		btnEditFunction.addActionListener(this);
		
		JPanel panelSystem = new JPanel();
		panelSystem.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		panelSystem.setBounds(10, 205, 537, 202);
		panelMain.add(panelSystem);
		panelSystem.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSystemTitle = new JLabel("Constraint system:");
		lblSystemTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSystemTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelSystem.add(lblSystemTitle, BorderLayout.NORTH);
		
		JPanel panelActions = new JPanel();
		panelSystem.add(panelActions, BorderLayout.EAST);
		panelActions.setLayout(new GridLayout(4, 1, 0, 0));
		
		btnAddConstraint = new JButton("+");
		btnAddConstraint.setActionCommand(ADD_CONSTRAINT);
		btnAddConstraint.addActionListener(this);
		panelActions.add(btnAddConstraint);
		
		btnEditConstraint = new JButton("*");
		btnEditConstraint.setActionCommand(EDIT_CONSTRAINT);
		btnEditConstraint.addActionListener(this);
		panelActions.add(btnEditConstraint);
		
		btnDeleteConstraint = new JButton("X");
		btnDeleteConstraint.setActionCommand(DELETE_CONSTRAINT);
		btnDeleteConstraint.addActionListener(this);
		panelActions.add(btnDeleteConstraint);
		
		listConstraints = new JList<>(listModel);
		listConstraints.setForeground(new Color(128, 0, 0));
		listConstraints.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelSystem.add(listConstraints, BorderLayout.CENTER);
		
		btnSolve = new JButton("Show solution...");
		btnSolve.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnSolve.setActionCommand("SHOW_AREA");
		btnSolve.setBounds(159, 410, 229, 39);
		btnSolve.setActionCommand(SOLVE);
		btnSolve.addActionListener(this);
		panelMain.add(btnSolve);
		
		JLabel lblProblem = new JLabel("Problem:");
		lblProblem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProblem.setBounds(10, 84, 57, 23);
		panelMain.add(lblProblem);
		
		comboProblem = new JComboBox<String>();
		comboProblem.setForeground(new Color(128, 0, 0));
		comboProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addTaskData();
			}
		});
		comboProblem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboProblem.setModel(new DefaultComboBoxModel<String>( TaskDataCollection.getTasksTitles() ));
		comboProblem.setBounds(68, 84, 332, 23);
		comboProblem.setSelectedIndex( TaskDataCollection.getInitTaskNo() );
		panelMain.add(comboProblem);
		
		grpFormat = new ButtonGroup();
		
		JRadioButton rbTxt = new JRadioButton("Plain text");
		rbTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbTxt.setBounds(197, 451, 79, 23);
		rbTxt.setActionCommand(Common.TXT);
		rbTxt.setSelected(false);
		panelMain.add(rbTxt);
		grpFormat.add(rbTxt);
		
		JRadioButton rbHtml = new JRadioButton("HTML");
		rbHtml.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbHtml.setBounds(283, 451, 57, 23);
		rbHtml.setActionCommand(Common.HTML);
		rbHtml.setSelected(true);
		grpFormat.add(rbHtml);
		panelMain.add(rbHtml);
		
		addTaskData();
	}
	
	// Add init values 
	private void addTaskData() {
		listModel.clear();
		
		TaskDataItem curTask = TaskDataCollection.getTaskData( comboProblem.getSelectedIndex() );
		objectiveFunction = curTask.getF();
		
		Constraint[] constraints = curTask.getConstraints();
		for (Constraint constraint : constraints)
			listModel.addElement(constraint);
		
		txtAmount.setText( "" + objectiveFunction.getVarsAmount() );
		lblFunctionName.setText( objectiveFunction.toString() );
		listConstraints.setSelectedIndex(0);
		checkButtons();
	}
	
	// Set buttons availability
	private void checkButtons() {
		boolean constrListNotEmpty = (listModel.getSize() != 0);
		
		btnEditConstraint.setEnabled(constrListNotEmpty);
		btnDeleteConstraint.setEnabled(constrListNotEmpty);
		btnSolve.setEnabled(listModel.getSize() >= 2);
	}
	
	// Action on close main window
	private void closeFrame(java.awt.AWTEvent evt) {
		if (Common.showConfirmDialog(this, "You really want to exit?", "Exit") == JOptionPane.YES_OPTION)
			System.exit(0);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		case SET_AMOUNT:
			int amount = objectiveFunction.getVarsAmount();
			int n = Common.showNumberDialog(this, "Select number of variables", "Number of variables", amount);
			if (n > 0)
				if (n != amount) 
					if (Common.showConfirmDialog(this, "Change number of variables from " + amount + " to " + n + "?", "Change number of variables") == JOptionPane.YES_OPTION) {
						txtAmount.setText("" + n);
						listModel.clear();
						
						double[] c = new double[n];
						for (int i = 0; i < n; i++)
							c[i] = 1;
						
						objectiveFunction = new Function(c, Common.F_MAX);
						lblFunctionName.setText( objectiveFunction.toString() );
						
						checkButtons();
					}
			break;
		
		case EDIT_FUNCTION:
			Common.showFrame(new InputDialog(objectiveFunction));
			lblFunctionName.setText( objectiveFunction.toString() );
			break;
		
		case ADD_CONSTRAINT:
			Common.showFrame(new InputDialog());
			
			if (constraintFromDialog != null) {
				listModel.addElement(constraintFromDialog);
				listConstraints.setSelectedIndex(listModel.getSize()-1);
				checkButtons();
			}
        	
			break;
			
		case EDIT_CONSTRAINT:
			Constraint selectedConstraint = listModel.get(listConstraints.getSelectedIndex());
			Common.showFrame(new InputDialog(selectedConstraint));
			
			if (constraintFromDialog != null) {
				listModel.set(listConstraints.getSelectedIndex(), constraintFromDialog);
				checkButtons();
			}
        	
			break;
        	
		case DELETE_CONSTRAINT:
			selectedConstraint = listModel.get(listConstraints.getSelectedIndex());
			
			if (Common.showConfirmDialog(this, "You really want to delete constraint: " + selectedConstraint.toString() + "?", "Delete") == JOptionPane.YES_OPTION) {
				listModel.removeElementAt(listConstraints.getSelectedIndex());
				if (listModel.getSize() != 0)
					listConstraints.setSelectedIndex(0);
				checkButtons();
			}
        	
			break;
        	
        case SOLVE:
        	Solution sol = new Solution( grpFormat.getSelection().getActionCommand() );
        	if (sol.initSolution(listModel, objectiveFunction))
        		sol.solve();
        	else
        		Common.showErrorMessage(this, "Error while initialization solution!");
            
        	break;
            
        case EXIT:
        	closeFrame(e);
            break;
		}
	}
}
