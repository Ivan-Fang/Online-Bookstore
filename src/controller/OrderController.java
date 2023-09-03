package controller;

import pojo.Order;
import pojo.User;
import service.OrderService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderController {

    private OrderService orderService = null;

    public String checkout(HttpSession session) {
        // create an order object and add it into database

        // 1. create an order object
        Order order = new Order();

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo = UUID.randomUUID().toString() + "-" + sdf.format(now);  // 32 digits global unique code

        order.setOrderNo(orderNo);
        order.setOrderDate(now);

        User user = (User) session.getAttribute("currUser");

        order.setOrderUser(user);
        order.setOrderPrice(user.getCart().getTotalPrice());
        order.setOrderStatus(0);

        // 2. add it into database
        orderService.addOrder(order);

        return "index";
    }

    public String getOrderList(HttpSession session) {
        User user = (User) session.getAttribute("currUser");

        List<Order> orderList = orderService.getOrderList(user);

        for (Order order : orderList) {
            System.out.println(order);
        }

        user.setOrderList(orderList);

        session.setAttribute("currUser", user);

        return "order/order";
    }

}
