package it.tinyOcean.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cart {
	ArticoloDAO model = new ArticoloDAO();
	private List<ItemOrder> products;

	public Cart() {
		products = new ArrayList<ItemOrder>();
	}

	public void addProduct(int itemID) throws SQLException {

		ItemOrder order;
		for (int i = 1; i <= products.size(); i++) {
			order = (ItemOrder) products.get(i - 1);
			if (order.getId() == itemID) {
				order.incrementNumItems();
				return;
			}
		}
		ItemOrder newOrder;
		ArticoloBean bean = model.doRetrieveByKey(itemID);
		newOrder = new ItemOrder(bean);

		products.add(newOrder);

	}

	public void deleteProduct(int Id) {
		ItemOrder order;
		for (int i = 0; i < products.size(); i++) {
			order = (ItemOrder) products.get(i);
			if (order.getId() == Id) {
				setNumOrdered(order.getId(), 0);
				return;
			}
		}
	}

	public synchronized void setNumOrdered(int Id, int numOrdered) {
		ItemOrder order;
		for (int i = 0; i < products.size(); i++) {
			order = (ItemOrder) products.get(i);
			if (order.getId() == Id) {
				if (numOrdered <= 0) {
					products.remove(i);
				} else {
					order.setNumItems(numOrdered);
				}
				return;
			}
		}
		ItemOrder newOrder;
		try {
			newOrder = new ItemOrder(model.doRetrieveByKey(Id));
		} catch (SQLException e) {

			e.printStackTrace();
			return;
		}
		products.add(newOrder);
	}

	public void EmptyCart() {
		products = new ArrayList<ItemOrder>();
	}

	public List<ItemOrder> getProducts() {
		return products;
	}

	public float getTotalCost() {
		float costo_totale = 0;
		for (int i = 0; i < products.size(); i++) {
			costo_totale += ((ItemOrder) products.get(i)).getTotalCost();

		}
		return costo_totale;
	}

	public boolean IsEmpty() {

		if (products.isEmpty()) {
			return true;
		}

		return false;

	}

}

