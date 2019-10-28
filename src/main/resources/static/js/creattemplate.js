jQuery('document').ready(function () {

    var doorClassList;
    var template;
    var restriction;

    //new instans
    grtListDoorClassToSelect();

    //new Template instans
    function getDoorTemplate() {

        var doorTypeId = $('#doortypeselect').val();

        $.ajax({
            url: 'getTemplate',
            data: {idDoorType: doorTypeId},
            dataType: 'json',
            success: function (data) {

                template = data.template;
                restriction = data.restriction;

                installFromTemplatAfterSelectingType();
                fillInAlLSelectAfterSelectingType();

            },
            error: function (data) {
                alert('!ERROR: данные шаблона получить не удалось:');
            }
        });
    }


    $('#doorclassselect').change(function () {

        fillInDoorType(getDoorClassbyId($('#doorclassselect').val()));

    });

    $('#doortypeselect').change(function () {

        getDoorTemplate();

    });


    $('#metalDiv').change('.metalSelect', function () {

        saveInJavaObjectforMetal('metal');
        if (allFieldsAreFilled('.metalSelect')) {
            addMetalField();
        }
        fillInMetal();

    });

    $('#widthDoorDiv').change('.widthDoorInput', function () {
        saveInJavaObjectSize('widthDoor');
        if (allFieldsAreFilled('.widthDoorInput')) {
            addInputField('widthDoorInput', 'widthDoorDiv');
        }
    });
    $('#widthSizMaxMinDiv').change(function () {
        saveInJavaObjectSize('widthDoor');
    });

    $('#heightDoorDiv').change('.heightDoorInput', function () {
        saveInJavaObjectSize('heightDoor');
        if (allFieldsAreFilled('.heightDoorInput')) {
            addInputField('heightDoorInput', 'heightDoorDiv');
        }
    });
    $('#heightSizMaxMinDiv').change(function () {
        saveInJavaObjectSize('heightDoor');
    });
    $('#deepnessDoorDiv').change('.deepnessDoorInput', function () {
        saveInJavaObjectSize('deepnessDoor');
        if (allFieldsAreFilled('.deepnessDoorInput')) {
            addInputField('deepnessDoorInput', 'deepnessDoorDiv');
        }
    });
    $('#thicknessDoorLeafDiv').change('.thicknessDoorLeafInput', function () {
        saveInJavaObjectSize('thicknessDoorLeaf');
        if (allFieldsAreFilled('.thicknessDoorLeafInput')) {
            addInputField('thicknessDoorLeafInput', 'thicknessDoorLeafDiv');
        }
    });

    $('#colorsDiv').change('.colorsSelect', function () {
        saveInJavaObjectColor('colors');
        if (allFieldsAreFilled('.colorsSelect')) {
            addSelectField('colors');
        }
        fillInColor();
    });

    $('#doorstepDiv').change('.doorstepSelect', function () {
        saveInJavaObjectSelect('doorstep');
        if (allFieldsAreFilled('.doorstepSelect')) {
            addSelectField('doorstep');
        }
        fillInSelector('.doorstepSelect', 'doorstep');
    });
    $('#stainlessSteelDoorstepDiv').change('.stainlessSteelDoorstepSelect', function () {
        saveInJavaObjectSelect('stainlessSteelDoorstep');
        if (allFieldsAreFilled('.stainlessSteelDoorstepSelect')) {
            addSelectField('stainlessSteelDoorstep');
        }
        fillInSelector('.stainlessSteelDoorstepSelect', 'stainlessSteelDoorstep');
    });

    $('#firstSealingLineDiv').change('.firstSealingLineSelect', function () {
        addNewFieldAndfillInforSealingLine('firstSealingLine');
    });
    $('#secondSealingLineDiv').change('.secondSealingLineSelect', function () {
        addNewFieldAndfillInforSealingLine('secondSealingLine');
    });
    $('#thirdSealingLineDiv').change('.thirdSealingLineSelect', function () {
        addNewFieldAndfillInforSealingLine('thirdSealingLine');
    });

    //furnitur

    $('#topLockDiv').change('.topLockSelect', function () {
        addNewFieldAndfillInforFurnitur('topLock');//nameJavaObject
    });
    $('#lowerLockDiv').change('.lowerLockSelect', function () {
        addNewFieldAndfillInforFurnitur('lowerLock');//nameJavaObject
    });
    $('#handleDiv').change('.handleSelect', function () {
        addNewFieldAndfillInforFurnitur('handle');//nameJavaObject
    });
    $('#lowerlockCylinderDiv').change('.lowerlockCylinderSelect', function () {
        addNewFieldAndfillInforFurnitur('lowerlockCylinder');//nameJavaObject
    });
    $('#toplockCylinderDiv').change('.toplockCylinderSelect', function () {
        addNewFieldAndfillInforFurnitur('toplockCylinder');//nameJavaObject
    });
    $('#topInLockDecorDiv').change('.topInLockDecorSelect', function () {
        addNewFieldAndfillInforFurnitur('topInLockDecor');//nameJavaObject
    });
    $('#topOutLockDecorDiv').change('.topOutLockDecorSelect', function () {
        addNewFieldAndfillInforFurnitur('topOutLockDecor');//nameJavaObject
    });
    $('#lowerInLockDecorDiv').change('.lowerInLockDecorSelect', function () {
        addNewFieldAndfillInforFurnitur('lowerInLockDecor');//nameJavaObject
    });
    $('#lowerOutLockDecorDiv').change('.lowerOutLockDecorSelect', function () {
        addNewFieldAndfillInforFurnitur('lowerOutLockDecor');
    });

    //additionally furnitur

    $('#closerDiv').change('.closerSelect', function () {
        addNewFieldAndfillInforFurnitur('closer');//nameJavaObject
    });
    $('#endDoorLockDiv').change('.endDoorLockSelect', function () {
        addNewFieldAndfillInforFurnitur('endDoorLock');//nameJavaObject
    });

    //glass

    $('#typeDoorGlassDiv').change('.typeDoorGlassSelect', function () {
        addNewFieldAndfillInforFurnitur('typeDoorGlass');//nameJavaObject
    });
    $('#toningDiv').change('.toningSelect', function () {
        addNewFieldAndfillInforFurnitur('toning');//nameJavaObject
    });
    $('#armorDiv').change('.armorSelect', function () {
        addNewFieldAndfillInforFurnitur('armor');//nameJavaObject
    });


    $('#saveTemplate').on('click', function () {

        var templateJSON = JSON.stringify(template);

        $.ajax({
            type: 'POST',
            url: 'saveTemplate',
            contentType: "application/json",
            data: templateJSON,
            //dataType: 'json',
            success: function (data) {
                alert('request it OK');
            },
            error: function (data) {
                alert('error: request');
            }
        });
    });

    function grtListDoorClassToSelect() {

        $.ajax({
            url: 'doorclassis',
            dataType: 'json',
            success: function (data) {
                doorClassList = data;
                fillInDoorClass(doorClassList);

            },
            error: function (data) {
                alert('!ERROR: данные о классах получить не удалось:');
            }
        });
    }

    function fillInDoorClass(listClass) {

        $('#doorclassselect').empty();

        $('#doorclassselect').append(
            $('<option></option>')
        );

        for (var i = 0; i < listClass.length; ++i) {
            $('#doorclassselect').append($('<option value=' + listClass[i].id + '>' + listClass[i].name + '</option>'));
        }

    }

    function fillInDoorType(doorClass) {

        $('#doortypeselect').empty();

        $('#doortypeselect').append(
            $('<option></option>')
        );

        for (var i = 0; i < doorClass.doorTypes.length; ++i) {
            $('#doortypeselect').append(
                $('<option value=' + doorClass.doorTypes[i].id + '>' + doorClass.doorTypes[i].name + '</option>')
            );
        }

    }

    function getDoorClassbyId(id) {

        if (id == null && id == 0) {
            alert('error id is null!')
        }

        for (var i = 0; i < doorClassList.length; ++i) {
            if (doorClassList[i].id == id) {
                return doorClassList[i];
            }
        }
        alert('error doorClass no found!')
        return 0;

    }


    function installFromTemplatAfterSelectingType() {

        installFromTemplateSize('widthDoor');
        installFromTemplateSize('heightDoor');
        installFromTemplateSize('deepnessDoor');
        installFromTemplateSize('thicknessDoorLeaf');
        installFromTemplateMetal();
        installFromTemplateColor();
        installFromTemplateSelect('doorstep');
        installFromTemplateSelect('stainlessSteelDoorstep');
        installFromTemplateSealingLine('firstSealingLine');
        installFromTemplateSealingLine('secondSealingLine');
        installFromTemplateSealingLine('thirdSealingLine');

        installFromTemplateFurnitur('topLock');
        installFromTemplateFurnitur('lowerLock');
        installFromTemplateFurnitur('handle');
        installFromTemplateFurnitur('lockCylinder');
        installFromTemplateFurnitur('toplockCylinder');
        installFromTemplateFurnitur('topInLockDecor');
        installFromTemplateFurnitur('topOutLockDecor');
        installFromTemplateFurnitur('lowerInLockDecor');
        installFromTemplateFurnitur('lowerOutLockDecor');

        installFromTemplateFurnitur('closer');
        installFromTemplateFurnitur('endDoorLock');

        installFromTemplateFurnitur('typeDoorGlass');
        installFromTemplateFurnitur('toning');
        installFromTemplateFurnitur('armor');

    }

    function installPairOfValues(nameJavaObject, min, max) {
        $('#' + nameJavaObject + 'Min').val(min);
        $('#' + nameJavaObject + 'Max').val(max);
    }

    function installOneValue(nameJavaObject, val) {
        var selector = '.' + nameJavaObject + 'Input';
        if (allFieldsAreFilled(selector)) {
            addSizeField(nameJavaObject);
        }
        elem = getNotCompletedFields(selector);
        $(elem).val(val);

        if (allFieldsAreFilled(selector)) {
            addSizeField(nameJavaObject);
        }
    }

    function installFromTemplateSize(nameJavaObject) {

        var table = template[nameJavaObject];
        var length = table.length;

        for (var i = 0; i < length; ++i) {
            if (table[i].pairOfValues == 1) {
                installPairOfValues(nameJavaObject, table[i].startRestriction, table[i].stopRestriction);
            }
            else {
                installOneValue(nameJavaObject, table[i].startRestriction);
            }

        }
    }

    function installFromTemplateMetal() {

        var table = template.metal;
        var length = table.length;
        var selector = '.metalSelect';
        var elem = null;

        for (var i = 0; i < length; ++i) {
            if (allFieldsAreFilled(selector)) {
                addMetalField();
            }

            elem = getNotCompletedFields(selector);
            fillInFieldFromLimit('#' + $(elem).attr('id'), restriction.metal);
            setValueInSelect('#' + $(elem).attr('id'),table[i].firstItem);

            addMetalField();
        }
    }

    function installFromTemplateColor() {

        var table = template.colors;
        var length = table.length;
        var selector = '.colorsSelect';
        var elem = null;

        for (var i = 0; i < length; ++i) {
            if (allFieldsAreFilled(selector)) {
                addSelectField('colors');
            }
            elem = getNotCompletedFields(selector);
            fillInFieldFromColor('#' + $(elem).attr('id'), restriction.colors);
            setValueInSelectInt('#' + $(elem).attr('id'),table[i].id);

            addSelectField('colors');

        }
    }

    function installFromTemplateSelect(nameJavaObject) {

        var table = template[nameJavaObject];
        var length = table.length;
        var selector = '.' + nameJavaObject + 'Select';
        var elem = null;

        for (var i = 0; i < length; ++i) {
            if (allFieldsAreFilled(selector)) {
                addSelectField(nameJavaObject);
            }
            elem = getNotCompletedFields(selector);
            //$(elem).append($('<option value=' + table[i].startRestriction + '>' + translateToBoolean(table[i].startRestriction) + '</option>'));
            //$(elem).find("option:contains('+table[i].firstItem+')").attr("selected", "selected");
            fillInFieldFromLimiBoolToInt('#' + $(elem).attr('id'), restriction[nameJavaObject]);
            setValueInSelectInt('#' + $(elem).attr('id'),table[i].startRestriction);
            addSelectField(nameJavaObject);
        }
    }

    function installFromTemplateSealingLine(nameJavaObject) {

        var table = template[nameJavaObject];
        var length = table.length;
        var selector = '.' + nameJavaObject + 'Select';
        var elem = null;

        for (var i = 0; i < length; ++i) {
            if (allFieldsAreFilled(selector)) {
                addSelectField(nameJavaObject);
            }
            elem = getNotCompletedFields(selector);
            //$(elem).append($('<option value=' + table[i].startRestriction + '>' + table[i].startRestriction + '</option>'));
            //$(elem).find("option:contains(table[i].startRestriction)").attr("selected", "selected");
            fillInFieldFromLimiSealingLine('#' + $(elem).attr('id'), restriction[nameJavaObject]);
            setValueInSelectInt('#' + $(elem).attr('id'),table[i].startRestriction);
            addSelectField(nameJavaObject);
        }
    }

    function installFromTemplateFurnitur(nameJavaObject) {

        var table = template[nameJavaObject];

        if (table != null) {
            var length = table.length;
            var selector = '.' + nameJavaObject + 'Select';
            var elem = null;

            for (var i = 0; i < length; ++i) {
                if (allFieldsAreFilled(selector)) {
                    addSelectField(nameJavaObject);
                }
                elem = getNotCompletedFields(selector);
                //$(elem).append($('<option value=' + table[i].id + '>' + table[i].name + '</option>'));
                //$(elem).find("option:contains(table[i].name)").attr("selected", "selected");
                fillInFieldFromLimiForFurnitur('#' + $(elem).attr('id'), restriction[nameJavaObject]);
                setValueInSelectInt('#' + $(elem).attr('id'),table[i].id);
                addSelectField(nameJavaObject);
            }
        }
    }

    function fillInAlLSelectAfterSelectingType() {

        fillInMetal();
        fillInColor();

        fillInSelector('.doorstepSelect', 'doorstep');
        fillInSelector('.stainlessSteelDoorstepSelect', 'stainlessSteelDoorstep');

        fillInSealingLine('firstSealingLine');
        fillInSealingLine('secondSealingLine');
        fillInSealingLine('thirdSealingLine');

        fillInFurnitur('topLock');
        fillInFurnitur('lowerLock');
        fillInFurnitur('handle');
        fillInFurnitur('lowerlockCylinder');
        fillInFurnitur('toplockCylinder');
        fillInFurnitur('topInLockDecor');
        fillInFurnitur('topOutLockDecor');
        fillInFurnitur('lowerInLockDecor');
        fillInFurnitur('lowerOutLockDecor');
        fillInFurnitur('closer');
        fillInFurnitur('endDoorLock');

        fillInFurnitur('typeDoorGlass');
        fillInFurnitur('toning');
        fillInFurnitur('armor');
    }

    function fillInMetal() {

        var metals = $('.metalSelect');

        for (var i = 0; i < metals.length; ++i) {
            if (!$(metals[i]).val()) {
                fillInFieldFromLimit('#' + $(metals[i]).attr('id'), restriction.metal);
            }
        }
    }

    function fillInColor() {

        var colors = $('.colorsSelect');

        for (var i = 0; i < colors.length; ++i) {
            if (!$(colors[i]).val()) {
                fillInFieldFromColor('#' + $(colors[i]).attr('id'), restriction.colors);
            }
        }
    }

    function fillInSelector(selector, nameTable) {

        var elem = $(selector);

        for (var i = 0; i < elem.length; ++i) {
            if (!$(elem[i]).val()) {
                fillInFieldFromLimiBoolToInt('#' + $(elem[i]).attr('id'), restriction[nameTable]);
            }
        }
    }

    function fillInSealingLine(nameJavaObject) {

        var elem = $('.' + nameJavaObject + 'Select');

        for (var i = 0; i < elem.length; ++i) {
            if (!$(elem[i]).val()) {
                fillInFieldFromLimiSealingLine('#' + $(elem[i]).attr('id'), restriction[nameJavaObject]);
            }
        }
    }

    function fillInFurnitur(nameJavaObject) {

        var elem = $('.' + nameJavaObject + 'Select');
        for (var i = 0; i < elem.length; ++i) {
            if (!$(elem[i]).val()) {
                fillInFieldFromLimiForFurnitur('#' + $(elem[i]).attr('id'), restriction[nameJavaObject]);
            }
        }
    }

    function fillInFieldFromLimit(selector, table) {

        $(selector).empty();

        $(selector).append($('<option ></option>'));
        <!--empty line-->

        for (var i = 0; i < table.length; ++i) {
            $(selector).append(
                $('<option value=' + table[i].firstItem + '>' + table[i].firstItem + '</option>')
            );
        }

    }

    function fillInFieldFromLimiBoolToInt(selector, table) {

        $(selector).empty();

        $(selector).append($('<option ></option>'));
        <!--empty line-->

        for (var i = 0; i < table.length; ++i) {
            $(selector).append(
                $('<option value=' + table[i].startRestriction + '>' + translateToBoolean(table[i].startRestriction) + '</option>')
            );
        }
    }

    function fillInFieldFromLimiSealingLine(selector, table) {

        $(selector).empty();

        $(selector).append($('<option ></option>'));
        <!--empty line-->

        for (var i = 0; i < table.length; ++i) {
            $(selector).append(
                $('<option value=' + table[i].startRestriction + '>' + table[i].startRestriction + '</option>')
            );
        }
    }

    function fillInFieldFromLimiForFurnitur(selector, table) {

        $(selector).empty();

        if (table != null) {
            $(selector).append($('<option ></option>'));
            <!--empty line-->
            for (var i = 0; i < table.length; ++i) {
                $(selector).append(
                    $('<option value=' + table[i].id + '>' + table[i].name + '</option>')
                );
            }
        }
    }

    function fillInFieldFromColor(selector, table) {

        $(selector).empty();

        $(selector).append($('<option ></option>'));
        <!--empty line-->

        for (var i = 0; i < table.length; ++i) {
            $(selector).append(
                $('<option value=' + table[i].id + '>' + table[i].name + '</option>')
            );
        }

    }

    function addMetalField() {

        var data = lastElemNumber('.metalSelect') + 1;
        $('<select>').attr('class', 'form-control metalSelect')
            .attr('id', 'metalSelect' + data)
            .attr('data', data)
            .appendTo('#metalDiv');

        return '#metalSelect' + data;

    }

    function addSizeField(nameJavaObject) {

        var data = lastElemNumber('.' + nameJavaObject) + 1;
        $('<input>').attr('class', 'form-control ' + nameJavaObject + 'Input')
            .attr('id', '' + nameJavaObject + 'Input' + data)
            .attr('data', data)
            .appendTo('#' + nameJavaObject + 'Div');

        return '#' + nameJavaObject + 'Input' + data;

    }

    function lastElemNumber(selector) {
        var elem = $(selector);
        var number = 0;

        for (var i = 0; i < elem.length; ++i) {
            var elemValue = Number.parseInt($(elem[i]).attr('data'));
            if (number < elemValue) {
                number = elemValue;
            }
        }
        return number;
    }

    function allFieldsAreFilled(selector) {
        var elem = $(selector);
        var allFilled = true;

        for (var i = 0; i < elem.length; ++i) {
            if (!$(elem[i]).val()) {
                allFilled = false;
            }
        }
        return allFilled;
    }

    function getNotCompletedFields(selector) {
        var elem = $(selector);

        for (var i = 0; i < elem.length; ++i) {
            if (!$(elem[i]).val()) {
                return elem[i];
            }
        }
        return null;
    }

    function addInputField(name, div) {
        var data = lastElemNumber('.' + name) + 1;
        $('<input>').attr('class', 'form-control ' + name)
            .attr('id', '' + name + data)
            .attr('data', data)
            .appendTo('#' + div);

        return '#' + name + data;
    }

    function addSelectField(nameJavaObject) {

        var name = '' + nameJavaObject + 'Select';
        var div = '' + nameJavaObject + 'Div';
        var data = lastElemNumber('.' + name) + 1;

        $('<select>').attr('class', 'form-control ' + name)
            .attr('id', '' + name + data)
            .attr('data', data)
            .appendTo('#' + div);

        return '#' + name + data;

    }

    function translateToBoolean(int) {
        if (int == 0) {
            return 'нет';
        }
        else if (int == 1) {
            return 'да';
        }
        alert('error: translateToBoolean: value is not valid!')
    }

    function addNewFieldAndfillInforFurnitur(nameJavaObject) {
        saveInJavaObjectFurnitur(nameJavaObject);
        if (allFieldsAreFilled('.' + nameJavaObject + 'Select')) {
            addSelectField(nameJavaObject);
        }
        fillInFurnitur(nameJavaObject);
    }

    function addNewFieldAndfillInforSealingLine(nameJavaObject) {
        saveInJavaObjectSelect(nameJavaObject);
        if (allFieldsAreFilled('.' + nameJavaObject + 'Select')) {
            addSelectField(nameJavaObject);
        }
        fillInSealingLine(nameJavaObject);
    }

    //refill template

    function saveInJavaObjectforMetal(nameFieldJavaObject) {

        //delete all
        var size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);

        var selector = '.' + nameFieldJavaObject + 'Select';
        var elem = $(selector);
        for (var i = 0; i < elem.length; ++i) {
            if ($(elem[i]).val()) {
                template[nameFieldJavaObject].push(findInRestriction($(elem[i]).val(), nameFieldJavaObject));
            }
        }
    }

    function findInRestriction(val, nameFieldJavaObject) {
        var tab = restriction[nameFieldJavaObject];
        for (var i = 0; i < tab.length; ++i) {
            if (tab[i].firstItem == val) {
                return tab[i];
            }
        }
    }

    function saveInJavaObjectSize(nameFieldJavaObject) {

        //delete all
        var size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);

        //for pairOfValues = 0
        var selector = '.' + nameFieldJavaObject + 'Input';
        var elem = $(selector);
        for (var i = 0; i < elem.length; ++i) {
            if ($(elem[i]).val()) {
                template[nameFieldJavaObject].push(newInstansLim($(elem[i]).val(),
                                                                 0,0,0));
            }
        }
        //for pairOfValues = 1
        if($('#' + nameFieldJavaObject + 'Min').val() ||
            $('#' + nameFieldJavaObject + 'Max').val() || $('#' + nameFieldJavaObject + 'Step').val()){

            template[nameFieldJavaObject].push(newInstansLim($('#' + nameFieldJavaObject + 'Min').val(),
                $('#' + nameFieldJavaObject + 'Max').val(),
                $('#' + nameFieldJavaObject + 'Step').val(),
                1));
        }

    }

    function newInstansLim(min, max, step,pairVal) {
        var lim = new function () {
            this.startRestriction = min;
            this.stopRestriction = max;
            this.step = step;
            this.pairOfValues = pairVal;
        }
        return lim;
    }

    function saveInJavaObjectColor(nameFieldJavaObject){
        //delete all
        var size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);

        var selector = '.' + nameFieldJavaObject + 'Select';
        var elem = $(selector);
        for (var i = 0; i < elem.length; ++i) {
            if ($(elem[i]).val()) {
                template[nameFieldJavaObject].push(findInRestrForColor($(elem[i]).val(), nameFieldJavaObject));
            }
        }
    }
    function findInRestrForColor(val, nameFieldJavaObject) {
        var tab = restriction[nameFieldJavaObject];
        for (var i = 0; i < tab.length; ++i) {
            if (tab[i].id == val){
                return tab[i];
            }
        }
    }

    function saveInJavaObjectSelect(nameFieldJavaObject){
        //delete all
        var size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);

        var selector = '.' + nameFieldJavaObject + 'Select';
        var elem = $(selector);
        for (var i = 0; i < elem.length; ++i) {
            if ($(elem[i]).val()) {
                template[nameFieldJavaObject].push(newInstansBool($(elem[i]).val()));
            }
        }
    }
    function newInstansBool(val) {
        var lim = new function () {
            this.startRestriction = val;
            this.stopRestriction = 0;
            this.step = 0;
            this.pairOfValues = 0;
        }
        return lim;
    }

    function saveInJavaObjectFurnitur(nameFieldJavaObject){
        //delete all
        var size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);

        var selector = '.' + nameFieldJavaObject + 'Select';
        var elem = $(selector);
        for (var i = 0; i < elem.length; ++i) {
            if ($(elem[i]).val()) {
                template[nameFieldJavaObject].push(findInRestrForFurnitur($(elem[i]).val(),nameFieldJavaObject));
            }
        }
    }
    function findInRestrForFurnitur(val, nameFieldJavaObject) {
        var tab = restriction[nameFieldJavaObject];
        for (var i = 0; i < tab.length; ++i) {
            if (tab[i].id == val){
                return tab[i];
            }
        }
    }

    function setValueInSelect(jqSelect,value){
        var opt = $(jqSelect+' > option');
        opt.each(function(indx, element){
            if ( $(this).text().toLowerCase() == value.toLowerCase() ) {
                $(this).attr("selected", "selected");
            }
        });
    }

    function setValueInSelectInt(jqSelect,value){
        var opt = $(jqSelect+' > option');
        opt.each(function(indx, element){
            if ( $(this).val() == value ) {
                $(this).attr("selected", "selected");
            }
        });
    }
});