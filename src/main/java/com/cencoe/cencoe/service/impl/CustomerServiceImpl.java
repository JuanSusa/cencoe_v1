package com.cencoe.cencoe.service.impl;

import com.cencoe.cencoe.models.entity.Customer;
import com.cencoe.cencoe.models.repository.ICustomerRepository;
import com.cencoe.cencoe.service.ICustomerService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;

    public CustomerServiceImpl(ICustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeResponse listCustomers() {
        List<Customer> getListCustomers;

        try {
            getListCustomers = customerRepository.findAll();
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (getListCustomers.isEmpty()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "No Hay registros en la base de datos",
                    true,
                    null);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta exitosa",
                    true,
                    getListCustomers);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeResponse findCustomers(Long customerId) {
        Optional<Customer> searchCustomer;
        try {
            searchCustomer = customerRepository.findById(customerId);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (searchCustomer.isPresent()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta Exitosa",
                    true,
                    searchCustomer);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El cliente que busca no se encuentra el registro en la base de datos",
                    true,
                    null);
        }
    }

    @Override
    @Transactional
    public MensajeResponse saveCustomer(Customer customer) {
        Customer customerToSave;
        try {
            customerToSave = customerRepository.save(customer);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        return MensajeResponse.buildMensajeGeneral(
                HttpStatus.CREATED,
                "Registro creado con éxito",
                true,
                customerToSave);
    }

    @Override
    @Transactional
    public MensajeResponse updateCustomer(Customer customerUpdate) {
        Optional<Customer> customerToUpdate;
        try {
            customerToUpdate = customerRepository.findById(customerUpdate.getCustomerId());

        } catch (DataAccessException dataEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dataEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (customerToUpdate.isPresent()) {

            Customer customerExisting = customerToUpdate.get();
            customerExisting.setCustomerId(customerUpdate.getCustomerId());
            customerExisting.setCustomerName(customerUpdate.getCustomerName());
            customerExisting.setCustomerPhone(customerUpdate.getCustomerPhone());
            customerExisting.setCustomerAddress(customerUpdate.getCustomerAddress());
            customerExisting.setCustomerState(customerUpdate.getCustomerState());

            Customer customerUpdated = customerRepository.save(customerExisting);
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Cliente actualizado con éxito",
                    true,
                    customerUpdated);

        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El cliente que desea actualizar no existe",
                    true,
                    null);
        }
    }

    @Override
    @Transactional
    public MensajeResponse deleteCustomer(Long customerId) {
        Optional<Customer> customerToDelete = customerRepository.findById(customerId);
        try {
            if (customerToDelete.isPresent()) {
                customerRepository.deleteById(customerId);
            } else {
                return MensajeResponse.buildMensajeGeneral(
                        HttpStatus.NOT_FOUND,
                        "El cliente que desea eliminar no existe",
                        true,
                        null);
            }
        } catch (DataAccessException dataEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dataEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        return MensajeResponse.buildMensajeGeneral(
                HttpStatus.NO_CONTENT,
                "Cliente eliminado con éxito",
                true,
                null);
    }

}
