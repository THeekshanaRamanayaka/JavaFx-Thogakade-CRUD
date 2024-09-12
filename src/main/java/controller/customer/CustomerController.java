package controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import util.CrudUtil;

import java.sql.*;

public class CustomerController implements CustomerService {

    private static CustomerController instance;
    private CustomerController() {}
    public static CustomerController getInstance() {
        return instance == null ? instance = new CustomerController() : instance;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        String SQL ="INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            return CrudUtil.execute(SQL,
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            );
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Error : "+e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        String SQL = "DELETE FROM Customer WHERE CustID = ?";
        try {
            return CrudUtil.execute(SQL, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM Customer";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()){
                customerObservableList.add(new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("postalCode")
                ));
            }
            return customerObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String SQL = "UPDATE Customer SET CustTitle = ?, CustName = ?, DOB = ?, salary = ?, CustAddress = ?, City = ?, Province = ?, postalCode = ? WHERE CustID = ?";
        try {
            return CrudUtil.execute(SQL,
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode(),
                    customer.getId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String id) {
        String SQL = "SELECT * FROM Customer WHERE CustID=?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, id);
            if (resultSet.next()) {
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ObservableList<String> getCustomerIds() {
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        ObservableList<Customer> customerObservableList = getAll();
        customerObservableList.forEach(customer -> customerIds.add(customer.getId()));
        return customerIds;
    }
}
