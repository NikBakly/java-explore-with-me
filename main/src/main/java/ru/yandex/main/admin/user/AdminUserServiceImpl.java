package ru.yandex.main.admin.user;

import org.springframework.stereotype.Service;
import ru.yandex.main.user.NewUserRequest;
import ru.yandex.main.user.UserDto;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Override
    public List<UserDto> findById(int[] ids, Integer from, Integer size) {
        return null;
    }

    @Override
    public UserDto createUser(NewUserRequest user) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
