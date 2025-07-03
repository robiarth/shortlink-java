package com.shorturl.shorturl.service;

import com.shorturl.shorturl.model.ShortUrl;
import com.shorturl.shorturl.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**
 * Service layer handling the business logic for shortened URLs.
 */
@Service
public class ShortUrlService {

    private final ShortUrlRepository repository;

    /**
     * Constructor with dependency injection for the repository.
     *
     * @param repository the repository used to persist and retrieve short URLs
     */
    public ShortUrlService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    /**
     * Generates a unique 4-character short code composed of alphanumeric characters.
     *
     * @return a unique short code
     */
    private String generateShortCode() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        do {
            for (int i = 0; i < 4; i++) {
                code.append(characters.charAt(random.nextInt(characters.length())));
            }
        } while (repository.findByShortCode(code.toString()).isPresent());
        return code.toString();
    }

    /**
     * Retrieves a ShortUrl by its database ID.
     *
     * @param id the unique identifier
     * @return an Optional containing the ShortUrl if found
     */
    public Optional<ShortUrl> getById(Long id) {
        return repository.findById(id);
    }

    /**
     * Creates and saves a new ShortUrl for a given original URL.
     *
     * @param originalUrl the original full URL
     * @return the newly created ShortUrl entity
     */
    public ShortUrl createShortUrl(String originalUrl) {
        String shortCode = generateShortCode();
        ShortUrl shortUrl = new ShortUrl(originalUrl, shortCode);
        return repository.save(shortUrl);
    }

    /**
     * Retrieves a ShortUrl by its short code.
     *
     * @param shortCode the code to search for
     * @return an Optional containing the ShortUrl if found
     */
    public Optional<ShortUrl> getByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode);
    }

    /**
     * Updates the original URL for a given ShortUrl ID.
     *
     * @param id the ID of the ShortUrl to update
     * @param newOriginalUrl the new URL to assign
     * @return an Optional containing the updated ShortUrl, or empty if not found
     */
    public Optional<ShortUrl> updateShortUrl(Long id, String newOriginalUrl) {
        Optional<ShortUrl> optional = repository.findById(id);
        if (optional.isPresent()) {
            ShortUrl url = optional.get();
            url.setOriginalUrl(newOriginalUrl);
            return Optional.of(repository.save(url));
        }
        return Optional.empty();
    }

    /**
     * Deletes a ShortUrl by its ID.
     *
     * @param id the ID of the ShortUrl to delete
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteShortUrl(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
