package org.iesfm.bank.ccontroller;

import org.iesfm.bank.pojos.Customer;
import org.iesfm.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El cliente ya existe");
        } else {
            customerRepository.save(customer);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/customers/{id}")
    public void delete(@PathVariable int id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        } else {
            customerRepository.deleteById(id);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/customers/{id}")
    public Optional<Customer> listById(@PathVariable int id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        } else {
            return customerRepository.findById(id);
        }
    }
}
