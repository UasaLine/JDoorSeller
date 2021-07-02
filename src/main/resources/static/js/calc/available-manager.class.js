class AvailableManager {

    doorTemplate;
    availableFurnitureList;
    changeAvailable = true;

    constructor(changeAvailable) {
        this.changeAvailable = changeAvailable !== 'false';
    }

    setTemplate(doorTemplate, availableFurnitureList) {
        this.doorTemplate = doorTemplate;
        this.availableFurnitureList = availableFurnitureList;

    }

    makeFieldsAvailable() {

        if (this.doorTemplate != null && this.changeAvailable) {

            AvailableManager.makeAvailable("comment");
            AvailableManager.makeAvailable("handle");
            AvailableManager.makeAvailable("shieldKit");
            AvailableManager.makeAvailable("doorColor");
            AvailableManager.makeAvailable("additionalDoorSettings");
            AvailableManager.makeAvailable("sideDoorOpen");

            this.makeAvailableIfExistsByHtmlName("outShieldColor", "outShieldKit");

            this.makeAvailableIfExists("topLock", "kit");
            this.makeAvailableIfExists("lowerLock", "kit");

            this.makeAvailableIfExists("handle", "");

            this.makeAvailableIfExists("lowerLockCylinder", "");
            this.makeAvailableIfExists("topLockCylinder", "");

            this.makeAvailableIfExists("topInLockDecor", "");
            this.makeAvailableIfExists("topOutLockDecor", "");

            this.makeAvailableIfExists("lowerInLockDecor", "");
            this.makeAvailableIfExists("lowerOutLockDecor", "");

            this.makeAvailableIfExists("closer", "");
            this.makeAvailableIfExists("peephole", "");
            this.makeAvailableIfExists("peephole", "Menu");
            this.makeAvailableIfExists("peepholePosition", "");
            this.makeAvailableIfExistsInTemplate("stainlessSteelDoorstep", "Menu");

            this.makeAvailableIfExists("typeDoorGlass", "");
            this.makeAvailableIfExists("toning", "");
            this.makeAvailableIfExists("armor", "");
            this.makeAvailableIfExists("nightLock", "");
        } else {
            AvailableManager.disabledButtonById('SaveAndСlose');
            AvailableManager.disabledButtonById('buttonSaveDoor');
            AvailableManager.disabledButtonById('buttonCalculate');
        }
    }

    makeAvailableIfExists(name, postfix) {
        if (availableFurnitureList[name] && availableFurnitureList[name].length > 0) {
            AvailableManager.makeAvailable(name + postfix);
            AvailableManager.unDisabledCheckbox(name + postfix);
        }
    }

    makeAvailableIfExistsByHtmlName(name, postfix) {
        if (availableFurnitureList[name] && availableFurnitureList[name].length > 0) {
            AvailableManager.makeAvailable(postfix);
        }
    }

    makeAvailableIfExistsInTemplate(name, postfix) {
        if (this.doorTemplate && this.doorTemplate[name] && this.doorTemplate[name].length > 0) {
            AvailableManager.makeAvailable(name + postfix);
        }
    }

    static makeAvailable(name) {
        $("#name" + name).attr("available", "yes");
    }

    static makeUnavailable(name) {
        $("#name" + name).attr("available", "no");
    }

    static disabledCheckbox(name) {
        $("#" + name + "_checkbox").attr('disabled', "");
    }

    static unDisabledCheckbox(name) {
        $("#" + name + "_checkbox").removeAttr('disabled');
    }

    static disabledButtonById(id) {
        $("#" + id).attr('disabled', "");
    }

}
