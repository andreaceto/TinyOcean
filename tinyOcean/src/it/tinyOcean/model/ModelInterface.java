package it.tinyOcean.model;


import java.sql.SQLException;
import java.util.Collection;

public interface ModelInterface<T> {
	public void doSave(T bean) throws SQLException;

	public boolean doDelete(String arg) throws SQLException;

	public T doRetrieveByKey(String arg) throws Exception;
	
	public Collection<T> doRetrieveAll(String order) throws Exception;
	
	public void doUpdate(T bean) throws SQLException;
}
