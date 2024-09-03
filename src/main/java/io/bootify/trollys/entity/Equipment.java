package io.bootify.trollys.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name_equipment")
    private String nameEquipment;

    @Column(nullable = false, name = "serial_number")
    private String serialNumber;

    @Column(nullable = false, name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "transport_vin", nullable = false)
    @JsonBackReference
    private Transport transport;

}
