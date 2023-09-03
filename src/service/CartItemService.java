package service;

import dao.CartItemDAO;
import pojo.Book;
import pojo.Cart;
import pojo.CartItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartItemService {

    private CartItemDAO cartItemDAO = null;
    private BookService bookService = null;

    public void addCartItem(CartItem cartItem) {
        cartItemDAO.addCartItem(cartItem);
    }

    public void updateCartItem(CartItem cartItem) {
        cartItemDAO.updateCartItem(cartItem);
    }

    public void addOrUpdateCartItem(CartItem cartItem, Cart cart) {
        // if the cart doesn't have the book, or the cart doesn't even exist -> addCartItem
        // else -> updateCartItem
        if (cart != null) {
            Map<Integer, CartItem> cartItemMap = cart.getCartItemMap();
            if (cartItemMap == null) {
                cartItemMap = new HashMap<>();
            }
            if (cartItemMap.containsKey(cartItem.getBook().getId())) {
                CartItem cartItemTemp = cart.getCartItemMap().get(cartItem.getBook().getId());
                cartItemTemp.setCount(cartItemTemp.getCount() + 1);
                updateCartItem(cartItemTemp);
            } else {
                addCartItem(cartItem);
            }
        } else {
            addCartItem(cartItem);
        }
    }

    public List<CartItem> getCartItemList(Integer userId) {
        // 1. get cart item list without book details (only book id)
        List<CartItem> cartItemList = cartItemDAO.getCartItemList(userId);

        // 2. set book details & subTotalPrice
        for (CartItem cartItem : cartItemList) {
            Book book = bookService.getBook(cartItem.getBook().getId());
            cartItem.setBook(book);
            cartItem.getSubTotalPrice();
        }

        return cartItemList;
    }

    public Cart getCart(Integer userId) {
        // use getCartItemList defined in CartItemService instead of CartItemDAO
        List<CartItem> cartItemList = getCartItemList(userId);

        // set the cart with cart item list
        Map<Integer, CartItem> cartItemMap = new HashMap<>();
        for (CartItem cartItem : cartItemList) {
            cartItemMap.put(cartItem.getBook().getId(), cartItem);
        }
        Cart cart = new Cart();
        cart.setCartItemMap(cartItemMap);

        // set totalCount and totalPrice
        cart.getTotalCount();
        cart.getTotalPrice();

        return cart;
    }

}
