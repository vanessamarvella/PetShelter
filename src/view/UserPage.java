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

import model.Favorite;
import model.Pet;
import model.User;

public class UserPage extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel inputPanel, adoptPanel, northPanel;
	JScrollPane scrollPane;
	JTable table;
	DefaultTableModel data;
	JButton adoptButton, favButton;
	JTextField idField;
	JLabel adoptLbl, titleLbl;
	JMenuBar menuBar;
	JMenu mainMenu, logoutMenu;
	JMenuItem favoriteMenu, adoptMenu;
	User user;
	Vector<Object> header;
	
	public UserPage(User user) {
		// TODO Auto-generated constructor stub
		
		this.user = user;
		northPanel = new JPanel();
		
		titleLbl = new JLabel("Adopt Pet");
		titleLbl.setFont(new Font("Calibri", Font.BOLD, 30)); 	
		titleLbl.setForeground(Color.BLACK);
		northPanel.add(titleLbl, BorderLayout.CENTER);
		
		adoptMenu = new JMenuItem("Adopt Pet");
		favoriteMenu = new JMenuItem("My Favorite Pet");
		
		mainMenu = new JMenu("Main Menu");
		logoutMenu = new JMenu("Logout");
		
		mainMenu.add(adoptMenu);
		mainMenu.add(favoriteMenu);
		
		menuBar = new JMenuBar();
		menuBar.add(mainMenu);
		menuBar.add(logoutMenu);
		
		setJMenuBar(menuBar);
		
		favoriteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new FavoritePage(user);
			}
			
		});
		
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
		
		adoptButton = new JButton("Adopt");
		adoptButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String id = idField.getText();
				if(!setStatus(id, user.getId())){
	                JOptionPane.showMessageDialog(null, "Failed adopt pet");
	            }else {
	            	JOptionPane.showMessageDialog(null, "Success adopt pet");
	            }
				idField.setText("");
				setData();
			}
			
		});
		favButton = new JButton("Favorite");
		favButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String id = idField.getText();
				if(!addToFavorite(id, user.getId())){
	                JOptionPane.showMessageDialog(null, "Failed add pet to favorite");
	            }else {
	            	JOptionPane.showMessageDialog(null, "Success add pet to favorite");
	            }
			}
			
		});
		
		idField = new JTextField();
		adoptLbl = new JLabel("Pet id : ");
		
		inputPanel = new JPanel(new GridLayout(1,2));
		inputPanel.add(adoptLbl);
		inputPanel.add(idField);
		
		adoptPanel = new JPanel(new GridLayout(1,3));
		adoptPanel.add(inputPanel, BorderLayout.NORTH);
		adoptPanel.add(adoptButton, BorderLayout.SOUTH);
		adoptPanel.add(favButton, BorderLayout.SOUTH);

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
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(adoptPanel, BorderLayout.SOUTH);
		
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
	
	private boolean setStatus(String id, int user_id) {
		Pet pet = new Pet();
		if(id.trim().equals("")) {
			return false;
		}
		int updateId = Integer.parseInt(id);
		Pet pets = Pet.get(updateId);
		
		//If pet already adopted, user can't adopt again
		if(pets == null || pets.getStatus().equals("Adopted")) {
			return false;
		}
		
		pet.setId(updateId);
		pet.setUser_id(user_id);
		return pet.setStatus();
	}
	
	private boolean addToFavorite(String id, int user_id) {
		Favorite fav = new Favorite();
		if(id.trim().equals("")) {
			return false;
		}
		int pet_id = Integer.parseInt(id);
		Pet pets = Pet.get(pet_id);
		
		if(pets == null) {
			return false;
		}
		
		fav.setPet_id(pet_id);
		fav.setUser_id(user_id);
		return fav.insert();
	}

}
