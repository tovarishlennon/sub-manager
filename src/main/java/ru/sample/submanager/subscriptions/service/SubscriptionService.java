package ru.sample.submanager.subscriptions.service;

import ru.sample.submanager.subscriptions.dto.*;
import ru.sample.submanager.subscriptions.model.Subscription;
import ru.sample.submanager.subscriptions.repository.SubscriptionRepository;
import ru.sample.submanager.users.model.User;
import ru.sample.submanager.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {
    private final SubscriptionRepository repo;
    private final UserRepository userRepo;

    @Transactional
    public SubscriptionDto add(Long userId, CreateSubscriptionRequest req) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User %d not found".formatted(userId)));

        Subscription saved = repo.save(Subscription.builder()
                .serviceName(req.serviceName())
                .user(user)
                .build());
        return toDto(saved);
    }

    public List<SubscriptionDto> list(Long userId) {
        if (!userRepo.existsById(userId))
            throw new EntityNotFoundException("User %d not found".formatted(userId));
        return repo.findByUserId(userId).stream().map(this::toDto).toList();
    }

    @Transactional
    public void delete(Long userId, Long subId) {
        Subscription sub = repo.findById(subId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription %d not found".formatted(subId)));
        if (!sub.getUser().getId().equals(userId))
            throw new IllegalArgumentException("Subscription %d does not belong to user %d".formatted(subId, userId));
        repo.delete(sub);
    }

    public List<String> top3() {
        return repo.findTopServiceNames(PageRequest.of(0, 3));
    }

    private SubscriptionDto toDto(Subscription s) {
        return new SubscriptionDto(s.getId(), s.getServiceName());
    }
}
