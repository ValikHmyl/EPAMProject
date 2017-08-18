package by.khmyl.cafe.tag;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import by.khmyl.cafe.entity.MenuItem;

@SuppressWarnings("serial")
public class cartAmountTag extends TagSupport {
	private HashMap<MenuItem, Integer> cart;

	public void setCart(HashMap<MenuItem, Integer> cart) {
		this.cart = cart;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			Integer amount = 0;
			if (cart != null) {
				for (Integer a : cart.values()) {
					amount += a;
				}
			}
			JspWriter out = pageContext.getOut();
			out.write(amount.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
}