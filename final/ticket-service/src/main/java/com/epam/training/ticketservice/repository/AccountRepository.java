package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
