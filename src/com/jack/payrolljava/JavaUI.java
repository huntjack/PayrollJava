package com.jack.payrolljava;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.CallableStatement;


public class JavaUI extends Main implements ActionListener, WindowListener {
	
	private ArrayList<String> jobString = new ArrayList<String>();
	private JFrame frame;
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JLabel firstLabel;
	private JLabel middleLabel;
	private JLabel lastLabel;
	private JTextField firstTextField;
	private JTextField middleTextField;
	private JTextField lastTextField;
	private JLabel jobLabel;
	private JComboBox<String> jobComboBox;
	private JLabel ageLabel;
	private JTextField ageTextField;
	private JLabel sexLabel;
	private JComboBox<String> sexComboBox;
	private JLabel companyLabel;
	private JTextField companyTextField;
	private JLabel regularLabel;
	private JLabel overtimeLabel;
	private JLabel penaltyLabel;
	private JTextField regularTextField;
	private JTextField overtimeTextField;
	private JTextField penaltyTextField;
	private JButton submitButton;
	private JLabel deleteLabel;
	private JTextField deleteTextField;
	private JButton deleteButton;
	private JLabel passwordLabel;
	private JTextField passwordTextField;
	private JButton connectButton;


	private JLabel displayLabel;
	private JTextArea displayTextArea;
	private int displayCounter = 0;
	private int deleteElement = 0;
	
	//database connection setup variables
	private String url = "jdbc:mysql://localhost:3306/payroll_database";
	private String username = "root";
	private String password;
	private Connection connection;
	private CallableStatement addStatement;
	private CallableStatement removeStatement;
	
	@Override
	public void actionPerformed (ActionEvent event ) { //event = something that happens
		
		if (event.getSource()==submitButton) { 
			//If the event = pressing submit button, the body will run (You should change this to If the event = answering the last question)
		
			//BODY
			// <- It will run the check method on the current instance of your class (Quiz)
			
			this.setFirst(firstTextField.getText());
			this.setMiddleInitial(middleTextField.getText());
			this.setLast(lastTextField.getText());
			this.setJobString(jobComboBox.getSelectedItem().toString());
			this.setAge(Integer.parseInt(ageTextField.getText()));
			this.setSex(sexComboBox.getSelectedItem().toString());
			this.setCompany(companyTextField.getText());
			this.setRegularHours(Integer.parseInt(regularTextField.getText()));
			this.setOvertimeHours(Integer.parseInt(overtimeTextField.getText()));
			this.setPenaltyNumber(Integer.parseInt(penaltyTextField.getText()));
			this.setGross(this.findGross(displayCounter));
			this.setNet(this.findNet(displayCounter));
			this.setPenalty(this.findPenalty(displayCounter));
			this.setFinalPay(this.findFinalPay(displayCounter));
			
			//Display Output
			displayTextArea.setText("Name: " + this.getFirstName(displayCounter) + " " + 
			this.getMiddle(displayCounter) + " " + this.getLastName(displayCounter) +
			"\nJob: " + this.getJobString(displayCounter) + "\nSex: " + this.getSex(displayCounter) +
			"\nCompany: " + this.getCompany(displayCounter) + "\nRegular Hours: " + 
			this.getRegularHours(displayCounter) + "\nOvertime Hours: " + this.getOvertimeHours(displayCounter) +
			"\nNumber of Penalties: " + this.getPenaltyNumber(displayCounter) + "\nGross: " + 
			this.getGross(displayCounter) + "\nNet: " + this.getNet(displayCounter) +
			"\nPenalty: " + this.getPenalty(displayCounter) + "\nFinal Pay: " + this.getFinalPay(displayCounter));
			
			//Add to database
			try {
				this.addStatement = this.connection.prepareCall("{call payroll_add (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
				
				this.addStatement.setString(1, this.getFirstName(displayCounter));
				this.addStatement.setString(2, this.getMiddle(displayCounter));
				this.addStatement.setString(3, this.getLastName(displayCounter));
				this.addStatement.setString(4, this.getSex(displayCounter));
				this.addStatement.setInt(5, this.getAge(displayCounter));
				this.addStatement.setString(6, this.getCompany(displayCounter));
				this.addStatement.setString(7, this.getJobString(displayCounter));
				this.addStatement.setInt(8, this.getRegularHours(displayCounter));
				this.addStatement.setInt(9, this.getOvertimeHours(displayCounter));
				this.addStatement.setInt(10, this.getPenaltyNumber(displayCounter));
				this.addStatement.setFloat(11, this.getGross(displayCounter));
				this.addStatement.setFloat(12, this.getNet(displayCounter));
				this.addStatement.setFloat(13, this.getPenalty(displayCounter));
				this.addStatement.setFloat(14, this.getFinalPay(displayCounter));
				
				//Calling Stored Procedure
				System.out.println("\nCalling Stored Procedure: payroll_add");
				this.addStatement.execute();
				
			} catch (Exception exception) {
				exception.printStackTrace();
			} finally {
				try {
					this.addStatement.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}

			//Reset textfields
			this.firstTextField.setText("");
			this.middleTextField.setText("");
			this.lastTextField.setText("");
			this.jobComboBox.setSelectedIndex(0);
			this.ageTextField.setText("");
			this.sexComboBox.setSelectedIndex(0);
			this.companyTextField.setText("");
			this.regularTextField.setText("");
			this.overtimeTextField.setText("");
			this.penaltyTextField.setText("");
			
			displayCounter++; 
		} else if (event.getSource()==deleteButton) {
			try {
				
				this.deleteElement = Integer.parseInt(deleteTextField.getText());
				
				this.removeStatement = this.connection.prepareCall("{call payroll_remove (?)}");	
				this.removeStatement.setInt(1, deleteElement);
				this.removeStatement.execute();
				System.out.println("Employee: " + deleteElement + " successfully deleted.");
				
			} catch (SQLException exception) {
				exception.printStackTrace();
			} finally {
				try {
					this.removeStatement.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			this.deleteTextField.setText("");
		} else if (event.getSource()==connectButton) {
			try {

				this.setPassword(passwordTextField.getText());

				connection = DriverManager.getConnection(url, username, password);

				this.passwordTextField.setText("");

				System.out.println("Connected to the database successfully.");

			} catch (SQLException e) {
				System.out.println("Could not connect to the server.");
				e.printStackTrace();
			}
		}
		else {}
		
	}
	
	@Override
	public float findGross (int counter) {
		float gross = 0;
        switch (jobString.get(counter)) {
            case "Hipster":
                gross = (float) ((regularHours.get(counter) * 450) + overtimeHours.get(counter) * (450 * 1.25));
                break;
            case "Hacker":
                gross = (float) ((regularHours.get(counter) * 400) + overtimeHours.get(counter) * (400 * 1.25));
                break;
            case "Hustler":
                gross = (float) ((regularHours.get(counter) * 350) + overtimeHours.get(counter) * (350 * 1.25));
                break;

        }
        return gross;
		
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setJobString(String jobString) {
		this.jobString.add(jobString);
	}
	
	public String getJobString(int counter) {
		return this.jobString.get(counter);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			System.out.println("Closing Connection.");
			
		} finally {
			try {
				this.connection.close();
				System.exit(0);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
	
	JavaUI() {
		
		//labels
		firstLabel = new JLabel("First Name");
		firstLabel.setBackground(Color.LIGHT_GRAY);
		firstLabel.setOpaque(true);
		
		middleLabel = new JLabel("Middle Initial");
		middleLabel.setBackground(Color.LIGHT_GRAY);
		middleLabel.setOpaque(true);
		
		lastLabel = new JLabel("Last Name");
		lastLabel.setBackground(Color.LIGHT_GRAY);
		lastLabel.setOpaque(true);
		
		jobLabel = new JLabel("Job");
		jobLabel.setBackground(Color.LIGHT_GRAY);
		jobLabel.setOpaque(true);
		
		ageLabel = new JLabel("Age");
		ageLabel.setBackground(Color.LIGHT_GRAY);
		ageLabel.setOpaque(true);
		
		sexLabel = new JLabel("Sex");
		sexLabel.setBackground(Color.LIGHT_GRAY);
		sexLabel.setOpaque(true);
		
		companyLabel = new JLabel("Company");
		companyLabel.setBackground(Color.LIGHT_GRAY);
		companyLabel.setOpaque(true);
		
		regularLabel = new JLabel("Regular Hours");
		regularLabel.setBackground(Color.LIGHT_GRAY);
		regularLabel.setOpaque(true);
		
		overtimeLabel = new JLabel("Overtime");
		overtimeLabel.setBackground(Color.LIGHT_GRAY);
		overtimeLabel.setOpaque(true);
		
		penaltyLabel = new JLabel("Number of Penalties");
		penaltyLabel.setBackground(Color.LIGHT_GRAY);
		penaltyLabel.setOpaque(true);
		
		deleteLabel = new JLabel("Delete Employee");
		deleteLabel.setBackground(Color.LIGHT_GRAY);
		deleteLabel.setOpaque(true);

		passwordLabel = new JLabel("Password");
		passwordLabel.setBackground(Color.LIGHT_GRAY);
		passwordLabel.setOpaque(true);
		
		displayLabel = new JLabel("Display");
		displayLabel.setBackground(Color.LIGHT_GRAY);
		displayLabel.setOpaque(true);
		
		//textfields
		firstTextField = new JTextField();
		firstTextField.setPreferredSize(new Dimension (135, 35));
		
		middleTextField = new JTextField();
		middleTextField.setPreferredSize(new Dimension (35, 35));
		
		lastTextField = new JTextField();
		lastTextField.setPreferredSize(new Dimension (135, 35));
		
		ageTextField = new JTextField();
		ageTextField.setPreferredSize(new Dimension (65, 35));
		
		companyTextField = new JTextField();
		companyTextField.setPreferredSize(new Dimension (150, 35));
		
		regularTextField = new JTextField();
		regularTextField.setPreferredSize(new Dimension (85, 35));
		
		overtimeTextField = new JTextField();
		overtimeTextField.setPreferredSize(new Dimension (85, 35));
		
		penaltyTextField = new JTextField();
		penaltyTextField.setPreferredSize(new Dimension (45, 35));
		
		deleteTextField = new JTextField();
		deleteTextField.setPreferredSize(new Dimension (45,35));

		passwordTextField = new JTextField();
		passwordTextField.setPreferredSize(new Dimension (45,35));
		
		displayTextArea = new JTextArea();
		displayTextArea.setPreferredSize(new Dimension (590, 204));
		displayTextArea.setEditable(false);
		
		
		//JButton
		
		submitButton = new JButton("Submit");
		submitButton.setBounds(100, 100, 155, 50);
		submitButton.addActionListener(this);
		
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(100, 100, 155, 50);
		deleteButton.addActionListener(this);

		connectButton = new JButton("Connect");
		connectButton.setBounds(100, 100, 155, 50);
		connectButton.addActionListener(this);
		
		//JComboBox
		
		String[] jobs = {"Hipster", "Hacker", "Hustler"};
		jobComboBox = new JComboBox<>(jobs);
		jobComboBox.addActionListener(this);
		
		String[] sex = {"Male", "Female"};
		sexComboBox = new JComboBox<>(sex);
		sexComboBox.addActionListener(this);
		
		//upperPanel
		upperPanel = new JPanel();
		upperPanel.setBackground(Color.GRAY);
		upperPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		upperPanel.setPreferredSize(new Dimension (600, 235));
		
		//lowerPanel = need to set maximum frame size so that it won't fit both panels, thus forcing the lower panel to the bottom
		lowerPanel = new JPanel();
		lowerPanel.setBackground(Color.GRAY);
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		lowerPanel.setPreferredSize(new Dimension (600, 235));
		
		//add stuff to upperPanel
		upperPanel.add(firstLabel);
		upperPanel.add(firstTextField);
		upperPanel.add(middleLabel);
		upperPanel.add(middleTextField);
		upperPanel.add(lastLabel);
		upperPanel.add(lastTextField);
		upperPanel.add(jobLabel);
		upperPanel.add(jobComboBox);
		upperPanel.add(ageLabel);
		upperPanel.add(ageTextField);
		upperPanel.add(sexLabel);
		upperPanel.add(sexComboBox);
		upperPanel.add(companyLabel);
		upperPanel.add(companyTextField);
		upperPanel.add(regularLabel);
		upperPanel.add(regularTextField);
		upperPanel.add(overtimeLabel);
		upperPanel.add(overtimeTextField);
		upperPanel.add(penaltyLabel);
		upperPanel.add(penaltyTextField);
		upperPanel.add(submitButton);
		upperPanel.add(deleteLabel);
		upperPanel.add(deleteTextField);
		upperPanel.add(deleteButton);
		upperPanel.add(passwordLabel);
		upperPanel.add(passwordTextField);
		upperPanel.add(connectButton);
		
		//add stuff to lowerPanel
		lowerPanel.add(displayLabel);
		lowerPanel.add(displayTextArea);
	
		
		//frame
		frame = new JFrame("Payroll Java");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setPreferredSize(new Dimension (625, 523));
		frame.addWindowListener(this);
		
		//add stuff to frame
		frame.add(upperPanel);
		frame.add(lowerPanel);
		
		//other
		frame.pack();
		frame.setVisible(true);
		
	}

	public static void main(String[] args) {
		
		JavaUI javaUI = new JavaUI();
		

	}

	
}