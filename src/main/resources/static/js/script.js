jQuery('document').ready(function(){

    var currentItem  = "";
    var currentItemForDisplay = "";

    var doorLeaf = 1;
    var fanlight = 0;

    var dataJson = "0";
    var door;

    $.ajax({
        url: 'data',
        data: {id: dataJson},
        dataType: 'json',
        success: function (data) {
            alert('success: ' + data.id);
            door = data;

        },
        error: function (data) {
            alert('error:' + data);
        }
    });

    $('.vertical_menu_button').on('click',function(){
            currentItem = $(this).attr('id');
            currentItemForDisplay = $(this).html();
        addNavigation();

        if (currentItem == "doorClass"){
            $('.select_door_class').attr('show','is_alive_lement');
        }
        else{
            $('.select_door_class').attr('show','ghost_lement');
        }

        if (currentItem == "metal"){
            $('.select_metal').attr('show','is_alive_lement');
        }
        else{
            $('.select_metal').attr('show','ghost_lement');
        }

        if (currentItem == "widthDoor")	{
            $('.select_widthDoor').attr('show','is_alive_lement');
            if (doorLeaf == 1){
                $('[DoorLeaf="1"]').attr('show','is_alive_lement');
                $('[DoorLeaf="2"]').attr('show','ghost_lement');
            }
            else{
                $('[DoorLeaf="1"]').attr('show','ghost_lement');
                $('[DoorLeaf="2"]').attr('show','is_alive_lement');
            }
        }
        else{
            $('.select_widthDoor').attr('show','ghost_lement');
        }

        if (currentItem == "heightDoor"){
            $('.select_heightDoor').attr('show','is_alive_lement');

            if (doorLeaf == 1){
                $('[DoorLeaf="1"]').attr('show','is_alive_lement');
                $('[DoorLeaf="2"]').attr('show','ghost_lement');
            }
            else{
                $('[DoorLeaf="1"]').attr('show','ghost_lement');
                $('[DoorLeaf="2"]').attr('show','is_alive_lement');
            }

            if(fanlight==1){
                $('[fanlight="1"]').attr('show','is_alive_lement');
                $('[doorLeaf="'+doorLeaf+'"][fanlight="1"]').attr('show','ghost_lement');
            }
            else{
                $('[fanlight="1"]').attr('show','ghost_lement');
            }

        }
        else{
            $('.select_heightDoor').attr('show','ghost_lement');

        }

        if(currentItem == "deepnessDoor"){
            $('.select_deepnessDoor').attr('show','is_alive_lement');
        }
        else{
            $('.select_deepnessDoor').attr('show','ghost_lement');
        }

        if (currentItem == "thicknessDoorLeaf"){
            $('.select_thicknessDoorLeaf').attr('show','is_alive_lement');
        }
        else {
            $('.select_thicknessDoorLeaf').attr('show','ghost_lement');
        }

        if(currentItem == "sideDoorOpen"){
            $('.select_sideDoorOpen').attr('show','is_alive_lement');
        }
        else{
            $('.select_sideDoorOpen').attr('show','ghost_lement');
        }

        if(currentItem == "additionalDoorSettings"){
            $('.select_additionalDoorSettings').attr('show','is_alive_lement');
        }
        else{
            $('.select_additionalDoorSettings').attr('id','ghost_lement');
        }

        if (currentItem == "doorColor"){
            $('.select_doorColor').attr('show','is_alive_lement');
        }
        else{
            $('.select_doorColor').attr('show','ghost_lement');
        }

    });

    //--------------------------------------
    //select
    //--------------------------------------

    $('.images_door_class').on('click',function(){
            set($(this).attr('data'));
            pickOut(this);
            doorLeaf = $(this).attr('data-LeafDoorLeaf');
    });

    $('.div_images_Color').on('click',function(){
        set($(this).attr('data'));
        pickOut(this);
    });

    $('.ios-toggle').on('click',function(){

        if (currentItem=="metal"){
            oneEnableAllDisable ("metal",this);
        }
        else if(currentItem=="heightDoor"){
            if ($(this).is(':checked')){
                fanlight = 1;
                $('[fanlight="1"]').attr('show','is_alive_lement');
                $('[doorLeaf="'+doorLeaf+'"][fanlight="1"]').attr('show','ghost_lement');
            }
            else{
                fanlight = 0;
                $('[fanlight="1"]').attr('show','ghost_lement');
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
            else if($(this).attr("item")=="stainlessSteelDoorstep"){
                if ($(this).is(':checked')&!($('[Item="doorstep"]').is(':checked'))){
                    $('[Item="doorstep"]').prop('checked', true);
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
            else if($(this).attr("item")=="firstSealingLine"){
                oneEnableAllDisable ("firstSealingLine",this);
            }
            else if($(this).attr("item")=="secondSealingLine"){
                oneEnableAllDisable ("secondSealingLine",this);
            }
            else if($(this).attr("item")=="thirdSealingLine"){
                oneEnableAllDisable ("thirdSealingLine",this);
            }
            else if($(this).attr("item")=="filler"){
                oneEnableAllDisable ("filler",this);
            }
        }

    });

    $('.to_calculate').on('click',function(){

        var strJSON = JSON.stringify(door);
        alert(strJSON);

        $.ajax({
            type: 'POST',
            url: 'data',
            contentType: "application/json",
            data: strJSON,
            dataType: 'json',
            success: function (data) {
                alert('price is: ' + data.price);
            },
            error: function (data) {
                alert('error:' + data);
            }
        });
    });

    $('.input_size').mouseleave(function(){
        set($(this).val());
    });

    //--------------------------------------
    //setter
    //--------------------------------------

    function pickOut(item){

        var attr = $(item).attr('class');

        var elems = $('.'+attr);
        var elemsTotal = elems.length;
        for(var i=0; i<elemsTotal; ++i){
            $(elems[i]).attr('id', 'no')
        }

        $(item).attr('id','checkbox');
    }

    function set(Value){
        $('.vertical_menu_button_rigtht#'+currentItem+'Show strong').html(Value);
        door[currentItem]=Value;
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

    function addNavigation (){
        $('.navigation_panel').remove();
        $('<a>').attr('class','navigation_panel').attr('href','#').html(currentItemForDisplay).appendTo('.navigation_panel_div');
        $('<span>').attr('class','navigation_panel').html('->').appendTo('.navigation_panel_div');
    };

});