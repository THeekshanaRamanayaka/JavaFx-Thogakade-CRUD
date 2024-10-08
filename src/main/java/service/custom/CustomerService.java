package service.custom;

import entity.CustomerEntity;
import javafx.collections.ObservableList;
import model.Customer;
import service.SuperService;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer);
    boolean deleteCustomer(String id);
    CustomerEntity searchCustomer(String id);
    boolean updateCustomer(Customer customer);
    ObservableList<CustomerEntity> getAll();
    ObservableList<String> getCustomerIds();
}
