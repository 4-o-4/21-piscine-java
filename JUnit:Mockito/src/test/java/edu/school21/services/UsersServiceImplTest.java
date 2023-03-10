package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UsersServiceImplTest {
    private final UsersRepository mock = mock(UsersRepository.class);
    private final User user = new User();
    private final UsersServiceImpl usersService = new UsersServiceImpl(mock);

    @Test
    void authenticate() throws AlreadyAuthenticatedException {
        when(mock.findByLogin(anyString())).thenReturn(user);
        assertFalse(usersService.authenticate("login", "password"));

        user.setPassword("password");
        assertTrue(usersService.authenticate("login", "password"));

        user.setIdentity(true);
        assertThrows(AlreadyAuthenticatedException.class,
                () -> usersService.authenticate("login", "password"));
        verify(mock).update(user);
        verify(mock, times(3)).findByLogin(anyString());
    }
}
