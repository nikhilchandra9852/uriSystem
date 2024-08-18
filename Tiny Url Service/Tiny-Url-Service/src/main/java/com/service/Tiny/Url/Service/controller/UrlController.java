package com.service.Tiny.Url.Service.controller;


import com.service.Tiny.Url.Service.service.ServiceLayer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.service.Tiny.Url.Service.components.StaticComponents.LONG_URL_IS_NOT_REGISTERED;

@RestController
@RequestMapping("shortservice/api/v1")
public class UrlController {

    @Autowired
    public ServiceLayer serviceLayer;
    @GetMapping("/shortToLong")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> getLongUrl(@Valid @RequestParam(value = "shortUrl") String shortUrl){

        String longUrl = serviceLayer.getLongUrl(shortUrl);
        if(longUrl.equals(LONG_URL_IS_NOT_REGISTERED)){
           return new ResponseEntity<>("Url is not registered ",HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return ResponseEntity.ok(longUrl);
        }
    }

    @GetMapping("/LongtoShort")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> getShortUrl(@Valid @RequestParam(value = "longUrl") String longUrl){

        String shortUrl = serviceLayer.getShortUrl(longUrl);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("all/uris")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUris(@RequestParam(name = "page",defaultValue = "0") int page,
                                        @RequestParam(name ="size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);

        return ResponseEntity.ok(serviceLayer.getAllUriEntities(pageable));
    }

}
