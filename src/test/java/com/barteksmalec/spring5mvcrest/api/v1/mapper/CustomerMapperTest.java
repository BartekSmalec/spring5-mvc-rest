package com.barteksmalec.spring5mvcrest.api.v1.mapper;

import com.barteksmalec.spring5mvcrest.api.v1.model.CustomerDTO;
import com.barteksmalec.spring5mvcrest.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    public static final long ID = 1L;
    public static final String ADA = "Ada";
    public static final String KOŁO = "Koło";
    public static final String URL = "/shop/1/ada";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(ADA);
        customer.setLastname(KOŁO);
        customer.setCustomer_url(URL);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(ADA, customerDTO.getFirstname());
        assertEquals(KOŁO, customerDTO.getLastname());
        assertEquals(URL, customerDTO.getCustomer_url());
    }
}