package com.example.demo.entity;

public enum OrderStatus {
    CREATED,     // order ilk oluşturulduğunda
    PENDING,     // payment işlemi bekleniyor
    PAID,        // payment başarılı, restaurant onayı bekleniyor
    SUCCESS,     // restaurant onayladı — tüm akış tamamlandı
    CANCELLING,  // compensating transaction başlatıldı
    CANCELLED,   // iptal edildi (payment başarısız)
    FAILED       // genel hata durumu
}
