jQuery('document').ready(function(){

    var currentItem  = "";
    var currentItemForDisplay = "";
    var currentItemDaughterForDisplay = "";
    var currentItemForDisplayId = "";

    var doorLeaf = 1;
    var fanlight = 0;

    var dataJson = "0";
    var door;

    var failureToSetValue = false;

    var minWidthDoor =0;
    var maxWidthDoor =0;
    var minHeightDoor =0;
    var maxHeightDoor =0;

    var id = $('#id').text();
    var orderId = $('#orderId').text();

    //getInstans door

    var mode = "loc";

    if (mode == "no"){
        door = new Object();
    }
    else{
        $.ajax({
            url: 'door',
            data: {id: id,orderId: orderId},
            dataType: 'json',
            success: function (data) {
                //alert('success: ' + data.id);
                door = data;
                displayОbject(door);
                displayDoorClass();
            },
            error: function (data) {
                alert('error:' + data);
            }
        });
    }

    //--------------------------------------
    //select
    //--------------------------------------

    $('.vertical_menu_button').on('click',function(){

        if ($('#name'+$(this).attr('id')+'').attr('available')=="yes"){
            assignPreviouValue();
            processItemSelection(this);
        }

    });

    $('.navigation_panel_div').on('click','.navigation_panel',function(){

            processItemSelection(this);

    });

    $('.select_door_class').on('click','.images_door_class',function(){
        doorLeaf = $(this).attr('data-LeafDoorLeaf');
        setDoorField($(this).attr('Item'),$(this).attr('data'));
        representationField($(this).attr('dataName'));
        pickOut(this);
        getListOfSelectionFields();

    });

    $('.div_images_Color').on('click',function(){
        setDoorField($(this).attr('Item'),$(this).attr('data'));
        representationField($(this).attr('data'));
        pickOut(this);
    });

    $('.div_images_DoorGlass').on('click',function(){
        setDoorGlassField($(this).attr('Item'),$(this).attr('data'));
        representationField($(this).attr('data'));
        pickOut(this);
    });

    $('.div_images_furniture').on('click',function(){
        setDoorField($(this).attr('Item'),$(this).attr('data'));
        representationField($(this).attr('data'));
        pickOut(this);
    });

    $('.ios-toggle').on('click',function(){

        if (currentItem=="metal"){
            oneEnableAllDisable ("metal",this);
            setDoorField($(this).attr('Item'),$(this).attr('data'));
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
                setDoorField($(this).attr('Item'),$(this).attr('data'));
            }
        }
        else if(currentItem=="thicknessDoorLeaf"){
            if ($(this).is(':checked')){
                oneEnableAllDisable ("thicknessDoorLeaf",this);
                setDoorField($(this).attr('Item'),$(this).attr('data'));
            }
        }
        else if(currentItem=="sideDoorOpen"){
            if ($(this).is(':checked')){
                oneEnableAllDisable ($(this).attr('Item'),this);
                setDoorField($(this).attr('Item'),$(this).attr('data'));
            }
            else{
                setDoorField($(this).attr('Item'),"");
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
        else if(currentItem=="doorGlass"){
            if ($(this).is(':checked')){
                $('.input_doorGlass_div').attr('show','is_alive_lement');
                setDoorField('isDoorGlass',1);
            }
            else{
                $('.input_doorGlass_div').attr('show','ghost_lement');
                setDoorField('isDoorGlass',0);
            }
        }
        representationField($(this).attr('data'));

    });

    $('#buttonCalculate').on('click',function(){

        var strJSON = JSON.stringify(door);

        $.ajax({
            type: 'POST',
            url: 'data',
            contentType: "application/json",
            data: strJSON,
            dataType: 'json',
            success: function (data) {
                //alert('price is: ' + data.price);
                door = data;
                displayPrice();
            },
            error: function (data) {
                alert('error:' + data);
            }
        });
    });

    $('#buttonGetCutteig').on('click',function(){

        var strJSON = JSON.stringify(door);

        $.ajax({
            type: 'POST',
            url: 'cutting',
            contentType: "application/json",
            data: strJSON,
            dataType: 'json',
            success: function (data) {
                alert('width is: ' + data.length);
                drawCutting(data);
            },
            error: function (data) {
                alert('error:' + data);
            }
        });
    });

    $('#buttonSaveDoor').on('click',function(){

        var strJSON = JSON.stringify(door);

        $.ajax({
            type: 'POST',
            url: 'saveDoor',
            contentType: "application/json",
            data: strJSON,
            dataType: 'json',
            success: function (data) {
                toOrder();
            },
            error: function (data) {
                alert('error:' + data);
            }
        });
    });

    $('.to_calculate').hover(
        function(){
            $('.priceghost').attr('show','is_alive_lement');
        },
        function(){
            $('.priceghost').attr('show','ghost_lement');
        });

    $('.input_div').keydown(function(eventObject){

        if (eventObject.which=='13'){

            var itamId = $(this).attr('id');

            if (itamId == "widthDoorDiv"){
                setDoorField("widthDoor",$('#inputWidthDoor').val());
            }
            else if(itamId == "ActivDoorLeafWidthDiv"){
                setDoorField("activDoorLeafWidth",$('#inputActivDoorLeafWidth').val());
            }
            else if(itamId == "heightDoorDiv"){
                setDoorField("heightDoor",$('#inputHeightDoor').val());
            }
            else if(itamId == "fanlightHeightDiv"){
                setDoorField("doorFanlightHeight",$('#inputHeightFanlight').val());
            }
            else if(itamId == "widthDoorGlassDiv"){
                setDoorGlassField("glassWidth",$('#inputWidthDoorGlass').val());
            }
            else if(itamId == "heightDoorGlassDiv"){
                setDoorGlassField("glassHeight",$('#inputHeightDoorGlass').val());
            }
            else if(itamId == "leftDoorGlassDiv"){
                setDoorGlassField("leftGlassPosition",$('#inputleftDoorGlass').val());
            }
            else if(itamId == "bottomDoorGlassDiv"){
                setDoorGlassField("bottomGlassPosition",$('#inputbottomDoorGlass').val());
            }

            representationField();

        }
    });

    //--------------------------------------
    //setter
    //--------------------------------------


    function setDoorField(fieldName,value){

        if (checkInstallationAvailability(fieldName,value)){
            door[fieldName] = value;
            failureToSetValue = false;
        }
        else {
            failureToSetValue = true;
        }
    }

    function setDoorGlassField(fieldName,value){
        door.doorGlass[fieldName] = value;
    }

    function checkInstallationAvailability(fieldName,value){
        if (fieldName == "widthDoor"){
            if(value<minWidthDoor || value>maxWidthDoor){
                alert("ограничение для размера: max -"+maxWidthDoor+" min - "+minWidthDoor);
                return false;
            }
        }
        else if(fieldName == "heightDoor"){
            if(value<minHeightDoor || value>maxHeightDoor){
                alert("ограничение для размера: max -"+maxHeightDoor+" min - "+minHeightDoor);
                return false;
            }
        }
        return true;
    };

    function representationField(value){

        if (!failureToSetValue) {

            var showValue = "";

            //widthDoor
            if (currentItem == "widthDoor") {
                if (door.activDoorLeafWidth && door.activDoorLeafWidth != 0) {
                    showValue = "" + door.widthDoor + " [" + door.activDoorLeafWidth + "]";
                }
                else if (door.widthDoor && door.widthDoor != 0) {
                    showValue = "" + door.widthDoor;
                }
            }
            //heightDoor
            else if (currentItem == "heightDoor") {
                if (door.doorFanlightheight && door.doorFanlightheight != 0) {
                    showValue = "" + door.heightDoor + " [" + door.doorFanlightheight + "]";
                }
                else if (door.heightDoor && door.heightDoor != 0) {
                    showValue = "" + door.heightDoor;
                }
            }
            //sideDoorOpen
            else if (currentItem == "sideDoorOpen") {
                if (door.innerDoorOpen && door.innerDoorOpen != 0) {
                    showValue = "" + door.sideDoorOpen + " [внутренняя]";
                }
                else if (door.sideDoorOpen && door.sideDoorOpen != 0) {
                    showValue = "" + door.sideDoorOpen;
                }
            }
            //doorClass
            else if(currentItem == "doorType"){
                showValue = getTheNameOfTheDoorType(value);
                currentItem = "doorClass";
            }
            else {
                showValue = value;
            }

            set(showValue);
            drawObject();
        }
    }

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
    };

    function allDisable(className){
        //all is ghost_lement
        var elems = $('.'+className+'');
        for(var i=0; i<elems.length; ++i){
            $(elems[i]).attr('show','ghost_lement');
        }
    };

    function addNavigation (){

        $('.navigation_panel').remove();

        $('<a>').attr('class','navigation_panel')
            .attr('href','#')
            .attr('id',currentItemForDisplayId)
            .html(currentItemForDisplay)
            .appendTo('.navigation_panel_div');
        $('<span>').attr('class','navigation_panel')
            .html('->')
            .appendTo('.navigation_panel_div');

        if (currentItemDaughterForDisplay != ""){
            $('<a>').attr('class','navigation_panel')
                .attr('href','#')
                .html(currentItemDaughterForDisplay)
                .appendTo('.navigation_panel_div');
        }



    };

    //--------------------------------------
    //periodic installation
    //--------------------------------------

    function displayОbject(){
        for (var key in door) {
            currentItem = key;
            representationField(door[key]);
        }
    }

    function drawObject(){

        var color = door.doorColor;
        var width = (door.widthDoor*2)/10;
        var height = (door.heightDoor*2)/10;

        if(doorLeaf == 1){
            var sideOpeningL = "InSlit";
            var sideOpeningR = "OutSlit";
        }
        else{
            if(!!door.sideDoorOpen ){
                if(door.sideDoorOpen == "LEFT" ){
                    var sideOpeningL = "InSlit_D_L";
                    var sideOpeningR = "OutSlit_D_L";
                }
                else{
                    var sideOpeningL = "InSlit_D_R";
                    var sideOpeningR = "OutSlit_D_R";
                }
            }
            else{
                var sideOpeningL = "InSlit_D_L";
                var sideOpeningR = "OutSlit_D_L";
            }
        }

        if(door.isDoorGlass==1){
            var glassHeight = (door.doorGlass.glassHeight*2)/10;
            var glassWidth = (door.doorGlass.glassWidth*2)/10;

            var topGlassPosition =0;
            if(door.doorGlass.bottomGlassPosition==0){
                topGlassPosition = (height - glassHeight)/2;
            }
            else {
                topGlassPosition = height - glassHeight - (door.doorGlass.bottomGlassPosition*2)/10;
            }

            var leftGlassPosition
            if(door.doorGlass.leftGlassPosition==0){
                leftGlassPosition = (width-glassWidth)/2;
            }
            else {
                leftGlassPosition = (door.doorGlass.leftGlassPosition*2)/10;
            }

        }

        //delete
        $('.picture_doorL').remove();
        $('.picture_doorR').remove();


        //draw door L
        $('<div>').attr('class','picture_doorL').appendTo('.daughter_container1');
        if	(!!color && !!width && !!height){
            $('<img>').attr('class','color_door').attr('src','images/Door/AColor1/'+color+'.jpg').attr('style','width:'+width+'px; height:'+height+'px;').appendTo('.picture_doorL');
            $('<img>').attr('class','opening_side_images').attr('src','images/Door/'+sideOpeningL+'.png').attr('style','width:'+width+'px; height:'+height+'px;').appendTo('.picture_doorL');

            if(door.isDoorGlass==1){
                   //draw glass
                    $('<img>')
                    .attr('class','opening_side_images')
                    .attr('src','images/Door/window.png')
                    .attr('style','width:'+glassWidth+'px; height:'+glassHeight+'px; top:'+topGlassPosition+'px; left:'+leftGlassPosition+'px;')
                    .appendTo('.picture_doorL');
            }
        }

        //draw door R
        $('<div>').attr('class','picture_doorR').appendTo('.daughter_container1');
        if	(!!color && !!width  && !!height){
            $('<img>').attr('class','color_door').attr('src','images/Door/AColor1/'+color+'.jpg').attr('style','width:'+width+'px; height:'+height+'px;').appendTo('.picture_doorR');
            $('<img>').attr('class','opening_side_images').attr('src','images/Door/'+sideOpeningR+'.png').attr('style','width:'+width+'px; height:'+height+'px;').appendTo('.picture_doorR');
            //draw size
            $('<span>').attr('class','sizeDoorSpan').attr('style','width:20px; height:'+height+'px; ').html(door.heightDoor).appendTo('.picture_doorR');
            $('<span>').attr('class','sizeDoorSpanBottom').attr('style','width:'+width+'px; height:20px;left:0px;top:'+height+'px;').html(door.widthDoor).appendTo('.picture_doorR');

            if(door.isDoorGlass==1){
                //draw glass
                var reflectionleftGlassPosition = (width-glassWidth-leftGlassPosition);
                $('<img>')
                    .attr('class','opening_side_images')
                    .attr('src','images/Door/window.png')
                    .attr('style','width:'+glassWidth+'px; height:'+glassHeight+'px; top:'+topGlassPosition+'px; left:'+reflectionleftGlassPosition+'px;')
                    .appendTo('.picture_doorR');
            }
        }

    }

    function drawCutting(Sheet){
        var k =0.186;
        for(var i=0; i<Sheet.length; ++i){

            //delete
            $('.picture_doorL').remove();
            $('.picture_doorR').remove();

            $('<div>').attr('class','Sheet').attr('style','width:'+Sheet[i].width/k+'px; height:'+Sheet[i].height/k+'px;').appendTo('.daughter_container1');

            var doorParts = Sheet[i].containsParts;
            for(var j=0; j<doorParts.length; ++j){
                $('<div>').attr('class','doorPart').attr('style','width:'+doorParts[j].width/k+'px; height:'+doorParts[j].height/k+'px;top:'+doorParts[j].positioningTop/k+'px;left:'+doorParts[j].positioningLeft/k+'px;').appendTo('.Sheet');
            }
        }
    }

    function toOrder(){
        location.href="order?orderId="+orderId;
    };

    function displayPrice(){
        $('#price').text(door.price);
        $('#discountPrice').text(door.discountPrice);
        $('#priceWithMarkup').text(door.priceWithMarkup);
    };

    function displayDoorClass(){

        for(var i=0; i<door.availableDoorClass.length; ++i){
            var divName = door.availableDoorClass[i].name;
            var divId = door.availableDoorClass[i].id;
            $('<div>')
                .attr('class','images_div')
                .attr('id','doorClass'+divId)
                .appendTo('.select_door_class');

            var doorTypes = door.availableDoorClass[i].doorTypes;
            for(var j=0; j<doorTypes.length; ++j){
                $('<img>')
                    .attr('class','images_door_class')
                    .attr('data',divId)
                    .attr('dataName',divName)
                    .attr('data-LeafDoorLeaf',doorTypes[j].doorLeaf)
                    .attr('src',doorTypes[j].namePicture)
                    .attr('Item','doorType')
                    .appendTo('#doorClass'+divId);
            }
            $('<div>')
                .attr('class','description_images_door_class')
                .text(door.availableDoorClass[i].description)
                .appendTo('#doorClass'+divId);
        }

    };

    function displayMetal(data){

        allDisable('metal_checkbox');

        for(var i=0; i<data.metal.length; ++i){
            $('#metal'+i).attr('show','is_alive_lement');
            $('#nememetal'+i).text(data.metal[i].firstItem);
            $('#checkboxmetal'+i).attr('data',data.metal[i].firstItem);
            if(data.metal[i].defaultValue == 1){
                $('#checkboxmetal'+i).prop('checked', true);
                setDoorField($('#checkboxmetal'+i).attr('Item'),
                    $('#checkboxmetal'+i).attr('data'));
                currentItem = "metal";
                representationField($('#checkboxmetal'+i).attr('data'));
            }
        }
        $('#namemetal').attr('available',"yes");
    };

    function displayWidthDoorAndHeightDoor(data){

        for(var i=0; i<data.widthDoor.length; ++i) {
            minWidthDoor = data.widthDoor[i].startRestriction;
            maxWidthDoor = data.widthDoor[i].stopRestriction;
            $('#namewidthDoor').attr('available',"yes");
        }

        for(var i=0; i<data.widthDoor.length; ++i) {
            minHeightDoor = data.heightDoor[i].startRestriction;
            maxHeightDoor = data.heightDoor[i].stopRestriction;
            $('#nameheightDoor').attr('available',"yes");
        }

    };

    function displayDeepnessDoorAndThicknessDoorLeaf(data){

        allDisable('deepnessDoor_checkbox');
        writeInCheckbox('deepnessDoor',data.deepnessDoor);
        $('#namedeepnessDoor').attr('available',"yes");

        allDisable('thicknessDoorLeaf_checkbox');
        writeInCheckbox('thicknessDoorLeaf',data.thicknessDoorLeaf);
        $('#namethicknessDoorLeaf').attr('available',"yes");

    };

    function writeInCheckbox(nameItem,tab){
        for(var i=0; i<tab.length; ++i){
            $('#'+nameItem+i).attr('show','is_alive_lement');
            $('#neme'+nameItem+i).text(tab[i].firstItem);
            $('#checkbox'+nameItem+i).attr('data',tab[i].firstItem);
            if(tab[i].defaultValue == 1){
                $('#checkbox'+nameItem+i).prop('checked', true);
                setDoorField($('#checkbox'+name+i).attr('Item'),
                    $('#checkbox'+nameItem+i).attr('data'));
                currentItem = nameItem;
                representationField($('#checkbox'+nameItem+i).attr('data'));
            }
        }
    }

    function getListOfSelectionFields(idDoorType) {
        $.ajax({
            url: 'doorlimit',
            data: {idDoorType: "2"},
            dataType: 'json',
            success: function (data) {
                //alert('success: ' + data);
                displayMetal(data);
                displayWidthDoorAndHeightDoor(data);
                displayDeepnessDoorAndThicknessDoorLeaf(data);
                displayColor(data);
            },
            error: function (data) {
                alert('error:' + data);
            }
        });
    };

    function processItemSelection(item){

        currentItem = $(item).attr('id');
        currentItemForDisplay = $(item).html();
        currentItemDaughterForDisplay = "";
        currentItemForDisplayId = currentItem;
        failureToSetValue = false;

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
            $('.select_additionalDoorSettings').attr('show','ghost_lement');
        }

        if (currentItem == "doorColor"){
            $('.select_doorColor').attr('show','is_alive_lement');
        }
        else{
            $('.select_doorColor').attr('show','ghost_lement');
        }

        if(currentItem == "doorGlass"){
            $('.select_doorGlass').attr('show','is_alive_lement');
        }
        else{
            $('.select_doorGlass').attr('show','ghost_lement');
        }

        if (currentItem =="typeDoorGlass"){

            $('.select_typeDoorGlass').attr('show','is_alive_lement');

            currentItemForDisplay = $('#namedoorGlass').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'doorGlass';
        }
        else{
            $('.select_typeDoorGlass').attr('show','ghost_lement');
        }

        if (currentItem =="toning"){

            $('.select_toning').attr('show','is_alive_lement');

            currentItemForDisplay = $('#namedoorGlass').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'doorGlass';
        }
        else{
            $('.select_toning').attr('show','ghost_lement');
        }

        if (currentItem =="armor"){
            $('.select_armor').attr('show','is_alive_lement');

            currentItemForDisplay = $('#namedoorGlass').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'doorGlass';
        }
        else{
            $('.select_armor').attr('show','ghost_lement');
        }

        if (currentItem =="topLockkit"){
            $('.select_topLockkit').attr('show','is_alive_lement');
        }
        else {
            $('.select_topLockkit').attr('show','ghost_lement');
        }

        if (currentItem =="topLock"){
            $('.select_topLock').attr('show','is_alive_lement');
            currentItemForDisplay = $('#nametopLockkit').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'topLockkit';
        }
        else {
            $('.select_topLock').attr('show','ghost_lement');
        }

        if (currentItem =="lowerLockkit"){
            $('.select_lowerLockkit').attr('show','is_alive_lement');
        }
        else {
            $('.select_lowerLockkit').attr('show','ghost_lement');
        }

        if (currentItem =="lowerLock"){
            $('.select_lowerLock').attr('show','is_alive_lement');
            currentItemForDisplay = $('#namelowerLockkit').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'lowerLockkit';
        }
        else {
            $('.select_lowerLock').attr('show','ghost_lement');
        }

        if (currentItem =="handle"){
            $('.select_handle').attr('show','is_alive_lement');
        }
        else {
            $('.select_handle').attr('show','ghost_lement');
        }

        if (currentItem =="additionally"){
            $('.select_additionally').attr('show','is_alive_lement');
        }
        else {
            $('.select_additionally').attr('show','ghost_lement');
        }

        if (currentItem =="closer"){
            $('.select_closer').attr('show','is_alive_lement');
            currentItemForDisplay = $('#nameadditionally').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'additionally';
        }
        else {
            $('.select_closer').attr('show','ghost_lement');
        }

        if (currentItem =="endDoorLock"){
            $('.select_endDoorLock').attr('show','is_alive_lement');
            currentItemForDisplay = $('#nameadditionally').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'additionally';
        }
        else {
            $('.select_endDoorLock').attr('show','ghost_lement');
        }

        addNavigation();

    };

    function  assignPreviouValue(){

        if (currentItem == "widthDoor"){
            setDoorField("widthDoor",$('#inputWidthDoor').val());
        }
        else if(currentItem == "ActivDoorLeafWidth"){
            setDoorField("activDoorLeafWidth",$('#inputActivDoorLeafWidth').val());
        }
        else if(currentItem == "heightDoor"){
            setDoorField("heightDoor",$('#inputHeightDoor').val());
        }
        else if(currentItem == "fanlightHeight"){
            setDoorField("doorFanlightHeight",$('#inputHeightFanlight').val());
        }

        representationField();
    }

    function getTheNameOfTheDoorType(value){
        for(var i=0; i<door.availableDoorClass.length; ++i){
            if (door.availableDoorClass[i].id==value){
                return door.availableDoorClass[i].name;
            }
        }
        return "..."
    };

    function displayColor(data) {

        var tab = data.colors;
        var tabL = tab.length;
        var bias = 0;

        for(var i=0; i<15; ++i){
            if ((i+bias)<tabL){
                $('#doorColorDiv'+i).attr('show','is_alive_lement');
                $('#doorColorDiv'+i).attr('data',tab[i+bias].name);
                $('#doorColorImg'+i).attr('src',tab[i+bias].picturePath);
                $('#doorColorSpan'+i).text(tab[i+bias].name);
            }

        }
    }
});