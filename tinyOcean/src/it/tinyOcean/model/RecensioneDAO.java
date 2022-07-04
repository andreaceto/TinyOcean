package it.tinyOcean.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import it.tinyOcean.model.ArticoloBean;
import it.tinyOcean.model.RecensioneBean;

public class RecensioneDAO implements ModelInterface<RecensioneBean> {
	private static final String TABLE_NAME = "recensione";
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
	
	@Override
	public synchronized void doSave(RecensioneBean bean) throws SQLException {
		String controllaAcquisto = "SELECT * FROM utente U JOIN (SELECT * FROM ordine O JOIN contenuto C ON O.numOrdine=C.ordine WHERE C.articolo = ?) J "+
									"ON U.username = J.utente WHERE U.username=?";
		
		try(Connection con = ds.getConnection()){
			try(PreparedStatement ps = con.prepareStatement(controllaAcquisto)){	
				ps.setInt(1, bean.getArticolo().getId());
				ps.setString(2, bean.getUtente().getUsername());

				ps.executeQuery();
			}
		}
		

		String sqlInsert = "INSERT INTO " + TABLE_NAME + " (utente, articolo, voto, commento, dataPubblicazione) VALUES (?,?,?,?,?)";
		try(Connection con = ds.getConnection()){
			try(PreparedStatement ps = con.prepareStatement(sqlInsert)){	
				
				ps.setString(1, bean.getUtente().getUsername());
				ps.setInt(2, bean.getArticolo().getId());
				ps.setInt(3, bean.getVoto());
				ps.setString(4, bean.getCommento());
				ps.setDate(5, Date.valueOf(LocalDate.now()));

				ps.execute();
			}
		}
		
	}

	@Override
	public boolean doDelete(String arg) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	  @Override
	  public RecensioneBean doRetrieveByKey(String arg) throws Exception {
		  return null; //non implementato
	  }
	 

	
	  @Override
	  public Collection<RecensioneBean> doRetrieveAll(String order) throws Exception {
		  return null; //non implementato
	  }
	 
	
	public synchronized ArrayList<RecensioneBean> getRecensioniByProduct(ArticoloBean prod) throws Exception{
		String sql = "SELECT * FROM "+ TABLE_NAME + " WHERE articolo = ?";
		ArrayList<RecensioneBean> lista = new ArrayList<>();
		RecensioneBean result = new RecensioneBean();
		
		try(Connection conn = ds.getConnection()){
			try(PreparedStatement statement = conn.prepareStatement(sql)){
				statement.setInt(1, prod.getId());
				ResultSet rs = statement.executeQuery();
				
				ArticoloDAO articoloDAO = new ArticoloDAO();
				UtenteDAO utenteDAO = new UtenteDAO();
				
				while(rs.next()) {
					
					result.setUtente(utenteDAO.doRetrieveByKey(rs.getString("utente")));
					result.setArticolo(articoloDAO.doRetrieveByKey(rs.getInt("articolo")));
					result.setVoto(rs.getInt("voto"));
					result.setCommento(rs.getString("commento"));
					result.setDataPubblicazione(rs.getDate("dataPubblicazione").toLocalDate());
					lista.add(result);
					
				}
			}
		}
		
		Set<RecensioneBean> set = new HashSet<>(lista);
		lista.clear();
		lista.addAll(set);
		return lista;

	}

	@Override
	public void doUpdate(RecensioneBean bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public static Double getVotoMedio(ArticoloBean articolo) throws Exception {
		String sql = "SELECT AVG(voto) AS media FROM recensione WHERE articolo=?";
		double result = 0;
		
		try(Connection conn = ds.getConnection()){
			try(PreparedStatement statement = conn.prepareStatement(sql)){
				statement.setInt(1, articolo.getId());
				ResultSet rs = statement.executeQuery();
				
				if(rs.next()) {
					result = rs.getDouble("media");
				}
			}
		}
		
		return result;
	}
	
	public static boolean isAlreadyReviewed(ArticoloBean articolo, UtenteBean user) throws SQLException {
		String controllaRecensione = "SELECT * FROM "+TABLE_NAME+" WHERE articolo = ? AND utente = ?";

		try(Connection con = ds.getConnection()){
			try(PreparedStatement ps = con.prepareStatement(controllaRecensione)){	
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
	


}

