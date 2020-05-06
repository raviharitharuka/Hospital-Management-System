package models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Users { // A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	private static String toMd5(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] messageDigest = md.digest(text.getBytes());
		BigInteger no = new BigInteger(1, messageDigest);
		String hashtext = no.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}

	public String readUsers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
// Prepare the html table to be displayed
			output = "<table border=\"1\">" + "<tr>" + "<th>Name</th>" + "<th>Email</th>" + "<th>Role</th>"
					+ "<th>Update User</th>" + "<th>Remove User</th>" + "</tr>";
			String query = "SELECT * FROM users";
			// Prepare the html table to be displayed

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
// iterate through the rows in the result set
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String name = rs.getString("name");
				String email = rs.getString("email");
				String role = rs.getString("role");

// Add into the html table
				output += "<tr><td><input id=\"hidUsersIDUpdate\"name=\"hidUsersIDUpdate\"type=\"hidden\" value=\"" + id
						+ "\">" + name + "</td>";

				output += "<td>" + email + "</td>";
				output += "<td>" + role + "</td>";
// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-id='"
						+ id + "' onClick=\"location.reload();\">" + "</td></tr>";
			}
			con.close();
// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the users.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String insertUsers(String name, String email, String password, String role) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = "INSERT INTO `users`( `name`, `email`, `password`, `role`) VALUES (?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, toMd5(password));
			preparedStmt.setString(4, role);
//execute the statement
			preparedStmt.execute();
			con.close();

			String newUsers = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateUsers(String id, String name, String email, String password, String role) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
// create a prepared statement
			String query = "UPDATE users SET name=?,email=?,password=?,role=?WHERE id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, password);
			preparedStmt.setString(4, role);
			preparedStmt.setInt(5, Integer.parseInt(id));
// execute the statement
			preparedStmt.execute();
			con.close();
			String newUsers = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the user\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteUsers(String id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
// create a prepared statement
			String query = "delete from users where id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));
// execute the statement
			preparedStmt.execute();
			con.close();
			String newUsers = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
