package com.shorturl.shorturl.controller;

import com.shorturl.shorturl.service.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Controller responsible for handling redirection based on short URLs.
 */
@RestController
public class RedirectController {



    /**
     * Constructor getting service.
     *
     * @param service the service used to retrieve original URLs
     */
    private final ShortUrlService service;
    public RedirectController(ShortUrlService service) {
        this.service = service;
    }

    /**
     * Redirects the user from a short code to the original URL.
     *
     * @param shortCode the short code used in the shortened URL
     * @return a 302 redirect to the original URL, or 404 if not found
     */
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        return service.getByShortCode(shortCode)
                .map(shortUrl -> ResponseEntity.status(302)
                        .location(URI.create(shortUrl.getOriginalUrl()))
                        .<Void>build())
                .orElse(ResponseEntity.notFound().<Void>build());
    }
}
