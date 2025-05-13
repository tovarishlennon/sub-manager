package ru.sample.submanager.subscriptions.controller;

import ru.sample.submanager.subscriptions.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class PopularController {
    private final SubscriptionService svc;

    @GetMapping("/top")
    public List<String> top() {
        return svc.top3();
    }
}
