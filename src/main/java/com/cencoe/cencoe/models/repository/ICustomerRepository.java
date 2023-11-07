package com.cencoe.cencoe.models.repository;

import com.cencoe.cencoe.models.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
}
