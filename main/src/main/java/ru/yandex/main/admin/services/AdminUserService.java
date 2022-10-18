package ru.yandex.main.admin.services;

import ru.yandex.main.user.User;

public interface AdminUserService {
    /**
     * Изменение пользователя.
     *
     * @param user данные пользователя для изменения
     * @return Измененный пользователь
     */
    User updateUser(
            User user
    );

    /**
     * Добавление нового пользователя.
     *
     * @param user данные добавляемого пользователя
     * @return Добавленный пользователь
     */
    User createUser(
            User user
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
