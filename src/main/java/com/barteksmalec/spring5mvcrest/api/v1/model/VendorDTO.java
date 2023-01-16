package com.barteksmalec.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
    private Long id;
    @ApiModelProperty(value = "Name of the Vendor", required = true)
    private String name;
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
