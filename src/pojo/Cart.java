package pojo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {

    private Map<Integer, CartItem> cartItemMap = new HashMap<>();   // key: book id, value: cartItem
    private Double totalPrice;          // total price of books in the cart
    private Integer totalCategory;      // total category of books in the cart
    private Integer totalCount;         // total count of books in the cart

    public Map<Integer, CartItem> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<Integer, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }

    public Double getTotalPrice() {
        BigDecimal bigDecimalTotalPrice = new BigDecimal("0");

        if (cartItemMap != null && !cartItemMap.isEmpty()) {
            Set<Map.Entry<Integer, CartItem>> entries = cartItemMap.entrySet();
            for (Map.Entry<Integer, CartItem> entry : entries) {
                CartItem cartItem = entry.getValue();

                BigDecimal bigDecimalBookPrice = new BigDecimal("" + cartItem.getBook().getPrice());
                BigDecimal bigDecimalCartItemCount = new BigDecimal("" + cartItem.getCount());
                bigDecimalTotalPrice = bigDecimalTotalPrice.add(bigDecimalBookPrice.multiply(bigDecimalCartItemCount));
            }
        }

        totalPrice = bigDecimalTotalPrice.doubleValue();
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalCategory() {
        totalCategory = 0;
        if (cartItemMap != null && !cartItemMap.isEmpty()) {
            totalCategory = cartItemMap.size();
        }

        return totalCategory;
    }

    public void setTotalCategory(Integer totalCategory) {
        this.totalCategory = totalCategory;
    }

    public Integer getTotalCount() {
        totalCount = 0;
        if (cartItemMap != null) {
            for (CartItem cartItem : cartItemMap.values()) {
                totalCount += cartItem.getCount();
            }
        }

        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
