package com.barteksmalec.spring5mvcrest.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
