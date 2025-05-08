package org.example.tasktracker.repository;

import org.example.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
