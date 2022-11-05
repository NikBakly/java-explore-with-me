package ru.yandex.main.admin.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.yandex.main.exception.NotFoundException;
import ru.yandex.main.user.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findById(List<Long> ids,
                                  @Min(value = 0, message = "The from field cannot be negative")
                                  Integer from,
                                  @Min(value = 1, message = "The size field cannot be negative or zero")
                                  Integer size) {
        BooleanExpression byAnyId = QUser.user.id.in(ids);
        Pageable pageable = PageRequest.of(from, size);
        List<User> foundUser = userRepository.findAll(byAnyId, pageable).getContent();
        log.info("Users with ids={} are found successfully", ids);
        return UserMapper.toUsersDto(foundUser);
    }

    @Override
    @Transactional
    public UserDto createUser(@Valid NewUserRequest newUserRequest) {
        User user = UserMapper.toUser(newUserRequest);
        userRepository.save(user);
        log.info("User with id={} is created successfully", user.getId());
        return UserMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        checkUser(userId);
        userRepository.deleteById(userId);
        log.info("User with id={} is deleted successfully", userId);
    }

    private void checkUser(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            log.warn("User with id={} was not found", userId);
            throw new NotFoundException("User with id=" + userId + " was not found");
        }
    }
}
