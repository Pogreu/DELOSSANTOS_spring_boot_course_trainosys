package com.trainosys.firstspring.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }

    @Override
    public String createUser(User newUser) {
        if (newUser.getUserName() == null || newUser.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: Name and Email are required.");
        }
        User savedUser = userRepository.save(newUser);
        return "User created! Name: " + savedUser.getUserName() + ", Email: " + savedUser.getEmail();
    }

    @Override
    public String updateUser(Long id, User updatedData) {
        if (updatedData.getUserName() == null || updatedData.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input data.");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        existingUser.setUserName(updatedData.getUserName());
        existingUser.setEmail(updatedData.getEmail());
        userRepository.save(existingUser);

        return "Updated user ID " + id + " with Name: " + updatedData.getUserName() + ", Email: " + updatedData.getEmail();
    }

    @Override
    public String deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        userRepository.delete(existingUser);
        return "Tinanggal ang user na may ID: " + id;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }
}
