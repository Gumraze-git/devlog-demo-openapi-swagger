package com.gumraze.opanapi.repository;

import com.gumraze.opanapi.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
