package org.example.parcial2.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TaskState {

    //Se debe insertar los tres estados:
    //id: 1, name: To do
    //id: 2, name: Doing
    //id: 3, name: Done

    @Id
    private int id;
    private String name;


    public TaskState(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TaskState() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
