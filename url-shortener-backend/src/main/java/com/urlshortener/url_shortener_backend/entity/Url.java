package com.urlshortener.url_shortener_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "urls")
public class Url {
    //====== for id
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @EqualsAndHashCode.Include
    private UUID id;

    //====== for url string
    @NotBlank(message = "Original URL is required")
    @Column(name = "original_url", length = 2048, nullable = false)
    private String originalUrl;

    //====== for short code string
    @NotBlank(message = "Short code is required")
    @Size(min = 6, max = 10, message = "Short code must contain between 6 and 10 characters")
    @Column(name = "short_code", unique = true, nullable = false)
    private String shortCode;

    //====== for 
    @Column(name = "click_count", nullable = false)
    private Long clickCount = 0L;

    //====== for created at timestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Url(String originalUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getShortCode() { return shortCode; }
    public void setShortCode(String shortCode) { this.shortCode = shortCode; }

    public Long getClickCount() { return clickCount; }
    public void setClickCount(Long clickCount) { this.clickCount = clickCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}