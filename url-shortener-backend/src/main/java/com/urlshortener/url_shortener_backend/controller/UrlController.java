package com.urlshortener.url_shortener_backend.controller;

import com.urlshortener.url_shortener_backend.entity.Url;
import com.urlshortener.url_shortener_backend.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UrlController {

    @Autowired
    private UrlService urlService;

    //====== for shortening the url
    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String,String> request){
        try{
            String originalUrl = request.get("url");
            if(originalUrl == null || originalUrl.trim().isEmpty()){
                return ResponseEntity.badRequest().body(Map.of("error", "URL is required"));
            }

            if(!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")){
                originalUrl = "http://" + originalUrl;
            }

            Url shortenedUrl = urlService.shortenUrl(originalUrl);

            return ResponseEntity.ok(Map.of(
                "originalUrl", shortenedUrl.getOriginalUrl(),
                "shortCode", shortenedUrl.getShortCode(),
                "shortUrl", "http://localhost:8080/api/" + shortenedUrl.getShortCode()
            ));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to shorten URL"));
        }
    }

    //====== for getting url
    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortCode){
        Optional<Url> urlOpt = urlService.getUrlByShortCode(shortCode);
        if(urlOpt.isPresent()){
            Url url = urlService.incrementClickCount(shortCode);
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", url.getOriginalUrl()).build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //====== for 
    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<?> getUrlStats(@PathVariable String shortCode) {
        Optional<Url> urlOpt = urlService.getUrlByShortCode(shortCode);
        if (urlOpt.isPresent()) {
            Url url = urlOpt.get();
            return ResponseEntity.ok(Map.of(
                "originalUrl", url.getOriginalUrl(),
                "shortCode", url.getShortCode(),
                "clickCount", url.getClickCount(),
                "createdAt", url.getCreatedAt()
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //====== for getting all urls
    @GetMapping("/urls")
    public ResponseEntity<List<Url> > getAllUrls(){
        return ResponseEntity.ok(urlService.getAllUrls());
    }
}