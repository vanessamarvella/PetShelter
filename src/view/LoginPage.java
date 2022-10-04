package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import connect.Connect;
import model.User;

public class LoginPage extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel centerPanel, southPanel, northPanel;
	JLabel titleLbl, usernameLbl, passLbl, regLbl, space;
	JTextField usernameField;
	JPasswordField passField;
	JButton loginButton;
	Connect conn = Connect.getConnection();
	
	public void init() {
		northPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(6,1));
		southPanel = new JPanel();
		
		titleLbl = new JLabel("Login");
		titleLbl.setFont(new Font("Calibri", Font.BOLD, 30)); 	
		titleLbl.setForeground(Color.BLACK);
		
		usernameLbl = new JLabel("Username");
		passLbl = new JLabel("Password");
		space = new JLabel("");
		
		regLbl = new JLabel("Don't have account? Register");
		regLbl.setFont(new Font("Calibri", Font.BOLD, 15)); 	
		regLbl.setForeground(Color.BLACK);
		regLbl.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				new RegisterPage();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		usernameField = new JTextField();
		passField = new JPasswordField();
		
		loginButton = new JButton("Login");
		loginButton.setForeground(Color.BLACK);
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String username = usernameField.getText();
				String password = passField.getText();
				if(loginAuth(username, password) != null){
					dispose();
				};
			}
			
		});
		
		northPanel.add(titleLbl);
		
		centerPanel.add(usernameLbl, BorderLayout.LINE_START);
		centerPanel.add(usernameField);
		
		centerPanel.add(passLbl, BorderLayout.LINE_START);
		centerPanel.add(passField);
		centerPanel.add(space);
		centerPanel.add(loginButton);
		
		southPanel.add(regLbl, BorderLayout.PAGE_END);
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	public LoginPage() {
		// TODO Auto-generated constructor stub
		init();
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private User loginAuth(String username, String password) {
		User user = User.get(username, password);
		if(user != null) {
			if(user.getRole().equals("Admin")) {
				new AdminPage();
			}else {
				new UserPage(user);	
			}
		}else {
			JOptionPane.showMessageDialog(null, "Invalid username or password", "Error Message", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

}
