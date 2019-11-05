jQuery('document').ready(function () {

    $('#close').on('click', function () {
        location.href = "doortypelist";
    });

    $('#priceList').on('click', function () {
        if ($(this).is(':checked')) {
            $('#priceSettingByPrice').removeClass('ghost');
        }
        else {
            $('#priceSettingByPrice').addClass('ghost');
        }
    });

});