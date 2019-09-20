jQuery('document').ready(function(){


    $('.btn').on('click',function(){

        var orderId = $('#orderId').text();
        var doorGroup = $(this).attr('id');
        location.href = 'calculation?orderId='+orderId+'&doorGroup='+doorGroup;

    });


});

