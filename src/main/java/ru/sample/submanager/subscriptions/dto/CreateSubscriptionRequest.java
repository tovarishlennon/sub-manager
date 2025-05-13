package ru.sample.submanager.subscriptions.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSubscriptionRequest(@NotBlank String serviceName) {}
