package com.service.Tiny.Url.Service.service;


import com.service.Tiny.Url.Service.entities.UriEntity;
import com.service.Tiny.Url.Service.repository.UriRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.service.Tiny.Url.Service.components.StaticComponents.LONG_URL_IS_NOT_REGISTERED;

@Service
public class ServiceLayer {


    // implementing base 64 code encription

    @Autowired
    public UriRepository uriRepository;
    @Transactional
    public String getLongUrl(String shortUrl){
        Optional<UriEntity> optionalLongUrl = uriRepository.findByShortUrl(shortUrl);

        if(optionalLongUrl.isPresent()){
            return optionalLongUrl.get().getLongUrl();
        }

        return LONG_URL_IS_NOT_REGISTERED;

    }

    @Transactional
    public String getShortUrl(String longUrl){
        Optional<UriEntity> optionalShortUrl = uriRepository.findByLongUrl(longUrl);

        if(optionalShortUrl.isPresent()){
            return optionalShortUrl.get().getShortUrl();
        }
        String shortUrl = Base64.getUrlEncoder().encodeToString(longUrl.getBytes(StandardCharsets.UTF_8));
        shortUrl = shortUrl.substring(0,7);

        uriRepository.save(new UriEntity(longUrl,shortUrl,LocalDateTime.now()));
        return shortUrl;

    }

    public Page<UriEntity> getAllUriEntities(Pageable pageable){
        return uriRepository.findAll(pageable);
    }

}
