jQuery('document').ready(function(){

    /*
    //test delete
    $('.Table > tbody').append('<tr><td class="id">1</td><td>DSN</td><td>DPD</td><td>1</td><td>20000</td></tr>');
    $('.Table > tbody').append('<tr><td class="id">2</td><td>DSN</td><td>DPD</td><td>1</td><td>20000</td></tr>');
    $('.Table > tbody').append('<tr><td class="id">3</td><td>DSN</td><td>DPD</td><td>1</td><td>20000</td></tr>');
    $('.Table > tbody').append('<tr><td class="id">4</td><td>DSN</td><td>DPD</td><td>1</td><td>20000</td></tr>');
    */

    //getInstans order
    var orderId=$('#order_id').text();
    var mode = "loc";
    var currentDoorId = 0;
    var order;

    if (mode == "no"){
        order = new Object();
    }
    else{
        $.ajax({
            url: 'getOrder',
            data: {orderId: orderId},
            dataType: 'json',
            success: function (data) {
                order = data;
                fillOutOfTheObject();
            },
            error: function (data) {
                alert('error:' + data);
            }
        });
    }


    $('#saveOrder').on('click',function(){
        saveOrder(0);
    });

    $('#addDoor').on('click',function(){
        if (orderId == "0"){
            saveOrder(1);
        }
    });

    $('#toChange').on('click',function(){
        changeDoor();
    });

    $('#delete').on('click',function(){
        deleteDoor();
    });

    $('tbody').on('click','tr',function(){

        currentDoorId = $(this).children('.id').text();
        oneEnableAllDisable(this);

    });

    $('tbody').on('dblclick','tr',function(){

        changeDoor();

    });

    function fillObject(){

        order.order_id = $('#order_id').html();
        order.company  = $('#company').val();
        order.partner  = $('#partner').val();
        order.data  = $('#data').val();
        order.releasDate  = $('#releasDate').val();
        order.productionStart  = $('#productionStart').val();
        //order.seller  = $('#seller').val();
        order.comment  = $('#comment').val();

    }
    function fillOutOfTheObject(){
        $('#company').val(order.company);
        $('#partner').val(order.partner);
        $('#data').val(order.data);
        $('#releasDate').val(order.releasDate);
        $('#productionStart').val(order.productionStart);
        //order.seller  = $('#seller').val();
        $('#comment').val(order.comment);

        $('tr').remove();
        $('.Table > tbody').append('<tr><th>id</th><th>name</th><th>doorClass</th><th>metal</th><th>price</th></tr>');
        var doors = order.doors;
        for(var j=0; j<doors.length; ++j) {
            $('.Table > tbody').append('<tr><td class="id">'+doors[j].id+'</td><td>'+doors[j].name+'</td><td>'+doors[j].doorClass+'</td><td>'+doors[j].metal+'</td><td>'+doors[j].price+'</td></tr>');
        }

    }
    function addDoor(orderId){
        location.href="calculation?orderId="+orderId;
    };
    function saveOrder(add){

        fillObject();

        var strJSON = JSON.stringify(order);

        $.ajax({
            type: 'POST',
            url: 'saveOrder',
            contentType: "application/json",
            data: strJSON,
            dataType: 'json',
            success: function (data) {
                $('#order_id').text(data.order_id);
                orderId = $('#order_id').text();
                if (add == 1){
                    addDoor(orderId);
                }

            },
            error: function (data) {
                alert('error:' + data);
            }
        });
    };
    function oneEnableAllDisable (item){

        var elems = $('tr[pickOut="on"]');
        var elemsTotal = elems.length;

        for(var i=0; i<elemsTotal; ++i){
            $(elems[i]).attr('pickOut','off');
        }
        $(item).attr('pickOut','on');
    };
    function changeDoor(){
        location.href="calculation?orderId="+orderId+"&id="+currentDoorId;
    };
    function deleteDoor(){

        $.ajax({
            type: 'DELETE',
            url: 'door?id='+currentDoorId+'&orderId='+orderId,
            dataType: 'json',
            success: function (data) {
                alert("delete completed"+ data);
                order = data;
                fillOutOfTheObject();
            },
            error: function (data) {
                alert('delete error:' + data);
            }
        });

    };
});