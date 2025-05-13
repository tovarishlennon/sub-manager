package ru.sample.submanager.users.controller;

import ru.sample.submanager.users.dto.*;
import ru.sample.submanager.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService svc;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Validated CreateUserRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(svc.create(req));
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return svc.get(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody @Validated CreateUserRequest req) {
        return svc.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }

    @GetMapping
    public List<UserDto> list() {
        return svc.list();
    }
}
