package org.iesfm.bank.repository;

import org.iesfm.bank.pojos.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByNif(String nif);

    int deleteByNif(String nif);

    Customer findOneByNif(String nif);

}
