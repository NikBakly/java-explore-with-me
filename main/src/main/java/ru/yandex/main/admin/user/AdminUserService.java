package ru.yandex.main.admin.user;

import ru.yandex.main.user.NewUserRequest;
import ru.yandex.main.user.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

public interface AdminUserService {
    /**
     * Получение информации о пользователях
     *
     * @param ids  id пользователей
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size количество элементов в наборе
     * @return найденные файлы
     */
    List<UserDto> findById(
            List<Long> ids,
            @Min(value = 0, message = "The from field cannot be negative")
            Integer from,
            @Min(value = 1, message = "The size field cannot be negative or zero")
            Integer size
    );

    /**
     * Добавление нового пользователя.
     *
     * @param user данные добавляемого пользователя
     * @return Добавленный пользователь
     */
    UserDto createUser(
            @Valid NewUserRequest user
    );

    /**
     * Удаление пользователя
     *
     * @param userId id пользователя
     */
    void deleteUser(
            Long userId
    );
}
