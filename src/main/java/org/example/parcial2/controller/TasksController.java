package org.example.parcial2.controller;

import org.example.parcial2.dto.ResponseMessage;
import org.example.parcial2.entity.Task;
import org.example.parcial2.entity.TaskState;
import org.example.parcial2.repository.TaskRepository;
import org.example.parcial2.repository.TaskStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskStateRepository taskStateRepository;

    /**
     * Crea una nueva tarea y la almacena en la base de datos. La tarea se crea con el estado "ToDo" de forma automática.
     * @param task La tarea a crear. Debe ser un objeto JSON con la estructura de la entidad Task.
     * @return ResponseEntity con el mensaje de respuesta indicando que la tarea se almacenó exitosamente.
     */
    @PostMapping(value = "tasks/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        Optional<TaskState> state = taskStateRepository.findById(1);
        if(!state.isPresent()){
            return ResponseEntity.status(400).body(
                    new ResponseMessage(
                            "No se pudo registrar la tarea porque el estado de To DO (1) aún no existe en la base de datos"
                    )
            );
        }else{
            task.setState(state.get());
            task.setTimestamp(new Date().getTime());
            taskRepository.save(task);
            return ResponseEntity.status(200).body(
                    new ResponseMessage(
                            "Tarea almacenada exitosamente"
                    )
            );
        }

    }

    /**
     * Obtiene todas las tareas almacenadas en la base de datos.
     *
     * @return ResponseEntity con la lista de tareas almacenadas.
     */
    @GetMapping(value = "tasks/all", produces = "application/json")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.status(200).body(
                taskRepository.findAll()
        );
    }

    /**
     * Obtiene todas las tareas asociadas a un estado específico según su ID.
     *
     * @param stateID El ID del estado para filtrar las tareas.
     * @return ResponseEntity con la lista de tareas asociadas al estado especificado.
     */
    @GetMapping(value = "tasks/state/{stateid}", produces = "application/json")
    public ResponseEntity<?> getAllTasksByStateID(@PathVariable("stateid") int stateID) {
        return ResponseEntity.status(200).body(
                taskRepository.getAllTasksByState(stateID)
        );
    }

    /**
     * Actualiza el estado de una tarea existente según su ID y el ID de un nuevo estado.
     *
     * @param taskID  El ID de la tarea a editar.
     * @param stateID El ID del nuevo estado al que se cambiará la tarea.
     * @return ResponseEntity con el mensaje de respuesta indicando que el estado de la tarea se cambió exitosamente.
     */
    @PutMapping(value = "tasks/{taskid}/state/{stateid}", produces = "application/json")
    public ResponseEntity<?> changeStateOfTask(@PathVariable("taskid") int taskID, @PathVariable("stateid") int stateID) {
        Optional<Task> taskToEdit = taskRepository.findById(taskID);
        if (!taskToEdit.isPresent()) {
            return ResponseEntity.status(404).body(
                    new ResponseMessage(
                            "La tarea que está editando no existe"
                    )
            );
        }

        Optional<TaskState> newState = taskStateRepository.findById(stateID);
        if (!newState.isPresent()) {
            return ResponseEntity.status(404).body(
                    new ResponseMessage(
                            "El estado al cual quiere cambiar la tarea " + taskToEdit.get().getTitle() + " no existe en la base de datos"
                    )
            );
        }
        taskToEdit.get().setState(newState.get());
        taskRepository.save(taskToEdit.get());
        return ResponseEntity.status(200).body(
                new ResponseMessage(
                        "El estado de la tarea cambió exitosamente a " + newState.get().getName()
                )
        );
    }

    /**
     * Elimina una tarea según su ID.
     *
     * @param taskID El ID de la tarea a eliminar.
     * @return ResponseEntity con el mensaje de respuesta indicando que la tarea se eliminó exitosamente.
     */
    @DeleteMapping(value = "tasks/delete/{taskid}", produces = "application/json")
    public ResponseEntity<?> deleteTasksByID(@PathVariable("taskid") int taskID) {
        taskRepository.deleteById(taskID);
        return ResponseEntity.status(200).body(
                new ResponseMessage(
                        "Tarea eliminada con éxito"
                )
        );
    }

}
