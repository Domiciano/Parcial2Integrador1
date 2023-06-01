package org.example.parcial2.repository;

import org.example.parcial2.entity.Task;
import org.example.parcial2.entity.TaskState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskStateRepository extends CrudRepository<TaskState, Integer> {

}
