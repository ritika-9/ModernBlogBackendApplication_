package com.ritika.blog.repositories;

import com.ritika.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);


}
