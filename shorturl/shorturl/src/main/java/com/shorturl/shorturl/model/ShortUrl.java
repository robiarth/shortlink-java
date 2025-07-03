package com.shorturl.shorturl.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a shortened URL.
 * Maps to a database table via JPA.
 */
@Entity
public class ShortUrl {

    /**
     * Unique identifier for the shortened URL (primary key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The original full URL provided by the user.
     */
    @Column(nullable = false)
    private String originalUrl;

    /**
     * The unique short code generated for the URL.
     */
    @Column(nullable = false, unique = true)
    private String shortCode;

    /**
     * Timestamp indicating when the short URL was created.
     */
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Default constructor required by JPA.
     */
    public ShortUrl() {}

    /**
     * Constructor with all required fields for creation.
     *
     * @param originalUrl the original full URL
     * @param shortCode the generated short code
     */
    public ShortUrl(String originalUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.createdAt = LocalDateTime.now();
    }

    // Getters & setters

    /**
     * @return the internal database ID of the short URL
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the original full URL
     */
    public String getOriginalUrl() {
        return originalUrl;
    }

    /**
     * Sets the original URL.
     *
     * @param originalUrl the new URL to set
     */
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    /**
     * @return the short code
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     * Sets the short code.
     *
     * @param shortCode the new short code
     */
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    /**
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
