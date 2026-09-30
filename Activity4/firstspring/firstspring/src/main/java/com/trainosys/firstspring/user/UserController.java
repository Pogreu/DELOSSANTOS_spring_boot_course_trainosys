package com.trainosys.firstspring.user;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users") // Base path for all endpoints in this controller
public class UserController {

    private List<User> users = new ArrayList<>(List.of(
            new User(1, "Ana", "ana@mail.com"),
            new User(2, "John", "john@mail.com")
    ));

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id) {
        return "Nakuha ang user na may ID: " + id;
    }

    @PostMapping
    public String createUser(@RequestBody User newUser) {
        return "User created! Name: " + newUser.getName() + ", Email: " + newUser.getEmail();
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User updatedData) {
        return "Updated user ID " + id + " with Name: " + updatedData.getName() + ", Email: " + updatedData.getEmail();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        return "Tinanggal ang user na may ID: " + id;
    }

    @GetMapping("/email/{email}")
    public String getUserByEmail(@PathVariable String email) {
        return "Hinahanap ang user gamit ang email na: " + email;
    }
}
