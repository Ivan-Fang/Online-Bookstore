package service;

import dao.CartItemDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import pojo.*;

import java.util.List;

public class OrderService {

    private OrderDAO orderDAO = null;
    private OrderItemDAO orderItemDAO = null;
    private CartItemDAO cartItemDAO = null;

    public void addOrder(Order order) {
        // 1. add an order
        orderDAO.addOrder(order);   // 執行完這一步後，order id 是有賦值的

        // 2. add order items
        // get the orderItem by encapsulating cartItem instead of getting it from order since we didn't set the orderItemList in it
        Cart cart = order.getOrderUser().getCart();
        for (CartItem cartItem : cart.getCartItemMap().values()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setCount(cartItem.getCount());
            orderItem.setOrder(order);
            orderItemDAO.addOrderItem(orderItem);
        }

        // 3. delete cart items
        for (CartItem cartItem : cart.getCartItemMap().values()) {
            cartItemDAO.delCartItem(cartItem);
        }
    }

    public List<Order> getOrderList(User user) {
        List<Order> orderList = orderDAO.getOrderList(user);

        for (Order order : orderList) {
            Integer orderCount = orderDAO.getOrderCount(order);
            order.setOrderCount(orderCount);
        }

        return orderList;
    }

}
