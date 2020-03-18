jQuery('document').ready(function () {

    var furnitureJavaObject;


    //new instans
    getFurnitureJavaObject();

    $('#save').on('click', function () {

    });

    $('#close').on('click', function () {
        toFurnirure();
    });

    $('#delete').on('click', function () {

        alert('delete '+ getIdFromUrl());
        toFurnirure();
    });

    function toFurnirure() {
        location.pathname = "furniture";
    }

    function getFurnitureJavaObject() {

        $.ajax({
            url: 'item/'+getIdFromUrl(),
            dataType: 'json',
            success: function (data) {
                alert(data.name)
            },
            error: function (data) {
                alert('!ERROR: елемнет фурнитуры получить не удалось:');
            }
        });
    }

    function getIdFromUrl(){
        var url = location.href;
        var id = url.substring(url.lastIndexOf('/') + 1);
        return id;
    }
});