jQuery('document').ready(function(){


    $('.classGroupDiv').on('click','.classGroupButton',function(){

        var orderId = $('#orderId').text();
        var doorGroup = $(this).attr('id');
        location.href = 'calculation?orderId='+orderId+'&doorGroup='+doorGroup;

    });


});

