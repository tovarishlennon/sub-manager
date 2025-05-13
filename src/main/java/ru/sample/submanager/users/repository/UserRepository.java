package ru.sample.submanager.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sample.submanager.users.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
