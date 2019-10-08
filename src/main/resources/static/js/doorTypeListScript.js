jQuery('document').ready(function(){
	
	 var classId = 0;

    $('.list-group').on('click','.list-group-item',function(){
        classId = $(this).attr('data');
        location.href="doortype?classId="+classId;
    });

});