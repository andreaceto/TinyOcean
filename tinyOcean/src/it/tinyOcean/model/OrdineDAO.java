package it.tinyOcean.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class OrdineDAO {
	private static DataSource ds;
	static ResultSet rs = null;
	static int numordine;
	private static final String TABLE_NAME = "ordine";
	private static final String TABLE_NAME2 = "contenuto";
	private static final int IVA = 22;

	public OrdineDAO() {
		super();

	}

	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/tinyocean");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	public synchronized void doSave(UtenteBean bean, Object indirizzo, Object pagamento, Cart cart) {

		PreparedStatement preparedStatement = null;

		String indirizzoSpedizione = String.valueOf(indirizzo);
		String metodoPagamento = String.valueOf(pagamento);
		String insertQuery = "INSERT INTO " + TABLE_NAME + " ( utente, costoTot, dataOrdine, "
				+ "dataSpedizione, indirizzoSpedizione, metodoPagamento )" + " VALUES(?,?,?,?,?,?)";

		// connect to DB
		Connection connection = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, bean.getUsername());
			preparedStatement.setFloat(2, cart.getTotalCost());
			preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
			preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
			preparedStatement.setString(5, indirizzoSpedizione);
			preparedStatement.setString(6, metodoPagamento);
			preparedStatement.executeUpdate();

		}

		catch (Exception ex) {
			System.out.println("Inserimento ordine fallito " + ex);
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

		try {
			connection = ds.getConnection();
			int autoIncKeyFromFunc = -1;
			preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				autoIncKeyFromFunc = rs.getInt(1);
			} else {
				// throw an exception from here
			}
			for (ItemOrder product : cart.getProducts()) {
				preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME2 + " VALUES (?,?,?,?,?,?)");
				preparedStatement.setLong(1, autoIncKeyFromFunc);
				preparedStatement.setInt(2, product.getId());
				preparedStatement.setString(3, product.getNome());
				preparedStatement.setFloat(4, product.getPrezzo());
				preparedStatement.setInt(5, IVA);
				preparedStatement.setInt(6, product.getNumItems());
				preparedStatement.executeUpdate();

			}
		} catch (SQLException e) {
			System.out.print("inserimento ordine fallito");
			e.printStackTrace();
		}

	}

	public synchronized OrdineBean getOrderById(String OrderID) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		OrdineBean bean = new OrdineBean();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE numOrdine = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, Integer.valueOf(OrderID));

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setNumOrdine(rs.getLong("numOrdine"));
				bean.setUtente(rs.getString("utente"));
				bean.setCostoTot(rs.getFloat("costoTot"));
				bean.setDataOrdine(rs.getDate("dataOrdine").toLocalDate());
				bean.setDataSpedizione(rs.getDate("dataSpedizione").toLocalDate());
				bean.setIndirizzoSped(rs.getString("indirizzoSpedizione"));
				bean.setMetodoPagamento(rs.getString("metodoPagamento"));
				
				
				
			}
		}

		catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.out.println("Error:" + e.getMessage());
			}

		}

		return bean;

	}

	public synchronized List<OrdineBean> getAllOrdersByUser(UtenteBean user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		List<OrdineBean> orders = new LinkedList<OrdineBean>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE utente = ? ";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, user.getUsername());
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrdineBean bean = new OrdineBean();
				bean.setNumOrdine(rs.getLong("numOrdine"));
				bean.setUtente(rs.getString("utente"));
				bean.setCostoTot(rs.getFloat("costoTot"));
				bean.setDataOrdine(rs.getDate("dataOrdine").toLocalDate());
				bean.setDataSpedizione(rs.getDate("dataSpedizione").toLocalDate());
				bean.setIndirizzoSped(rs.getString("indirizzoSpedizione"));
				bean.setMetodoPagamento(rs.getString("metodoPagamento"));
				orders.add(bean);
			}
		} catch (Exception ex) {
			System.out.println("Errore,impossibile recuperare ordini " + ex);
		}

		finally {

			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.out.println("Errore,impossibile recuperare ordini " + e);
			}

		}

		return orders;
	}

	public synchronized List<OrdineBean> getAllOrders() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		List<OrdineBean> orders = new LinkedList<OrdineBean>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrdineBean bean = new OrdineBean();
				bean.setNumOrdine(rs.getLong("numOrdine"));
				bean.setUtente(rs.getString("utente"));
				bean.setCostoTot(rs.getFloat("costoTot"));
				bean.setDataOrdine(rs.getDate("dataOrdine").toLocalDate());
				bean.setDataSpedizione(rs.getDate("dataSpedizione").toLocalDate());
				bean.setIndirizzoSped(rs.getString("indirizzoSpedizione"));
				bean.setMetodoPagamento(rs.getString("metodoPagamento"));
				orders.add(bean);
			}
		} catch (Exception ex) {
			System.out.println("Inserimento ordine fallito " + ex);
		}

		finally {

			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.out.println("Errore,impossibile recuperare ordini " + e);
			}

		}

		return orders;

	}

	public synchronized List<OrdineBean> getAllOrders(LocalDate startdate, LocalDate enddate) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		List<OrdineBean> orders = new LinkedList<OrdineBean>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME +" WHERE dataOrdine BETWEEN  CAST(? AS DATE) AND CAST(? AS DATE)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setDate(1,Date.valueOf(startdate) );
			preparedStatement.setDate(2,Date.valueOf(enddate) );
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrdineBean bean = new OrdineBean();
				bean.setNumOrdine(rs.getLong("numOrdine"));
				bean.setUtente(rs.getString("utente"));
				bean.setCostoTot(rs.getFloat("costoTot"));
				bean.setDataOrdine(rs.getDate("dataOrdine").toLocalDate());
				bean.setDataSpedizione(rs.getDate("dataSpedizione").toLocalDate());
				bean.setIndirizzoSped(rs.getString("indirizzoSpedizione"));
				bean.setMetodoPagamento(rs.getString("metodoPagamento"));
				orders.add(bean);
			}
		} catch (Exception ex) {
			System.out.println("Inserimento ordine fallito " + ex);
		}

		finally {

			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.out.println("Errore,impossibile recuperare ordini " + e);
			}

		}

		return orders;
	}

	public synchronized LinkedList<ContenutoBean> getContentByOrderId(String OrderID) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		LinkedList<ContenutoBean> products = new LinkedList<ContenutoBean>();

		String selectSQL = "SELECT nomeArticolo, prezzoAcquisto, iva, quantita FROM "
							+ TABLE_NAME2 +" WHERE numOrdine = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, Integer.valueOf(OrderID));

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ContenutoBean bean = new ContenutoBean();
				
				bean.setNomeArt(rs.getString("nomeArticolo"));
				bean.setPrezzoAcq(rs.getFloat("prezzoAcquisto"));
				bean.setIva(rs.getInt("iva"));
				bean.setQuantita(rs.getInt("quantita"));
				products.add(bean);

			}
		}

		catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.out.println("Error:" + e.getMessage());
			}

		}

		return products;
	}

	public List<OrdineBean> getAllOrders(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		List<OrdineBean> orders = new LinkedList<OrdineBean>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE utente = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrdineBean bean = new OrdineBean();
				bean.setNumOrdine(rs.getLong("numOrdine"));
				bean.setUtente(rs.getString("utente"));
				bean.setCostoTot(rs.getFloat("costoTot"));
				bean.setDataOrdine(rs.getDate("dataOrdine").toLocalDate());
				bean.setDataSpedizione(rs.getDate("dataSpedizione").toLocalDate());
				bean.setIndirizzoSped(rs.getString("indirizzoSpedizione"));
				bean.setMetodoPagamento(rs.getString("metodoPagamento"));
				orders.add(bean);
			}
		} catch (Exception ex) {
			System.out.println("Inserimento ordine fallito " + ex);
		}

		finally {

			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.out.println("Errore,impossibile recuperare ordini " + e);
			}

		}

		return orders;
	}

}
