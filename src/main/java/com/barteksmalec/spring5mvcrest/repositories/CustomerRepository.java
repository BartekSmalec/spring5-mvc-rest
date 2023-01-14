package com.barteksmalec.spring5mvcrest.repositories;

import com.barteksmalec.spring5mvcrest.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
