package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;

import model.Pet;
import model.User;

public class AdminPage extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel formPanel, northPanel, insertPanel, deletePanel;
	JScrollPane scrollPane;
	JTable table;
	DefaultTableModel data;
	JButton insertButton, deleteButton;
	JTextField nameField, typeField, idField;
	JMenuBar menuBar;
	JMenu mainMenu, logoutMenu;
	JLabel titleLbl, nameLbl, typeLbl, idLbl, space, insertLbl, deleteLbl;
	User user;
	Vector<Object> header;
	
	public AdminPage() {
		// TODO Auto-generated constructor stub
		
		northPanel = new JPanel();
		
		titleLbl = new JLabel("Admin Pet Shelter");
		titleLbl.setFont(new Font("Calibri", Font.BOLD, 30)); 	
		titleLbl.setForeground(Color.BLACK);
		northPanel.add(titleLbl, BorderLayout.CENTER);
		
		mainMenu = new JMenu("Main Menu");
		logoutMenu = new JMenu("Logout");
		
		menuBar = new JMenuBar();
		menuBar.add(mainMenu);
		menuBar.add(logoutMenu);
		
		setJMenuBar(menuBar);
		
		logoutMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new LoginPage();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//INSERT
		
		insertLbl = new JLabel("Insert Form");
		insertLbl.setFont(new Font("Calibri", Font.BOLD, 20));
		
		nameLbl = new JLabel("Pet Name");
		typeLbl = new JLabel("Type of Animal");
		space = new JLabel(" ");
		
		nameField = new JTextField();
		typeField = new JTextField();
		
		insertButton = new JButton("Insert");
		insertButton.addActionListener(this);
		
		insertPanel = new JPanel(new GridLayout(6,1));
		
		insertPanel.add(insertLbl);
		insertPanel.add(nameLbl);
		insertPanel.add(nameField);
		insertPanel.add(typeLbl);
		insertPanel.add(typeField);
		insertPanel.add(space);
		insertPanel.add(insertButton);
		
		//DELETE
		
		deleteLbl = new JLabel("Delete Form");
		deleteLbl.setFont(new Font("Calibri", Font.BOLD, 20));
		
		idField = new JTextField();
		idLbl = new JLabel("Pet id");
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		
		deletePanel = new JPanel(new GridLayout(6,1));
		
		deletePanel.add(deleteLbl);
		deletePanel.add(idLbl);
		deletePanel.add(idField);
		deletePanel.add(space);
		deletePanel.add(deleteButton);
			
		formPanel = new JPanel(new GridLayout(1,2));
		
		formPanel.add(insertPanel);
		formPanel.add(deletePanel);
		
		header = new Vector<>();
		header.add("Pet Id");
		header.add("Pet Name");
		header.add("Pet Type");
		header.add("Status");
		header.add("Owner");
		
		data = new DefaultTableModel(header, 0);
		setData();
		
		table = new JTable(data) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		scrollPane = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println(table.getSelectedRow());
			}
		});
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(formPanel, BorderLayout.SOUTH);
		this.add(scrollPane, BorderLayout.CENTER);
		
		setSize(800, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void setData() {
		while(data.getRowCount() > 0) {
			data.removeRow(0);
		}
        Vector<Pet> vPet = Pet.getAll();
        for (Pet pet : vPet) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(pet.getId());
            rowData.add(pet.getName());
            rowData.add(pet.getType());
            rowData.add(pet.getStatus());
            User user = User.get(pet.getUser_id());
            if(user == null) {
            	rowData.add("-");
            }else {
            	rowData.add(user.getUsername());
            }
            data.addRow(rowData);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == deleteButton) {
			String id = idField.getText();
			int input = JOptionPane.showConfirmDialog(null, "Are you sure want to delete pet?");
			if(input == 0) {
				if(deletePet(id)) {
					JOptionPane.showMessageDialog(null, "Pet successfully deleted!");
				}else{
					JOptionPane.showMessageDialog(null, "Pet failed deleted!");
				}
			}
			idField.setText("");
			setData();
		} else if(e.getSource() == insertButton) {
			int user_id = -1;
			String name = nameField.getText();
			String type = typeField.getText();
			String status = "Available";
			
			if(!insertPet(user_id, name, type, status)) {
				JOptionPane.showMessageDialog(null, "Failed inserted pet!");
			}else{
				JOptionPane.showMessageDialog(null, "Pet successfully inserted!");
				nameField.setText("");
				typeField.setText("");
				setData();
			};
		}
	}
	
	public static boolean insertPet(int user_id, String name, String type, String status) {
		if(name.trim().equals("") || type.trim().equals("")) {
			return false;
		}
		
		Pet pet = new Pet();
		pet.setUser_id(user_id);
		pet.setName(name);
		pet.setType(type);
		pet.setStatus(status);
		return pet.insert();
	}

	public static boolean deletePet(String id) {
		Pet pet = new Pet();
		if(id.trim().equals("")) {
			return false;
		}
		int deleteId = Integer.parseInt(id);
		if(Pet.get(deleteId) == null) {
			return false;
		}
		pet.setId(deleteId);
		return pet.delete();
	}

}
