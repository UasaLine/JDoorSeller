jQuery('document').ready(function(){
	
	var doorClassList;
	var template;

	grtListDoorClassToSelect();
	
	$('#doorclassselect').change(function() {

        fillInDoorType(getDoorClassbyId($('#doorclassselect').val()));
		
	});

    $('#doortypeselect').change(function() {

        getDoorTemplate();

    });

    $('#metalDiv').change('.metalSelect',function() {

    	if (allFieldsAreFilled('.metalSelect')) {
            addMetalField();
        }
        fillInMetal();

    });

    $('#widthSizeDiv').change('.widthDoorInput',function(){
        if (allFieldsAreFilled('.widthDoorInput')) {
            addInputField('widthDoorInput','widthSizeDiv');
        }
    });

    $('#heightSizeDiv').change('.heightDoorInput',function(){
        if (allFieldsAreFilled('.heightDoorInput')) {
            addInputField('heightDoorInput','heightSizeDiv');
        }
    });

    $('#deepnessDoorDiv').change('.deepnessDoorInput',function(){
        if (allFieldsAreFilled('.deepnessDoorInput')) {
            addInputField('deepnessDoorInput','deepnessDoorDiv');
        }
    });

    $('#thicknessDoorLeafDiv').change('.thicknessDoorLeafInput',function(){
        if (allFieldsAreFilled('.colorsSelect')) {
            addInputField('thicknessDoorLeafInput','thicknessDoorLeafDiv');
        }
    });

    $('#colorsDiv').change('.colorsSelect',function(){
        if (allFieldsAreFilled('.colorsSelect')) {
            addSelectField('colorsSelect','colorsDiv');
        }
        fillInColor();
    });

    $('#doorstepDiv').change('.doorstepSelect',function(){
        if (allFieldsAreFilled('.doorstepSelect')) {
            addSelectField('doorstepSelect','doorstepDiv');
        }
        fillInSelector('.doorstepSelect','doorstep');
    });

    $('#stainlessSteelDoorstepDiv').change('.stainlessSteelDoorstepSelect',function(){

    });

    function grtListDoorClassToSelect(){
		
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

    function fillInDoorClass (listClass){

        $('#doorclassselect').empty();

        $('#doorclassselect').append(
            $('<option></option>')
        );

		for(var i=0;i<listClass.length;++i){
            $('#doorclassselect').append( $('<option value='+listClass[i].id+'>'+listClass[i].name+'</option>'));
		}

	}

    function fillInDoorType (doorClass){

        $('#doortypeselect').empty();

        $('#doortypeselect').append(
            $('<option></option>')
        );

        for(var i=0;i<doorClass.doorTypes.length;++i){
            $('#doortypeselect').append(
            	$('<option value='+doorClass.doorTypes[i].id+'>'+doorClass.doorTypes[i].name+'</option>')
			                            );
        }

    }

    function getDoorClassbyId(id){

		if(id==null && id == 0){
			alert('error id is null!')
		}

		for(var i=0;i<doorClassList.length;++i){
			if (doorClassList[i].id==id){
				return doorClassList[i];
			}
		}
		alert('error doorClass no found!')
		return 0;

	}

    function getDoorTemplate(){

		var doorTypeId = $('#doortypeselect').val();

		$.ajax({
            url: 'doorlimit',
            data: {idDoorType: doorTypeId},
            dataType: 'json',
            success: function (data) {
                template = data;
                fillInMetal();
                fillInColor();
                fillInSelector('.doorstepSelect','doorstep');
                fillInSelector('.stainlessSteelDoorstepSelect','stainlessSteelDoorstep');
                fillInSelector('.firstSealingLineSelect','firstSealingLine');
                fillInSelector('.secondSealingLineSelect','secondSealingLine');
                fillInSelector('.thirdSealingLineSelect','thirdSealingLine');
            },
            error: function (data) {
                alert('!ERROR: данные шаблона получить не удалось:');
            }
        });
	}

    function fillInMetal(){

        var metals = $('.metalSelect');

        for (var i=0;i<metals.length;++i){
        	if (!$(metals[i]).val()){
                fillInFieldFromLimit('#'+$(metals[i]).attr('id'),template.metal);
			}
		}
	}

    function fillInColor(){

        var colors = $('.colorsSelect');

        for (var i=0;i<colors.length;++i){
            if (!$(colors[i]).val()){
                fillInFieldFromColor('#'+$(colors[i]).attr('id'),template.colors);
            }
        }
    }

    function fillInSelector(selector,nameTable){

        var elem = $(selector);

        for (var i=0;i<elem.length;++i){
            if (!$(elem[i]).val()){
                fillInFieldFromLimiBoolToInt('#'+$(elem[i]).attr('id'),template[nameTable]);
            }
        }
    }

	function fillInFieldFromLimit(selector,table){

        $(selector).empty();

        $(selector).append($('<option ></option>')); <!--empty line-->

        for(var i=0;i<table.length;++i){
            $(selector).append(
                $('<option value='+table[i].id+'>'+table[i].firstItem+'</option>')
            );
        }

	}

    function fillInFieldFromLimiBoolToInt(selector,table){

        $(selector).empty();

        $(selector).append($('<option ></option>')); <!--empty line-->

        for(var i=0;i<table.length;++i){
            $(selector).append(
                $('<option value='+table[i].id+'>'+translateToBoolean(table[i].startRestriction)+'</option>')
            );
        }
    }

    function fillInFieldFromColor(selector,table){

        $(selector).empty();

        $(selector).append($('<option ></option>')); <!--empty line-->

        for(var i=0;i<table.length;++i){
            $(selector).append(
                $('<option value='+table[i].id+'>'+table[i].name+'</option>')
            );
        }

    }

	function addMetalField(){

		var data = lastElemNumber('.metalSelect')+1;
		$('<select>').attr('class','form-control metalSelect')
                     .attr('id','metalSelect'+data)
                     .attr('data',data)
			         .appendTo('#metalDiv');

		return '#metalSelect'+data;

	}

	function lastElemNumber(selector){
        var elem = $(selector);
        var number = 0;

        for (var i=0;i<elem.length;++i){
        	var elemValue = Number.parseInt($(elem[i]).attr('data'));
            if (number < elemValue){
    			number = elemValue;
            }
        }
         return number;
	}

    function allFieldsAreFilled(selector){
        var elem = $(selector);
        var allFilled = true;

        for (var i=0;i<elem.length;++i) {
            if(!$(elem[i]).val()){
                allFilled = false;
            }
        }
        return allFilled;
	}

    function addInputField(name,div){
        var data = lastElemNumber('.'+name)+1;
        $('<input>').attr('class','form-control '+name)
            .attr('id',''+name+data)
            .attr('data',data)
            .appendTo('#'+div);

        return '#'+name+data;
    }

    function addSelectField(name,div) {

        var data = lastElemNumber('.'+name)+1;
        $('<select>').attr('class','form-control '+name)
            .attr('id',''+name+data)
            .attr('data',data)
            .appendTo('#'+div);

        return '#'+name+data;

    }

    function translateToBoolean(int){
        if (int == 0){
            return 'нет';
        }
        else if (int == 1){
            return 'да';
        }
        alert('error: translateToBoolean: value is not valid!')
    }
});