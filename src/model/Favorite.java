package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connect.Connect;

public class Favorite {

	private int pet_id;
	private int user_id;
	private static Connect conn = Connect.getConnection();
	
	public int getPet_id() {
		return pet_id;
	}
	public void setPet_id(int pet_id) {
		this.pet_id = pet_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public boolean delete() {
		String query = "DELETE FROM favorite WHERE user_id = ? AND pet_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, user_id);
			ps.setInt(2, pet_id);
			ps.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Error delete favorite");
			return false;
		}
	}
	
	public boolean insert() {
		String query = "INSERT INTO favorite VALUES(?, ?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, user_id);
			ps.setInt(2, pet_id);
			ps.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error add pet to favorite");
			return false;
		}
	}
	
	public static Favorite getFavorite(int user_id, int pet_id) {
		Favorite fav = null;
		String query = "SELECT * FROM favorite WHERE user_id = '" + user_id + "' AND pet_id = '" + pet_id + "'";
		try {
			ResultSet rs = conn.executeQuery(query);
			while(rs.next()) {
				fav = new Favorite();
				fav.setUser_id(rs.getInt("user_id"));
				fav.setPet_id(rs.getInt("pet_id"));
			}
			return fav;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return fav;
		}
	}
	
	public static Vector<Pet> getAllFavoritePet(int user_id){
		String query = "SELECT * FROM favorite JOIN pet ON favorite.pet_id = pet.id WHERE favorite.user_id = " + user_id;
		Vector<Pet> vPet = new Vector<>();
		
		try {
			ResultSet rs = conn.executeQuery(query);
			while(rs.next()) {
				Pet pet = new Pet();
				pet.setId(rs.getInt("id"));
				pet.setUser_id(rs.getInt("user_id"));
				pet.setName(rs.getString("name"));
				pet.setType(rs.getString("type"));
				vPet.add(pet);
			}
			return vPet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return vPet;
		}
	}
	
	
}
