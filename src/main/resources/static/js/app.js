$(document).ready(function() {
    loadTransports();

    $('#transport-form').off('submit').on('submit', function(event) {
        event.preventDefault();
        addTransport();
    });
});

function loadTransports() {
    $.get('/api/v1/transport/all', function(data) {
        console.log('Получены данные:', data);
        var transportsTableBody = $('#transports-table-body');
        transportsTableBody.empty();

        if (Array.isArray(data) && data.length > 0) {
            data.forEach(function(transport) {
                if (transport && transport.vin && transport.garageNumber && transport.infoteh) {
                    var row = '<tr>' +
                                '<td><a href="/equipments/' + escapeHTML(transport.vin) + '">' + escapeHTML(transport.vin) + '</a></td>' +
                                '<td>' + escapeHTML(transport.garageNumber) + '</td>' +
                                '<td>' + escapeHTML(transport.infoteh) + '</td>' +
                              '</tr>';
                    transportsTableBody.append(row);
                } else {
                    console.error('Некорректные данные транспорта:', transport);
                }
            });
        } else {
            console.error('Пустой или некорректный список транспортных средств');
            transportsTableBody.append('<tr><td colspan="4">Нет доступных транспортных средств</td></tr>');
        }
    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.error('Ошибка при получении транспортных средств:', textStatus, errorThrown); // Обработка ошибки
        $('#transports-table-body').append('<tr><td colspan="4">Ошибка при загрузке данных</td></tr>');
    });
}

function addTransport() {
    var transportData = {
        vin: $('#vin').val(),
        garageNumber: $('#garageNumber').val(),
        infoteh: $('#infoteh').val()
    };

    $.ajax({
        type: 'POST',
        url: '/api/v1/transport/create',
        contentType: 'application/json',
        data: JSON.stringify(transportData),
        success: function(response) {
            alert('Транспорт успешно добавлен');
            loadTransports();
            $('#transport-form')[0].reset();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('Ошибка при добавлении транспорта: ' + jqXHR.responseText);
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
