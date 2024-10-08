package controller.item;

import com.jfoenix.controls.JFXTextField;
import entity.ItemEntity;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import service.ServiceFactory;
import service.custom.ItemService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemViewFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<ItemEntity> tblViewItemForm;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblViewItemForm.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues(newValue);
            }
        }));
        loadTable();
    }

    private void setTextToValues(ItemEntity newValue) {
        txtItemCode.setText(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtPackSize.setText(newValue.getPackSize());
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQtyOnHand.setText(newValue.getQtyOnHand());
    }

    @FXML
    void btnReloadOnAction() {
        loadTable();
    }

    @FXML
    void btnAddOnAction() {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                txtQtyOnHand.getText()
        );
        if (itemService.addItem(item)) {
            new Alert(Alert.AlertType.INFORMATION,"Item Added Successful !!").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Item Not Added :(").show();
        }
        loadTable();
    }

    @FXML
    void btnDeleteOnAction() {
        if (itemService.deleteItem(txtItemCode.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Item deleted successfully !!").show();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
        loadTable();
    }

    @FXML
    void btnSearchOnAction() {
        ItemEntity item = itemService.searchItem(txtItemCode.getText());
        setTextToValues(item);
    }

    @FXML
    void btnUpdateOnAction() {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                txtQtyOnHand.getText()
        );
        if (itemService.updateItem(item)) {
            new Alert(Alert.AlertType.INFORMATION,"Item Update Successful !!").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Item Not updated :(").show();
        }
        loadTable();
    }

    private void loadTable(){
        ObservableList<ItemEntity> itemObservableList = itemService.getAll();
        tblViewItemForm.setItems(itemObservableList);
    }
}
