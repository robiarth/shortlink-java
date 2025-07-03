package com.shorturl.shorturl.service;

import com.shorturl.shorturl.model.ShortUrl;
import com.shorturl.shorturl.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ShortUrlService {

    private final ShortUrlRepository repository;

    public ShortUrlService(ShortUrlRepository repository) {
        this.repository = repository;
    }

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

    public Optional<ShortUrl> getById(Long id) {
        return repository.findById(id);
    }

    public ShortUrl createShortUrl(String originalUrl) {
        String shortCode = generateShortCode();
        ShortUrl shortUrl = new ShortUrl(originalUrl, shortCode);
        return repository.save(shortUrl);
    }


    public Optional<ShortUrl> getByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode);
    }


    public Optional<ShortUrl> updateShortUrl(Long id, String newOriginalUrl) {
        Optional<ShortUrl> optional = repository.findById(id);
        if (optional.isPresent()) {
            ShortUrl url = optional.get();
            url.setOriginalUrl(newOriginalUrl);
            return Optional.of(repository.save(url));
        }
        return Optional.empty();
    }

    public boolean deleteShortUrl(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
