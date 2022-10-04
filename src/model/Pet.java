package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connect.Connect;

public class Pet {
	private int id;
	private int user_id;
	private String name;
	private String type;
	private String status;
	private static Connect conn = Connect.getConnection();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean insert() {
		String query = "INSERT INTO pet VALUES(NULL, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, user_id);
			ps.setString(2, name);
			ps.setString(3, type);
			ps.setString(4, status);
			ps.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error insert pet");
			return false;
		}
	}
	
	public boolean delete() {
		String query = "DELETE FROM pet WHERE id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Error delete pet");
			return false;
		}
	}
	
	public boolean setStatus() {
		String query = "UPDATE pet SET user_id = ? , status = 'Adopted' WHERE id = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, user_id);
			ps.setInt(2, id);
			ps.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error update pet status");
			return false;
		}
	}
	
	public static Vector<Pet> getAll(){
		String query = "SELECT * FROM pet";
		Vector<Pet> vPet = new Vector<>();
		
		try {
			ResultSet rs = conn.executeQuery(query);
			while(rs.next()) {
				Pet pet = new Pet();
				pet.setId(rs.getInt("id"));
				pet.setUser_id(rs.getInt("user_id"));
				pet.setName(rs.getString("name"));
				pet.setType(rs.getString("type"));
				pet.setStatus(rs.getString("status"));
				vPet.add(pet);
			}
			return vPet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return vPet;
		}
	}
	
	public static Pet get(int id) {
		Pet pet = null;
		String query = "SELECT * FROM pet WHERE id = " + id;
		try {
			ResultSet rs = conn.executeQuery(query);
			while(rs.next()) {
				pet = new Pet();
				pet.setId(rs.getInt("id"));
				pet.setUser_id(rs.getInt("user_id"));
				pet.setName(rs.getString("name"));
				pet.setType(rs.getString("type"));
				pet.setStatus(rs.getString("status"));
			}
			return pet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return pet;
		}
	}
}
