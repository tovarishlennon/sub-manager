package ru.sample.submanager.users.service;

import ru.sample.submanager.users.dto.*;
import ru.sample.submanager.users.model.User;
import ru.sample.submanager.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository repo;

    @Transactional
    public UserDto create(CreateUserRequest req) {
        User saved = repo.save(User.builder()
                .email(req.email())
                .fullName(req.fullName())
                .build());
        return toDto(saved);
    }

    public UserDto get(Long id) {
        return repo.findById(id).map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User %d not found".formatted(id)));
    }

    @Transactional
    public UserDto update(Long id, CreateUserRequest req) {
        User u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User %d not found".formatted(id)));
        u.setEmail(req.email());
        u.setFullName(req.fullName());
        return toDto(u);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id))
            throw new EntityNotFoundException("User %d not found".formatted(id));
        repo.deleteById(id);
    }

    public List<UserDto> list() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    private UserDto toDto(User u) {
        return new UserDto(u.getId(), u.getEmail(), u.getFullName());
    }
}
