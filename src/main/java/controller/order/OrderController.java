package controller.order;

import controller.item.ItemController;
import db.DBConnection;
import javafx.scene.control.Alert;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public void placeOrder(Order order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            String SQL = "INSERT INTO Orders VALUES(?,?,?)";
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1,order.getOrderId());
            preparedStatement.setObject(2,order.getOrderDate());
            preparedStatement.setObject(3,order.getCustomerId());
            boolean isOrderAdd = preparedStatement.executeUpdate()>0;
            if (isOrderAdd) {
                boolean isOrderDetailAdd = new OrderDetailController().addOrderDetail(order.getOrderDetails());
                if (isOrderDetailAdd) {
                    boolean isUpdateStock = ItemController.getInstance().updateStock(order.getOrderDetails());
                    if (isUpdateStock) {
                        connection.commit();
                        new Alert(Alert.AlertType.INFORMATION,"Order Placed !!").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Order Not Placed !!").show();
                    }
                }
            }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
