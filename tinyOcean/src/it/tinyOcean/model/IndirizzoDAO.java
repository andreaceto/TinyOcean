package it.tinyOcean.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class IndirizzoDAO {
	private static DataSource ds;
	static Connection currentCon = null;
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
	private static final String TABLE_NAME2 = "indirizzo";
	private static final String TABLE_NAME3 = "indirizzo_spedizione";

	public synchronized List<IndirizzoBean> getAddresses(UtenteBean user) {
		List<IndirizzoBean> addresses = new LinkedList<IndirizzoBean>();
		PreparedStatement preparedStatement = null;
		String SearchQuery = "Select " + TABLE_NAME3 + ".*" + " FROM " + TABLE_NAME + " Join " + TABLE_NAME2
				+ " ON username=utente " + " Join " + TABLE_NAME3 + " ON " + TABLE_NAME2 + ".via = " + TABLE_NAME3 + ".via"
				+ " AND " + TABLE_NAME2 + ".cap= " + TABLE_NAME3 + ".cap" + " AND " + TABLE_NAME2 + ".numCivico = "
				+ TABLE_NAME3 + ".numCivico "
				+" WHERE utente.username = ? " ;

		Connection connection = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(SearchQuery);
			preparedStatement.setString(1, user.getUsername());

			rs = preparedStatement.executeQuery();
			

			while (rs.next()) {
				IndirizzoBean bean = new IndirizzoBean();
				bean.setVia(rs.getString("via"));
				bean.setNumCivico(rs.getInt("numero_civico"));
				bean.setCap(rs.getInt("cap"));
				bean.setProvincia(rs.getString("provincia"));
				bean.setCitta(rs.getString("città"));
				addresses.add(bean);

			}

		}

		catch (Exception ex) {
			System.out.println( ex);
		}

		return addresses;

	}

	public synchronized void doSave(UtenteBean user, IndirizzoBean address) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + TABLE_NAME3
				+ " (via,numCivico,cap,provincia,citta ) VALUES (?, ?, ?,?,?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, address.getVia());
			preparedStatement.setInt(2, address.getNumCivico());
			preparedStatement.setInt(3, address.getCap());
			preparedStatement.setString(4, address.getProvincia());
			preparedStatement.setString(5, address.getCitta());
			
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			if (ex.getErrorCode() == 1062) {
				System.out.println("Indirizzo già esistente nel DB " + ex);
				try {
					preparedStatement.close();

					insertSQL = "INSERT INTO " + TABLE_NAME2 + " (utente,via,numCivico,cap) VALUES (?, ?, ?,?)";
					preparedStatement = connection.prepareStatement(insertSQL);
					preparedStatement.setString(1, user.getUsername());
					preparedStatement.setString(2, address.getVia());
					preparedStatement.setInt(3, address.getNumCivico());
					preparedStatement.setInt(4, address.getCap());
					preparedStatement.executeUpdate();

				} catch (Exception e) {
					System.out.print("could not update table " + TABLE_NAME2 + " to reflect changes");
				}

				finally {
					try {
						if (preparedStatement != null)
							preparedStatement.close();

						if (connection != null)
							connection.close();
					} catch (Exception e) {
						System.out.println(e);
					}
				}

				return;

			} else
				System.out.println("Inserimento indirizzo fallito " + ex);
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();

				if (connection != null)
					connection.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}

		try {
			preparedStatement.close();
			connection = ds.getConnection();
			insertSQL = "INSERT INTO " + TABLE_NAME2 + " (utente,via,numCivico,cap) VALUES (?, ?, ?,?)";
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, address.getVia());
			preparedStatement.setInt(3, address.getNumCivico());
			preparedStatement.setInt(4, address.getCap());
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.out.print("could not update table " + TABLE_NAME2 + " to reflect changes");
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();

				if (connection != null)
					connection.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}

	}

}

