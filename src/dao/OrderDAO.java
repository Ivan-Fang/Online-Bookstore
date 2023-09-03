package dao;

import basedao.BaseDAO;
import pojo.Order;
import pojo.User;

import java.math.BigDecimal;
import java.util.List;

public class OrderDAO extends BaseDAO<Order> {

    public void addOrder(Order order) {
        String sql = "insert into t_order values(0, ?, ?, ?, ?, ?)";
        int orderId = executeUpdate(sql, order.getOrderNo(), order.getOrderDate(), order.getOrderUser().getId(), order.getOrderPrice(), order.getOrderStatus());
        order.setId(orderId);
    }

    public List<Order> getOrderList(User user) {
        String sql = "select * from t_order where orderUser=?";
        return executeQuery(sql, user.getId());
    }

    public Integer getOrderCount(Order order) {
        String sql = "select sum(t3.count) as orderCount\n" +
                     "from (select t1.id, t2.count, t1.orderUser from t_order t1 inner join t_order_item t2 on (t1.id = t2.order)) t3\n" +
                     "where t3.id=?\n" +
                     "group by t3.id";

        return ((BigDecimal) executeComplexQuery(sql, order.getId())[0]).intValue();
    }

}
