package dao;

import basedao.BaseDAO;
import pojo.OrderItem;

public class OrderItemDAO extends BaseDAO<OrderItem> {

    public void addOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item values(0, ?, ?, ?)";
        int orderItemId = executeUpdate(sql, orderItem.getBook().getId(), orderItem.getCount(), orderItem.getOrder().getId());
        orderItem.setId(orderItemId);
    }

}
