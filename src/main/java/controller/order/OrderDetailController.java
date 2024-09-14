package controller.order;

import model.OrderDetail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    public boolean addOrderDetail(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            boolean isAdd = AddOrderDetail(orderDetail);
            if (!isAdd) {
                return false;
            }
        }
        return true;
    }

    private boolean AddOrderDetail(OrderDetail orderDetail) {
        String SQL = "INSERT INTO OrderDetail VALUES(?,?,?,?)";
        try {
            return CrudUtil.execute(SQL,
                    orderDetail.getOrderId(),
                    orderDetail.getItemCode(),
                    orderDetail.getQty(),
                    orderDetail.getDiscount()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
