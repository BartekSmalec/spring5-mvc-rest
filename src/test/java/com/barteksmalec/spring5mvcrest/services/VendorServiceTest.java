package com.barteksmalec.spring5mvcrest.services;

import com.barteksmalec.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.barteksmalec.spring5mvcrest.api.v1.model.VendorDTO;
import com.barteksmalec.spring5mvcrest.domain.Vendor;
import com.barteksmalec.spring5mvcrest.repositories.VendorRepository;
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
import static org.mockito.Mockito.*;

class VendorServiceTest {
    public static final String NAME = "Buck";
    public static final long ID = 1L;
    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;
    VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);

    }

    @Test
    void getAllVendors() {
        List<Vendor> vendorList = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        when(vendorRepository.findAll()).thenReturn(vendorList);

        List<VendorDTO> vendorDTOList = vendorService.getAllVendors();

        assertEquals(3, vendorDTOList.size());
    }

    @Test
    void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    void createNewVendor() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO vendorDTO = vendorService.createNewVendor(vendorMapper.vendorToVendorDTO(vendor));

        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    void saveVendorDTOById() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO vendorDTO = vendorService.saveVendorDTOById(ID, vendorMapper.vendorToVendorDTO(vendor));

        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());

    }

    @Test
    void deleteVendorById() {
        vendorService.deleteVendorById(ID);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}