package com.barteksmalec.spring5mvcrest.repositories;

import com.barteksmalec.spring5mvcrest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
