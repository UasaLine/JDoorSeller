jQuery('document').ready(function(){



    var orderId = $('#shadowId').text();
    alert('Hello '+orderId);

    $.ajax({
        url: 'getPrintApp',
        data: {orderId: orderId},
        dataType: 'json',
        success: function (data) {
            alert('it\'s ok');
        },
        error: function (data) {
            alert('error:' + data);
        }
    });

});