package com.ctrl.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctrl.demo.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
