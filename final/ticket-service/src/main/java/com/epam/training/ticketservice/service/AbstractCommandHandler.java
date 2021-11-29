package com.epam.training.ticketservice.service;

import org.springframework.shell.Availability;

public abstract class AbstractCommandHandler {

    protected AccountService accountService;

    public Availability isAdmin() {
        return this.accountService.isAdmin() ? Availability.available() : Availability.unavailable("you are already signed in.");
    }

    public Availability signedIn() {
        return this.accountService.isSignedIn() ? Availability.available() : Availability.unavailable("you are not signed in.");
    }

    public Availability notSignedIn() {
        return !this.accountService.isSignedIn() ? Availability.available() : Availability.unavailable("you are already signed in.");
    }
}
