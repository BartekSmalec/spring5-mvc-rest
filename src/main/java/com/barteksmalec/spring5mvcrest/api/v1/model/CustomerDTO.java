package com.barteksmalec.spring5mvcrest.api.v1.model;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String customer_url;
}
