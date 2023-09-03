package controller;

import com.google.gson.Gson;
import pojo.Book;
import pojo.Cart;
import pojo.CartItem;
import pojo.User;
import service.CartItemService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CartController {

    private CartItemService cartItemService = null;

    public String index(HttpSession session) {
        // renew the cart of a user and go to cart/cart.html
        User user = (User) session.getAttribute("currUser");
        user.setCart(cartItemService.getCart(user.getId()));
        session.setAttribute("currUser", user);

        return "cart/cart";
    }

    public String addToCart(Integer bookId, HttpSession session) {

        // if the book has been placed in the user's cart, cartItem.count +1
        // else, create a new cartItem and set cartItem.count to 1
        User user = (User) session.getAttribute("currUser");
        CartItem cartItem = new CartItem(new Book(bookId), 1, user);

        cartItemService.addOrUpdateCartItem(cartItem, user.getCart());

        return "redirect:cart.do";
    }

    public String editCartItem(Integer cartItemId, Integer newCount) {
        cartItemService.updateCartItem(new CartItem(cartItemId, newCount));
        return "";
    }

    public String getCartInfo(HttpSession session) {
        User user = (User) session.getAttribute("currUser");
        user.setCart(cartItemService.getCart(user.getId()));
        session.setAttribute("currUser", user);

        Cart cart = user.getCart();
        Gson gson = new Gson();
        String cartStr = gson.toJson(cart);

        return "json:" + cartStr;
    }

}
