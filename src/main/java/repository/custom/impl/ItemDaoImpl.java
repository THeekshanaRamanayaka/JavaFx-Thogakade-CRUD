package repository.custom.impl;

import entity.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Item;
import repository.custom.ItemDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDaoImpl implements ItemDao {

    @Override
    public boolean save(ItemEntity item) {
        String SQL ="INSERT INTO Item VALUES(?,?,?,?,?)";
        try {
            return CrudUtil.execute(SQL,
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            );
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Error : "+e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        String SQL = "DELETE FROM Item WHERE ItemCode=?";
        try {
            return CrudUtil.execute(SQL, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemEntity search(String id) {
        String SQL = "SELECT * FROM Item WHERE ItemCode = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, id);
            if (resultSet.next()) {
                return new ItemEntity(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getString("QtyOnHand")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(ItemEntity item) {
        String SQL = "UPDATE Item SET Description = ?, PackSize = ?, UnitPrice = ?, QtyOnHand = ? WHERE ItemCode = ?";
        try {
            return CrudUtil.execute(SQL,
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getItemCode()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<ItemEntity> getAll() {
        ObservableList<ItemEntity> itemObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM Item";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()){
                itemObservableList.add(new ItemEntity(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getString("QtyOnHand")
                ));
            }
            return itemObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
