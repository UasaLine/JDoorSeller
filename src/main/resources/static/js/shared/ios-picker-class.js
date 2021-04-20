class IosPicker {

    currentNumber;
    firstPress;
    open = false;
    currentId = '';
    item;
    defaultValue;
    min;
    max;
    step = 5;
    valueList;

    constructor(setValue, pickerId, item) {
        this.open = true;
        this.defaultValue = setValue;
        this.min = SizingDrum.minValueFromRestriction(item);
        this.max = SizingDrum.maxValueFromRestriction(item);
        this.currentId = pickerId;
        this.item = item;
        this.firstPress = true;
        this.valueList = [];
        this.iosPickerInit(setValue, pickerId, item);

    }

    setToDoor() {
        Door.set(needToSet.item, needToSet.value);

        needToSet.item = "";
        needToSet.value = "";

        const saveCurrentItem = currentItem;
        RepresentationManager.showAllFieldsValues(door);
        return saveCurrentItem;
    }

    add(number) {

        this.currentNumber = $('.clone-scroller').children('.option').text();

        if (this.firstPress) {
            this.currentNumber = "";
        }
        this.currentNumber = this.currentNumber + number;

        $("#" + this.currentId).picker({
            data: [this.currentNumber],
            lineHeight: 40,
            selected: 0
        }, function (selectValue) {
        })
        this.firstPress = false;
    }

    delete() {

        this.currentNumber = $('.clone-scroller').children('.option').text();
        let length = this.currentNumber.length;
        this.currentNumber = this.currentNumber.slice(0, length - 1);

        $("#" + this.currentId).picker({
            data: [this.currentNumber],
            lineHeight: 40,
            selected: 0
        }, function (selectValue) {
        })
        this.firstPress = false;
    }

    processKey(e) {
        const sound = $("#scrollSoundClip")[0];
        if (e.which == 48 || e.which == 96) {
            this.add(0);
        } else if (e.which == 49 || e.which == 97) {
            this.add(1);
        } else if (e.which == 50 || e.which == 98) {
            this.add(2);
        } else if (e.which == 51 || e.which == 99) {
            this.add(3);
        } else if (e.which == 52 || e.which == 100) {
            this.add(4);
        } else if (e.which == 53 || e.which == 101) {
            this.add(5);
        } else if (e.which == 54 || e.which == 102) {
            this.add(6);
        } else if (e.which == 55 || e.which == 103) {
            this.add(7);
        } else if (e.which == 56 || e.which == 104) {
            this.add(8);
        } else if (e.which == 57 || e.which == 105) {
            this.add(9);
        } else if (e.which == 8) {
            this.delete(); //backspace
        } else if (e.which == 13) {
            if (!this.firstPress) {
                needToSet.item = this.item;
                needToSet.value = this.checkValue(this.currentNumber);
            }
            this.iosPickerInit(needToSet.value, this.currentId, this.item);
            this.setToDoor();
            this.firstPress = true;
        }
        sound.play();
    }

    clear() {
        if (this.currentId != '') {
            $("#" + this.currentId).empty();
        }
        this.open = false;
        this.firstPress = true;
    }

    iosPickerInit(setValue, pickerId, item) {

        const sound = $("#scrollSoundClip")[0];

        let currentValue = this.min;
        let arrForPicker = [];
        let defaultIndex = 0;
        let index = 0;


        while (currentValue < this.max) {
            arrForPicker.push(currentValue);
            if (currentValue == setValue) {
                defaultIndex = index;
            }
            currentValue = currentValue + this.step;
            index = index + 1;
        }

        needToSet.item = item;
        this.valueList = arrForPicker;
        $("#" + pickerId).picker({
            data: arrForPicker,
            lineHeight: 40,
            selected: defaultIndex
        }, function (selectValue) {
            if (setValue != selectValue) {
                sound.play();
                needToSet.value = selectValue;
            }
        })
    }

    checkValue(value) {
        if (value > this.max || value < this.min) {
            alert("Значение не доступно! доступные значения от " + this.min + " до " + this.max);
            return this.defaultValue;
        }

        let fineValue = this.valueList.filter(function (elem) {
            return elem == value;
        });
        if (fineValue.length == 0) {
            alert("Значение не доступно! введите значение из списка");
            return this.defaultValue;
        }

        return value;
    }
}
