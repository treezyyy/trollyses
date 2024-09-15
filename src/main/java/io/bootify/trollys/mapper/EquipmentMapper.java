package io.bootify.trollys.mapper;

import io.bootify.trollys.dto.EquipmentDTO;
import io.bootify.trollys.entity.Equipment;
import io.bootify.trollys.entity.Transport;

public class EquipmentMapper { //TODO переделать мапперы с mapstruct

    public static EquipmentDTO toDTO(Equipment equipment) {
        EquipmentDTO dto = new EquipmentDTO();
        dto.setId(equipment.getId());
        dto.setTransport_vin(equipment.getTransport().getVin());
        dto.setNameEquipment(equipment.getNameEquipment());
        dto.setSerialNumber(equipment.getSerialNumber());
        dto.setStatus(equipment.getStatus());
        return dto;
    }

    public static Equipment toEntity(EquipmentDTO dto) {
        Equipment equipment = new Equipment();
        equipment.setId(dto.getId());
        Transport transport = new Transport();
        transport.setVin(dto.getTransport_vin());
        equipment.setTransport(transport);
        equipment.setNameEquipment(dto.getNameEquipment());
        equipment.setSerialNumber(dto.getSerialNumber());
        equipment.setStatus(dto.getStatus());
        return equipment;
    }

}
