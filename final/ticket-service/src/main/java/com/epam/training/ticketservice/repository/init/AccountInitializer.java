package com.epam.training.ticketservice.repository.init;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AccountInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountInitializer.class);
    private static final Account ADMIN_ACCOUNT = new Account("admin", "admin", true);
    private final AccountRepository accountRepository;

    public AccountInitializer(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    public void initAccounts() {
        LOGGER.info("Init accounts...");
        accountRepository.save(ADMIN_ACCOUNT);
        LOGGER.info("Account initialization has finished.");
        LOGGER.info("Initialized account: {}", ADMIN_ACCOUNT);
    }
}