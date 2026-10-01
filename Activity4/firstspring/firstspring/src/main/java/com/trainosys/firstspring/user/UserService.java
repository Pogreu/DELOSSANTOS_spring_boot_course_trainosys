package com.trainosys.firstspring.user;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    String createUser(User newUser);
    String updateUser(int id, User updatedData);
    String deleteUser(int id);
    User getUserByEmail(String email);
}
