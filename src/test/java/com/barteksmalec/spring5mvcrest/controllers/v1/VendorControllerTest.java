package com.barteksmalec.spring5mvcrest.controllers.v1;

import com.barteksmalec.spring5mvcrest.api.v1.model.VendorDTO;
import com.barteksmalec.spring5mvcrest.controllers.RestResponseEntityExceptionHandler;
import com.barteksmalec.spring5mvcrest.services.ResourceNotFoundException;
import com.barteksmalec.spring5mvcrest.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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

class VendorControllerTest {

    public static final String API_V_1_VENDORS = "/api/v1/vendors/";
    public static final String NAME = "Ola";
    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getAllVendors() throws Exception {
        List<VendorDTO> vendorDTOList = Arrays.asList(new VendorDTO(), new VendorDTO());

        when(vendorService.getAllVendors()).thenReturn(vendorDTOList);

        mockMvc.perform(get(API_V_1_VENDORS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void getVendorById() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName(NAME);

        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        mockMvc.perform(get(API_V_1_VENDORS + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    void createNewVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl(API_V_1_VENDORS + "1");

        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(vendorDTO);

        mockMvc.perform(post(API_V_1_VENDORS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(API_V_1_VENDORS + "1")));
    }

    @Test
    void updateVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl(API_V_1_VENDORS + "1");

        VendorDTO returnVendor = new VendorDTO();
        returnVendor.setVendorUrl(vendorDTO.getVendorUrl());
        returnVendor.setName(vendorDTO.getName());

        when(vendorService.saveVendorDTOById(anyLong(), any(VendorDTO.class))).thenReturn(returnVendor);

        mockMvc.perform(put(API_V_1_VENDORS + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(API_V_1_VENDORS + "1")));
    }

    @Test
    void patchVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName(NAME);

        VendorDTO returnVendor = new VendorDTO();
        returnVendor.setVendorUrl(API_V_1_VENDORS + "1");
        returnVendor.setName(vendorDTO.getName());

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnVendor);

        mockMvc.perform(patch(API_V_1_VENDORS + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(API_V_1_VENDORS + "1")));
    }

    @Test
    void deleteVendor() throws Exception {
        mockMvc.perform(delete(API_V_1_VENDORS + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(API_V_1_VENDORS + "7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}