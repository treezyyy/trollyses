$(document).ready(function() {
    var url = window.location.href;
    const parts = url.split('/');
    const vin = parts[parts.length - 1];

    if (vin) {
        console.log(url);
        loadEquipment(vin);
        $('#VINname').text('VIN: ' + vin);

        $('#equipment-form').off('submit').on('submit', function(event) {
            event.preventDefault();
            addEquipment(vin);
        });
    } else {
        console.error('Неправильный формат VIN в URL');
    }
});

function loadEquipment(vin) {
    $.get('/api/v1/equipment/getByVin/' + vin, function(equipmentList) {
        console.log('Полученное оборудование:', equipmentList);
        var transportsTableBody = $('#equipment-details');
        transportsTableBody.empty();

        if (Array.isArray(equipmentList) && equipmentList.length > 0) {
            equipmentList.forEach(function(equipment) {
                if (equipment && equipment.nameEquipment && equipment.serialNumber && equipment.status) {
                    var row = '<tr data-id="' + equipment.id + '">' +
                                '<td>' + escapeHTML(equipment.nameEquipment) + '</td>' +
                                '<td>' + escapeHTML(equipment.serialNumber) + '</td>' +
                                '<td class="status-cell">' + escapeHTML(equipment.status) + '</td>' +
                              '</tr>';
                    transportsTableBody.append(row);
                } else {
                    console.error('Некорректные данные оборудования:', equipment);
                }
            });
            $('.status-cell').click(function() {
                var row = $(this).closest('tr');
                var equipmentId = row.data('id');
                var currentStatus = $(this).text().trim();
                var newStatus = currentStatus === 'Работает' ? 'Не работает' : 'Работает';
                updateStatus(equipmentId, newStatus, $(this));
            });
        } else {
            console.error('Пустой или некорректный список оборудования');
            transportsTableBody.append('<tr><td colspan="3">Нет доступного оборудования</td></tr>');
        }
    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.error('Ошибка при загрузке оборудования:', textStatus, errorThrown);
        $('#equipment-details').append('<tr><td colspan="3">Ошибка при загрузке данных</td></tr>');
    });
}

function addEquipment(vin) {
    var equipmentData = {
        transport_vin: vin,
        nameEquipment: $('#nameEquipment').val(),
        serialNumber: $('#serialNumber').val(),
        status: $('#status').val()
    };

    console.log('Отправка данных оборудования:', equipmentData);

    $.ajax({
        type: 'POST',
        url: '/api/v1/equipment/create',
        contentType: 'application/json',
        data: JSON.stringify(equipmentData),
        success: function(response) {
            alert('Оборудование успешно добавлено');
            loadEquipment(vin);
            $('#equipment-form')[0].reset();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('Ошибка при добавлении оборудования: ' + jqXHR.responseText);
            console.error('Ошибка:', textStatus, errorThrown);
        }
    });
}

function updateStatus(id, status, element) {
    $.ajax({
        url: '/api/v1/equipment/update-status/' + id,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({ status: status }),
        success: function(updatedEquipment) {
            console.log('Статус обновлен:', updatedEquipment);
            element.text(status);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Ошибка при обновлении статуса:', textStatus, errorThrown);
        }
    });
}

function escapeHTML(str) {
    return str.replace(/[&<>"']/g, function(match) {
        const escape = {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#39;'
        };
        return escape[match];
    });
}
