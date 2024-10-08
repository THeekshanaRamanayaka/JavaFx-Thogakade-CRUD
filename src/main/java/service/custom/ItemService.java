package service.custom;

import entity.ItemEntity;
import javafx.collections.ObservableList;
import model.Customer;
import model.Item;
import model.OrderDetail;
import service.SuperService;

import java.util.List;

public interface ItemService extends SuperService {
    boolean addItem(Item item);
    boolean deleteItem(String itemCode);
    ItemEntity searchItem(String itemCode);
    boolean updateItem(Item item);
    ObservableList<ItemEntity> getAll();
    ObservableList<String> getItemCodes();
    boolean updateStock(List<OrderDetail> orderDetails);
}
