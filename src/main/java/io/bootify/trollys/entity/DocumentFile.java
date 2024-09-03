package io.bootify.trollys.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="document_file")
public class DocumentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "file_name")
    private String fileName;

    @Column(nullable = false, name = "file_type")
    private String fileType;

    @Column(nullable = false, name = "upload_date")
    private LocalDateTime uploadDate;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
