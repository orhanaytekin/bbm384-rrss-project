package com.ctrl.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctrl.demo.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
