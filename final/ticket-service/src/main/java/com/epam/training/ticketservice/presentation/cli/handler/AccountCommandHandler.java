package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.exception.IncorrectCredentialsException;
import com.epam.training.ticketservice.service.exception.NoUserFoundException;
import com.epam.training.ticketservice.service.exception.NotSignedInException;
import com.epam.training.ticketservice.service.exception.UserNameAlreadyTakenException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class AccountCommandHandler extends AbstractCommandHandler {

    public AccountCommandHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    /*@ShellMethod(value = "Sign up", key = "sign up")
    public String signUp(final String userName, final String password) {
        try {
            this.accountService.createUser(userName, password);
            return "Successfully signed up as " + userName;
        } catch (UserNameAlreadyTakenException e) {
            return "Username is already taken.";
        }
    }*/

    /*@ShellMethod(value = "Sign in", key = "sign in")
    @ShellMethodAvailability("notSignedIn")
    public String signIn(final String userName, final String password) {
        try {
            this.accountService.signIn(userName, password);
            return "Successfully signed in as " + userName;
        } catch (IncorrectCredentialsException e) {
            return "Login failed due to incorrect credentials.";
        } catch (NoUserFoundException e) {
            return "No user found with the given username.";
        }
    }*/

    @ShellMethod(value = "Sign in as administrator", key = "sign in privileged")
    @ShellMethodAvailability("notSignedIn")
    public String signInPrivileged(final String userName, final String password) {
        if ("admin".equals(userName) && "admin".equals(password)) {
            this.accountService.signInPrivileged(userName, password);
            return "Signed in as administrator.";
        } else {
            return "Login failed due to incorrect credentials";
        }
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    public String signOut() {
        try {
            this.accountService.signOut();
            return "Successfully signed out.";
        } catch (NotSignedInException e) {
            return "You are not signed in";
        }

    }

    @ShellMethod(value = "Describes the currently logged in account", key = "describe account")
    public String describeAccount() {
        return this.accountService.describeAccount();
    }
}
