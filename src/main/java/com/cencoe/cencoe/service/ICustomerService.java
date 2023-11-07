package com.cencoe.cencoe.service;

import com.cencoe.cencoe.models.entity.Customer;
import com.cencoe.cencoe.util.MensajeResponse;

public interface ICustomerService {

    MensajeResponse listCustomers();

    MensajeResponse findCustomers(Long customerId);

    MensajeResponse saveCustomer(Customer customer);

    MensajeResponse updateCustomer(Customer customerUpdate);

    MensajeResponse deleteCustomer(Long customerId);
}
