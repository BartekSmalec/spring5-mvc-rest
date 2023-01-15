package com.barteksmalec.spring5mvcrest.controllers.v1;

import com.barteksmalec.spring5mvcrest.api.v1.model.CustomerDTO;
import com.barteksmalec.spring5mvcrest.controllers.RestResponseEntityExceptionHandler;
import com.barteksmalec.spring5mvcrest.services.CustomerService;
import com.barteksmalec.spring5mvcrest.services.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.barteksmalec.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

    public static final String FIRSTNAME = "Tom";
    public static final String API_V_1_CUSTOMERS_1 = "/api/v1/customers/1";
    public static final String API_V_1_CUSTOMERS = "/api/v1/customers/";
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testListCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(1L);
        customer1.setFirstname("Ola");
        customer1.setLastname("Kot");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstname("Monika");
        customer2.setLastname("Lis");

        List<CustomerDTO> customerDTOS = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(get(API_V_1_CUSTOMERS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstname(FIRSTNAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get(API_V_1_CUSTOMERS_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));
    }

    @Test
    public void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setCustomerUrl(API_V_1_CUSTOMERS_1);

        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(post(API_V_1_CUSTOMERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(API_V_1_CUSTOMERS_1)));
    }

    @Test
    public void updateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Ron");
        customer.setLastname("Wesleay");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl(API_V_1_CUSTOMERS_1);

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(API_V_1_CUSTOMERS_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Ron")))
                .andExpect(jsonPath("$.lastname", equalTo("Wesleay")))
                .andExpect(jsonPath("$.customer_url", equalTo(API_V_1_CUSTOMERS_1)));
    }

    @Test
    public void patchCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Brandon");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname("Sanderson");
        returnDTO.setCustomerUrl(API_V_1_CUSTOMERS_1);

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(API_V_1_CUSTOMERS_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Brandon")))
                .andExpect(jsonPath("$.lastname", equalTo("Sanderson")))
                .andExpect(jsonPath("$.customer_url", equalTo(API_V_1_CUSTOMERS_1)));
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete(API_V_1_CUSTOMERS_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomerById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(API_V_1_CUSTOMERS + "7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}