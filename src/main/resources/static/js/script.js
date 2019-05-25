jQuery('document').ready(function(){

    var currentItem  = "";

    var doorLeaf = 1;
    var fanlight = 0;


    $('.vertical_menu_button').on('click',function(){


        currentItem = $(this).attr('id');


        if (currentItem == "doorclass"){
            $('.select_door_class').attr('id','is_alive_lement');
        }
        else{
            $('.select_door_class').attr('id','ghost_lement');
        }

        if (currentItem == "metal"){
            $('.select_metal').attr('id','is_alive_lement');
        }
        else{
            $('.select_metal').attr('id','ghost_lement');
        }

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
            $('.select_deepnessDoor').attr('id','is_alive_lement');
        }
        else{
            $('.select_deepnessDoor').attr('id','ghost_lement');
        }

        if (currentItem == "thicknessDoorLeaf"){
            $('.select_thicknessDoorLeaf').attr('id','is_alive_lement');
        }
        else {
            $('.select_thicknessDoorLeaf').attr('id','ghost_lement');
        }

        if(currentItem == "sideDoorOpen"){
            $('.select_sideDoorOpen').attr('id','is_alive_lement');
        }
        else{
            $('.select_sideDoorOpen').attr('id','ghost_lement');
        }

        if(currentItem == "additionalDoorSettings"){
            $('.select_additionalDoorSettings').attr('id','is_alive_lement');
        }
        else{
            $('.select_additionalDoorSettings').attr('id','ghost_lement');
        }

    });


    $('.images_door_class').on('click',function(){


        set($(this).attr('data'));
        pickOut(this);
        doorLeaf = $(this).attr('data-LeafDoorLeaf');

    });


    $('.ios-toggle').on('click',function(){

        if (currentItem=="metal"){
            oneEnableAllDisable ("metal",this);
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
        else if(currentItem=="deepnessDoor"){
            if ($(this).is(':checked')){
                oneEnableAllDisable ("deepnessDoor",this);
            }
        }
        else if(currentItem=="thicknessDoorLeaf"){
            if ($(this).is(':checked')){
                oneEnableAllDisable ("thicknessDoorLeaf",this);
            }
        }
        else if(currentItem=="sideDoorOpen"){
            if ($(this).is(':checked')){
                oneEnableAllDisable ("sideDoorOpen",this);
            }
        }
        else if(currentItem=="additionalDoorSettings"){
            if($(this).attr("item")=="doorstep"){
                if (!$(this).is(':checked')){
                    $('[Item="stainlessSteelDoorstep"]').prop('checked', false);
                }
            }
            else if($(this).attr("item")=="doorTrim"){
                if ($(this).is(':checked')){
                    $('[Item="topDoorTrim"]').prop('checked', true);
                    $('[Item="leftDoorTrim"]').prop('checked', true);
                    $('[Item="rightDoorTrim"]').prop('checked', true);
                }
                else{
                    $('[Item="topDoorTrim"]').prop('checked', false);
                    $('[Item="leftDoorTrim"]').prop('checked', false);
                    $('[Item="rightDoorTrim"]').prop('checked', false);
                }
            }
            else if($(this).attr("item")=="topDoorTrim"){
                if(($(this).is(':checked')) & (!$('[Item="doorTrim"]').is(':checked'))){
                    $('[Item="doorTrim"]').prop('checked', true);
                }
                else{
                    if((!$('[Item="leftDoorTrim"]').is(':checked'))&(!$('[Item="rightDoorTrim"]').is(':checked'))){
                        $('[Item="doorTrim"]').prop('checked', false);
                    }
                }
            }
            else if($(this).attr("item")=="leftDoorTrim"){
                if(($(this).is(':checked')) & (!$('[Item="doorTrim"]').is(':checked'))){
                    $('[Item="doorTrim"]').prop('checked', true);
                }
                else{
                    if((!$('[Item="topDoorTrim"]').is(':checked'))&(!$('[Item="rightDoorTrim"]').is(':checked'))){
                        $('[Item="doorTrim"]').prop('checked', false);
                    }
                }
            }
            else if($(this).attr("item")=="rightDoorTrim"){
                if(($(this).is(':checked')) & (!$('[Item="doorTrim"]').is(':checked'))){
                    $('[Item="doorTrim"]').prop('checked', true);
                }
                else{
                    if((!$('[Item="leftDoorTrim"]').is(':checked'))&(!$('[Item="topDoorTrim"]').is(':checked'))){
                        $('[Item="doorTrim"]').prop('checked', false);
                    }
                }
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

    function oneEnableAllDisable (Item,thisItem){

        var elems = $('.ios-toggle[Item="'+Item+'"]');
        var elemsTotal = elems.length;

        for(var i=0; i<elemsTotal; ++i){
            if ($(thisItem).attr('id')==$(elems[i]).attr('id')){

            }
            else{
                $(elems[i]).prop('checked', false);
            }
        }
        set($(thisItem).attr('data'));
    }



});