package com.cydeo.entity;

import com.cydeo.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity{


    @ManyToOne
    private Project project;

    @ManyToOne
    private User assignedEmployee;

    private String taskSubject;
    private String taskDetail;

    @Column(columnDefinition = "DATE")
    private LocalDate assignedDate;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;
}
