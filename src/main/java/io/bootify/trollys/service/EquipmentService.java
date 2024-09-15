package io.bootify.trollys.service;


import io.bootify.trollys.dto.EquipmentDTO;
import io.bootify.trollys.entity.Equipment;
import io.bootify.trollys.entity.Transport;
import io.bootify.trollys.mapper.EquipmentMapper;
import io.bootify.trollys.repos.EquipmentRepository;
import io.bootify.trollys.repos.TransportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final TransportRepository transportRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentService.class);


    @Transactional
    public List<EquipmentDTO> readAll() {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        return equipmentList.stream()
                .map(EquipmentMapper::toDTO)
                .toList();
    }

    @Transactional
    public Equipment create(EquipmentDTO equipmentDTO) {
        LOGGER.debug("Старт создания оборудования");
        Transport transport = transportRepository.findByVin(equipmentDTO.getTransport_vin());
        Equipment equipment = EquipmentMapper.toEntity(equipmentDTO);
        if (transport == null) {
            LOGGER.error("Транспорт не найден с VIN: {}", equipmentDTO.getTransport_vin());
            throw new EntityNotFoundException("Транспорт не найден с VIN: " + equipmentDTO.getTransport_vin());
        }
        equipment.setTransport(transport);

        return equipmentRepository.save(equipment);
    }

    @Transactional
    public Equipment update(Long id, EquipmentDTO equipmentDTO) {
        LOGGER.debug("Старт обновления оборудования");
        Equipment existingEquipment = equipmentRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Оборудование не найдено с id: {}", id);
                    return new EntityNotFoundException("Оборудование не найдено с id: " + id);
                });
        Transport transport = new Transport();
        transport.setVin(equipmentDTO.getTransport_vin());
        existingEquipment.setTransport(transport);
        existingEquipment.setNameEquipment(equipmentDTO.getNameEquipment());
        existingEquipment.setSerialNumber(equipmentDTO.getSerialNumber());
        existingEquipment.setStatus(equipmentDTO.getStatus());
        return equipmentRepository.save(existingEquipment);
    }

    @Transactional
    public  List<EquipmentDTO> findByTransportVin(String vin){
        LOGGER.debug("Старт поиска по VIN оборудования");
        List<Equipment> equipmentList = equipmentRepository.findByTransportVin(vin);
        return equipmentList.stream()
                .map(EquipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Equipment updateStatus(Long id, String status) {
        Equipment existingEquipment = equipmentRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Оборудование не найдено id: {}", id);
                    return new EntityNotFoundException("Оборудование не найдено с id: " + id);
                });

        existingEquipment.setStatus(status);
        return equipmentRepository.save(existingEquipment);
    }

}
