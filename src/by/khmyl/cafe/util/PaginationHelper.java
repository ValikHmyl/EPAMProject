package by.khmyl.cafe.util;

import java.util.ArrayList;

/**
 * Class that stored list of items to show them on page and total amount of them in database.
 *
 * @param <T> the generic type
 */
public class PaginationHelper<T> {
	private ArrayList<T> items;
	private int amount;

	/**
	 * Instantiates a new pagination helper.
	 */
	public PaginationHelper() {
		items = new ArrayList<>();
	}

	/**
	 * Instantiates a new pagination helper.
	 *
	 * @param items the items
	 * @param amount the amount
	 */
	public PaginationHelper(ArrayList<T> items, int amount) {
		this.items = items;
		this.amount = amount;
	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public ArrayList<T> getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(ArrayList<T> items) {
		this.items = items;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
