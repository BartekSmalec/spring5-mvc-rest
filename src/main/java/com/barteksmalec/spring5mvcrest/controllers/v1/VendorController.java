package com.barteksmalec.spring5mvcrest.controllers.v1;

import com.barteksmalec.spring5mvcrest.api.v1.model.VendorDTO;
import com.barteksmalec.spring5mvcrest.api.v1.model.VendorListDTO;
import com.barteksmalec.spring5mvcrest.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "This is my vendor API")
@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @GetMapping
    @ApiOperation(value = "View List of Vendros", notes = "These are some API notes")
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }


    @ApiOperation(value = "Get vendor by ID", notes = "These are some API notes")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @PostMapping
    @ApiOperation(value = "Create new vendor", notes = "These are some API notes")
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update vendor by ID", notes = "These are some API notes")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendorDTOById(id, vendorDTO);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update vendor properties by ID", notes = "These are some API notes")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(id, vendorDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete vendor by Id", notes = "These are some API notes")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }

    ;
}
