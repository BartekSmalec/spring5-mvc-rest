package com.barteksmalec.spring5mvcrest.api.v1.mapper;

import com.barteksmalec.spring5mvcrest.api.v1.model.VendorDTO;
import com.barteksmalec.spring5mvcrest.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorMapperTest {
    public static final String ADA = "Ada";
    public static final long ID = 1L;
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(ADA);

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(Long.valueOf(ID), vendorDTO.getId());
        assertEquals(ADA, vendorDTO.getName());
    }
}
