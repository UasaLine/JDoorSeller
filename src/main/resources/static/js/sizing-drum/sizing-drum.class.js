class SizingDrum {

    static init(){
        $(".select_size").on("click", SizingDrum.click);
    }
    static click(){

    }

    static sizeLimMin(elemId) {

        let nameField = SizingDrum.getFieldNameFromInputId(elemId);

        var tab = RestrictionOfSelectionFields[nameField];
        for (var i = 0; i < tab.length; i++) {
            if (tab[i].pairOfValues == 1) {
                return tab[i].startRestriction;
            }
        }
        return 0;
    }

    static sizeLimMax(elemId) {

        let nameField = SizingDrum.getFieldNameFromInputId(elemId);

        var tab = RestrictionOfSelectionFields[nameField];
        for (var i = 0; i < tab.length; i++) {
            if (tab[i].pairOfValues == 1) {
                return tab[i].stopRestriction;
            }
        }
        return 2100;
    }

    static setSize() {
        if ($("#select_set").hasClass("notAvailable")) {
            return;
        }

        var number = SizingDrum.prepareTheNumber(
            $(".counter_line.numberL.line").text(),
            $(".counter_line.numberR.line").text()
        );

        var fieldName =  SizingDrum.getFieldNameFromInputId($("#nameSelectForm").attr("data"));

        Door.set(fieldName,number);

        currentItem = fieldName;
        RepresentationManager.showFieldValue(number);

        closeSelect();
        selectSizeOpen = false;
    }

    static getFieldNameFromInputId(elemId){
        return elemId.replace('input_', '');
    }

    static prepareTheNumber(number1, number2) {
        if (number2.length == 1) {
            number2 = "0" + number2;
        }
        return "" + number1 + number2;
    }
}
