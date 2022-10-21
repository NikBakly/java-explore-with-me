package ru.yandex.main.admin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.user.NewUserRequest;
import ru.yandex.main.user.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    List<UserDto> findById(
            @RequestParam(name = "ids", required = false) int[] ids,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        return adminUserService.findById(ids, from, size);
    }

    @PostMapping
    UserDto createCategory(
            @RequestBody NewUserRequest newUser
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
