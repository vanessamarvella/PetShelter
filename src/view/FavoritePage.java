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

public class FavoritePage extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel inputPanel, adoptPanel, northPanel;
	JScrollPane scrollPane;
	JTable table;
	DefaultTableModel data;
	JButton deleteButton;
	JTextField idField;
	JLabel adoptLbl, titleLbl;
	JMenuBar menuBar;
	JMenu mainMenu, logoutMenu;
	JMenuItem favoriteMenu, adoptMenu;
	User user;
	Vector<Object> header;
	
	public FavoritePage(User user) {
		// TODO Auto-generated constructor stub
		
		this.user = user;
		northPanel = new JPanel();
		
		titleLbl = new JLabel("My Favorite Pet");
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
		
		adoptMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new UserPage(user);
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
		
		deleteButton = new JButton("Unfavorite");
		deleteButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String id = idField.getText();
				if(!unfavorite(id, user.getId())){
	                JOptionPane.showMessageDialog(null, "Failed unfavorite pet");
	            }else {
	            	JOptionPane.showMessageDialog(null, "Success unfavorite pet");
	            }
				idField.setText("");
				setData();
			}
			
		});
		
		idField = new JTextField();
		adoptLbl = new JLabel("Pet id : ");
		
		inputPanel = new JPanel(new GridLayout(1,2));
		inputPanel.add(adoptLbl);
		inputPanel.add(idField);
		
		adoptPanel = new JPanel(new GridLayout(2,1));
		adoptPanel.add(inputPanel, BorderLayout.NORTH);
		adoptPanel.add(deleteButton, BorderLayout.SOUTH);

		header = new Vector<>();
		header.add("Pet Id");
		header.add("Pet Name");
		header.add("Pet Type");
		
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
        Vector<Pet> vPet = Favorite.getAllFavoritePet(user.getId());
        for (Pet pet : vPet) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(pet.getId());
            rowData.add(pet.getName());
            rowData.add(pet.getType());
            data.addRow(rowData);
		}
	}

	public boolean unfavorite(String id, int user_id) {
		Favorite fav = new Favorite();
		if(id.trim().equals("")) {
			return false;
		}
		int deleteId = Integer.parseInt(id);
		if(Favorite.getFavorite(user_id, deleteId) == null) {
			return false;
		}
		fav.setPet_id(deleteId);
		fav.setUser_id(user_id);
		return fav.delete();
	}
}
