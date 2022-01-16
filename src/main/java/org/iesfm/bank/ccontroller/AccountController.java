package org.iesfm.bank.ccontroller;

import org.iesfm.bank.pojos.Account;
import org.iesfm.bank.repository.AccountRepository;
import org.iesfm.bank.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
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

    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, path = "/accounts/{iban}")
    public void delete(@PathVariable("iban") String iban) {
        int deletedRows = accountRepository.deleteByIban(iban);
        if (deletedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/accounts/{iban}")
    public Account getAccount(@PathVariable("iban") String iban) {
        Optional<Account> account = accountRepository.findById(iban);

        return account
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""));
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

    @RequestMapping(method = RequestMethod.GET, path = "/customers/{nif}/accounts")
    public List<Account> list(
            @PathVariable("nif") String customerNif
    ) {
        return accountRepository.findByNif(customerNif);
    }
}
