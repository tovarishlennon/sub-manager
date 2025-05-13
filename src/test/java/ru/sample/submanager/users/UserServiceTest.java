package ru.sample.submanager.users;

import ru.sample.submanager.users.dto.CreateUserRequest;
import ru.sample.submanager.users.model.User;
import ru.sample.submanager.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sample.submanager.users.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository repo;
    @InjectMocks
    UserService svc;

    @Test
    void create_ok() {
        var req = new CreateUserRequest("a@a.com", "Alice");
        when(repo.save(any())).thenAnswer(a -> {
            User u = a.getArgument(0);
            u.setId(1L); return u;
        });

        var dto = svc.create(req);

        assertThat(dto.id()).isEqualTo(1L);
        verify(repo).save(any());
    }
}
