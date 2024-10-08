package service.custom.impl;

import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.CustomerDao;
import service.custom.CustomerService;
import util.DaoType;

public class CustomerServiceImpl implements CustomerService {

    CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);

    @Override
    public boolean addCustomer(Customer customer) {
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        customerDao.save(entity);
        return true;
    }

    @Override
    public boolean deleteCustomer(String id) {
        customerDao.delete(id);
        return true;
    }

    @Override
    public CustomerEntity searchCustomer(String id) {
        return customerDao.search(id);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        customerDao.update(entity);
        return true;
    }

    @Override
    public ObservableList<CustomerEntity> getAll() {
        return customerDao.getAll();
    }

    @Override
    public ObservableList<String> getCustomerIds() {
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        ObservableList<CustomerEntity> customerObservableList = getAll();
        customerObservableList.forEach(customer -> customerIds.add(customer.getId()));
        return customerIds;
    }
}
