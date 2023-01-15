package com.barteksmalec.spring5mvcrest.bootstrap;

import com.barteksmalec.spring5mvcrest.domain.Category;
import com.barteksmalec.spring5mvcrest.domain.Customer;
import com.barteksmalec.spring5mvcrest.domain.Vendor;
import com.barteksmalec.spring5mvcrest.repositories.CategoryRepository;
import com.barteksmalec.spring5mvcrest.repositories.CustomerRepository;
import com.barteksmalec.spring5mvcrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();

        loadCustomers();

        loadVendors();
    }

    private void loadVendors() {
        Vendor vendor = new Vendor();
        vendor.setName("Ola");
        vendorRepository.save(vendor);
        Vendor vendor1 = new Vendor();
        vendor1.setName("Kot");
        vendorRepository.save(vendor1);
        Vendor vendor2 = new Vendor();
        vendor2.setName("Aga");
        vendorRepository.save(vendor2);
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Category loaded: " + categoryRepository.count());
    }

    private void loadCustomers() {
        Customer joe = new Customer();
        joe.setFirstname("Joe");
        joe.setLastname("Buck");
        joe.setId(1L);

        Customer stephen = new Customer();
        stephen.setFirstname("Stephen");
        stephen.setLastname("King");
        stephen.setId(2L);

        Customer joanne = new Customer();
        joanne.setFirstname("Joanne");
        joanne.setLastname("Rowling");
        joanne.setId(3L);

        customerRepository.save(joe);
        customerRepository.save(stephen);
        customerRepository.save(joanne);

        System.out.println("Loaded cutomers: " + customerRepository.count());
    }
}
