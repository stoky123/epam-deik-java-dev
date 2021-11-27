package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.service.AccountService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AccountCommandHandler {

    private final AccountService accountService;

    public AccountCommandHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @ShellMethod(value = "Sign up", key = "sign up")
    public String signUp(final String userName, final String password) {
        this.accountService.createUser(userName, password);
        return "Successfully signed up as " + userName;
    }

    @ShellMethod(value = "Sign in", key = "sign in")
    public String signIn(final String adminUserName, final String adminPassword) {
        if (true) {
            return "Successfully signed in.";
        }
        else {
            return "Login failed due to incorrect credentials";
        }
    }

    @ShellMethod(value = "Sign in as administrator", key = "sign in privileged")
    public String adminSignIn(final String userName, final String password) {
        if ("admin".equals(userName) && "admin".equals(password)) {
            return "Signed in as administrator.";
        }
        else {
            return "Login failed due to incorrect credentials";
        }
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    public String signOut() {
        // Not signed in
        if (true) {
            return "You are not signed in.";
        }
        else {
            return "Successfully signed out.";
        }
    }

    @ShellMethod(value = "Describes the currently logged in account", key = "describe account")
    public String describeAccount() {
        // Not signed in
        if (true) {
            return "You are not signed in.";
        }
        // Admin
        else if (false) {
            return "Signed in with privileged account" + "'<felhasználónév>'";
        }
        else {
            return "Describe account";
        }
    }
}
