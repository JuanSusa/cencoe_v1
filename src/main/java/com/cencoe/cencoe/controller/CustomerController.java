package com.cencoe.cencoe.controller;

import com.cencoe.cencoe.models.entity.Customer;
import com.cencoe.cencoe.service.ICustomerService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v2/cencoe")
@CrossOrigin(origins="http://localhost:4200")
public class CustomerController {

    private final ICustomerService customerService;
    @Autowired
    public CustomerController(ICustomerService customerService){

        this.customerService = customerService;
    }

    @GetMapping("/clientes")
    public ResponseEntity<Object> listCustomers(@RequestParam (defaultValue = "0") int page,
                                                @RequestParam (defaultValue = "5") int size) {

        MensajeResponse responseListCustomers = customerService.listCustomers(page, size);
        return new ResponseEntity<>(responseListCustomers, HttpStatus.OK);
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<Object> findCustomerById(@PathVariable Long id) {

        MensajeResponse responseFindCustomer =customerService.findCustomers(id);
        return new ResponseEntity<>(responseFindCustomer, HttpStatus.OK);
    }

    @PostMapping("/cliente")
    public ResponseEntity<Object> saveCustomer(@RequestBody Customer customer) {

        MensajeResponse responseSaveCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(responseSaveCustomer, HttpStatus.OK);
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer customerToUpdate, @PathVariable Long id) {

        MensajeResponse findCustomerToUpdate = customerService.findCustomers(id);
        if (findCustomerToUpdate != null) {

            MensajeResponse responseCustomerToUpdate = customerService.updateCustomer(customerToUpdate);
            return new ResponseEntity<>(responseCustomerToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {

        MensajeResponse responseCustomerToDelete = customerService.deleteCustomer(id);
        return new ResponseEntity<>(responseCustomerToDelete, HttpStatus.OK);
    }
}
