package it.tinyOcean.model;

import java.sql.SQLException;
import java.util.Collection;

import it.tinyOcean.model.ArticoloBean;

public interface ArticoloModel {
	public void doSave(ArticoloBean product) throws SQLException;

	public boolean doDelete(int code) throws SQLException;

	public ArticoloBean doRetrieveByKey(int code) throws SQLException;
	
	public Collection<ArticoloBean> doRetrieveAll(String order) throws SQLException;
}
