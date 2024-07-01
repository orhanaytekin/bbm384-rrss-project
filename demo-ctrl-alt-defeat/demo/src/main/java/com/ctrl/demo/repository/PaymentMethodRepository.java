package com.ctrl.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctrl.demo.model.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
