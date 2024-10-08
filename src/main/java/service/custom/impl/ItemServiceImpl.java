package service.custom.impl;

import entity.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Item;
import model.OrderDetail;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.ItemDao;
import service.custom.ItemService;
import util.CrudUtil;
import util.DaoType;

import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    ItemDao itemDao = DaoFactory.getInstance().getDaoType(DaoType.ITEM);

    @Override
    public boolean addItem(Item item) {
        System.out.println("Service Layer" + item);
        ItemEntity entity = new ModelMapper().map(item, ItemEntity.class);
        itemDao.save(entity);
        return true;
    }

    @Override
    public boolean deleteItem(String itemCode) {
        itemDao.delete(itemCode);
        return true;
    }

    @Override
    public ItemEntity searchItem(String itemCode) {
        return itemDao.search(itemCode);
    }

    @Override
    public boolean updateItem(Item item) {
        ItemEntity entity = new ModelMapper().map(item, ItemEntity.class);
        itemDao.update(entity);
        return true;
    }

    @Override
    public ObservableList<ItemEntity> getAll() {
        return itemDao.getAll();
    }

    @Override
    public ObservableList<String> getItemCodes() {
        ObservableList<String> itemCodes = FXCollections.observableArrayList();
        ObservableList<ItemEntity> itemObservableList = getAll();
        itemObservableList.forEach(item -> itemCodes.add(item.getItemCode()));
        return itemCodes;
    }

    @Override
    public boolean updateStock(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            boolean updateStock = updateStock(orderDetail);
            if (!updateStock) {
                return false;
            }
        }
        return true;
    }

    public boolean updateStock(OrderDetail orderDetail) {
        String SQL = "UPDATE Item SET QtyOnHand = QtyOnHand - ? WHERE ItemCode = ?";
        try {
            return CrudUtil.execute(SQL,orderDetail.getQty(),orderDetail.getItemCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
