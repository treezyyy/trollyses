package io.bootify.trollys.controller.rest;

import io.bootify.trollys.dto.EquipmentDTO;
import io.bootify.trollys.entity.Equipment;
import io.bootify.trollys.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/equipment")
@Tag(name="Контроллер оборудования", description="Контроллер взаимодействия с оборудованием")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentService.class);


    @Operation(
            summary = "Получение оборудований борта",
            description = "Позволяет получить всё оборудование привязанное к борту"
    )
    @GetMapping("/all")
    public ResponseEntity<List<EquipmentDTO>> readAll(){
        return new ResponseEntity<>(equipmentService.readAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание единицы оборудовния",
            description = "Позволяет создать единицы оборудования связанного с конкретным бортом"
    )
    @PostMapping("/create")
    public ResponseEntity<Equipment> create(@RequestBody EquipmentDTO dto) {
        try {
            Equipment createdEquipment = equipmentService.create(dto);
            return new ResponseEntity<>(createdEquipment, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Ошибка при добавлении оборудовния: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Обновление единицы оборудования",
            description = "Позволяет обновить единицу борта по его id"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Equipment> update(@PathVariable @Min(1) @Parameter(description = "ID оборудования") Long id, @RequestBody EquipmentDTO equipmentDTO) {
        return new ResponseEntity<>(equipmentService.update(id, equipmentDTO),HttpStatus.OK);
    }

    @Operation(
            summary = "Получение единицы оборудования",
            description = "Позволяет получить единицу борта по его id"
    )
    @GetMapping("getByVin/{vin}")
    public ResponseEntity<List<EquipmentDTO>> findByTransportVin(@PathVariable @Parameter(description = "VIN номер танспорта") String vin){
        return new ResponseEntity<>(equipmentService.findByTransportVin(vin), HttpStatus.OK);
    }

    @Operation(
            summary = "Обновление стуса оборудования",
            description = "Позволяет обновить статус единицы борта по его id"
    )
    @PutMapping("/update-status/{id}")
    public ResponseEntity<Equipment> updateStatus(@PathVariable @Min(1) @Parameter(description = "ID оборудования") Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        return new ResponseEntity<>(equipmentService.updateStatus(id, status), HttpStatus.OK);
    }
}
