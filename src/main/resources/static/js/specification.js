jQuery('document').ready(function(){

    var doorClassList;

    grtListDoorClassToSelect();

    $('#doorclassselect').change(function () {

        fillInDoorType(getDoorClassbyId($('#doorclassselect').val()));

    });

    $('#doortypeselect').change(function () {

        var doorTypeId = $('#doortypeselect').val();

        $.ajax({
            url: 'specificationbyid',
            data: {typeId: doorTypeId},
            dataType: 'json',
            success: function (data) {

                alert('its OK');

            },
            error: function (data) {
                alert('!ERROR: данные шаблона получить не удалось:');
            }
        });

    });


    $('tbody').on('dblclick','.vary_field',function(e)	{

        var t = e.target || e.srcElement;

        var elm_name = t.tagName.toLowerCase();

        if(elm_name == 'input')	{
            return false;
        }

        addFieldInCell(this);

        $('#edit').focus();
        $('#edit').blur(function()	{
            var val = $(this).val();
            $(this).parent().empty().text(val);
        });
    });

    $(window).keydown(function(event){

        if(event.keyCode == 13) {
            $('#edit').blur();
        }
    });

    $('#addLine').click(function(){

        $('.Table > tbody').append('<tr class="newLiine" id="line0">' +
            '<td class="id">0</td>' +
            '<td class="vary_field text_select">new name</td>' +
            '<td class="vary_field text_input">new value</td>' +
            '<td class="vary_field text_input" >new formula</td>' +
            '</tr>');


        var elementPosition = $("#line0").offset().top;

        $('body,html').animate({scrollTop: elementPosition}, 500);

    });

    function addFieldInCell(jsOb){

        if ($(jsOb).hasClass('text_select')) {
            addSelect(jsOb);
        }
        else if ($(jsOb).hasClass('text_input')){
            addInput(jsOb);
        }
    };
    function addSelect(jsOb){

        var val = $(jsOb).text();

        $(jsOb).empty()
        $('<select>').attr('class', 'form-control')
            .attr('id', 'edit')
            .appendTo(jsOb);

        $('#edit').append($('<option>'+val+'</option>'));

    };
    function addInput(jsOb){

        var val = $(jsOb).html();

        $(jsOb).empty()
        $('<input>')
            .attr('class', 'form-control')
            .attr('type','text')
            .attr('id', 'edit')
            .appendTo(jsOb);

        $('#edit').val(val);

    }

    function grtListDoorClassToSelect() {

        $.ajax({
            url: 'doorclassis',
            dataType: 'json',
            success: function (data) {
                doorClassList = data;
                fillInDoorClass(doorClassList);

            },
            error: function (data) {
                alert('!ERROR: данные о классах получить не удалось:');
            }
        });
    }
    function fillInDoorClass(listClass) {

        $('#doorclassselect').empty();

        $('#doorclassselect').append(
            $('<option></option>')
        );

        for (var i = 0; i < listClass.length; ++i) {
            $('#doorclassselect').append($('<option value=' + listClass[i].id + '>' + listClass[i].name + '</option>'));
        }

    }
    function fillInDoorType(doorClass) {

        $('#doortypeselect').empty();

        $('#doortypeselect').append(
            $('<option></option>')
        );

        for (var i = 0; i < doorClass.doorTypes.length; ++i) {
            $('#doortypeselect').append(
                $('<option value=' + doorClass.doorTypes[i].id + '>' + doorClass.doorTypes[i].name + '</option>')
            );
        }

    }
    function getDoorClassbyId(id) {

        if (id == null && id == 0) {
            alert('error id is null!')
        }

        for (var i = 0; i < doorClassList.length; ++i) {
            if (doorClassList[i].id == id) {
                return doorClassList[i];
            }
        }
        alert('error doorClass no found!')
        return 0;

    }

});