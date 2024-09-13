package io.bootify.trollys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Сущность транспорта")
public class TransportDTO {

    @Schema(description = "VIN борта")
    private String vin;

    @Schema(description = "Гаражный номер")
    private String garageNumber;

    @Schema(description = "Инфортех")
    private String infoteh;

    @Schema(description = "Список оборудовния")
    private List<EquipmentDTO> equipmentList;

}
