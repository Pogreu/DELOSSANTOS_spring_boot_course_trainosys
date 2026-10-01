package com.trainosys.firstspring.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private int nextId = 3;
    private List<User> users = new ArrayList<>(List.of(
            new User(1, "Alex", "alex@mail.com"),
            new User(2, "Mark", "mrak@mail.com")
    ));

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
    }

    @Override
    public String createUser(User newUser) {
        if (newUser.getName() == null || newUser.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: Name and Email are required.");
        }
        newUser.setId(nextId++);
        users.add(newUser);
        return "User created! Name: " + newUser.getName() + ", Email: " + newUser.getEmail();
    }

    @Override
    public String updateUser(int id, User updatedData) {
        if (updatedData.getName() == null || updatedData.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input data.");
        }
        for (User user : users) {
            if (user.getId() == id) {
                user.setName(updatedData.getName());
                user.setEmail(updatedData.getEmail());
                return "Updated user ID " + id + " with Name: " + updatedData.getName() + ", Email: " + updatedData.getEmail();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
    }

    @Override
    public String deleteUser(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                users.remove(user);
                return "Tinanggal ang user na may ID: " + id;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
    }

    @Override
    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
    }
}
