package dao;

import basedao.BaseDAO;
import pojo.Cart;
import pojo.CartItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartItemDAO extends BaseDAO<CartItem> {

    public void addCartItem(CartItem cartItem) {
        String sql = "insert into t_cart_item values(0, ?, ?, ?)";
        executeUpdate(sql, cartItem.getBook().getId(), cartItem.getCount(), cartItem.getUser().getId());
    }

    public void updateCartItem(CartItem cartItem) {
        String sql = "update t_cart_item set count=? where id=?";
        executeUpdate(sql, cartItem.getCount(), cartItem.getId());
    }

    public List<CartItem> getCartItemList(Integer userId) {
        String sql = "select * from t_cart_item where user=?";
        return executeQuery(sql, userId);
    }

    public void delCartItem(CartItem cartItem) {
        String sql = "delete from t_cart_item where id=?";
        executeUpdate(sql, cartItem.getId());
    }

}
