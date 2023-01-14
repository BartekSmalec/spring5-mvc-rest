package com.barteksmalec.spring5mvcrest.services;

import com.barteksmalec.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.barteksmalec.spring5mvcrest.api.v1.model.CustomerDTO;
import com.barteksmalec.spring5mvcrest.domain.Customer;
import com.barteksmalec.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomer_url("/api/v1/customer/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerRepository.findById(id).get());
        customerDTO.setCustomer_url("/api/v1/customer/" + id);
        return customerDTO;
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);
        Customer savedCusstomer = customerRepository.save(customer);
        CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCusstomer);
        returnDto.setCustomer_url("/api/v1/customers/" + customer.getId());
        return returnDto;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);
        customer.setId(id);
        Customer savedCusstomer = customerRepository.save(customer);
        CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCusstomer);
        returnDto.setCustomer_url("/api/v1/customers/" + customer.getId());
        return returnDto;
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if (customerDTO.getFirstname() != null) {
                customer.setFirstname(customerDTO.getFirstname());
            }
            if (customerDTO.getLastname() != null) {
                customer.setLastname(customerDTO.getLastname());
            }
            CustomerDTO returnCustomer = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
            returnCustomer.setCustomer_url("/api/v1/customers/" + returnCustomer.getId());

            return returnCustomer;
        }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
