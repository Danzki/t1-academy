package com.danzki.runner;

import com.danzki.model.User;
import com.danzki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner  implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("=== DELETE ALL ===");
        List<User> usersToDelete = userService.findAll();
        usersToDelete.forEach(userService::delete);

        System.out.println("=== CREATE USER ===");
        User user1 = userService.add("Аня");
        System.out.println("Created: " + user1);

        User user2 = userService.add("Ваня");
        System.out.println("Created: " + user2);

        System.out.println("\n=== GET ALL USERS ===");
        List<User> allUsers = userService.findAll();
        allUsers.forEach(System.out::println);

        System.out.println("\n=== GET USER BY ID ===");
        User foundUser = userService.find(user1);
        System.out.println("Found user: " + foundUser);

        System.out.println("\n=== UPDATE USER ===");
        User updatedUser = userService.update(user1.getId(), "Аня_updated");
        System.out.println("Updated user: " + updatedUser);

        System.out.println("\n=== VERIFY UPDATE ===");
        System.out.println("After update: " + userService.find(user1));

        System.out.println("\n=== DELETE USER ===");
        boolean isDeleted = userService.delete(user2);
        System.out.println("User deleted: " + isDeleted);

        System.out.println("\n=== FINAL USER LIST ===");
        userService.findAll().forEach(System.out::println);
    }
}
