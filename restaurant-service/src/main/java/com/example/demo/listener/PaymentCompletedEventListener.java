package com.example.demo.listener;

import com.example.common.kafka.EventPublisher;
import com.example.common.kafka.config.KafkaProperties;
import com.example.common.kafka.event.PaymentCompletedEvent;
import com.example.common.kafka.event.RestaurantApprovedEvent;
import com.example.demo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Payment başarılı olunca tetiklenir.
 * Restaurantın şu anki saate göre açık olup olmadığını kontrol eder:
 *  - Açıksa: restaurant-approved event yayımlar → Order Service SUCCESS yapar
 *  - Kapalıysa: (şimdilik sadece loglama — ilerleyen geliştirmede restaurant-rejected eklenebilir)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCompletedEventListener {

    private final RestaurantRepository restaurantRepository;
    private final EventPublisher eventPublisher;
    private final KafkaProperties kafkaProperties;

    @KafkaListener(topics = "#{@kafkaProperties.topics.paymentCompleted.name}",
            groupId = "#{@kafkaProperties.topics.paymentCompleted.groupId}",
            concurrency = "#{@kafkaProperties.topics.paymentCompleted.concurrency}")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("PaymentCompletedEvent received for orderId={}, restaurantId={}", event.orderId(), event.restaurantId());

        var restaurant = restaurantRepository.findById(event.restaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found: " + event.restaurantId()));

        // Sipariş saatine göre açık mı kontrolü
        Instant orderTime = event.orderTime();
        ZonedDateTime orderZdt = orderTime.atZone(ZoneId.systemDefault());

        ZonedDateTime openZdt  = restaurant.getOpenTime().atZone(ZoneId.systemDefault());
        ZonedDateTime closeZdt = restaurant.getCloseTime().atZone(ZoneId.systemDefault());

        // Sadece saat kısmını karşılaştır (gün farkı gözetmeksizin)
        boolean isOpen = isWithinOpenHours(orderZdt, openZdt, closeZdt);

        if (isOpen) {
            log.info("Restaurant {} is OPEN for orderId={}. Approving order.", restaurant.getName(), event.orderId());
            eventPublisher.publish(
                    kafkaProperties.getTopics().getRestaurantApproved().getName(),
                    new RestaurantApprovedEvent(event.orderId()));
        } else {
            log.warn("Restaurant {} is CLOSED for orderId={}. Order cannot be fulfilled at this time.",
                    restaurant.getName(), event.orderId());
            // İleride: restaurant-rejected event eklenebilir (compensating transaction)
        }
    }

    /**
     * Sipariş saatinin restaurantın açılış-kapanış aralığında olup olmadığını kontrol eder.
     * Gece yarısı geçen vardiyayı da destekler (örn: 22:00 - 02:00).
     */
    private boolean isWithinOpenHours(ZonedDateTime orderTime,
                                      ZonedDateTime openTime,
                                      ZonedDateTime closeTime) {
        int orderMinutes = orderTime.getHour() * 60 + orderTime.getMinute();
        int openMinutes  = openTime.getHour()  * 60 + openTime.getMinute();
        int closeMinutes = closeTime.getHour() * 60 + closeTime.getMinute();

        if (openMinutes <= closeMinutes) {
            // Normal aralık: örn 09:00 - 22:00
            return orderMinutes >= openMinutes && orderMinutes <= closeMinutes;
        } else {
            // Gece yarısını geçen aralık: örn 22:00 - 02:00
            return orderMinutes >= openMinutes || orderMinutes <= closeMinutes;
        }
    }
}
