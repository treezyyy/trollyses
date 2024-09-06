package io.bootify.trollys.controller.rest;

import io.bootify.trollys.dto.EquipmentDTO;
import io.bootify.trollys.entity.Equipment;
import io.bootify.trollys.service.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping("/all")
    public ResponseEntity<List<EquipmentDTO>> readAll(){
        return new ResponseEntity<>(equipmentService.readAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Equipment> create(@RequestBody EquipmentDTO dto) {
        try {
            Equipment createdEquipment = equipmentService.create(dto);
            return new ResponseEntity<>(createdEquipment, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Ошибка при создании оборудования: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipment> update(@PathVariable Long id, @RequestBody EquipmentDTO equipmentDTO) {
        return new ResponseEntity<>(equipmentService.update(id, equipmentDTO),HttpStatus.OK);
    }

    @DeleteMapping("del/{id}")
    public HttpStatus delete(@PathVariable Long id){
        equipmentService.delete(id);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{vin}")
    public HttpStatus deleteByTransportVin(@PathVariable String vin){
        equipmentService.deleteByTransportVin(vin);
        return HttpStatus.OK;
    }

    @GetMapping("getByVin/{vin}")
    public ResponseEntity<List<EquipmentDTO>> findByTransportVin(@PathVariable String vin){
        return new ResponseEntity<>(equipmentService.findByTransportVin(vin), HttpStatus.OK);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Equipment> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        return new ResponseEntity<>(equipmentService.updateStatus(id, status), HttpStatus.OK);
    }
}
