package com.example.demo.service;


import com.example.common.kafka.EventPublisher;
import com.example.common.kafka.config.KafkaProperties;
import com.example.common.kafka.event.PaymentCompletedEvent;
import com.example.common.kafka.event.PaymentFailedEvent;
import com.example.demo.dto.response.PaymentResponseDTO;
import com.example.demo.dto.request.CreatePaymentRequestDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Payment;
import com.example.demo.mapper.PaymentMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final PaymentMapper paymentMapper;
    private final EventPublisher eventPublisher;
    private final KafkaProperties kafkaProperties;

    /**
     * Kafka'dan gelen order-created event'i ile çağrılır.
     * Bakiye kontrolü yapar:
     *  - Bakiye yeterliyse: bakiyeyi düşer, payment-completed yayımlar → Restaurant Service dinler
     *  - Bakiye yetersizse: payment-failed yayımlar → Order Service dinler (compensate)
     */
    @Transactional
    public PaymentResponseDTO createPayment(CreatePaymentRequestDTO dto,
                                            java.util.UUID restaurantId,
                                            java.time.Instant orderTime) {
        Account account = accountRepository.findById(dto.accountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Payment payment = paymentMapper.toEntity(dto, account);
        paymentRepository.save(payment);

        // Bakiye kontrolü
        if (account.getBalance().compareTo(dto.amount()) < 0) {
            // Yetersiz bakiye — payment FAILED
            paymentMapper.failPayment(payment);
            paymentRepository.save(payment);

            String reason = "Insufficient balance. Required: " + dto.amount()
                    + ", Available: " + account.getBalance();
            log.warn("Payment FAILED for orderId={}. Reason: {}", dto.orderId(), reason);

            eventPublisher.publish(
                    kafkaProperties.getTopics().getPaymentFailed().getName(),
                    new PaymentFailedEvent(dto.orderId(), reason));
        } else {
            // Bakiye yeterli — bakiyeyi düş, payment COMPLETED
            account.setBalance(account.getBalance().subtract(dto.amount()));
            accountRepository.save(account);

            paymentMapper.completePayment(payment);
            paymentRepository.save(payment);

            log.info("Payment COMPLETED for orderId={}. Forwarding to restaurant={}", dto.orderId(), restaurantId);

            eventPublisher.publish(
                    kafkaProperties.getTopics().getPaymentCompleted().getName(),
                    new PaymentCompletedEvent(dto.orderId(), restaurantId, dto.amount(), orderTime));
        }

        return paymentMapper.toResponse(payment);
    }

    public PaymentResponseDTO getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(paymentMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public PaymentResponseDTO getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(paymentMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}
