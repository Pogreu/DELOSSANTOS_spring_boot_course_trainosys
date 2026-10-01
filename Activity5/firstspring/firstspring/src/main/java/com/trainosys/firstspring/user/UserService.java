package com.trainosys.firstspring.user;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    String createUser(User newUser);
    String updateUser(Long id, User updatedData);
    String deleteUser(Long id);
    User getUserByEmail(String email);
}
