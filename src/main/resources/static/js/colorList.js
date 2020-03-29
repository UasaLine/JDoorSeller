jQuery('document').ready(function () {


    var curreiId = 0;
    var types;

    getFilterList();

    $('tr').on('dblclick', function () {

        getItem($(this).children('.id').text());

    });

    $('tr').on('click', function () {

        curreiId = $(this).children('.id').text();
        oneEnableAllDisable(this);

    });

    $('#typeOfImage').change(function () {

        filterOut();

    });

    $('#addLine').on('click', function () {
        location.pathname = "/color/0";
    });

    $('#deletLine').on('click', function () {

        if (curreiId !== 0) {

            $.ajax({
                url: 'color/' + curreiId,
                method: "DELETE",
                dataType: 'json',
                success: function (data) {
                    location.pathname = "/color";
                },
                error: function (data) {
                    alert('!ERROR: елемнет удалить не удалось:');
                }
            });

        } else {
            alert('select lines');
        }
    });

    function getItem(id) {
        location.href = "color/" + id;
    };

    function oneEnableAllDisable(item) {

        var elems = $('tr[pickOut="on"]');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr('pickOut', 'off');
        }
        $(item).attr('pickOut', 'on');
    }

    function getFilterList() {
        $.ajax({
            url: 'image/types',
            dataType: 'json',
            success: function (data) {
                types = data;
                fillInTypes();
            },
            error: function (data) {
                alert('!ERROR: типы фурнитуры получить не удалось:');
            }
        });
    }

    function fillInTypes() {
        if (types != null) {

            $('#typeOfImage').empty();

            $('#typeOfImage').append(
                $('<option></option>')
            );

            for (var i = 0; i < types.length; ++i) {
                $('#typeOfImage').append($('<option value=' + types[i] + '>' + types[i] + '</option>'));
            }
        }
    }

    function filterOut() {
        var filtrValue = $('#typeOfImage').val();

        var elems = $('.type-line');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {

            var line = $(elems[i]).parent();

            if (filtrValue === '') {
                $(line).removeClass('ghost');
            } else if ($(elems[i]).text() !== filtrValue) {
                $(line).addClass('ghost');
            } else {
                $(line).removeClass('ghost');
            }
        }

    }
});