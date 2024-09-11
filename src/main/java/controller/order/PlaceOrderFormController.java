package controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private JFXComboBox<?> cmbCustomerID;

    @FXML
    private JFXComboBox<?> cmbItemCode;

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
    private Label lblOrderID;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<?> tblAddToCart;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
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

    @FXML
    void btnAddToCartOnAction() {

    }

    @FXML
    void btnPlaceOrderOnAction() {

    }
}
