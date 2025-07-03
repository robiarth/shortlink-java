package com.shorturl.shorturl.controller;

import com.shorturl.shorturl.model.ShortUrl;
import com.shorturl.shorturl.service.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.Map;

/**
 * REST Controller handling all operations related to Short URLs.
 * Provides CRUD endpoints as well as redirection support.
 */
@RestController
@RequestMapping("/short_url")
public class ShortUrlController {

    private final ShortUrlService service;

    /**API key used for securing PUT and DELETE*/
    private static final String API_KEY = "A1B1C1";

    /**
     * @param service the ShortUrlService to manage business logic
     */
    public ShortUrlController(ShortUrlService service) {
        this.service = service;
    }

    /**
     * Creates a new Short URL based on a given original URL.
     *
     * @param payload a JSON object containing "originalUrl"
     * @return the created ShortUrl
     */
    @PostMapping
    public ResponseEntity<ShortUrl> create(@RequestBody Map<String, String> payload) {
        String url = payload.get("originalUrl");
        ShortUrl shortUrl = service.createShortUrl(url);
        return ResponseEntity.ok(shortUrl);
    }

    /**
     * Retrieves a Short URL by its short code.
     *
     * @param shortCode the short URL code
     * @return the corresponding ShortUrl or 404 if not found
     */
    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortUrl> get(@PathVariable String shortCode) {
        Optional<ShortUrl> result = service.getByShortCode(shortCode);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a Short URL by its internal database ID.
     *
     * @param id the database ID
     * @return the corresponding ShortUrl or 404 if not found
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ShortUrl> getById(@PathVariable Long id) {
        Optional<ShortUrl> result = service.getById(id);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates the original URL associated with a Short URL.
     * Requires a valid API key via the "X-API-KEY" header.
     *
     * @param id      the ID of the short URL to update
     * @param payload a JSON object containing "originalUrl"
     * @param apiKey  the API key for authentication
     * @return the updated ShortUrl or 403/404 depending on access and existence
     */
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

    /**
     * Deletes a Short URL by its ID.
     * Requires a valid API key via the "X-API-KEY" header.
     *
     * @param id     the ID of the ShortUrl to delete
     * @param apiKey the API key for authentication
     * @return 204 if deleted, 403 if unauthorized, or 404 if not found
     */
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
