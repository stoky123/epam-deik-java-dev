package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.exception.IncorrectCredentialsException;
import com.epam.training.ticketservice.service.exception.UserNameAlreadyTakenException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
public class AccountCommandHandler {

    private final AccountService accountService;

    public AccountCommandHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @ShellMethod(value = "Sign up", key = "sign up")
    public String signUp(final String userName, final String password) {
        try {
            this.accountService.createUser(userName, password);
            return "Successfully signed up as " + userName;
        }
        catch (UserNameAlreadyTakenException e) {
            return "Username is already taken.";
        }
    }

    @ShellMethod(value = "Sign in", key = "sign in")
    public String signIn(final String userName, final String password) {
        if (this.accountService.getSignedInAccount().isEmpty()) {
            try {
                this.accountService.signIn(userName, password);
                return "Successfully signed in as " + userName;
            }
            catch (IncorrectCredentialsException e) {
                return "Login failed due to incorrect credentials.";
            }
        }
        else {
            return "You are already signed in.";
        }
    }

    @ShellMethod(value = "Sign in as administrator", key = "sign in privileged")
    public String adminSignIn(final String userName, final String password) {
        if (this.accountService.getSignedInAccount().isEmpty()) {
            if ("admin".equals(userName) && "admin".equals(password)) {
                this.accountService.signIn("admin", "admin");
                return "Signed in as administrator.";
            }
            else {
                return "Login failed due to incorrect credentials";
            }
        }
        else {
            return "You are already signed in.";
        }
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    public String signOut() {
        if (this.accountService.getSignedInAccount().isEmpty()) {
            return "You are not signed in.";
        }
        else {
            this.accountService.signOut();
            return "Successfully signed out.";
        }
    }

    @ShellMethod(value = "Describes the currently logged in account", key = "describe account")
    public String describeAccount() {
        if (this.accountService.getSignedInAccount().isEmpty()) {
            return "You are not signed in.";
        }
        else if (this.accountService.getSignedInAccount().get().getAdmin()) {
            return "Signed in with privileged account " + this.accountService.getSignedInAccount().get().getUsername();
        }
        else {
            return "Signed in with account " + this.accountService.getSignedInAccount().get().getUsername();
        }
    }
}
