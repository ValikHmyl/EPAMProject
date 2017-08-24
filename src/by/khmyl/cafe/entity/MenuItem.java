package by.khmyl.cafe.entity;

import java.math.BigDecimal;

/**
 * Encapsulate an information from database about menu item.
 */
public class MenuItem {
	private int id;
	private String name;
	private BigDecimal price;
	private int categoryId;
	private String portion;
	private String imageName;

	/**
	 * Instantiates a new menu item.
	 */
	public MenuItem() {
	}

	/**
	 * Instantiates a new menu item.
	 *
	 * @param id menu id
	 * @param name name of menu item
	 * @param price price
	 * @param categoryId category id
	 * @param portion portion
	 * @param imageName image name 
	 */
	public MenuItem(int id, String name, BigDecimal price, int categoryId, String portion, String imageName) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.categoryId = categoryId;
		this.portion = portion;
		this.imageName = imageName;
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * Gets the category id.
	 *
	 * @return the category id
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the category id.
	 *
	 * @param categoryId the new category id
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Gets the portion.
	 *
	 * @return the portion
	 */
	public String getPortion() {
		return portion;
	}

	/**
	 * Sets the portion.
	 *
	 * @param portion the new portion
	 */
	public void setPortion(String portion) {
		this.portion = portion;
	}

	/**
	 * Gets the image name.
	 *
	 * @return the image name
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * Sets the image name.
	 *
	 * @param imageName the new image name
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryId;
		result = prime * result + id;
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((portion == null) ? 0 : portion.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		MenuItem other = (MenuItem) obj;
		if (categoryId != other.categoryId)
			return false;
		if (id != other.id)
			return false;
		if (imageName == null) {
			if (other.imageName != null)
				return false;
		} else if (!imageName.equals(other.imageName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (portion == null) {
			if (other.portion != null)
				return false;
		} else if (!portion.equals(other.portion))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", name=" + name + ", price=" + price + ", categoryId=" + categoryId
				+ ", portion=" + portion + ", imageName=" + imageName + "]";
	}
	
}