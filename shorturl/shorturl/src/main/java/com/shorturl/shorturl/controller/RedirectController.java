package com.shorturl.shorturl.controller;

import com.shorturl.shorturl.model.ShortUrl;
import com.shorturl.shorturl.service.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import java.net.URI;

@RestController
public class RedirectController {

    private final ShortUrlService service;

    public RedirectController(ShortUrlService service) {
        this.service = service;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        return service.getByShortCode(shortCode)
                .map(shortUrl -> ResponseEntity.status(302)
                        .location(URI.create(shortUrl.getOriginalUrl()))
                        .<Void>build())
                .orElse(ResponseEntity.notFound().<Void>build());
    }
}
