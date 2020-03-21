jQuery('document').ready(function () {


    var curreiId = 0;

    $('tr').on('dblclick', function () {

        getFurniture($(this).children('.id').text());

    });

    $('tr').on('click', function () {

        curreiId = $(this).children('.id').text();
        oneEnableAllDisable(this);

    });


    $('#addLine').on('click', function () {
        location.pathname = "/metal/0";
    });

    $('#deletLine').on('click', function () {

        if (curreiId !== 0) {

            $.ajax({
                url: 'metal/' + curreiId,
                method: "DELETE",
                dataType: 'json',
                success: function (data) {
                    location.pathname = "/metal";
                },
                error: function (data) {
                    alert('!ERROR: елемнет удалить не удалось:');
                }
            });

        } else {
            alert('select lines');
        }
    });

    function getFurniture(orderId) {
        location.href = "furniture/" + orderId;
    };

    function oneEnableAllDisable(item) {

        var elems = $('tr[pickOut="on"]');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr('pickOut', 'off');
        }
        $(item).attr('pickOut', 'on');
    }


});