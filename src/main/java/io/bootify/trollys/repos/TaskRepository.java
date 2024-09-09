package io.bootify.trollys.repos;

import io.bootify.trollys.entity.Task;
import io.bootify.trollys.entity.TaskStatus;
import io.bootify.trollys.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.status = 'TAKEN'")
    List<Task> getUserTask(@Param("user") User user);
}

