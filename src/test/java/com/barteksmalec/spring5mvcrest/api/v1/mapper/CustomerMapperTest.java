package com.barteksmalec.spring5mvcrest.api.v1.mapper;

import com.barteksmalec.spring5mvcrest.api.v1.model.CustomerDTO;
import com.barteksmalec.spring5mvcrest.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

    public static final long ID = 1L;
    public static final String ADA = "Ada";
    public static final String KOŁO = "Koło";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(ADA);
        customer.setLastname(KOŁO);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(ADA, customerDTO.getFirstname());
        assertEquals(KOŁO, customerDTO.getLastname());

    }

    @Test
    void customerDTOtoCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstname(ADA);
        customerDTO.setLastname(KOŁO);

        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);

        assertEquals(Long.valueOf(ID), customer.getId());
        assertEquals(ADA, customer.getFirstname());
        assertEquals(KOŁO, customer.getLastname());

    }
}