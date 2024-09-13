package io.bootify.trollys.controller.rest;

import io.bootify.trollys.dto.TransportDTO;
import io.bootify.trollys.entity.Transport;
import io.bootify.trollys.service.TransportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transport")
@Tag(name="Контроллер транспорта", description="Контроллер взаимодействия с транспортом")
public class TransportController {

    private final TransportService transportService;

    @Operation(
            summary = "Получение всех зарегистрированных боротов",
            description = "Позволяет получить борты"
    )
    @GetMapping("/all")
    public ResponseEntity<List<TransportDTO>> readAll(){
        return new ResponseEntity<>(transportService.readAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание борота",
            description = "Позволяет создать борт"
    )
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TransportDTO dto){
        try {
            Transport transport = transportService.create(dto);
            return new ResponseEntity<>(transport, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Обновление борта",
            description = "Позволяет обновить борты"
    )
    @PutMapping("/{vin}")
    public ResponseEntity<Transport> update(@PathVariable @Parameter(description = "VIN номер танспорта") String vin, @RequestBody @Parameter(description = "DTO транспорта")  TransportDTO transp){
        return new ResponseEntity<>(transportService.update(vin, transp), HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление борта",
            description = "Позволяет удалить борт"
    )
    @DeleteMapping("/{vin}")
    public HttpStatus delete(@PathVariable @Parameter(description = "VIN номер танспорта") String vin){
        transportService.delete(vin);
        return HttpStatus.OK;
    }

    //Поиск  infoteh по Vin
    @Operation(
            summary = "Получение борта по инфотех номеру",
            description = "Позволяет получить борт по его уникальному инфотех номеру"
    )
    @GetMapping("/infoteh/{vin}")
    public ResponseEntity<String> findByVin(@PathVariable @Parameter(description = "VIN номер танспорта") String vin){
        return new ResponseEntity<>(transportService.findByVin(vin), HttpStatus.OK);
    }

}
