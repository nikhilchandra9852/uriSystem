package com.service.Tiny.Url.Service.repository;


import com.service.Tiny.Url.Service.entities.UriEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UriRepository extends JpaRepository<UriEntity,String>, PagingAndSortingRepository<UriEntity,String> {

    public Optional<UriEntity> findByShortUrl(String longUrl);
    public Optional<UriEntity> findByLongUrl(String shortUrl);


}
