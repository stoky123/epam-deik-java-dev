package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.repository.AccountRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private Optional<Account> signedInAccount = Optional.empty();

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createUser(String userName, String password) {
        this.accountRepository.save(new Account(userName, password, false));
    }

    public void signIn(String userName, String password) {
        if (this.accountRepository.findById(userName).get().getUsername().equals(userName)
            && this.accountRepository.findById(userName).get().getPassword().equals(password)
            && this.signedInAccount.isEmpty()) {
            this.signedInAccount = this.accountRepository.findById(userName);
        }
        return;
    }

    public void signOut() {
        if (signedInAccount.isEmpty()) {

        }
        else {
            signedInAccount = Optional.empty();
        }
    }

}
