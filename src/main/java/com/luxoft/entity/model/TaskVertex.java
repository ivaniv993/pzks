package com.luxoft.entity.model;

import com.luxoft.entity.model.enumeration.TaskType;

import javax.persistence.*;
import java.util.List;

/**
 * Created by iivaniv on 18.03.2016.
 *
 */
@Entity
@Table(name="TASK_VERTEX", schema = "public")
public class TaskVertex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "upload_id")
    private Etl etl;

    @ManyToOne
    private TaskVertex taskVertex;

    @OneToMany
    private List<TaskVertex> relatedTaskVertex;

    @Column(name = "time")
    private int time;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)

    private TaskType taskType;

    public TaskVertex() { }

    public Etl getEtl() {
        return etl;
    }

    public void setEtl(Etl etl) {
        this.etl = etl;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskVertex getTaskVertex() {
        return taskVertex;
    }

    public void setTaskVertex(TaskVertex taskVertex) {
        this.taskVertex = taskVertex;
    }

    public List<TaskVertex> getRelatedTaskVertex() {
        return relatedTaskVertex;
    }

    public void setRelatedTaskVertex(List<TaskVertex> relatedTaskVertex) {
        this.relatedTaskVertex = relatedTaskVertex;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
