package controller.customer;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;

import java.sql.*;

public class CustomerController implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        try {
            String SQL ="INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,customer.getId());
            psTm.setObject(2,customer.getTitle());
            psTm.setObject(3,customer.getName());
            psTm.setDate(4, Date.valueOf(customer.getDob()));
            psTm.setDouble(5,customer.getSalary());
            psTm.setObject(6,customer.getAddress());
            psTm.setObject(7,customer.getCity());
            psTm.setObject(8,customer.getProvince());
            psTm.setObject(9,customer.getPostalCode());
            return psTm.executeUpdate()>0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Error : "+e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        String SQL = "DELETE FROM Customer WHERE CustID='" + id + "'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            return connection.createStatement().executeUpdate(SQL)>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM Customer";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString("CustTitle")+resultSet.getString("CustName"));
                Customer customer =new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getString("CustAddress"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("postalCode")
                );
                customerObservableList.add(customer);
                System.out.println(customer);
            }
            return customerObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer searchCustomer(String name) {
        return null;
    }
}
