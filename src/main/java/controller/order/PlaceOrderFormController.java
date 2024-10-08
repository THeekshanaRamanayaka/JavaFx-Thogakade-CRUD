package controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entity.CustomerEntity;
import entity.ItemEntity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.*;
import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.ItemService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    public JFXTextField txtOrderId;
    @FXML
    private JFXComboBox<String> cmbCustomerID;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    //@FXML
    //private Label lblOrderID;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<CartTM> tblAddToCart;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtItemDescription;

    @FXML
    private JFXTextField txtItemQty;

    @FXML
    private JFXTextField txtItemStock;

    @FXML
    private JFXTextField txtItemUnitPrice;

    ObservableList<CartTM> cartTM = FXCollections.observableArrayList();
    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
        loadCustomerIds();
        loadItemCodes();

        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newVal) -> {
            if (newVal != null) {
                searchCustomer(newVal);
            }
        });
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                searchItem(t1);
            }
        });
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = simpleDateFormat.format(date);

        lblDate.setText(dateNow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,e-> {
            LocalTime localTime = LocalTime.now();
            lblTime.setText(localTime.getHour() + " : " + localTime.getMinute() + " : " + localTime.getSecond());
        }),
            new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadCustomerIds() {
        cmbCustomerID.setItems(customerService.getCustomerIds());
    }

    private void searchCustomer(String customerID) {
        CustomerEntity customer = customerService.searchCustomer(customerID);
        txtCustomerName.setText(customer.getName());
        txtCustomerAddress.setText(customer.getAddress());
    }

    private void loadItemCodes() {
        cmbItemCode.setItems(itemService.getItemCodes());
    }

    private void searchItem(String itemCode) {
        ItemEntity item = itemService.searchItem(itemCode);
        txtItemDescription.setText(item.getDescription());
        txtItemStock.setText(item.getQtyOnHand());
        txtItemUnitPrice.setText(String.valueOf(item.getUnitPrice()));
    }

    @FXML
    void btnAddToCartOnAction() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        String itemCode = cmbItemCode.getValue();
        String description = txtItemDescription.getText();
        Integer qty = Integer.parseInt(txtItemQty.getText());
        Double unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        Double total = unitPrice*qty;

        if (Integer.parseInt(txtItemStock.getText()) < qty) {
            new Alert(Alert.AlertType.ERROR,"Invalid Qty");
        }else{
            cartTM.add(new CartTM(itemCode, description, qty, unitPrice, total));
            getNetTotal();
        }
        tblAddToCart.setItems(cartTM);
    }

    private void getNetTotal() {
        Double total = 0.0;

        for (CartTM cartTM1 : cartTM) {
            total+= cartTM1.getTotal();
        }
        lblNetTotal.setText(String.valueOf(total));
    }

    @FXML
    void btnPlaceOrderOnAction() {
        String orderId = txtOrderId.getText();
        LocalDate orderDate = LocalDate.now();
        String customerId = cmbCustomerID.getValue();

        List<OrderDetail> orderDetails = new ArrayList<>();
        cartTM.forEach(obj-> orderDetails.add(new OrderDetail(orderId, obj.getItemCode(), obj.getQty(), 0.0)));

        Order order = new Order(orderId, orderDate, customerId, orderDetails);
        try {
            new OrderController().placeOrder(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(order);
    }
}