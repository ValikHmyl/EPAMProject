package by.khmyl.cafe.entity;

import java.math.BigDecimal;
import java.util.HashMap;


/**
 * Encapsulate an information about user's order.
 */
public class Order {
	private int id;
	private int userId;
	private String confirmDate;
	private String orderDate;
	private String status;
	private BigDecimal totalPrice;
	private HashMap<MenuItem, Integer> cart;

	/**
	 * Instantiates a new order.
	 *
	 * @param id order id
	 * @param userId user id that made order
 	 * @param confirmDate date which user chosen for pick up his order
	 * @param orderDate date when he made an order
	 * @param status status of order
	 * @param totalPrice total price of order with discount
	 * @param cart cart with menu items and amount 
	 */
	public Order(int id, int userId, String confirmDate, String orderDate, String status,
			BigDecimal totalPrice, HashMap<MenuItem, Integer> cart) {
		this.id = id;
		this.userId = userId;
		this.confirmDate = confirmDate;
		this.orderDate = orderDate;
		this.status = status;
		this.totalPrice = totalPrice;
		this.cart = cart;
			}

	/**
	 * Instantiates a new order.
	 */
	public Order() {

	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the confirm date.
	 *
	 * @return the confirm date
	 */
	public String getConfirmDate() {
		return confirmDate;
	}

	/**
	 * Sets the confirm date.
	 *
	 * @param confirmDate the new confirm date
	 */
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	/**
	 * Gets the order date.
	 *
	 * @return the order date
	 */
	public String getOrderDate() {
		return orderDate;
	}

	/**
	 * Sets the order date.
	 *
	 * @param orderDate the new order date
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the cart.
	 *
	 * @return the cart
	 */
	public HashMap<MenuItem, Integer> getCart() {
		return cart;
	}

	/**
	 * Sets the cart.
	 *
	 * @param cart the cart
	 */
	public void setCart(HashMap<MenuItem, Integer> cart) {
		this.cart = cart;
	}

	/**
	 * Gets the total price.
	 *
	 * @return the total price
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Sets the total price.
	 *
	 * @param totalPrice the new total price
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cart == null) ? 0 : cart.hashCode());
		result = prime * result + ((confirmDate == null) ? 0 : confirmDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
		result = prime * result + userId;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (cart == null) {
			if (other.cart != null)
				return false;
		} else if (!cart.equals(other.cart))
			return false;
		if (confirmDate == null) {
			if (other.confirmDate != null)
				return false;
		} else if (!confirmDate.equals(other.confirmDate))
			return false;
		if (id != other.id)
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (status != other.status)
			return false;
		if (totalPrice == null) {
			if (other.totalPrice != null)
				return false;
		} else if (!totalPrice.equals(other.totalPrice))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", confirmDate=" + confirmDate + ", orderDate=" + orderDate
				+ ", status=" + status + ", totalPrice=" + totalPrice + ", cart=" + cart + "]";
	}

}