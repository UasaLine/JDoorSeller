jQuery('document').ready(function(){
	
	
	//getInstans order
    var orderId=$('#order_id').text();
    var mode = "loc";

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
        saveOrder();
	});

    $('#addDoor').on('click',function(){
        if (orderId == "0"){
            saveOrder();
        }
        addDoor(orderId);
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

        var doors = order.doors;
        for(var j=0; j<doors.length; ++j) {
            $('.Table > tbody').append('<tr><td>'+doors[j].id+'</td><td>'+doors[j].name+'</td><td>'+doors[j].doorClass+'</td><td>'+doors[j].metal+'</td><td>'+doors[j].price+'</td></tr>');
        }
    }
    function addDoor(orderId){
        location.href="calculation?orderId="+orderId;
    };
    function saveOrder(){
        fillObject();

        var strJSON = JSON.stringify(order);

        $.ajax({
            type: 'POST',
            url: 'saveOrder',
            contentType: "application/json",
            data: strJSON,
            dataType: 'json',
            success: function (data) {
                $('#order_id').val(order.order_id);
                orderId=$('#order_id').text();
            },
            error: function (data) {
                alert('error:' + data);
            }
        });
    };
});