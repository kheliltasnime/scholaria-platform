package com.research.paper.repository;

import com.research.paper.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,String> {
}
