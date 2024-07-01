package com.ctrl.demo.controller;

import com.ctrl.demo.model.PaymentMethod;
import com.ctrl.demo.service.PaymentMethodService;
import com.ctrl.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public PaymentMethod createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        return paymentMethodService.savePaymentMethod(paymentMethod);
    }

    @GetMapping
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodService.getAllPaymentMethods();
    }

    @GetMapping("/{id}")
    public PaymentMethod getPaymentMethodById(@PathVariable Long id) {
        return paymentMethodService.getPaymentMethodById(id);
    }

    @PutMapping("/{id}")
    public PaymentMethod updatePaymentMethod(@PathVariable Long id, @RequestBody PaymentMethod updatedPaymentMethod) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
        if (paymentMethod != null) {
            paymentMethod.setCardNumber(updatedPaymentMethod.getCardNumber());
            paymentMethod.setCardHolderName(updatedPaymentMethod.getCardHolderName());
            paymentMethod.setExpiryDate(updatedPaymentMethod.getExpiryDate());
            paymentMethod.setCvv(updatedPaymentMethod.getCvv());
            return paymentMethodService.savePaymentMethod(paymentMethod);
        } else {
            throw new RuntimeException("Payment method not found");
        }
    }

    @DeleteMapping("/{id}")
    public void deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
    }
}
