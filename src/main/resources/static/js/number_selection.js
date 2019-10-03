
    function select_introduction(startNumber,headName,idElement){

        $('#nameSelectForm').text(headName);
        $('#nameSelectForm').attr('data',idElement);

		var numberL = (startNumber - (startNumber%100))/100;
        var numberR = startNumber%100;

        add ('L',1,getPreviousValue(numberL,1));
        add ('L',1,numberL);
        add ('L',1,getNextValue(numberL,1));


        add ('R',5,getPreviousValue(numberR,5));
        add ('R',5,numberR);
        add ('R',5,getNextValue(numberR,5));

        //alert(''+startNamber+', int:'+aInt+', nat:'+aNat);

        colorize('.numberR');
        colorize('.numberL');

		$('.android_main_contener').attr('show', 'no');
		$('.wall_of_darkness').attr('show', 'no');

	};

	function add (side,n,firstValue){
		
		var elems = $('.number'+side);
		var sizeЕlems = elems.length;

		if (firstValue==null){
            firstValue = getNextValue($(elems[0]).text(),n);
		}

		$(elems[sizeЕlems-1]).remove();

		$('<span>').attr('class','counter_line number'+side).text(firstValue).prependTo($('.counter_line_int'+side));
		
		colorize ('.number'+side);
	};
	
	function reduce (side,n){
		
		var elems = $('.number'+side);
		var sizeЕlems = elems.length;
		var firstValue = $(elems[sizeЕlems-1]).text();
		$(elems[0]).remove();
		$('<span>').attr('class','counter_line number'+side).text(getPreviousValue(firstValue,n)).appendTo($('.counter_line_int'+side));
			
		colorize ('.number'+side);
		
	};
	
	function colorize (className){		
		
		var elems = $(className);
        var sizeЕlems = elems.length;		
		for (var i=0; i<sizeЕlems ; ++i) {
						
			deleteDown ($(elems[i]));
			
			if (i == 0 || i == sizeЕlems-1) {
				$(elems[i]).addClass("line1");		
			}
			else if(i == 1 ){
				$(elems[i]).addClass("line");		
			}		

		}
	};

	function deleteDown (elem){

		if (elem.hasClass('line1')){
			elem.removeClass("line1");
		}
		if(elem.hasClass('line')){
			elem.removeClass("line");
		}
	};

	function getNextValue(firstValue,n){

		if (((1*n) + parseInt(firstValue))<99) {
            return (1*n) + parseInt(firstValue);
		}
		return 0 ;

	};

	function getPreviousValue(firstValue,n){

        if (parseInt(firstValue)-(1*n)>0) {
            return parseInt(firstValue)-(1*n);
        }
        return 95;


};

    function closeSelect(){
        $('.android_main_contener').attr('show', 'ghost_lement');
        $('.wall_of_darkness').attr('show', 'ghost_lement');
    }
