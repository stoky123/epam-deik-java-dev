package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.repository.AccountRepository;
import com.epam.training.ticketservice.service.exception.IncorrectCredentialsException;
import com.epam.training.ticketservice.service.exception.NoUserFoundException;
import com.epam.training.ticketservice.service.exception.NotSignedInException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private Optional<Account> signedInAccount = Optional.empty();

    public Optional<Account> getSignedInAccount() {
        return this.signedInAccount;
    }

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isSignedIn() {
        return !signedInAccount.isEmpty();
    }

    public boolean isAdmin() {
        if (signedInAccount.isEmpty()) {
            return false;
        }
        return signedInAccount.get().getAdmin();
    }

    public void signInPrivileged(String userName, String password)
            throws IncorrectCredentialsException, NoUserFoundException {
        Optional<Account> signInAccount = this.accountRepository.findById(userName);
        if (signInAccount.isPresent()) {
            if (signInAccount.get().getUsername().equals(userName)
                    && signInAccount.get().getPassword().equals(password)) {
                signedInAccount = signInAccount;
            } else {
                throw new IncorrectCredentialsException();
            }
        } else {
            throw new NoUserFoundException();
        }
    }

    public void signOut() throws NotSignedInException {
        if (this.signedInAccount.isEmpty()) {
            throw new NotSignedInException();
        }
        this.signedInAccount = Optional.empty();
    }

    public String describeAccount() {
        if (this.signedInAccount.isEmpty()) {
            return "You are not signed in";
        }
        if (this.isAdmin()) {
            return "Signed in with privileged account '" + this.signedInAccount.get().getUsername() + "'";
        }
        return "Signed in with account '" + this.signedInAccount.get().getUsername() + "'";
    }
}
