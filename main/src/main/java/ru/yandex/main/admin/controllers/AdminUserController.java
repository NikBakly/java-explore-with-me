package ru.yandex.main.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.admin.services.AdminUserService;
import ru.yandex.main.user.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @PatchMapping
    User updateUser(
            @RequestBody User updateUser
    ) {
        return adminUserService.updateUser(updateUser);
    }

    @PostMapping
    User createCategory(
            @RequestBody User newUser
    ) {
        return adminUserService.createUser(newUser);
    }

    @DeleteMapping("/{userId}")
    void deleteCategoryById(
            @PathVariable Long userId
    ) {
        adminUserService.deleteUser(userId);
    }
}
