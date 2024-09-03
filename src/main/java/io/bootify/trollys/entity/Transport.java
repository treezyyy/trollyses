package io.bootify.trollys.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transport")
public class Transport {

    @Id
    @Column(nullable = false)
    private String vin;

    @Column(nullable = false, name = "garage_number")
    private String garageNumber;

    @Column(nullable = false, name = "infoteh")
    private String infoteh;

    @OneToMany(mappedBy = "transport", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Equipment> equipmentList = new ArrayList<>();

}
