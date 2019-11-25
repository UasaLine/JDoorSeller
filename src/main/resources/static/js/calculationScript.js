jQuery('document').ready(function () {

    var currentItem = "";
    var currentItemForDisplay = "";
    var currentItemDaughterForDisplay = "";
    var currentItemForDisplayId = "";

    var doorLeaf = 1;
    var fanlight = 0;

    var door;
    var failureToSetValue = false;

    var colors;
    var RestrictionOfSelectionFields;

    var id = $('#id').text();
    var orderId = $('#orderId').text();

    var sizeMin = 0;
    var sizeMax = 0;


    getNewDoorInstans();

    function FillOutForm(data) {
        door = data;
        id = door.id;
        displayObject(door);
        displayDoorClass2();
        displayPrice();
        fillInTheFieldsToTheTemplate(data.template);
        $('.select_door_class').attr('show', 'is_alive_lement');
    }

    //--------------------------------------
    //select
    //--------------------------------------

    $('.select_door_class').on('click', '.images_door_class', function () {
        doorLeaf = $(this).attr('data-LeafDoorLeaf');
        setDoorField("doorLeaf", doorLeaf);
        setDoorField($(this).attr('Item'), getDoorTypeFromAvailable($(this).attr('data')));
        representationField($(this).attr('dataName'));
        pickOut(this);
        //getListOfSelectionFields();
        getNewDoorInstans();
    });

    $('.list-group-item-action').on('click', function () {
        alert($(this).text());
    });

    $('.vertical_menu_button').on('click', function () {

        $('.arrow_explanations').attr('show', 'ghost_lement');

        if ($('#name' + $(this).attr('id') + '').attr('available') === "yes") {
            assignPreviouValue();
            processItemSelection(this);
        }

    });

    $('.navigation_panel_div').on('click', '.navigation_panel', function () {

        processItemSelection(this);

    });

    $('.color_pages').on('click', '.pag', function () {
        if ($(this).attr('data') == ">") {
            var val = Number.parseInt($('.color_pages').attr('data'));
            displayColor(val + 1);
        }
        else {
            displayColor($(this).attr('data'));
        }

    });

    $('.div_images_Color').on('click', function () {
        setDoorField($(this).attr('Item'), $(this).attr('data'));
        representationField($(this).attr('data'));
        pickOut(this);
    });

    $('.div_images_DoorGlass').on('click', function () {
        setDoorGlassImg($(this).attr('Item'), $(this).attr('data'));
        representationField($(this).children('span').html());
        pickOut(this);
    });

    $('.div_images_furniture').on('click', function () {
        setDoorFurniture($(this).attr('Item'), $(this).attr('data'));
        representationField($(this).children('span').html());
        pickOut(this);

    });

    $('.ios-toggle').on('click', function () {

        var isRepRun = true;

        if (currentItem == "metal") {
            oneEnableAllDisable("metal", this);
            setDoorField($(this).attr('Item'), $(this).attr('data'));
        }
        else if (currentItem == "heightDoor") {
            if ($(this).is(':checked')) {
                fanlight = 1;
                $('[fanlight="1"]').attr('show', 'is_alive_lement');
                $('[doorLeaf="' + doorLeaf + '"][fanlight="1"]').attr('show', 'ghost_lement');
            }
            else {
                fanlight = 0;
                setDoorField('doorFanlightHeight', 0);
                $('[fanlight="1"]').attr('show', 'ghost_lement');
            }
        }
        else if (currentItem == "deepnessDoor") {
            if ($(this).is(':checked')) {
                oneEnableAllDisable("deepnessDoor", this);
                setDoorField($(this).attr('Item'), $(this).attr('data'));
            }
        }
        else if (currentItem == "thicknessDoorLeaf") {
            if ($(this).is(':checked')) {
                oneEnableAllDisable("thicknessDoorLeaf", this);
                setDoorField($(this).attr('Item'), $(this).attr('data'));
            }
        }
        else if (currentItem == "sideDoorOpen") {
            if ($(this).is(':checked')) {
                oneEnableAllDisable($(this).attr('Item'), this);
                setDoorField($(this).attr('Item'), $(this).attr('data'));
            }
            else {
                setDoorField($(this).attr('Item'), "");
            }

        }
        else if (currentItem == "additionalDoorSettings") {

            if ($(this).attr("available") == 'yes') {
                if ($(this).attr("item") == "doorstep") {
                    if (!$(this).is(':checked')) {
                        $('#stainlessSteelDoorstep_checkbox').prop('checked', false);
                        setDoorField($(this).attr('Item'), 0);
                        setDoorField($('#stainlessSteelDoorstep_checkbox').attr('Item'), 0);
                    }
                    else {
                        setDoorField($(this).attr('Item'), $(this).attr('data'));
                    }
                }
                else if ($(this).attr("item") == "stainlessSteelDoorstep") {
                    if ($(this).is(':checked') & !($('#doorstepcheckbox').is(':checked'))) {
                        $('#doorstepcheckbox').prop('checked', true);
                        setDoorField($(this).attr('Item'), $(this).attr('data'));
                        setDoorField($('#doorstepcheckbox').attr('Item'), $(this).attr('data'));
                    }
                    else {
                        setDoorField($(this).attr('Item'), 0);
                    }
                }
                else if ($(this).attr("item") == "doorTrim") {
                    if ($(this).is(':checked')) {
                        $('[Item="topDoorTrim"]').prop('checked', true);
                        $('[Item="leftDoorTrim"]').prop('checked', true);
                        $('[Item="rightDoorTrim"]').prop('checked', true);
                    }
                    else {
                        $('[Item="topDoorTrim"]').prop('checked', false);
                        $('[Item="leftDoorTrim"]').prop('checked', false);
                        $('[Item="rightDoorTrim"]').prop('checked', false);
                    }
                }
                else if ($(this).attr("item") == "topDoorTrim") {
                    if ($(this).is(':checked')) {
                        setDoorField($(this).attr('Item'), 1);
                        $('[Item="doorTrim"]').prop('checked', true);
                    }
                    else {
                        setDoorField($(this).attr('Item'), 0);
                        maybeTurnItOffDoorTrim();
                    }
                }
                else if ($(this).attr("item") == "leftDoorTrim") {
                    if ($(this).is(':checked')) {
                        setDoorField($(this).attr('Item'), 1);
                        $('[Item="doorTrim"]').prop('checked', true);
                    }
                    else {
                        setDoorField($(this).attr('Item'), 0);
                        maybeTurnItOffDoorTrim();
                    }
                }
                else if ($(this).attr("item") == "rightDoorTrim") {
                    if ($(this).is(':checked')) {
                        $('[Item="doorTrim"]').prop('checked', true);
                        setDoorField($(this).attr('Item'), 1);
                    }
                    else {
                        setDoorField($(this).attr('Item'), 0);
                        maybeTurnItOffDoorTrim();
                    }

                }
                else if ($(this).attr("item") == "firstSealingLine") {
                    oneEnableAllDisable("firstSealingLine", this);
                    setDoorField($(this).attr('Item'), $(this).attr('data'));
                }
                else if ($(this).attr("item") == "secondSealingLine") {
                    oneEnableAllDisable("secondSealingLine", this);
                    setDoorField($(this).attr('Item'), $(this).attr('data'));
                }
                else if ($(this).attr("item") == "thirdSealingLine") {
                    if ($(this).is(':checked')) {
                        oneEnableAllDisable("thirdSealingLine", this);
                        setDoorField($(this).attr('Item'), $(this).attr('data'));
                        setDoorField('sealingLine', 3);
                    }
                    else {
                        setDoorField($(this).attr('Item'), 0);
                        setDoorField('sealingLine', 2);
                    }

                }
                else if ($(this).attr("item") == "filler") {
                    oneEnableAllDisable("filler", this);
                }
            }
            else {
                if ($(this).is(':checked')) {
                    $(this).prop('checked', false);
                }
                else {
                    $(this).prop('checked', true);
                }
            }

        }
        else if (currentItem == "doorGlass") {
            if ($(this).is(':checked')) {
                $('.input_doorGlass_div').attr('show', 'is_alive_lement');
                setDoorField('isDoorGlass', 1);
                isRepRun = false;
            }
            else {
                $('.input_doorGlass_div').attr('show', 'ghost_lement');
                setDoorField('isDoorGlass', 0);
            }
        }
        else if (currentItem == 'additionally') {
            if ($(this).attr("available") == 'yes') {
                if ($(this).attr("item") == "nightLock") {
                    if ($(this).is(':checked')) {
                        setDoorFurniture($(this).attr('Item'), $(this).attr('data'));
                    }
                    else {
                        setDoorFurniture($(this).attr('Item'), 0);
                    }
                }
                else if ($(this).attr("item") == "peephole") {
                    if ($(this).is(':checked')) {
                        setDoorFurniture($(this).attr('Item'), $(this).attr('data'));
                    }
                    else {
                        setDoorFurniture($(this).attr('Item'), 0);
                    }
                }
                else if ($(this).attr("item") == "amplifierCloser") {
                    if ($(this).is(':checked')) {
                        setDoorFurniture($(this).attr('Item'), $(this).attr('data'));
                    }
                    else {
                        setDoorFurniture($(this).attr('Item'), 0);
                    }
                }
            }
            else {
                if ($(this).is(':checked')) {
                    $(this).prop('checked', false);
                }
                else {
                    $(this).prop('checked', true);
                }
            }

        }

        if (isRepRun) {
            representationField($(this).attr('data'));
        }
    });

    $('#buttonCalculate').on('click', function () {

        if (checkThCompletedFields()) {

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

        }

    });

    $('#buttonGetCutteig').on('click', function () {

        drawCutting();
        showAdditional_data();
    });

    $('#buttonSaveDoor').on('click', function () {

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
        function () {
            $('.priceghost').attr('show', 'is_alive_lement');
        },
        function () {
            $('.priceghost').attr('show', 'ghost_lement');
        });

    //select namber

    $('#select_set').on('click', function () {

        var number = prepareTheNumber($('.counter_line.numberL.line').text(),
            $('.counter_line.numberR.line').text());
        var idElement = $('#nameSelectForm').attr('data');

        $('#' + idElement).attr('data', number);
        $('#' + idElement).text('' + $('#' + idElement).attr('name') + ' ' + $('#' + idElement).attr('data'));

        closeSelect();

        assignPreviouValue();

    });

    $('#select_cancel').on('click', function () {

        closeSelect();

    });

    $('.select_size').on('click', function () {

        sizeMin = sizeLimMin($(this).attr('id'));
        sizeMax = sizeLimMax($(this).attr('id'));

        select_introduction($(this).attr('data'),
            $(this).attr('name'),
            $(this).attr('id'),
            sizeMin,
            sizeMax);

    });

    $('#addL').on('click', function () {

        add('L', 5,
            null,
            sizeMin,
            sizeMax);

    });

    $('#reduceL').on('click', function () {

        reduce('L', 5,
            sizeMin,
            sizeMax);

    });

    $('#addR').on('click', function () {

        add('R', 5,
            null,
            sizeMin,
            sizeMax);

    });

    $('#reduceR').on('click', function () {

        reduce('R', 5,
            sizeMin,
            sizeMax);

    });


    //--------------------------------------
    //setter
    //--------------------------------------

    function prepareTheNumber(number1, number2) {

        if (number2.length == 1) {
            number2 = '0' + number2;
        }
        return '' + number1 + number2;

    };

    function setDoorField(fieldName, value) {

        if (checkInstallationAvailability(fieldName, value)) {
            door[fieldName] = value;
            failureToSetValue = false;
        }
        else {
            failureToSetValue = true;
        }
    }

    function setDoorGlassImg(fieldName, value) {
        var furn = findObject(fieldName, value);
        door.doorGlass[fieldName] = furn;
    }

    function setDoorGlassField(fieldName, value) {
        door.doorGlass[fieldName] = value;
    }

    function setDoorFurniture(fieldName, value) {
        var furn = findObject(fieldName, value);
        door.furnitureKit[fieldName] = furn;
        if (currentItem == 'lowerLock') {
            if (furn.itCylinderLock == 1) {
                $('#namelowerlockCylinder').attr('available', 'yes');
            }
            else {
                $('#namelowerlockCylinder').attr('available', 'no');
            }
        }
    }

    function findObject(name, value) {
        var sizeRest = RestrictionOfSelectionFields[name].length;
        var tab = RestrictionOfSelectionFields[name];
        for (var i = 0; i < sizeRest; ++i) {
            if (tab[i].id == value) {
                return tab[i];
            }
        }
        return new Object();
    }

    function checkInstallationAvailability(fieldName, value) {
        return true;
    };

    function representationField(value) {

        if (!failureToSetValue) {

            var showValue = "";

            //widthDoor
            if (currentItem == "widthDoor") {
                if (door.activeDoorLeafWidth != 0) {
                    showValue = "" + door.widthDoor + " [" + door.activeDoorLeafWidth + "]";
                }
                else if (door.widthDoor && door.widthDoor != 0) {
                    showValue = "" + door.widthDoor;
                }
            }
            //heightDoor
            else if (currentItem == "heightDoor") {
                if (door.doorFanlightHeight != 0) {
                    showValue = "" + door.heightDoor + " [" + door.doorFanlightHeight + "]";
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
            else if (currentItem == "doorType") {
                showValue = getTheNameOfTheDoorType(value);
                currentItem = "doorClass";
            }
            //doorGlass
            else if (currentItem == "doorGlass") {
                showValue = getNameGlass(value);
            }
            //furnitureKit
            else if (currentItem == "furnitureKit") {

                currentItem = 'topLockkit';
                set(getFurniture(value, 'topLock'));

                currentItem = 'lowerLockkit';
                set(getFurniture(value, 'lowerLock'));

                currentItem = 'handle';
                showValue = getFurniture(value, 'handle');
            }
            //doorstep//DoorTrim
            else if(currentItem == "doorstep"
                || currentItem == "stainlessSteelDoorstep"
                || currentItem == "topDoorTrim"
                || currentItem == "leftDoorTrim"
                || currentItem == "rightDoorTrim"){

                fillCheckbox(currentItem,value);
            }
            else if(currentItem == "firstSealingLine"
                || currentItem == "secondSealingLine"
                || currentItem == "thirdSealingLine"){

                fillCheckbox(currentItem+'_'+value,1);
            }
            else {
                if (value != 0 || value != "0") {
                    showValue = value;
                }
            }

            set(showValue);
            drawObject(door, 1);
        }
    }

    function pickOut(item) {

        var attr = $(item).attr('class');

        var elems = $('.' + attr);
        var elemsTotal = elems.length;
        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr('check', 'no')
        }

        $(item).attr('check', 'checkbox');
    }

    function set(Value) {
        $('.vertical_menu_button_rigtht#' + currentItem + 'Show strong').html(Value);
    }

    function oneEnableAllDisable(Item, thisItem) {

        var elems = $('.ios-toggle[Item="' + Item + '"]');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {
            if ($(thisItem).attr('id') == $(elems[i]).attr('id')) {

            }
            else {
                $(elems[i]).prop('checked', false);
            }
        }
    };

    function allDisable(className) {
        //all is ghost_lement
        var elems = $('.' + className + '');
        for (var i = 0; i < elems.length; ++i) {
            $(elems[i]).attr('show', 'ghost_lement');
        }
    };

    function addNavigation() {

        $('.navigation_panel').remove();

        $('<a>').attr('class', 'navigation_panel')
            .attr('href', '#')
            .attr('id', currentItemForDisplayId)
            .html(currentItemForDisplay)
            .appendTo('.navigation_panel_div');
        $('<span>').attr('class', 'navigation_panel')
            .html('->')
            .appendTo('.navigation_panel_div');

        if (currentItemDaughterForDisplay != "") {
            $('<a>').attr('class', 'navigation_panel')
                .attr('href', '#')
                .html(currentItemDaughterForDisplay)
                .appendTo('.navigation_panel_div');
        }


    };

    //--------------------------------------
    //periodic installation
    //--------------------------------------

    function displayObject(elem) {
        for (var key in elem) {
            currentItem = key;
            representationField(elem[key]);
        }
    }

    function drawCutting() {

        //delete
        $('.Sheet').remove();
        var k = 0.186;
        var sheet = door.sheets;

        for (var i = 0; i < sheet.length; ++i) {

            $('<div>').attr('class', 'Sheet')
                .attr('style', 'width:' + sheet[i].width * k + 'px; height:' + sheet[i].height * k + 'px;')
                .appendTo('.data_L');

            var doorParts = sheet[i].containsParts;
            for (var j = 0; j < doorParts.length; ++j) {
                $('<div>')
                    .attr('class', 'doorPart')
                    .attr('style', 'width:' + doorParts[j].height * k + 'px; height:' + doorParts[j].width * k + 'px;top:' + doorParts[j].positioningTop * k + 'px;left:' + doorParts[j].positioningLeft * k + 'px;')
                    .appendTo('.Sheet');
            }
        }
    }

    function toOrder() {
        location.href = "order?orderId=" + orderId;
    };

    function displayPrice() {

        $('#price').text('Цена: '+door.priceWithMarkup);

        $('.decryption').remove();

        if (door.costList !== null) {
            var tab = door.costList.list;
            var size = tab.length;
            for (var i = 0; i < size; ++i) {
                $('<sran>')
                    .attr('class', 'decryption')
                    .text('' + tab[i].name + ' - ' + tab[i].cost)
                    .appendTo('#calculateResultDiv');
                $('<br>')
                    .attr('class', 'decryption')
                    .appendTo('#calculateResultDiv');
            }
        }
    };

    function displayDoorClass() {

        for (var i = 0; i < door.availableDoorClass.length; ++i) {

            var divName = door.availableDoorClass[i].name;
            var divId = door.availableDoorClass[i].id;

            $('<div>')
                .attr('class', 'images_div')
                .attr('id', 'doorClass' + divId)
                .appendTo('.select_door_class');

            var doorTypes = door.availableDoorClass[i].doorTypes;
            for (var j = 0; j < doorTypes.length; ++j) {
                $('<img>')
                    .attr('class', 'images_door_class')
                    .attr('data', doorTypes[j].id)
                    .attr('dataName', divName)
                    .attr('data-LeafDoorLeaf', doorTypes[j].doorLeaf)
                    .attr('src', doorTypes[j].namePicture)
                    .attr('Item', 'doorType')
                    .appendTo('#doorClass' + divId);
            }
            $('<div>')
                .attr('class', 'description_images_door_class')
                .text(door.availableDoorClass[i].description)
                .appendTo('#doorClass' + divId);
        }

    };

    function displayDoorClass2() {


        $('.class_card').remove();

        for (var i = 0; i < door.availableDoorClass.length; ++i) {

            var divName = door.availableDoorClass[i].name;
            var divId = door.availableDoorClass[i].id;

            var doorTypes = door.availableDoorClass[i].doorTypes;
            for (var j = 0; j < doorTypes.length; ++j) {

                $('<div>')
                    .attr('class', 'card class_card')
                    .attr('id', 'doorClass' + divId)
                    .appendTo('.select_door_class');

                $('<img>')
                    .attr('class', 'images_door_class card-img-top')
                    .attr('data', doorTypes[j].id)
                    .attr('dataName', divName)
                    .attr('data-LeafDoorLeaf', doorTypes[j].doorLeaf)
                    .attr('src', doorTypes[j].namePicture)
                    .attr('Item', 'doorType')
                    .appendTo('#doorClass' + divId);

                $('<div>')
                    .attr('class', 'card-body')
                    .attr('id', 'card-body' + divId + j)
                    .appendTo('#doorClass' + divId);

                $('<p>')
                    .attr('class', 'card-text')
                    .text(doorTypes[j].name)
                    .appendTo('#card-body' + divId + j);
            }
        }
    };


    function displayMetal(data) {

        allDisable('metal_checkbox');


        for (var i = 0; i < data.metal.length; ++i) {
            $('#metal' + i).attr('show', 'is_alive_lement');
            $('#nememetal' + i).text(data.metal[i].firstItem);
            $('#checkboxmetal' + i).attr('data', data.metal[i].firstItem);
            if (data.metal[i].defaultValue == 1) {
                $('#checkboxmetal' + i).prop('checked', true);
                setDoorField($('#checkboxmetal' + i).attr('Item'), $('#checkboxmetal' + i).attr('data'));
                currentItem = "metal";
                representationField($('#checkboxmetal' + i).attr('data'));
            }
        }
        $('#namemetal').attr('available', "yes");


    };

    function displayWidthDoorAndHeightDoor(data) {

        if (data.widthDoor.length > 0) {
            $('#namewidthDoor').attr('available', "yes");
        }

        if (data.heightDoor.length > 0) {
            $('#nameheightDoor').attr('available', "yes");
        }

    };

    function displayDeepnessDoorAndThicknessDoorLeaf(data) {

        allDisable('deepnessDoor_checkbox');
        writeInCheckbox('deepnessDoor', data.deepnessDoor);
        if(data.deepnessDoor.length>1){
            $('#namedeepnessDoor').attr('available', "yes");
        }


        allDisable('thicknessDoorLeaf_checkbox');
        writeInCheckbox('thicknessDoorLeaf', data.thicknessDoorLeaf);
        if(data.thicknessDoorLeaf.length>1) {
            $('#namethicknessDoorLeaf').attr('available', "yes");
        }

    };

    function writeInCheckbox(nameItem, tab) {
        for (var i = 0; i < tab.length; ++i) {
            $('#' + nameItem + i).attr('show', 'is_alive_lement');
            $('#neme' + nameItem + i).text(tab[i].startRestriction);
            $('#checkbox' + nameItem + i).attr('data', tab[i].startRestriction);
            if (tab[i].defaultValue == 1) {
                $('#checkbox' + nameItem + i).prop('checked', true);
                setDoorField(nameItem, tab[i].startRestriction);
                currentItem = nameItem;
                representationField(tab[i].startRestriction);
            }
        }
    }

    function getListOfSelectionFields() {

        if (door.doorType != null) {
            $.ajax({
                url: 'door',
                data: {id: id, typid: door.doorType.id, orderId: orderId},
                dataType: 'json',
                success: function (data) {
                    //alert('success: ' + data);
                    FillOutForm(data);
                    fillInTheFieldsToTheTemplate(data.template);
                },
                error: function (data) {
                    alert('error:' + data);
                }
            });
        }
    };

    function processItemSelection(item) {

        currentItem = $(item).attr('id');
        currentItemForDisplay = $(item).html();
        currentItemDaughterForDisplay = "";
        currentItemForDisplayId = currentItem;
        failureToSetValue = false;

        if (currentItem == "doorClass") {
            $('.select_door_class').attr('show', 'is_alive_lement');
        }
        else {
            $('.select_door_class').attr('show', 'ghost_lement');
        }

        if (currentItem == "metal") {
            $('.select_metal').attr('show', 'is_alive_lement');
        }
        else {
            $('.select_metal').attr('show', 'ghost_lement');
        }

        if (currentItem == "widthDoor") {
            $('.select_widthDoor').attr('show', 'is_alive_lement');
            if (door != null && door.widthDoor != null && door.widthDoor != 0) {
                $('#inputWidthDoor').attr('value', door.widthDoor);
            }
            if (doorLeaf == 1) {
                $('[DoorLeaf="1"]').attr('show', 'is_alive_lement');
                $('[DoorLeaf="2"]').attr('show', 'ghost_lement');
            }
            else {
                $('[DoorLeaf="1"]').attr('show', 'ghost_lement');
                $('[DoorLeaf="2"]').attr('show', 'is_alive_lement');
            }
        }
        else {
            $('.select_widthDoor').attr('show', 'ghost_lement');
        }

        if (currentItem == "heightDoor") {

            $('.select_heightDoor').attr('show', 'is_alive_lement');
            if (door != null && door.heightDoor != null && door.heightDoor != 0) {
                $('#inputHeightDoor').attr('value', door.heightDoor);
            }


            if (doorLeaf == 1) {
                $('[DoorLeaf="1"]').attr('show', 'is_alive_lement');
                $('[DoorLeaf="2"]').attr('show', 'ghost_lement');
            }
            else {
                $('[DoorLeaf="1"]').attr('show', 'ghost_lement');
                $('[DoorLeaf="2"]').attr('show', 'is_alive_lement');
            }

            if (fanlight == 1) {
                $('[fanlight="1"]').attr('show', 'is_alive_lement');
                $('[doorLeaf="' + doorLeaf + '"][fanlight="1"]').attr('show', 'ghost_lement');
            }
            else {
                $('[fanlight="1"]').attr('show', 'ghost_lement');
            }

        }
        else {
            $('.select_heightDoor').attr('show', 'ghost_lement');

        }

        if (currentItem == "deepnessDoor") {
            $('.select_deepnessDoor').attr('show', 'is_alive_lement');
        }
        else {
            $('.select_deepnessDoor').attr('show', 'ghost_lement');
        }

        if (currentItem == "thicknessDoorLeaf") {
            $('.select_thicknessDoorLeaf').attr('show', 'is_alive_lement');
        }
        else {
            $('.select_thicknessDoorLeaf').attr('show', 'ghost_lement');
        }

        if (currentItem == "sideDoorOpen") {
            $('.select_sideDoorOpen').attr('show', 'is_alive_lement');
            displaySideDoorOpen();
        }
        else {
            $('.select_sideDoorOpen').attr('show', 'ghost_lement');
        }

        if (currentItem == "additionalDoorSettings") {
            $('.select_additionalDoorSettings').attr('show', 'is_alive_lement');
        }
        else {
            $('.select_additionalDoorSettings').attr('show', 'ghost_lement');
        }

        if (currentItem == "doorColor") {
            $('.select_doorColor').attr('show', 'is_alive_lement');
        }
        else {
            $('.select_doorColor').attr('show', 'ghost_lement');
        }

        if (currentItem == "doorGlass") {
            $('.select_doorGlass').attr('show', 'is_alive_lement');
            displayGlass();
        }
        else {
            $('.select_doorGlass').attr('show', 'ghost_lement');
        }

        if (currentItem == "typeDoorGlass") {

            $('.select_typeDoorGlass').attr('show', 'is_alive_lement');

            currentItemForDisplay = $('#namedoorGlass').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'doorGlass';
        }
        else {
            $('.select_typeDoorGlass').attr('show', 'ghost_lement');
        }

        if (currentItem == "toning") {

            $('.select_toning').attr('show', 'is_alive_lement');

            currentItemForDisplay = $('#namedoorGlass').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'doorGlass';
        }
        else {
            $('.select_toning').attr('show', 'ghost_lement');
        }

        if (currentItem == "armor") {
            $('.select_armor').attr('show', 'is_alive_lement');

            currentItemForDisplay = $('#namedoorGlass').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'doorGlass';
        }
        else {
            $('.select_armor').attr('show', 'ghost_lement');
        }

        if (currentItem == "topLockkit") {
            $('.select_topLockkit').attr('show', 'is_alive_lement');
        }
        else {
            $('.select_topLockkit').attr('show', 'ghost_lement');
        }

        if (currentItem == "topLock") {
            $('.select_topLock').attr('show', 'is_alive_lement');
            currentItemForDisplay = $('#nametopLockkit').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'topLockkit';
        }
        else {
            $('.select_topLock').attr('show', 'ghost_lement');
        }

        if (currentItem == "lowerLockkit") {
            $('.select_lowerLockkit').attr('show', 'is_alive_lement');
        }
        else {
            $('.select_lowerLockkit').attr('show', 'ghost_lement');
        }

        if (currentItem == "lowerLock") {
            $('.select_lowerLock').attr('show', 'is_alive_lement');
            currentItemForDisplay = $('#namelowerLockkit').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'lowerLockkit';
        }
        else {
            $('.select_lowerLock').attr('show', 'ghost_lement');
        }

        if (currentItem == "handle") {
            $('.select_handle').attr('show', 'is_alive_lement');
        }
        else {
            $('.select_handle').attr('show', 'ghost_lement');
        }

        if (currentItem == "additionally") {
            $('.select_additionally').attr('show', 'is_alive_lement');
        }
        else {
            $('.select_additionally').attr('show', 'ghost_lement');
        }

        if (currentItem == "closer") {
            $('.select_closer').attr('show', 'is_alive_lement');
            currentItemForDisplay = $('#nameadditionally').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'additionally';
        }
        else {
            $('.select_closer').attr('show', 'ghost_lement');
        }

        if (currentItem == "endDoorLock") {
            $('.select_endDoorLock').attr('show', 'is_alive_lement');
            currentItemForDisplay = $('#nameadditionally').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'additionally';
        }
        else {
            $('.select_endDoorLock').attr('show', 'ghost_lement');
        }

        if (currentItem == "lowerlockCylinder") {
            $('.select_lowerlockCylinder').attr('show', 'is_alive_lement');
            currentItemForDisplay = $('#namelowerLockkit').html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'lowerLockkit';
        }
        else {
            $('.select_lowerlockCylinder').attr('show', 'ghost_lement');
        }
        addNavigation();

    };

    function assignPreviouValue() {

        if (currentItem == "widthDoor") {
            setDoorField("widthDoor", $('#inputWidthDoor').attr('data'));
            setDoorField("activDoorLeafWidth", $('#inputActivDoorLeafWidth').attr('data'));
        }
        else if (currentItem == "heightDoor") {
            setDoorField("heightDoor", $('#inputHeightDoor').attr('data'));
            if (fanlight == 1) {
                setDoorField("doorFanlightHeight", $('#inputHeightFanlight').attr('data'));
            }
        }
        else if (currentItem == "doorGlass") {
            setDoorGlassField("glassWidth", $('#inputWidthDoorGlass').attr('data'));
            setDoorGlassField("glassHeight", $('#inputHeightDoorGlass').attr('data'));
            setDoorGlassField("leftGlassPosition", $('#inputleftDoorGlass').attr('data'));
            setDoorGlassField("bottomGlassPosition", $('#inputbottomDoorGlass').attr('data'));
        }


        representationField();
    }

    function getTheNameOfTheDoorType(value) {

        if (value instanceof Object) {
            return value.name;
        }
        else {
            for (var i = 0; i < door.availableDoorClass.length; ++i) {
                var type = door.availableDoorClass[i].doorTypes;
                for (var j = 0; j < type.length; ++j) {
                    if (type[j].id === value) {
                        return door.availableDoorClass[i].name;
                    }
                }
            }
        }
        return ""
    };

    function getDoorTypeFromAvailable(value) {
        for (var i = 0; i < door.availableDoorClass.length; ++i) {
            var type = door.availableDoorClass[i].doorTypes;
            for (var j = 0; j < type.length; ++j) {
                if (type[j].id == value) {
                    return type[j];
                }
            }
        }
        return ""
    };

    function displayColor(bias) {

        var tabSize = colors.length;
        var amountElements = 15;
        var amountPag = (tabSize / amountElements).toFixed(0);
        var biasInt = Number.parseInt(bias) * amountElements;

        //delete
        $('.pag').remove();

        for (var i = 0; i < amountPag; ++i) {
            $('<a>').attr('class', 'pag')
                .attr('data', i)
                .text('' + i + ' ')
                .appendTo('.color_pages');
        }
        $('<a>').attr('class', 'pag')
            .attr('data', '>')
            .text(' > ')
            .appendTo('.color_pages');

        $('.color_pages').attr('data', bias);

        for (var i = 0; i < amountElements; ++i) {
            if ((i + biasInt) < tabSize) {
                $('#imagesdoorColorDiv' + i).attr('show', 'is_alive_lement');
                $('#imagesdoorColorDiv' + i).attr('data', colors[i + biasInt].firstItem);
                $('#imagesdoorColorImg' + i).attr('src', colors[i + biasInt].picturePath);
                $('#imagesdoorColorSpan' + i).text(colors[i + biasInt].firstItem);
            }
            else {
                $('#imagesdoorColorDiv' + i).attr('show', 'ghost_lement');
                $('#imagesdoorColorDiv' + i).attr('data', "");
                $('#imagesdoorColorImg' + i).attr('src', "");
                $('#imagesdoorColorSpan' + i).text("");
            }
        }
    }

    function displayadditionalDoorSettings(data) {

        displayElement("doorstep", data);
        displayElement("stainlessSteelDoorstep", data);

        displayElement("topDoorTrim", data);
        displayElement("leftDoorTrim", data);
        displayElement("rightDoorTrim", data);

        displayElementSealingLine("firstSealingLine", data);
        displayElementSealingLine("secondSealingLine", data);
        displayElementSealingLine("thirdSealingLine", data);


    };

    function displayElement(name, data) {
        var tabSize = data[name].length;
        if (tabSize > 1) {
            $('#name' + name).attr('available', 'yes');
            $('#' + name + '_checkbox').attr('available', 'yes');
        }
    };

    function displayElementSealingLine(name, data) {

        var tab = data[name];
        var tabSize = tab.length;

        if(tabSize>1){
            for (var i = 0; i < tabSize; i++) {

                var lineName = name+'_'+tab[i].startRestriction;

                $('#name' + lineName).attr('available', 'yes');
                $('#' + lineName + '_checkbox').attr('available', 'yes');

            }
        }

    };


    function displayListOfItems(nameTab, tab, bias, postfixName) {

        if (tab != null) {
            var tabSize = tab.length;
            var amountElements = 4;
            var amountPag = (tabSize / amountElements).toFixed(0);
            var biasInt = Number.parseInt(bias) * amountElements;

            if (tabSize > 0) {
                $('#name' + nameTab + postfixName).attr('available', 'yes');
            }
            else {
                $('#name' + nameTab + postfixName).attr('available', 'no');
            }

            //delete
            $('.' + nameTab + 'pag').remove();

            for (var i = 0; i < amountPag; ++i) {
                $('<a>').attr('class', 'pag')
                    .attr('data', i)
                    .text('' + i + ' ')
                    .appendTo('.color_pages');
            }
            $('<a>').attr('class', nameTab + 'pag')
                .attr('data', '>')
                .text(' > ')
                .appendTo('.' + nameTab + '_pages');

            $('.color_pages').attr('data', bias);

            for (var i = 0; i < amountElements; ++i) {
                if ((i + biasInt) < tabSize) {
                    $('#' + nameTab + 'Div' + i).attr('show', 'is_alive_lement');
                    $('#' + nameTab + 'Div' + i).attr('data', tab[i + biasInt].id);
                    $('#' + nameTab + 'Img' + i).attr('src', tab[i + biasInt].picturePathFirst);
                    $('#' + nameTab + 'Span' + i).text(tab[i + biasInt].name);
                }
                else {
                    $('#topLockDiv' + i).attr('show', 'ghost_lement');
                    $('#topLockDiv' + i).attr('data', "");
                    $('#topLockImg' + i).attr('src', "");
                    $('#topLockSpan' + i).text("");
                }
            }

        }
    }

    function getNameGlass(value) {

        if (value !== null) {
            if (typeof value == "object" && value.glassWidth > 0 && value.glassHeight > 0) {
                return '' + value.glassWidth + ' X ' + value.glassHeight;
            }
        }
        return '';

    }

    function displayGlass() {

        if (door.isDoorGlass == 1) {
            $('#checkboxdoorGlass0').prop('checked', true);
            $('.input_doorGlass_div').attr('show', 'is_alive_lement');

            if (door.doorGlass.typeDoorGlass !== null) {
                currentItem = 'typeDoorGlass';
                representationField(door.doorGlass.typeDoorGlass.name);
            }
            if (door.doorGlass.toning !== null) {
                currentItem = 'toning';
                representationField(door.doorGlass.toning.name);
            }
            if (door.doorGlass.armor !== null) {
                currentItem = 'armor';
                representationField(door.doorGlass.armor.name);
            }
            currentItem = 'doorGlass';
            representationField(door.doorGlass);

            $('#inputWidthDoorGlass').attr('value', door.doorGlass.glassWidth);
            $('#inputHeightDoorGlass').attr('value', door.doorGlass.glassHeight);
            $('#inputleftDoorGlass').attr('value', door.doorGlass.leftGlassPosition);
            $('#inputbottomDoorGlass').attr('value', door.doorGlass.bottomGlassPosition);
        }

    }

    function displaySideDoorOpen() {
        $('[data = ' + door.sideDoorOpen + ']').prop('checked', true);
    }

    function getFurniture(value, name) {

        if (value != null && value[name] !== null) {
            return value[name].name;
        }
        return '';
    }

    function checkThCompletedFields() {

        if ((door.widthDoor == 0) && (door.heightDoor == 0)) {
            alert("please select door dimensions!");
            return false;
        }
        if ((door.sideDoorOpen == null)) {
            alert("please select side door open!");
            return false;
        }
        if ((door.doorColor == null)) {
            alert("please select door color!");
            return false;
        }
        if ((doorLeaf == 2) && (door.activeDoorLeafWidth == null || door.activeDoorLeafWidth == 0)) {
            alert("please select active door leaf width!");
            return false;
        }
        return true;
    }

    function showAdditional_data() {
        if ($('.additional_data').attr('show') == 'ghost_lement') {
            $('.additional_data').attr('show', 'yas');
        }
        else {
            $('.additional_data').attr('show', 'ghost_lement');
        }
    }

    function maybeTurnItOffDoorTrim() {
        if (!$('#additionalDoorSettings_topDoorTrim').is(':checked') &&
            !$('#additionalDoorSettings_leftDoorTrim').is(':checked') &&
            !$('#additionalDoorSettings_rightDoorTrim').is(':checked'))

            $('[Item="doorTrim"]').prop('checked', false);
    };

    function sizeLimMin(elemId) {
        if (elemId == 'inputWidthDoor') {
            var tab = RestrictionOfSelectionFields['widthDoor'];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].startRestriction;
                }
            }
            return 0;
        }
        else if (elemId == 'inputHeightDoor') {
            var tab = RestrictionOfSelectionFields['heightDoor'];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].startRestriction;
                }
            }
            return 0;
        }
        else if (elemId == 'inputHeightFanlight') {
            var tab = RestrictionOfSelectionFields['heightFanlight'];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].startRestriction;
                }
            }

        }
        return 0;
    }

    function sizeLimMax(elemId) {
        if (elemId == 'inputWidthDoor') {
            var tab = RestrictionOfSelectionFields['widthDoor'];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].stopRestriction;
                }
            }
            return 5000;
        }
        else if (elemId == 'inputHeightDoor') {
            var tab = RestrictionOfSelectionFields['heightDoor'];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].stopRestriction;
                }
            }
            return 5000;
        }
        else if (elemId == 'inputHeightFanlight') {
            var tab = RestrictionOfSelectionFields['heightFanlight'];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].stopRestriction;
                }
            }
            return 800;
        }
        return 0;
    }

    function getNewDoorInstans() {

        var typid = 0;
        if (door != null) {
            typid = door.doorType.id;
        }

        $.ajax({
            url: 'door',
            data: {id: id, orderId: orderId, typid: typid},
            dataType: 'json',
            success: function (data) {
                FillOutForm(data);
            },
            error: function (data) {
                alert('error:' + data);
            }
        });

    };

    function fillInTheFieldsToTheTemplate(data) {

        if (data != null) {
            displayMetal(data);
            displayWidthDoorAndHeightDoor(data);
            displayDeepnessDoorAndThicknessDoorLeaf(data);
            colors = data.colors;
            displayColor(0);
            displayadditionalDoorSettings(data);
            displayListOfItems('topLock', data.topLock, 0, 'kit');
            displayListOfItems('lowerLock', data.lowerLock, 0, 'kit');
            displayListOfItems('handle', data.handle, 0, '');
            displayListOfItems('lowerlockCylinder', data.lowerlockCylinder, 0, '');
            displayListOfItems('closer', data.closer, 0, '');
            displayListOfItems('endDoorLock', data.endDoorLock, 0, '');
            displayListOfItems('typeDoorGlass', data.typeDoorGlass, 0, '');
            displayListOfItems('toning', data.toning, 0, '');
            displayListOfItems('armor', data.armor, 0, '');
            RestrictionOfSelectionFields = data;

        }
    };

    function fillCheckbox(name,value) {
        if (value==1){
            $('#'+name+'_checkbox').prop('checked', true);
        }
        else {
            $('#'+name+'_checkbox').prop('checked', false);
        }
    };
});