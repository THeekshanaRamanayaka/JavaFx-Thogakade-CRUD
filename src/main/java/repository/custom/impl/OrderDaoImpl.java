package repository.custom.impl;

import javafx.collections.ObservableList;
import model.Order;
import repository.custom.OrderDao;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(Order order) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<Order> getAll() {
        return null;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public Order search(String id) {
        return null;
    }
}
