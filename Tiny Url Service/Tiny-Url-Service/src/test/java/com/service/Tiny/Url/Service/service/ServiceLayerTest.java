package com.service.Tiny.Url.Service.service;

import com.service.Tiny.Url.Service.entities.UriEntity;
import com.service.Tiny.Url.Service.repository.UriRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
//@SpringBootTest used for integration testing
class ServiceLayerTest {

    @Mock
    UriRepository uriRepository;
    @InjectMocks
    ServiceLayer serviceLayer;

    @Test
    void getLongUrl() {
//        UriEntity uriEntity = new UriEntity();
//        uriEntity.setLongUrl("http://example.com");
//        Mockito.when(uriRepository.findByLongUrl(Mockito.anyString())).thenReturn(Optional.of(uriEntity));
//        String result = serviceLayer.getLongUrl("ah1");
//        assertNotNull(result);
        Mockito.lenient().when(uriRepository.findByLongUrl(Mockito.anyString())).thenReturn(Optional.of(new UriEntity()));

        String result = serviceLayer.getLongUrl("https://short.url/test");

        assertNotNull(result);

    }

    @Test
    void getLongUrlWithoutRpresent() {
//        UriEntity uriEntity = new UriEntity();
//        uriEntity.setLongUrl("http://example.com");
//        Mockito.when(uriRepository.findByLongUrl(Mockito.anyString())).thenReturn(Optional.of(uriEntity));
//        String result = serviceLayer.getLongUrl("ah1");
//        assertNotNull(result);
        Mockito.lenient().when(uriRepository.findByLongUrl(Mockito.anyString())).thenReturn(Optional.empty());

        String result = serviceLayer.getLongUrl("https://short.url/test");

        assertNotNull(result);

    }

    @Test
    void getShortUrl() {
    }

    @Test
    void getAllUriEntities() {
    }
}