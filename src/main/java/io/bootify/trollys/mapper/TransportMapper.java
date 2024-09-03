package io.bootify.trollys.mapper;

import io.bootify.trollys.dto.TransportDTO;
import io.bootify.trollys.entity.Transport;

public class TransportMapper { //TODO переделать мапперы с mapstruct


    public static TransportDTO toDTO(Transport transport){
        TransportDTO dto = new TransportDTO();
        dto.setVin(transport.getVin());
        dto.setInfoteh(transport.getInfoteh());
        dto.setGarageNumber(transport.getGarageNumber());
        dto.setEquipmentList(transport.getEquipmentList().stream()
                .map(EquipmentMapper::toDTO)
                .toList());
        return dto;

    }

    public static Transport toEntity(TransportDTO dto) {
        Transport transport = new Transport();
        transport.setVin(dto.getVin());
        transport.setGarageNumber(dto.getGarageNumber());
        transport.setInfoteh(dto.getInfoteh());
        if (dto.getEquipmentList() != null) {
            transport.setEquipmentList(dto.getEquipmentList().stream()
                    .map(EquipmentMapper::toEntity)
                    .toList());
        }
        return transport;
    }


}
