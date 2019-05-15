jQuery('document').ready(function(){

    var currentItem  = "";


    $('.vertical_menu_button').on('click',function(){

        currentItem = $(this).attr('id');


        if (currentItem == "doorclass")
            $('.select_door_class').attr('id','is_alive_lement');
        else
            $('.select_door_class').attr('id','ghost_lement');

        if (currentItem == "metal")
            $('.select_metal').attr('id','is_alive_lement');
        else
            $('.select_metal').attr('id','ghost_lement');

        if (currentItem == "widthDoor")
            $('.select_widthDoor').attr('id','is_alive_lement');
        else
            $('.select_widthDoor').attr('id','ghost_lement');
    });


    $('.images_div').on('click',function(){

        set($(this).children('.images_door_class').attr('id'));
        pickOut(this);

    });


    $('.ios-toggle').on('click',function(){

        if ($(this).is(':checked')){

            var elems = $('.ios-toggle');
            var elemsTotal = elems.length;

            for(var i=0; i<elemsTotal; ++i){
                if ($(this).attr('id')==$(elems[i]).attr('id')){

                }
                else{
                    $(elems[i]).prop('checked', false);
                }
            }
            set($(this).attr('data'));
        }
        else{
            set('');
        }


    });

    //--------------------------------------
    //seter
    //--------------------------------------

    function pickOut(item){

        var elems = $('.images_div');
        var elemsTotal = elems.length;
        for(var i=0; i<elemsTotal; ++i){
            $(elems[i]).attr('id', 'no')
        }

        $(item).attr('id','checkbox');
    }


    function set(Value){

        $('.vertical_menu_button_rigtht#'+currentItem+' strong').html(Value);

    }



});