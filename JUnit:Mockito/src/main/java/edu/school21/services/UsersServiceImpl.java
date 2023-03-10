package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) throws AlreadyAuthenticatedException {
        User user = usersRepository.findByLogin(login);
        if (user.getIdentity())
            throw new AlreadyAuthenticatedException("...");
        if (password.equals(user.getPassword())) {
            user.setIdentity(true);
            usersRepository.update(user);
            return true;
        } else
            return false;
    }
}
