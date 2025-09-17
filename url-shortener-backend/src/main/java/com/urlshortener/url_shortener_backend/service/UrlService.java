package com.urlshortener.url_shortener_backend.service;

import com.urlshortener.url_shortener_backend.entity.Url;
import com.urlshortener.url_shortener_backend.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;
    private final Random random = new Random();

    public Url shortenUrl(String originalUrl) {
        String shortCode = generateUniqueShortCode();
        Url url = new Url(originalUrl, shortCode);
        return urlRepository.save(url);
    }

    public Optional<Url> getUrlByShortCode(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }
    public Url incrementClickCount(String shortCode) {
        Optional<Url> urlOpt = urlRepository.findByShortCode(shortCode);
        if (urlOpt.isPresent()) {
            Url url = urlOpt.get();
            url.setClickCount(url.getClickCount() + 1);
            return urlRepository.save(url);
        }
        return null;
    }

    //====== getting all urls
    public List<Url> getAllUrls(){
        return urlRepository.findAll();
    }

    //====== for making short code unique
    private String generateUniqueShortCode(){
        String shortCode;
        do{
            shortCode = generateShortCode();
        }while(urlRepository.existsByShortCode(shortCode));

        return shortCode;
    }

    //====== for generating short code
    private String generateShortCode(){
        StringBuilder sb= new StringBuilder(SHORT_CODE_LENGTH);
        for(int i = 0; i< SHORT_CODE_LENGTH; i++){
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
