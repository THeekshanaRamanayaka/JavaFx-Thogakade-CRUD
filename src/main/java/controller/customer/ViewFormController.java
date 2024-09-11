package controller.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private DatePicker dateDob;

    @FXML
    private TableView<Customer> tblResult;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    CustomerService service = CustomerController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Ms");
        cmbTitle.setItems(titles);
        tblResult.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues(newValue);
            }
        }));
        loadTable();
    }

    private void setTextToValues(Customer newValue) {
        txtId.setText(newValue.getId());
        cmbTitle.setAccessibleText(newValue.getTitle());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        dateDob.setAccessibleText(String.valueOf(newValue.getDob()));
        txtSalary.setText(String.valueOf(newValue.getSalary()));
        txtCity.setText(newValue.getCity());
        txtProvince.setText(newValue.getProvince());
        txtPostalCode.setText(newValue.getPostalCode());
    }

    @FXML
    void btnReloadOnAction() {
        loadTable();
    }

    @FXML
    void btnAddOnAction() {
        Customer customer = new Customer(
                txtId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),txtProvince.getText(),
                txtPostalCode.getText()
        );
        if(service.addCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added Successful !!").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Customer Not Added :(").show();
        }
        loadTable();
    }

    @FXML
    void btnDeleteOnAction() {
        if (service.deleteCustomer(txtId.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Customer deleted successfully !!").show();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
        loadTable();
    }

    @FXML
    void btnSearchOnAction() {

    }

    @FXML
    void btnUpdateOnAction() {

    }

    private void loadTable(){
        ObservableList<Customer> customerObservableList = service.getAll();
        tblResult.setItems(customerObservableList);
    }
}
