package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.repository.AccountRepository;
import com.epam.training.ticketservice.service.exception.IncorrectCredentialsException;
import com.epam.training.ticketservice.service.exception.UserNameAlreadyTakenException;
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

    public void createUser(String userName, String password) throws UserNameAlreadyTakenException {
        if(this.accountRepository.findById(userName).isPresent()) {
            throw new UserNameAlreadyTakenException();
        }

        this.accountRepository.save(new Account(userName, password, false));
    }

    public void signIn(String userName, String password) throws IncorrectCredentialsException {
        if (this.accountRepository.findById(userName).get().getUsername().equals(userName)
            && this.accountRepository.findById(userName).get().getPassword().equals(password)
            && this.signedInAccount.isEmpty()) {
            this.signedInAccount = this.accountRepository.findById(userName);
        }
        else {
            throw new IncorrectCredentialsException();
        }
    }

    public void signOut() {
        if (signedInAccount.isEmpty()) {
            this.signedInAccount = Optional.empty();
        }
        else {
            this.signedInAccount = Optional.empty();
        }
    }

}
