package com.shorturl.shorturl.controller;

import com.shorturl.shorturl.model.ShortUrl;
import com.shorturl.shorturl.service.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/short_url")
public class ShortUrlController {

    private final ShortUrlService service;
    private static final String API_KEY = "A1B1C1";

    public ShortUrlController(ShortUrlService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ShortUrl> create(@RequestBody Map<String, String> payload) {
        String url = payload.get("originalUrl");
        ShortUrl shortUrl = service.createShortUrl(url);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortUrl> get(@PathVariable String shortCode) {
        Optional<ShortUrl> result = service.getByShortCode(shortCode);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ShortUrl> getById(@PathVariable Long id) {
        Optional<ShortUrl> result = service.getById(id);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShortUrl> update(@PathVariable Long id,
                                           @RequestBody Map<String, String> payload,
                                           @RequestHeader("X-API-KEY") String apiKey) {
        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.status(403).build();
        }
        String url = payload.get("originalUrl");
        Optional<ShortUrl> result = service.updateShortUrl(id, url);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestHeader("X-API-KEY") String apiKey) {
        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.status(403).build();
        }
        boolean deleted = service.deleteShortUrl(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}