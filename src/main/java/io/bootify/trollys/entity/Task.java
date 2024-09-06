package io.bootify.trollys.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private int basePoints;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    private User assignedUser;

    private LocalDateTime assignedTime;
    private LocalDateTime completedTime;


}
