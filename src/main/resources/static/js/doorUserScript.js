jQuery('document').ready(function(){

    if($('#enabled').text()=="1"){
        $('#enabledcheckbox').prop('checked', true);
    }
    else {
        $('#enabledcheckbox').prop('checked', false);
    }


    $('#close').on('click',function() {
        location.href="users";
    });

});