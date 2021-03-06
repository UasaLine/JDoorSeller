class Trim {
    static init(data) {
        $(".trims").click(Trim.click);

        Trim.checkAndAvailable("topDoorTrim", data);
        Trim.checkAndAvailable("leftDoorTrim", data);
        Trim.checkAndAvailable("rightDoorTrim", data);

    }

    static click() {

        let val = $(this).is(":checked");
        let elem_id = $(this).attr("id");

        if (elem_id == "mainDoorTrim_checkbox") {
            Trim.turnOffAll(val);
            Door.set("topDoorTrim", val ? 1 : 0);
            Door.set("leftDoorTrim", val ? 1 : 0);
            Door.set("rightDoorTrim", val ? 1 : 0);
        } else {
            Trim.turnOffMain(val);
            Door.set($(this).attr("Item"), val ? 1 : 0);
            Trim.showButtonSize($(this).attr("Item"), val);
        }

        Door.draw(door, 1);
    }

    static checkAndAvailable(name, data) {
        if (data != null) {
            let tabSize = data[name].length;
            if (tabSize > 1) {
                Trim.availableOff(name, true);

                if (!Trim.isAvailable($("#mainDoorTrim_checkbox"))) {
                    Trim.availableOff("mainDoorTrim", true);
                } else {
                    if (Trim.anyOn()) {
                        Trim.turnOffMain(true);
                    }
                }
            }
        }
    }

    static turnOffAll(val) {
        $("#topDoorTrim_checkbox").prop("checked", val);
        Trim.showButtonSize('topDoorTrim', val);
        $("#leftDoorTrim_checkbox").prop("checked", val);
        Trim.showButtonSize('leftDoorTrim', val);
        $("#rightDoorTrim_checkbox").prop("checked", val);
        Trim.showButtonSize('rightDoorTrim', val);
        if (Trim.anyOn()) {
            Trim.turnOffMain(true);
        }
    }

    static turnOffMain(val) {
        if (!val && !Trim.anyOn()) {
            $("#mainDoorTrim_checkbox").prop("checked", val);
        } else if (val) {
            $("#mainDoorTrim_checkbox").prop("checked", val);
        }
    }

    static turnOff(item) {
        $(item).prop("checked", false);
    }

    static turnOn(name, value) {

        if (value == 1) {
            $("#" + name + "_checkbox").prop("checked", true);
            Trim.showButtonSize(name, true);
        } else {
            $("#" + name + "_checkbox").prop("checked", false);
            Trim.showButtonSize(name, true);
        }

    }

    static availableOff(name, val) {
        $("#name" + name).attr("available", val ? "yes" : "no");
        $("#" + name + "_checkbox").attr("available", val ? "yes" : "no");
        if (val === true) {
            $("#" + name + "_checkbox").removeAttr("disabled");
        } else {
            $("#" + name + "_checkbox").attr("disabled",);
        }
    }

    static isAvailable(item) {
        return $(item).attr("available") == "yes";
    }

    static anyOn() {
        if ($("#topDoorTrim_checkbox").is(":checked")) {
            return true;
        } else if ($("#leftDoorTrim_checkbox").is(":checked")) {
            return true;
        } else if ($("#rightDoorTrim_checkbox").is(":checked")) {
            return true;
        } else {
            return false;
        }
    }

    static showButtonSize(item, val) {
        if (val) {
            $('#' + item + 'SizeDiv').removeClass('ghost');
        } else {
            $('#' + item + 'SizeDiv').addClass('ghost');
        }
    }
}
