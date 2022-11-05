package ru.yandex.main.admin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.user.NewUserRequest;
import ru.yandex.main.user.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    List<UserDto> findById(
            @RequestParam(name = "ids") List<Long> ids,
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return adminUserService.findById(ids, from, size);
    }

    @PostMapping
    UserDto createUser(
            @RequestBody NewUserRequest newUser
    ) {
        return adminUserService.createUser(newUser);
    }

    @DeleteMapping("/{userId}")
    void deleteUserById(
            @PathVariable Long userId
    ) {
        adminUserService.deleteUser(userId);
    }
}
