package io.bootify.trollys.service;

import io.bootify.trollys.dto.TransportDTO;
import io.bootify.trollys.entity.Transport;
import io.bootify.trollys.mapper.EquipmentMapper;
import io.bootify.trollys.mapper.TransportMapper;
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
public class TransportService {

    private final TransportRepository transportRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportService.class);


    @Transactional
    public List<TransportDTO> readAll(){
        LOGGER.debug("Старт чтения всех бортов");
        List<Transport> transportList = transportRepository.findAll();
        return transportList.stream()
                .map(TransportMapper::toDTO)
                .toList();
    }

    @Transactional
    public Transport create(TransportDTO transportDTO){
        if (transportRepository.existsById(transportDTO.getVin())) {
            LOGGER.error("Борта с VIN не найден {}", transportDTO.getVin());
            throw new IllegalArgumentException("Борта с VIN " + transportDTO.getVin() + " не найден");
        }
        Transport transport = TransportMapper.toEntity(transportDTO);
        return transportRepository.save(transport);
    }

    @Transactional
    public Transport update(String vin, TransportDTO dto) {
        Transport existingTransport = transportRepository.findById(vin)
                .orElseThrow(() -> {
                    LOGGER.error("Борт с VIN не найден {}", vin);
                    return new EntityNotFoundException("Борт с VIN " + vin);
                });
        existingTransport.setGarageNumber(dto.getGarageNumber());
        existingTransport.setInfoteh(dto.getInfoteh());
        if (dto.getEquipmentList() != null) {
            existingTransport.setEquipmentList(dto.getEquipmentList().stream()
                    .map(EquipmentMapper::toEntity)
                    .collect(Collectors.toList()));
        }
        return transportRepository.save(existingTransport);
    }

    @Transactional
    public void delete(String vin) {
        Transport transports = transportRepository.findById(vin)
                .orElseThrow(() -> new EntityNotFoundException("Борт с VIN " + vin));
        transportRepository.delete(transports);
    }

    @Transactional
    public String findByVin(String vin) {
        Transport transport = transportRepository.findByVin(vin);
        if (transport == null) {
            throw new EntityNotFoundException("Борт с VIN " + vin);
        }
        return transport.getInfoteh();
    }

}
