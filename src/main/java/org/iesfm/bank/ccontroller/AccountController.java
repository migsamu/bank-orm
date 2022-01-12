package org.iesfm.bank.ccontroller;

import org.iesfm.bank.pojos.Account;
import org.iesfm.bank.repository.AccountRepository;
import org.iesfm.bank.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class AccountController {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;


    public AccountController(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/accounts")
    public List<Account> list() {
        return accountRepository.findAll();
    }


    @RequestMapping(method = RequestMethod.GET, path = "/customers/{nif}/accounts")
    public void insertAccount(@PathVariable("nif") String customerNif, @RequestBody Account account) {
        if (customerRepository.existsByNif(customerNif)) {
            if (accountRepository.existsById(account.getIban())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "");
            } else {
                accountRepository.save(account);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");
        }
    }
}
