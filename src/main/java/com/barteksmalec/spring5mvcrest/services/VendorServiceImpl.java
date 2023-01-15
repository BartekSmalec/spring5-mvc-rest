package com.barteksmalec.spring5mvcrest.services;

import com.barteksmalec.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.barteksmalec.spring5mvcrest.api.v1.model.VendorDTO;
import com.barteksmalec.spring5mvcrest.domain.Vendor;
import com.barteksmalec.spring5mvcrest.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    public static final String API_V_1_VENDORS = "/api/v1/vendors/";
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(API_V_1_VENDORS + vendor.getId());
                    return vendorDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if (vendor.isPresent()) {
            VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor.get());
            vendorDTO.setVendorUrl(API_V_1_VENDORS + vendorDTO.getId());
            return vendorDTO;
        } else {
            throw new ResourceNotFoundException("There is no vendor with id: " + id);
        }
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {

        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO vendorToReturn = vendorMapper.vendorToVendorDTO(savedVendor);
        vendorToReturn.setVendorUrl(API_V_1_VENDORS + vendorToReturn.getId());

        return vendorToReturn;
    }

    @Override
    public VendorDTO saveVendorDTOById(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        vendor.setId(id);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO vendorToReturn = vendorMapper.vendorToVendorDTO(savedVendor);
        vendorToReturn.setVendorUrl(API_V_1_VENDORS + vendorToReturn.getId());

        return vendorToReturn;
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }

            VendorDTO returnVendor = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
            returnVendor.setVendorUrl(API_V_1_VENDORS + id);

            return returnVendor;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
