package it.tinyOcean.model;

import java.sql.Date;
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

public class MetodoPagamentoDAO {

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
	private static final String TABLE_NAME2 = "fatturazione";
	private static final String TABLE_NAME3 = "metodo_pagamento";


	public synchronized List<MetodoPagamentoBean> getPaymentMethods(UtenteBean user) {
		List<MetodoPagamentoBean> Methods = new LinkedList<MetodoPagamentoBean>();
		PreparedStatement preparedStatement = null;
		String SearchQuery = "Select " + TABLE_NAME3 + ".*" + " FROM " + TABLE_NAME + " Join " + TABLE_NAME2
				+ " ON username=utente " + " Join " + TABLE_NAME3 + " ON " + TABLE_NAME2 + ".numCarta = " + TABLE_NAME3 + ".numCarta"
				+" WHERE "+TABLE_NAME+".username = ?";	
		Connection connection = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(SearchQuery);
			preparedStatement.setString(1, user.getUsername());
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				MetodoPagamentoBean bean = new MetodoPagamentoBean();
				bean.setNumCarta(rs.getLong("numCarta"));
				bean.setTipo(rs.getInt("tipo"));
				bean.setScadenza(rs.getDate("scadenza").toLocalDate());
				bean.setTitolare(rs.getString("titolare"));
				bean.setIndirizzoFatt(rs.getString("indirizzoFatturazione"));
				bean.setPredefinito(rs.getBoolean("predefinito"));
				Methods.add(bean);

			}

		}

		catch (Exception ex) {
			
		}

		return Methods;

	}

	public synchronized void doSave(UtenteBean user, MetodoPagamentoBean PaymentMethod) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + TABLE_NAME3
				+ " (numCarta,tipo,scadenza,titolare,indirizzoFatt,predefinito) VALUES (?,?,?,?,?,?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setLong(1, PaymentMethod.getNumCarta());
			preparedStatement.setInt(2, PaymentMethod.getRawTipo());
			preparedStatement.setDate(3, Date.valueOf(PaymentMethod.getScadenza()));
			preparedStatement.setString(4, PaymentMethod.getTitolare());
			preparedStatement.setString(5, PaymentMethod.getIndirizzoFatt());
			preparedStatement.setBoolean(6, PaymentMethod.isPredefinito());
			
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			if (ex.getErrorCode() == 1062) {
				System.out.println("MetodoPagamento già esistente nel DB " + ex);
				try {
					preparedStatement.close();

					insertSQL = "INSERT INTO " + TABLE_NAME2 + " (utente, numCarta) VALUES (?, ?)";
					preparedStatement = connection.prepareStatement(insertSQL);
					preparedStatement.setString(1, user.getUsername());
					preparedStatement.setLong(2, PaymentMethod.getNumCarta());
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
				System.out.println("Inserimento metodo di pagamento  fallito " + ex);
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
			insertSQL = "INSERT INTO " + TABLE_NAME2 + " (utente,numCarta) VALUES (?, ?)";
			connection = ds.getConnection();
			
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setLong(2, PaymentMethod.getNumCarta());
			
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.out.print("could not update table " + TABLE_NAME2 + " to reflect changes");
			System.out.print(e);
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
