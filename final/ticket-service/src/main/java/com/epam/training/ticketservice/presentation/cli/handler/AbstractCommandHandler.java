package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.service.AccountService;
import org.springframework.shell.Availability;

public abstract class AbstractCommandHandler {

    protected AccountService accountService;

    public Availability isAdmin() {
        return (this.accountService.isSignedIn() && this.accountService.isAdmin())
                ? Availability.available()
                : Availability.unavailable("you have to be admin in order to use this command.");
    }

    public Availability signedIn() {
        return this.accountService.isSignedIn()
                ? Availability.available()
                : Availability.unavailable("you are not signed in.");
    }

    public Availability notSignedIn() {
        return !this.accountService.isSignedIn()
                ? Availability.available()
                : Availability.unavailable("you are already signed in.");
    }
}
