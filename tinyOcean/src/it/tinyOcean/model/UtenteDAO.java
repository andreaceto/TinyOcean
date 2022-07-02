package it.tinyOcean.model;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public class UtenteDAO {
	private static DataSource ds;
	static ResultSet rs = null;

	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/tinyocean");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	private static final String TABLE_NAME = "utente";

	public  synchronized  UtenteBean doRetrieve(UtenteBean bean) {

		PreparedStatement preparedStatement = null;

		String username = bean.getUsername();
		String password = bean.getPassword();

		String searchQuery = "select * from " + TABLE_NAME + " WHERE username='" + username + "' AND password='"
				+ password + "'";

		// "System.out.println" prints in the console; Normally used to trace the
		// process
		System.out.println("Your user name is " + username);
		System.out.println("Your password is " + password);
		System.out.println("Query: " + searchQuery);

		// connect to DB
		Connection connection = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			rs = preparedStatement.executeQuery();
			boolean more = rs.next();
			if (!more) {
				System.out.println("Sorry, you are not a registered user! Please sign up first");
				bean.setValid(false);
			}
			// if user exists set the isValid variable to true
			else if (more) {
				String firstName = rs.getString("nome");
				String lastName = rs.getString("cognome");
				String email= rs.getString("email");
				String numTel= rs.getString("numTelefono");
				String paeseResidenza= rs.getString("paeseResidenza");
				LocalDate dataNascita=rs.getDate("dataNascita").toLocalDate();
				Boolean admin= rs.getBoolean("admin");
				System.out.println("Welcome " + firstName);
				bean.setNome(firstName);
				bean.setCognome(lastName);
				bean.setEmail(email);
				bean.setNumTel(numTel);
				bean.setPaeseResidenza(paeseResidenza);
				bean.setDataNascita(dataNascita);
				bean.setValid(true);
				bean.setAdmin(admin);
			}

		}

		catch (Exception ex) {
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		}

		// some exception handling
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
				}
				preparedStatement = null;
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
				}

				connection = null;
			}
		}

		return bean;

	}
	
	public UtenteBean doRetrieveByKey(String username) throws Exception {
		UtenteBean bean = new UtenteBean();
		String selectSQL = "SELECT * FROM utente WHERE username = ?";

		try(Connection connection = ds.getConnection()){
			try(PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)){
				preparedStatement.setString(1, username);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					bean.setUsername(rs.getString("username"));
					bean.setNome(rs.getString("nome"));
					bean.setCognome(rs.getString("cognome"));
					bean.setEmail(rs.getString("email"));
					bean.setPassword(rs.getString("password"));
					bean.setNumTel(rs.getString("numTelefono"));
					bean.setPaeseResidenza(rs.getString("paeseResidenza"));
					bean.setDataNascita(rs.getDate("dataNascita").toLocalDate());
					bean.setAdmin(rs.getBoolean("admin"));
				}
			}
		}
		return bean;
	}
	
	public  synchronized  boolean checkUser(String username) {
		
		PreparedStatement preparedStatement = null;

		String searchQuery = "select * from " + TABLE_NAME + " WHERE username='" + username + "'";
		
		Connection connection = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			rs = preparedStatement.executeQuery();
			boolean more = rs.next();
			if (!more) {
				System.out.println("Not registered user!");
			}
			else if (more) {
				return true;
			}

		}

		catch (Exception ex) {
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		}

		// some exception handling
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
				}
				preparedStatement = null;
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
				}

				connection = null;
			}
		}
		return false;
	}
	
	public synchronized  void doSave(UtenteBean bean){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (username,nome,cognome, email, password,numTelefono,paeseResidenza,dataNascita) VALUES (?,?,?,?,?,?,?,?)";
		
		System.out.println("Query: " + insertSQL);
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, bean.getUsername());
			preparedStatement.setString(2, bean.getNome());
			preparedStatement.setString(3, bean.getCognome());
			preparedStatement.setString(4, bean.getEmail());
			preparedStatement.setString(5, bean.getPassword());
			preparedStatement.setString(6, bean.getNumTel());
			preparedStatement.setString(7, bean.getPaeseResidenza());
			preparedStatement.setDate(8,  Date.valueOf(bean.getDataNascita()));
			
	
			preparedStatement.executeUpdate();
	
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			
				if (connection != null)
					connection.close();
			}
			catch(SQLException e) {
				System.out.println("Registration failed : An Exception has occurred! " + e);
			}
			
			}
	}
	
	public synchronized  UtenteBean newGuestUser(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String username = UUID.randomUUID().toString().substring(0, 29);
		while(checkUser(username)) {
			username = UUID.randomUUID().toString().substring(0, 29);
		}
		String firstName = "guest";
		String lastName = "guest";
		String email = "guest@guest.com";
		Long password = Math.abs(new Random().nextLong());
		String numTel = "3333333333";
		String paeseResidenza = "Antartica";
		LocalDate dataNascita = LocalDate.EPOCH;
		
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (username,nome,cognome, email, password,numTelefono,paeseResidenza,dataNascita) VALUES (?,?,?,?,?,?,?,?)";
		
		System.out.println("Query: " + insertSQL);
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, password.toString());
			preparedStatement.setString(6, numTel);
			preparedStatement.setString(7, paeseResidenza);
			preparedStatement.setDate(8,  Date.valueOf(dataNascita));
			
	
			preparedStatement.executeUpdate();
	
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			
				if (connection != null)
					connection.close();
			}
			catch(SQLException e) {
				System.out.println("Registration failed : An Exception has occurred! " + e);
			}
			
			}
		
		UtenteBean guest = new UtenteBean();
		guest.setUsername(username);
		guest.setNome(firstName);
		guest.setCognome(lastName);
		guest.setEmail(email);
		guest.setPassword(password.toString());
		guest.setNumTel(numTel);
		guest.setPaeseResidenza(paeseResidenza);
		guest.setDataNascita(dataNascita);
		guest.setValid(false);
		guest.setAdmin(false);
		
		return guest;
	}
	
	public synchronized void Alter(String username, UtenteBean user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String alterStatement = "UPDATE `tinyoceam`.`utente` SET `nome` = ?, `cognome` = ?, "
				+ "`email` = ?,  `dataNascita` = ? "
				+ " WHERE (`username` = ? ) ";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(alterStatement);
			preparedStatement.setString(1, user.getNome());
			preparedStatement.setString(2, user.getCognome());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setDate(4, Date.valueOf(user.getDataNascita()));
			preparedStatement.setString(5, username);
			System.out.print(preparedStatement);
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			
				if (connection != null)
					connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
	}
	}
	
	
	
}
