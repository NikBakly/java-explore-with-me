package ru.yandex.main.admin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.user.NewUserRequest;
import ru.yandex.main.user.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    List<UserDto> findById(
            @RequestParam(name = "ids") List<Long> ids,
            @RequestParam(name = "from", defaultValue = "0")
            @Min(value = 0, message = "The from field cannot be negative")
            Integer from,
            @RequestParam(name = "size", defaultValue = "10")
            @Min(value = 1, message = "The size field cannot be negative or zero")
            Integer size
    ) {
        return adminUserService.findById(ids, from, size);
    }

    @PostMapping
    UserDto createUser(
            @Valid @RequestBody NewUserRequest newUser
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
