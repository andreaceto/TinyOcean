package it.tinyOcean.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ArticoloDAO implements ArticoloModel {

	private static DataSource ds;

	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/tinyocean");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	private static final String TABLE_NAME = "articolo";

	@Override
	public synchronized void doSave(ArticoloBean product) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + ArticoloDAO.TABLE_NAME
				+ " (id,produttore,nome,descrizione,prezzo,stock,peso,altezza,larghezza,lunghezza,saldo) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setLong(1, product.getId());
			preparedStatement.setString(2, product.getProduttore());
			preparedStatement.setString(3, product.getNome());
			preparedStatement.setString(4, product.getDescrizione());
			preparedStatement.setFloat(5, product.getPrezzo());
			preparedStatement.setInt(6, product.getStock());
			preparedStatement.setFloat(7, product.getPeso());
			preparedStatement.setFloat(8, product.getAltezza());
			preparedStatement.setFloat(9, product.getLarghezza());
			preparedStatement.setFloat(10, product.getLunghezza());
			preparedStatement.setInt(11, product.getSaldo());
			preparedStatement.executeUpdate();

			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	}

	@Override
	public synchronized ArticoloBean doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArticoloBean bean = new ArticoloBean();

		String selectSQL = "SELECT * FROM " + ArticoloDAO.TABLE_NAME + " WHERE id = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setProduttore(rs.getString("produttore"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setStock(rs.getInt("stock"));
				bean.setPeso(rs.getFloat("peso"));
				bean.setAltezza(rs.getFloat("altezza"));
				bean.setLunghezza(rs.getFloat("lunghezza"));
				bean.setLarghezza(rs.getFloat("larghezza"));
				bean.setSaldo(rs.getInt("saldo"));
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return bean;
	}

	@Override
	public synchronized boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + ArticoloDAO.TABLE_NAME + " WHERE id = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id);

			result = preparedStatement.executeUpdate();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return (result != 0);
	}

	@Override
	public synchronized Collection<ArticoloBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ArticoloBean> products = new LinkedList<ArticoloBean>();

		String selectSQL = "SELECT * FROM " + ArticoloDAO.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ArticoloBean bean = new ArticoloBean();
				
				bean.setId(rs.getInt("id"));
				bean.setProduttore(rs.getString("produttore"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setStock(rs.getInt("stock"));
				bean.setPeso(rs.getFloat("peso"));
				bean.setAltezza(rs.getFloat("altezza"));
				bean.setLunghezza(rs.getFloat("lunghezza"));
				bean.setLarghezza(rs.getFloat("larghezza"));
				bean.setSaldo(rs.getInt("saldo"));
				products.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return products;
	}
	
	public static boolean isAcquired(ArticoloBean articolo, UtenteBean user) throws SQLException {
		String controllaAcquisto = "SELECT * FROM utente U JOIN (SELECT * FROM ordine O JOIN contenuto C ON O.numOrdine=C.ordine WHERE C.articolo = ?) J "+
				"ON U.username = J.utente WHERE U.username=?";

		try(Connection con = ds.getConnection()){
			try(PreparedStatement ps = con.prepareStatement(controllaAcquisto)){	
				ps.setInt(1, articolo.getId());
				ps.setString(2, user.getUsername());

				ResultSet rs = ps.executeQuery();
				if(rs.next())
					return true;

				else 
					return false;
			}
		}
	}
	
	public synchronized void Alter(long id, ArticoloBean product) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String alterStatement = "UPDATE `tinyocean`.`articolo` "
				+ "SET `id` = ?, 'produttore' = ?, `nome` = ?, 'descrizione' = ?, "
				+ "`prezzo` = ?, `stock` = ?, `peso` = ?, 'altezza' = ?"
				+ "`larghezza` = ?, `lunghezza` = ?, `saldo` = ?  "
				+ " WHERE (`id` = ?);";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(alterStatement);
			preparedStatement.setLong(1, product.getId());
			preparedStatement.setString(2, product.getProduttore());
			preparedStatement.setString(3, product.getNome());
			preparedStatement.setString(4, product.getDescrizione());
			preparedStatement.setFloat(5, product.getPrezzo());
			preparedStatement.setInt(6, product.getStock());
			preparedStatement.setFloat(7, product.getPeso());
			preparedStatement.setFloat(8, product.getAltezza());
			preparedStatement.setFloat(9, product.getLarghezza());
			preparedStatement.setFloat(10, product.getLunghezza());
			preparedStatement.setInt(11, product.getSaldo());
			preparedStatement.setLong(12, id);
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
