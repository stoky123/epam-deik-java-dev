package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.repository.AccountRepository;
import com.epam.training.ticketservice.service.exception.IncorrectCredentialsException;
import com.epam.training.ticketservice.service.exception.NoUserFoundException;
import com.epam.training.ticketservice.service.exception.NotSignedInException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private final Account admin = new Account("admin", "admin", true);

    private AccountService underTest;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AccountService(accountRepository);
        BDDMockito.given(accountRepository.findById("admin"))
                .willReturn(Optional.of(admin));
    }

    @Test
    void testIsSignedInShouldReturnFalseWhenNotSignedInYet() {
        // Given
        final boolean expected = false;
        // When
        final boolean actual = underTest.isSignedIn();
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testIsAdminShouldReturnFalseWhenNotSignedInOrSignedInAccountIsNotAdmin() {
        // Given
        final boolean expected = false;
        // When
        final boolean actual = underTest.isAdmin();
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testIsAdminShouldReturnTrueWhenSignedInAccountIsAdmin() {
        // Given
        final boolean expected = true;
        underTest.signInPrivileged("admin", "admin");
        // When
        final boolean actual = underTest.isAdmin();
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testSignInPrivilegedShouldSetSignedInAccountToAdminWhenCorrectCredentialsGiven() {
        // Given
        final Account expected = admin;
        underTest.signInPrivileged("admin", "admin");
        // When
        final Account actual = underTest.getSignedInAccount().get();
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testSignInPrivilegedShouldThrowIncorrectCredentialsExceptionWhenIncorrectCredentialsGiven() {
        // Given in setup
        // When + Then
        assertThrows(IncorrectCredentialsException.class,
                () -> underTest.signInPrivileged("admin", "notPassword"));
    }

    @Test
    void testSignInPrivilegedShouldThrowNoUserFoundExceptionWhenNoUserFound() {
        // Given in setup
        // When + Then
        assertThrows(NoUserFoundException.class,
                () -> underTest.signInPrivileged("notUser", "admin"));
    }

    @Test
    void testSignOutShouldThrowUserNotSignedInException() {
        // Given in setup
        // When + Then
        assertThrows(NotSignedInException.class, () -> underTest.signOut());
    }

    @Test
    void testSignOutShouldSetSignedInUserToOptionalEmptyWhenSignedIn() {
        // Given
        Optional<Account> expected = Optional.empty();
        underTest.signInPrivileged("admin", "admin");
        // When
        underTest.signOut();
        Optional<Account> actual = underTest.getSignedInAccount();
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testDescribeAccountShouldReturnYouAreNotSignedInWhenNotSignedIn() {
        // Given
        final String expected = "You are not signed in";
        // When
        final String actual = underTest.describeAccount();
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testDescribeAccountShouldReturnSignedInWithPrivilegedAccountWhenAdminIsSignedIn() {
        // Given
        final String expected = "Signed in with privileged account 'admin'";
        underTest.signInPrivileged("admin", "admin");
        // When
        final String actual = underTest.describeAccount();
        // Then
        assertEquals(expected, actual);
    }

}
