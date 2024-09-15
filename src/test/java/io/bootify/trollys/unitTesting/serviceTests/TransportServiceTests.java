package io.bootify.trollys.unitTesting.serviceTests;

import io.bootify.trollys.dto.TransportDTO;
import io.bootify.trollys.entity.Transport;
import io.bootify.trollys.repos.TransportRepository;
import io.bootify.trollys.service.TransportService;
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
class TransportServiceTests {

    @Mock
    private TransportRepository transportRepository;

    @InjectMocks
    private TransportService transportService;

    private TransportDTO transportDTO;
    private Transport transport;

    @BeforeEach
    void setUp() {
        transportDTO = new TransportDTO();
        transportDTO.setVin("789076");
        transportDTO.setGarageNumber("Пенза 1");
        transportDTO.setInfoteh("Инфотех");
        transportDTO.setEquipmentList(new ArrayList<>());

        transport = new Transport();
        transport.setVin("789076");
        transport.setGarageNumber("Пенза 1");
        transport.setInfoteh("Инфотех");
        transport.setEquipmentList(new ArrayList<>());
    }

    @Test
    void testReadAll() {
        List<Transport> transportList = new ArrayList<>();
        transportList.add(transport);

        when(transportRepository.findAll()).thenReturn(transportList);

        List<TransportDTO> result = transportService.readAll();
        assertEquals(1, result.size());
        assertEquals(transportDTO.getVin(), result.get(0).getVin());

        verify(transportRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(transportRepository.existsById(transportDTO.getVin())).thenReturn(false);
        when(transportRepository.save(any(Transport.class))).thenReturn(transport);

        Transport createdTransport = transportService.create(transportDTO);

        assertNotNull(createdTransport);
        assertEquals(transportDTO.getVin(), createdTransport.getVin());
        verify(transportRepository, times(1)).existsById(transportDTO.getVin());
        verify(transportRepository, times(1)).save(any(Transport.class));
    }

    @Test
    void testUpdate() {
        when(transportRepository.findById(transportDTO.getVin())).thenReturn(Optional.of(transport));
        when(transportRepository.save(any(Transport.class))).thenReturn(transport);

        Transport updatedTransport = transportService.update(transportDTO.getVin(), transportDTO);

        assertNotNull(updatedTransport);
        assertEquals(transportDTO.getGarageNumber(), updatedTransport.getGarageNumber());
        verify(transportRepository, times(1)).findById(transportDTO.getVin());
        verify(transportRepository, times(1)).save(any(Transport.class));
    }

    @Test
    void testFindByVin() {
        when(transportRepository.findByVin(transportDTO.getVin())).thenReturn(transport);

        String infoteh = transportService.findByVin(transportDTO.getVin());

        assertNotNull(infoteh);
        assertEquals(transportDTO.getInfoteh(), infoteh);
        verify(transportRepository, times(1)).findByVin(transportDTO.getVin());
    }

}
