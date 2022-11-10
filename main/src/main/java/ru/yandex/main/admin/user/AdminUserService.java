package ru.yandex.main.admin.user;

import ru.yandex.main.user.NewUserRequest;
import ru.yandex.main.user.UserDto;

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
            Integer from,
            Integer size
    );

    /**
     * Добавление нового пользователя.
     *
     * @param user данные добавляемого пользователя
     * @return Добавленный пользователь
     */
    UserDto createUser(
            NewUserRequest user
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
