package io.bootify.trollys.service;


import io.bootify.trollys.dto.EquipmentDTO;
import io.bootify.trollys.entity.Equipment;
import io.bootify.trollys.entity.Transport;
import io.bootify.trollys.mapper.EquipmentMapper;
import io.bootify.trollys.repos.EquipmentRepository;
import io.bootify.trollys.repos.TransportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final TransportRepository transportRepository;

    @Transactional
    public List<EquipmentDTO> readAll() {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        return equipmentList.stream()
                .map(EquipmentMapper::toDTO)
                .toList();
    }

    @Transactional
    public Equipment create(EquipmentDTO equipmentDTO) {
        Transport transport = transportRepository.findByVin(equipmentDTO.getTransport_vin());
        Equipment equipment = EquipmentMapper.toEntity(equipmentDTO);
        if (transport == null) {
            throw new EntityNotFoundException("Transport not found with VIN: " + equipmentDTO.getTransport_vin());
        }
        equipment.setTransport(transport);

        return equipmentRepository.save(equipment);
    }

    @Transactional
    public Equipment update(Long id, EquipmentDTO equipmentDTO) {
        Equipment existingEquipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + id));
        Transport transport = new Transport();
        transport.setVin(equipmentDTO.getTransport_vin());
        existingEquipment.setTransport(transport);
        existingEquipment.setNameEquipment(equipmentDTO.getNameEquipment());
        existingEquipment.setSerialNumber(equipmentDTO.getSerialNumber());
        existingEquipment.setStatus(equipmentDTO.getStatus());
        return equipmentRepository.save(existingEquipment);
    }

    //TODO не работает
    @Transactional
    public void delete(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + id));
        System.out.println(equipment.getId());
        try {
            equipmentRepository.delete(equipment);
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }

    }

    //TODO не работает
    @Transactional
    public void deleteByTransportVin(String transportVin) {
        List<Equipment> equipmentList = equipmentRepository.findByTransportVin(transportVin);
        System.out.println("Found " + equipmentList.size() + " equipment items for transport_vin: " + transportVin);
        equipmentRepository.deleteAll(equipmentList);
    }


    @Transactional
    public  List<EquipmentDTO> findByTransportVin(String vin){
        List<Equipment> equipmentList = equipmentRepository.findByTransportVin(vin);
        return equipmentList.stream()
                .map(EquipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Equipment updateStatus(Long id, String status) {
        Equipment existingEquipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + id));

        existingEquipment.setStatus(status);
        return equipmentRepository.save(existingEquipment);
    }

}
