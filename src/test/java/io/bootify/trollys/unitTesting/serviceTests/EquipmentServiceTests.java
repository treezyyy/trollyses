package io.bootify.trollys.unitTesting.serviceTests;


import io.bootify.trollys.dto.EquipmentDTO;
import io.bootify.trollys.entity.Equipment;
import io.bootify.trollys.entity.Transport;
import io.bootify.trollys.repos.EquipmentRepository;
import io.bootify.trollys.repos.TransportRepository;
import io.bootify.trollys.service.EquipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTests {

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private TransportRepository transportRepository;

    @InjectMocks
    private EquipmentService equipmentService;

    private EquipmentDTO equipmentDTO;
    private Equipment equipment;
    private Transport transport;

    @BeforeEach
    void setUp() {
        transport = new Transport();
        transport.setVin("789076");

        equipmentDTO = new EquipmentDTO();
        equipmentDTO.setTransport_vin("789076");
        equipmentDTO.setNameEquipment("Тестовое оборудование");
        equipmentDTO.setSerialNumber("12345");
        equipmentDTO.setStatus("Работает");

        equipment = new Equipment();
        equipment.setTransport(transport);
        equipment.setNameEquipment("Тестовое оборудование");
        equipment.setSerialNumber("12345");
        equipment.setStatus("Работает");
    }

    @Test
    void testReadAll() {
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(equipment);

        when(equipmentRepository.findAll()).thenReturn(equipmentList);

        List<EquipmentDTO> result = equipmentService.readAll();

        assertEquals(1, result.size());
        assertEquals(equipmentDTO.getNameEquipment(), result.get(0).getNameEquipment());
        verify(equipmentRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(transportRepository.findByVin(equipmentDTO.getTransport_vin())).thenReturn(transport);
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);

        Equipment createdEquipment = equipmentService.create(equipmentDTO);

        assertNotNull(createdEquipment);
        assertEquals(equipmentDTO.getNameEquipment(), createdEquipment.getNameEquipment());
        verify(transportRepository, times(1)).findByVin(equipmentDTO.getTransport_vin());
        verify(equipmentRepository, times(1)).save(any(Equipment.class));
    }

    @Test
    void testUpdate() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);

        Equipment updatedEquipment = equipmentService.update(1L, equipmentDTO);

        assertNotNull(updatedEquipment);
        assertEquals(equipmentDTO.getNameEquipment(), updatedEquipment.getNameEquipment());
        
        verify(equipmentRepository, times(1)).findById(1L);
        verify(equipmentRepository, times(1)).save(any(Equipment.class));
    }


    @Test
    void testFindByTransportVin() {
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(equipment);

        when(equipmentRepository.findByTransportVin("789076")).thenReturn(equipmentList);

        List<EquipmentDTO> result = equipmentService.findByTransportVin("789076");

        assertEquals(1, result.size());
        assertEquals(equipmentDTO.getNameEquipment(), result.get(0).getNameEquipment());
        verify(equipmentRepository, times(1)).findByTransportVin("789076");
    }

    @Test
    void testUpdateStatus() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);

        Equipment updatedEquipment = equipmentService.updateStatus(1L, "Не работает");

        assertNotNull(updatedEquipment);
        assertEquals("Не работает", updatedEquipment.getStatus());
        verify(equipmentRepository, times(1)).findById(1L);
        verify(equipmentRepository, times(1)).save(any(Equipment.class));
    }

}