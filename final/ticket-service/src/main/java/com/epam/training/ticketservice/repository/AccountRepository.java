package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Account;
import org.springframework.data.repository.Repository;

public interface AccountRepository extends Repository<Account, Long> {
    void save(Account account);
}
