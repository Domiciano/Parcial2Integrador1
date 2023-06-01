package org.example.parcial2.repository;

import org.example.parcial2.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {

    @Query(value = "SELECT t FROM Task t WHERE t.state.id = :id")
    List<Task> getAllTasksByState(int id);

}
