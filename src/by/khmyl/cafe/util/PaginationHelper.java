package by.khmyl.cafe.util;

import java.util.ArrayList;

public class PaginationHelper<T> {
	private ArrayList<T> items;
	private int amount;

	public PaginationHelper() {
		items = new ArrayList<>();
	}

	public PaginationHelper(ArrayList<T> items, int amount) {
		this.items = items;
		this.amount = amount;
	}

	public ArrayList<T> getItems() {
		return items;
	}

	public void setItems(ArrayList<T> items) {
		this.items = items;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
