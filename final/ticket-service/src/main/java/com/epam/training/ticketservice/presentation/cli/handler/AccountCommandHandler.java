package com.epam.training.ticketservice.presentation.cli.handler;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AccountCommandHandler {

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
    public String adminSignIn(final String adminUserName, final String adminPassword) {
        if ("admin".equals(adminUserName) && "admin".equals(adminPassword)) {
            return "Signed in as administrator.";
        }
        else {
            return "Login failed due to incorrect credentials";
        }
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    public String signOut() {
        if (true /* Not signed in*/ ) {
            return "You are not signed in.";
        }
        else {
            return "Successfully signed out.";
        }
    }

    @ShellMethod(value = "Describes the currently logged in account", key = "describe account")
    public String describeAccount() {
        if (true /* Not signed in*/ ) {
            return "You are not signed in.";
        }
        else if (false /* Admin */ ) {
            return "Signed in with privileged account" + "'<felhasználónév>'";
        }
        else {
            return "Describe account";
        }
    }
}
