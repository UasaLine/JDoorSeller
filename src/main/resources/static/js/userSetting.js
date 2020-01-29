jQuery('document').ready(function () {

    if ($('#includesTaxInt').val() == 1) {
        $('#includesTax').prop('checked', true);
    }
    else {
        $('#includesTax').prop('checked', false);
    }

    $('#close').on('click', function () {
        location.href = "/";
    });

});