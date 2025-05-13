package ru.sample.submanager.subscriptions.repository;

import ru.sample.submanager.subscriptions.model.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("""
         SELECT s.serviceName
         FROM Subscription s
         GROUP BY s.serviceName
         ORDER BY COUNT(s.id) DESC
         """)
    List<String> findTopServiceNames(Pageable pageable);

    List<Subscription> findByUserId(Long userId);
}
