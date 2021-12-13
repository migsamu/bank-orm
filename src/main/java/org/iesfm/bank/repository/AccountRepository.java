package org.iesfm.bank.repository;

import org.iesfm.bank.pojos.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
