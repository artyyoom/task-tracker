package org.example.tasktracker.repository;

import org.example.tasktracker.model.Task;
import org.example.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select t from Task t" +
            " where t.userId = :userId" +
            " and t.status = true" +
            " and t.done_timestamp between :startOfDay and :endOfDay")
    List<Task> findFinishedTasksByUserId(
            @Param("userId") Long id,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    @Query("select t from Task t" +
            " where t.userId = :userId" +
            " and t.status = false")
    List<Task> findUnfinishedTasksByUserId(@Param("userId") Long id);

}
