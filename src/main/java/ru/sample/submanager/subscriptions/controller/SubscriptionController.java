package ru.sample.submanager.subscriptions.controller;

import ru.sample.submanager.subscriptions.dto.*;
import ru.sample.submanager.subscriptions.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/subscriptions")
@RequiredArgsConstructor
@Validated
public class SubscriptionController {
    private final SubscriptionService svc;

    @PostMapping
    public ResponseEntity<SubscriptionDto> add(@PathVariable Long userId,
                                               @RequestBody @Validated CreateSubscriptionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(svc.add(userId, req));
    }

    @GetMapping
    public List<SubscriptionDto> list(@PathVariable Long userId) {
        return svc.list(userId);
    }

    @DeleteMapping("/{subId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId, @PathVariable Long subId) {
        svc.delete(userId, subId);
    }
}
