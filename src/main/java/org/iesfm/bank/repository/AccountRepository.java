package org.iesfm.bank.repository;

import org.iesfm.bank.pojos.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {

    int deleteByIban(String iban);

    @Query(value = "SELECT a.* FROM Account a INNER JOIN Customer c ON c.id=a.owner_id WHERE nif=?", nativeQuery = true)
    List<Account> findByNif(String customerNif);
    // Esta query es de jpa, no es sql
    //@Query(value = "SELECT a FROM Account a INNER JOIN Customer c ON c.id=a.ownerId WHERE nif=?1")
    Account findOneByIban(String iban);
}
