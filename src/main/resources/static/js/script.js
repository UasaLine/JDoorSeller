jQuery('document').ready(function(){

    var currentItem  = "";

    var doorLeaf = 1;
    var fanlight = 0;


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

        if (currentItem == "widthDoor")	{
            $('.select_widthDoor').attr('id','is_alive_lement');
            if (doorLeaf == 1){
                $('[DoorLeaf="1"]').attr('id','is_alive_lement');
                $('[DoorLeaf="2"]').attr('id','ghost_lement');
            }
            else{
                $('[DoorLeaf="1"]').attr('id','ghost_lement');
                $('[DoorLeaf="2"]').attr('id','is_alive_lement');
            }
        }
        else{
            $('.select_widthDoor').attr('id','ghost_lement');
        }

        if (currentItem == "heightDoor"){
            $('.select_heightDoor').attr('id','is_alive_lement');

            if (doorLeaf == 1){
                $('[DoorLeaf="1"]').attr('id','is_alive_lement');
                $('[DoorLeaf="2"]').attr('id','ghost_lement');
            }
            else{
                $('[DoorLeaf="1"]').attr('id','ghost_lement');
                $('[DoorLeaf="2"]').attr('id','is_alive_lement');
            }

            if(fanlight==1){
                $('[fanlight="1"]').attr('id','is_alive_lement');
                $('[doorLeaf="'+doorLeaf+'"][fanlight="1"]').attr('id','ghost_lement');
            }
            else{
                $('[fanlight="1"]').attr('id','ghost_lement');
            }

        }
        else{
            $('.select_heightDoor').attr('id','ghost_lement');

        }

        if(currentItem == "deepnessDoor"){

        }

    });


    $('.images_door_class').on('click',function(){


        set($(this).attr('data'));
        pickOut(this);
        doorLeaf = $(this).attr('data-LeafDoorLeaf');

    });


    $('.ios-toggle').on('click',function(){

        if (currentItem=="metal"){
            if ($(this).is(':checked')){

                var elems = $('.ios-toggle[Item="metal"]');
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
        }
        else if(currentItem=="heightDoor"){
            if ($(this).is(':checked')){
                fanlight = 1;
                $('[fanlight="1"]').attr('id','is_alive_lement');
                $('[doorLeaf="'+doorLeaf+'"][fanlight="1"]').attr('id','ghost_lement');
            }
            else{
                fanlight = 0;
                $('[fanlight="1"]').attr('id','ghost_lement');
            }
        }


    });



    $('.input_size').mouseleave(function(){
        set($(this).val());
    });

    //--------------------------------------
    //seter
    //--------------------------------------

    function pickOut(item){

        var elems = $('.images_door_class');
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