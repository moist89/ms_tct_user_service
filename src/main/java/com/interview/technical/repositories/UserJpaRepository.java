package com.interview.technical.repositories;

import com.interview.technical.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<User,String> {

    List<User> findByEmail(String email);
    User findByEmailIgnoreCase(String email);
    List<User> findByName(String name);

    List<User> findByNameAndEmail(String name ,String email);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsById(String id);


}
