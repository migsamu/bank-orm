package org.iesfm.bank.ccontroller;

import org.iesfm.bank.pojos.Customer;
import org.iesfm.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/customers")
    public List<Customer> list() {
        return customerRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/customers")
    public void insert(@RequestBody Customer customer) {
        if (customerRepository.existsByNif(customer.getNif())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El cliente con nif: " + customer.getNif() + " ya existe.");
        } else {
            customerRepository.save(customer);
        }
    }

    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, path = "/customers/{nif}")
    public void delete(@PathVariable("nif") String nif) {
        int deletedRows = customerRepository.deleteByNif(nif);
        if (deletedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/customers/{nif}")
    public Customer getCustomer(@PathVariable("nif") String nif) {
        Customer customer = customerRepository.findOneBynif(nif);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        } else {
            return customer;
        }
    }
}
