class InnerOpen {
    static init() {
        $(".innerOpenCheckbox").click(InnerOpen.clickCheckbox);
        $(".div_images_innerOpen").click(InnerOpen.clickImages);


    }
    static updateView(){
        $('#innerOpenCheckboxId').prop("checked", door.innerOpen>0);
        InnerOpen.showTypeImages(door.innerOpen>0);

    }
    static clickCheckbox() {
        if (InnerOpen.checkAndHide()){
            return;
        }
        let val = $(this).is(":checked");
        InnerOpen.showTypeImages(val);
        Door.set($(this).attr('Item'), val ? '0' : '');
        RepresentationManager.showFieldValue($(this).attr("data"));
    }

    static clickImages() {
        Door.set($(this).attr('Item'), $(this).attr('data'));
        RepresentationManager.showFieldValue($(this).attr("data"));
    }

    static showTypeImages(val) {
        let sideShow = (door.sideDoorOpen != null && door.sideDoorOpen == 'LEFT') ? 'L' : 'R';
        let sideHide = (door.sideDoorOpen != null && door.sideDoorOpen == 'LEFT') ? 'R' : 'L';
        if (val) {
            InnerOpen.show(sideShow);
            InnerOpen.hide(sideHide);
        } else {
            InnerOpen.hide(sideShow);
            InnerOpen.hide(sideHide);
        }
    }

    static hide(side){
        $(".innerOpen_" + side).each(function () {
            $(this).addClass('ghost');
        });
    }
    static show(side){
        $(".innerOpen_" + side).each(function () {
            $(this).removeClass('ghost');
        });
    }

    static checkAndHide() {
        if (door.template.innerOpen > 0) {
            let hideInnerOpen = door.template.innerOpen[0].startRestriction;
            if (hideInnerOpen == 1) {
                return true;
            } else false;
        } else {
            return false;
        }

    }
}

class SideOpen {
    static init() {
        $(".openCheckbox").click(SideOpen.clickCheckbox);
    }

    static updateView(){
        $("[data = " + door.sideDoorOpen + "]").prop("checked", true);
    }

    static clickCheckbox() {
        let val = $(this).is(":checked");
        let setVal = val ? $(this).attr("data") : '';
        Door.set($(this).attr('Item'), setVal);
        InnerOpen.showTypeImages($("#innerOpenCheckboxId").is(":checked"));
        RepresentationManager.showFieldValue($(this).attr("data"));
    }
}
