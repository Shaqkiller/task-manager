package com.example.task_manager.controller;


import com.example.task_manager.model.Task;
import com.example.task_manager.repository.TaskRepository;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping ("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<Task> getAllTasks(){return  taskRepository.findAll();}

    @PostMapping
    public Task createTask(@RequestBody Task task) {return taskRepository.save(task); }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id , @RequestBody Task taskDetails) {
        return taskRepository.findById(id)
        .map(task ->{
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.isCompleted());
            Task updatedTask = taskRepository.save(task);
            return ResponseEntity.ok(updatedTask);
        })
                .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping ("/{id}")
public ResponseEntity<?> deleteTask(@PathVariable long id ){
    return taskRepository.findById(id)
    .map (task -> {
        taskRepository.delete(task);
        return  ResponseEntity.ok().build();
        })
            .orElse(ResponseEntity.notFound().build());
    }

}

