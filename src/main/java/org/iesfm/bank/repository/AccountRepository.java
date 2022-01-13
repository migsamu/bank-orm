package org.iesfm.bank.repository;

import org.iesfm.bank.pojos.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {

    int deleteByIban(String iban);

    @Query(value = "SELECT a.* FROM Account a INNER JOIN Customer c ON c.id=a.owner_id WHERE nif=?", nativeQuery = true)
    List<Account> findByNif(String customerNif);

    Account findOneByIban(String iban);
}
