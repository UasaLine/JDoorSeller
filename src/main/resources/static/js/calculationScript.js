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

    var colors;
    var RestrictionOfSelectionFields;

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
                displayObject();
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

    $('.color_pages').on('click','.pag',function(){
         if($(this).attr('data')==">"){
            var val = Number.parseInt($('.color_pages').attr('data'));
            displayColor(val+1);
         }
         else {
             displayColor($(this).attr('data'));
         }

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
        setDoorFurniture($(this).attr('Item'),$(this).attr('data'));
        representationField($(this).children('span').html());
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

            if ($(this).attr("available")=='yes'){
                if($(this).attr("item")=="doorstep"){
                    if (!$(this).is(':checked')){
                        $('#stainlessSteelDoorstep_checkbox').prop('checked', false);
                        setDoorField($(this).attr('Item'),0);
                        setDoorField($('#stainlessSteelDoorstep_checkbox').attr('Item'),0);
                    }
                    else {
                        setDoorField($(this).attr('Item'),$(this).attr('data'));
                    }
                }
                else if($(this).attr("item")=="stainlessSteelDoorstep"){
                    if ($(this).is(':checked')&!($('#doorstepcheckbox').is(':checked'))){
                        $('#doorstepcheckbox').prop('checked', true);
                        setDoorField($(this).attr('Item'),$(this).attr('data'));
                        setDoorField($('#doorstepcheckbox').attr('Item'),$(this).attr('data'));
                    }
                    else {
                        setDoorField($(this).attr('Item'),0);
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
                    setDoorField($(this).attr('Item'),$(this).attr('data'));
                }
                else if($(this).attr("item")=="secondSealingLine"){
                    oneEnableAllDisable ("secondSealingLine",this);
                    setDoorField($(this).attr('Item'),$(this).attr('data'));
                }
                else if($(this).attr("item")=="thirdSealingLine"){
                    if ($(this).is(':checked')){
                        oneEnableAllDisable ("thirdSealingLine",this);
                        setDoorField($(this).attr('Item'),$(this).attr('data'));
                        setDoorField('sealingLine',3);
                    }
                    else {
                        setDoorField($(this).attr('Item'),0);
                        setDoorField('sealingLine',2);
                    }

                }
                else if($(this).attr("item")=="filler"){
                    oneEnableAllDisable ("filler",this);
                }
            }
            else {
                if($(this).is(':checked')){
                    $(this).prop('checked', false);
                }
                else {
                    $(this).prop('checked', true);
                }
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
        else if(currentItem=='additionally') {
            if ($(this).attr("available")=='yes'){
                if($(this).attr("item")=="nightLock"){
                    if ($(this).is(':checked')){
                        setDoorFurniture($(this).attr('Item'),$(this).attr('data'));
                    }
                    else {
                        setDoorFurniture($(this).attr('Item'),0);
                    }
                }
                else if($(this).attr("item")=="peephole"){
                    if ($(this).is(':checked')){
                        setDoorFurniture($(this).attr('Item'),$(this).attr('data'));
                    }
                    else {
                        setDoorFurniture($(this).attr('Item'),0);
                    }
                }
                else if($(this).attr("item")=="amplifierCloser"){
                    if ($(this).is(':checked')){
                        setDoorFurniture($(this).attr('Item'),$(this).attr('data'));
                    }
                    else {
                        setDoorFurniture($(this).attr('Item'),0);
                    }
                }
            }
            else {
                if($(this).is(':checked')){
                    $(this).prop('checked', false);
                }
                else {
                    $(this).prop('checked', true);
                }
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

                drawCutting();
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

    function setDoorFurniture(fieldName,value){
        var furn = findObject(fieldName,value);
        door.furnitureKit[fieldName] = furn;
        if(currentItem=='lowerLock'){
            if(furn.itCylinderLock == 1){
                $('#namelowerlockCylinder').attr('available','yes');
            }
            else {
                $('#namelowerlockCylinder').attr('available','no');
            }
        }
    }

    function findObject(name,value){
        var sizeRest =  RestrictionOfSelectionFields[name].length;
        var tab = RestrictionOfSelectionFields[name];
        for(var i=0;i<sizeRest;++i){
            if (tab[i].id = value){
                return tab[i];
            }
        }
        return new Object();
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
            drawObject(door,1);
        }
    }

    function pickOut(item){

        var attr = $(item).attr('class');

        var elems = $('.'+attr);
        var elemsTotal = elems.length;
        for(var i=0; i<elemsTotal; ++i){
            $(elems[i]).attr('check', 'no')
        }

        $(item).attr('check','checkbox');
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

    function displayObject(){
        for (var key in door) {
            currentItem = key;
            representationField(door[key]);
        }
    }

    function drawCutting(){
        var k =0.186;
        var sheet = door.sheets;
        for(var i=0; i<sheet.length; ++i){

            //delete
            $('.picture_doorL').remove();
            $('.picture_doorR').remove();

            $('<div>').attr('class','Sheet')
                .attr('style','width:'+sheet[i].width*k+'px; height:'+sheet[i].height*k+'px;')
                .appendTo('.daughter_container1');

            var doorParts = sheet[i].containsParts;
            for(var j=0; j<doorParts.length; ++j){
                $('<div>')
                    .attr('class','doorPart')
                    .attr('style','width:'+doorParts[j].height*k+'px; height:'+doorParts[j].width*k+'px;top:'+doorParts[j].positioningTop*k+'px;left:'+doorParts[j].positioningLeft*k+'px;')
                    .appendTo('.Sheet');
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

        var tab = door.costList.list;
        var size = tab.length;

        for(var i=0;i<size;++i){
            $('<sran>')
                .text(''+tab[i].name+' - '+tab[i].cost)
                .appendTo('#calculateResultDiv');
            $('<br>').appendTo('#calculateResultDiv');
        }

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
                    .attr('data',doorTypes[j].id)
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
                setDoorField($('#checkboxmetal'+i).attr('Item'),$('#checkboxmetal'+i).attr('data'));
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
            data: {idDoorType: door.doorType},
            dataType: 'json',
            success: function (data) {
                //alert('success: ' + data);
                displayMetal(data);
                displayWidthDoorAndHeightDoor(data);
                displayDeepnessDoorAndThicknessDoorLeaf(data);
                colors = data.colors;
                displayColor(0);
                displayadditionalDoorSettings(data);
                displayFurniture('topLock',data.topLock,0,'kit');
                displayFurniture('lowerLock',data.lowerLock,0,'kit');
                displayFurniture('handle',data.handle,0,'');
                displayFurniture('lowerlockCylinder',data.lowerlockCylinder,0,'');
                displayFurniture('closer',data.closer,0,'');
                displayFurniture('endDoorLock',data.endDoorLock,0,'');
                RestrictionOfSelectionFields = data;
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

        if (currentItem =="lowerlockCylinder"){
            $('.select_lowerlockCylinder').attr('show','is_alive_lement');
            currentItemForDisplay = $('#namelowerLockkit').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'lowerLockkit';
        }
        else {
            $('.select_lowerlockCylinder').attr('show','ghost_lement');
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

    function displayColor(bias) {

        var tabSize = colors.length;
        var amountElements =15;
        var amountPag = (tabSize/amountElements).toFixed(0);
        var biasInt = Number.parseInt(bias)*amountElements;

        //delete
        $('.pag').remove();

        for (var i=0; i<amountPag ; ++i) {
            $('<a>').attr('class','pag')
                .attr('data',i)
                .text(''+i+' ')
                .appendTo('.color_pages');
        }
        $('<a>').attr('class','pag')
            .attr('data','>')
            .text(' > ')
            .appendTo('.color_pages');

        $('.color_pages').attr('data',bias);

        for(var i=0; i<amountElements; ++i){
            if ((i+biasInt)<tabSize){
                $('#imagesdoorColorDiv'+i).attr('show','is_alive_lement');
                $('#imagesdoorColorDiv'+i).attr('data',colors[i+biasInt].name);
                $('#imagesdoorColorImg'+i).attr('src',colors[i+biasInt].picturePath);
                $('#imagesdoorColorSpan'+i).text(colors[i+biasInt].name);
            }
            else {available
                $('#imagesdoorColorDiv'+i).attr('show','ghost_lement');
                $('#imagesdoorColorDiv'+i).attr('data',"");
                $('#imagesdoorColorImg'+i).attr('src',"");
                $('#imagesdoorColorSpan'+i).text("");
            }
        }
    }

    function  displayadditionalDoorSettings(data){

        var tabSize = data.additionalDoorSetting.length;

        for (var i=0; i<tabSize ; ++i){
            var additionalName = data.additionalDoorSetting[i].secondItem;

            if(data.additionalDoorSetting[i].startRestriction != data.additionalDoorSetting[i].stopRestriction){
                $('#neme'+additionalName).attr('available','yes');
                $('#'+additionalName+'_checkbox')
                    .attr('available','yes')
                    .attr('data',data.additionalDoorSetting[i].startRestriction);

            }


            if(data.additionalDoorSetting[i].defaultValue == 1){
                $('#'+additionalName+'_checkbox').prop('checked', true);
                setDoorField($('#'+additionalName+'_checkbox').attr('Item'),
                    $('#'+additionalName+'_checkbox').attr('data'));
            }

        }

    }

    function displayFurniture(nameTab,tab,bias,postfixName){

        var tabSize = tab.length;
        var amountElements =4;
        var amountPag = (tabSize/amountElements).toFixed(0);
        var biasInt = Number.parseInt(bias)*amountElements;

        if(tabSize>0){
           $('#name'+nameTab+postfixName).attr('available','yes');
        }
        else {
            $('#name'+nameTab+postfixName).attr('available','no');
        }

        //delete
        $('.'+nameTab+'pag').remove();

        for (var i=0; i<amountPag ; ++i) {
            $('<a>').attr('class','pag')
                .attr('data',i)
                .text(''+i+' ')
                .appendTo('.color_pages');
        }
        $('<a>').attr('class',nameTab+'pag')
            .attr('data','>')
            .text(' > ')
            .appendTo('.'+nameTab+'_pages');

        $('.color_pages').attr('data',bias);

        for(var i=0; i<amountElements; ++i){
            if ((i+biasInt)<tabSize){
                $('#'+nameTab+'Div'+i).attr('show','is_alive_lement');
                $('#'+nameTab+'Div'+i).attr('data',tab[i+biasInt].id);
                $('#'+nameTab+'Img'+i).attr('src',tab[i+biasInt].picturePathFirst);
                $('#'+nameTab+'Span'+i).text(tab[i+biasInt].name);
            }
            else {
                $('#topLockDiv'+i).attr('show','ghost_lement');
                $('#topLockDiv'+i).attr('data',"");
                $('#topLockImg'+i).attr('src',"");
                $('#topLockSpan'+i).text("");
            }
        }

    }
});