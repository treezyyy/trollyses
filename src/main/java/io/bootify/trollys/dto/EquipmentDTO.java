package io.bootify.trollys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность оборудования")
public class EquipmentDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "VIN транспорта")
    private String transport_vin;

    @Schema(description = "Наименование оборуддования")
    private String nameEquipment;

    @Schema(description = "Серийный номер")
    private String serialNumber;

    @Schema(description = "Статус")
    private String status;

}
