package com.barteksmalec.spring5mvcrest.services;

import com.barteksmalec.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.barteksmalec.spring5mvcrest.api.v1.model.CustomerDTO;
import com.barteksmalec.spring5mvcrest.domain.Customer;
import com.barteksmalec.spring5mvcrest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    public static final long ID = 1L;
    public static final String FIRSTNAME = "Bartek";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customerList = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        assertEquals(3, customerDTOS.size());
    }

    @Test
    void getCustomerById() {
        Customer customer = new Customer();
        customer.setFirstname(FIRSTNAME);
        customer.setId(ID);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        assertEquals(ID, customerDTO.getId());
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
    }

    @Test
    void createNewCustomer()
    {
        Customer customer = new Customer();
        customer.setFirstname("Tom");
        customer.setLastname("Riddle");
        customer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO savedDTO =  customerService.createNewCustomer(customerMapper.customerToCustomerDTO(customer));

        assertEquals(customer.getId(), savedDTO.getId());
        assertEquals(customer.getFirstname(), savedDTO.getFirstname());
        assertEquals(customer.getLastname(), savedDTO.getLastname());
    }


    @Test
    void updateCustomer()
    {
        Customer customer = new Customer();
        customer.setFirstname("Tom");
        customer.setLastname("Riddle");
        customer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO savedDTO =  customerService.saveCustomerByDTO(1L ,customerMapper.customerToCustomerDTO(customer));

        assertEquals(customer.getId(), savedDTO.getId());
        assertEquals(customer.getFirstname(), savedDTO.getFirstname());
        assertEquals(customer.getLastname(), savedDTO.getLastname());
    }
}