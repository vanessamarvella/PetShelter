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

public class RegisterPage extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel centerPanel, southPanel, northPanel;
	JLabel titleLbl, nameLbl, usernameLbl, passLbl, cpLbl, signLbl, space;
	JTextField nameField, usernameField;
	JPasswordField passField, cpField;
	JButton regButton;
	
	public void init() {
		northPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(8,1));
		southPanel = new JPanel();
		
		titleLbl = new JLabel("Register Page");
		titleLbl.setFont(new Font("Calibri", Font.BOLD, 30)); 	
		titleLbl.setForeground(Color.BLACK);

		space = new JLabel("");
		usernameLbl = new JLabel("Username");
		usernameLbl.setFont(new Font("Calibri", Font.BOLD, 15)); 	
		usernameLbl.setForeground(Color.BLACK);
		passLbl = new JLabel("Password");
		passLbl.setFont(new Font("Calibri", Font.BOLD, 15)); 	
		passLbl.setForeground(Color.BLACK);
		cpLbl = new JLabel("Confirm Password");
		cpLbl.setFont(new Font("Calibri", Font.BOLD, 15)); 	
		cpLbl.setForeground(Color.BLACK);
		
		regButton = new JButton("Register");
		regButton.setFont(new Font("Calibri", Font.BOLD, 15)); 	
		regButton.setForeground(Color.BLACK);
		regButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String username = usernameField.getText();
				@SuppressWarnings("deprecation")
				String password = passField.getText();
				String cp = cpField.getText();
				String role = "User";
				
				if(insertUser(username, password, cp, role) == false) {
					JOptionPane.showMessageDialog(null, "Register user failed", "Error Message", JOptionPane.ERROR_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "Register user success", "Success Message", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					new LoginPage();
				};
		}
		});
		
		signLbl = new JLabel("Already Have Account? Sign in");
		signLbl.setFont(new Font("Calibri", Font.BOLD, 15)); 	
		signLbl.setForeground(Color.BLACK);
		signLbl.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				new LoginPage();
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
		
		nameField = new JTextField();
		usernameField = new JTextField();
		passField = new JPasswordField();
		cpField = new JPasswordField();
		
		northPanel.add(titleLbl);
		
		centerPanel.add(usernameLbl);
		centerPanel.add(usernameField);
		centerPanel.add(passLbl);
		centerPanel.add(passField);
		centerPanel.add(cpLbl);
		centerPanel.add(cpField);
		centerPanel.add(space);
		centerPanel.add(regButton);
		southPanel.add(signLbl);
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		
}
	
	public RegisterPage() {
		// TODO Auto-generated constructor stub
		init();
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private boolean insertUser(String username, String password, String confirmPass, String role) {
		User user = new User();
		
		if(username.trim().equals("") || password.trim().equals("") || confirmPass.trim().equals("")) {
			return false;
		// Username must be unique
		}else if(User.getUser(username) != null) {
			return false;
		// Confirm password must same with password
		}else if(!confirmPass.equals(password)) {
			return false;
		}
		
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);
		return user.insert();
	}
}
