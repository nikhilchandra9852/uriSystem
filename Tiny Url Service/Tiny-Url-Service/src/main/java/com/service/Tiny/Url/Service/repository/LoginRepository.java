package com.service.Tiny.Url.Service.repository;

import com.service.Tiny.Url.Service.entities.Login;
import com.service.Tiny.Url.Service.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing Role entities in the MongoDB database.
 * It extends MongoRepository, providing CRUD operations for Role objects.
 */
public interface LoginRepository extends JpaRepository<Login, String> {

    /**
     * Find a Role by its name.
     *
     * @param name The name of the role represented as an EmployeeRole enum.
     * @return An Optional containing the Role if found, or empty if not found.
     */
    Optional<Login> findByName(String name);
}
