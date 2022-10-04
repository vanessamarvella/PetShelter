package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connect.Connect;

public class User {
	private int id;
	private String username;
	private String password;
	private String role;
	private static Connect conn = Connect.getConnection();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public boolean insert() {
		String query = "INSERT INTO user VALUES(NULL, ?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, role);
			ps.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error insert user");
			return false;
		}
	}
	
	public static User get(String username, String password) {
		User user = null;
		String query = "SELECT * FROM user WHERE username = '" + username + "'AND password = '" + password + "'";
		try {
			ResultSet rs = conn.executeQuery(query);
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				user.setUsername(rs.getString("username"));
			}
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error get user");
			return user;
		}
	}
	
	public static User getUser(String username) {
		User user = null;
		String query = "SELECT * FROM user WHERE username = '" + username + "'";
		try {
			ResultSet rs = conn.executeQuery(query);
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				user.setUsername(rs.getString("username"));
			}	
			return user;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error get user");
			return user;
		}
	}
	
	public static User get(int id) {
		User user = null;
		String query = "SELECT * FROM user WHERE id=" + id;
		try {
			ResultSet rs = conn.executeQuery(query);
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				user.setUsername(rs.getString("username"));
			}
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return user;
		}
	}
}
