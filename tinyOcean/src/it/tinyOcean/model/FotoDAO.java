package it.tinyOcean.model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FotoDAO {
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

	public synchronized List<FotoBean> getPhotos(ProductBean product) {
		List<FotoBean> photos = new LinkedList<FotoBean>();
		PreparedStatement preparedStatement = null;
		String SearchQuery = "Select foto.* FROM foto Join articolo ON " + "foto.articolo=articolo.id "
				+ " WHERE articolo.id = " + product.getId();

		Connection connection = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(SearchQuery);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				FotoBean bean = new FotoBean();
				Blob blob = rs.getBlob("foto");
				InputStream inputStream = blob.getBinaryStream();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				byte[] imageBytes = outputStream.toByteArray();

				String base64Image = Base64.getEncoder().encodeToString(imageBytes);

				inputStream.close();
				outputStream.close();
				bean.setBase64img(base64Image);
				bean.setId(rs.getInt("id"));
				bean.setArticolo(rs.getInt("articolo"));
				photos.add(bean);

			}
connection.close();
rs.close();
		}

		catch (Exception ex) {
			System.out.println(ex);
		}

		return photos;

	}

}

