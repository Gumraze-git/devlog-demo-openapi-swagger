package com.gumraze.opanapi.repository;

import com.gumraze.opanapi.entity.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Order와 items를 한 번에 fetch하여 N+1문제 해결
    @EntityGraph(attributePaths = "items")
    Optional<Order> findWithItemsById(Long id);
}
