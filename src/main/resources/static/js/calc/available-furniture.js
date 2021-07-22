class AvailableFurniture {
    list;
    restrictionFields

    constructor(list, restrictionFields) {
        this.list = list;
        this.restrictionFields = restrictionFields;
    }

    isDefault(fieldName, id) {

        let returnValue = false;

        if (this.restrictionFields && this.restrictionFields[fieldName] && this.restrictionFields[fieldName].length > 0) {
            let foundList = this.restrictionFields[fieldName].filter(item => item.id = id);
            if (foundList && foundList.length > 0) {
                returnValue = foundList[0].default;
            }
        } else {
            alert('no available fittings found');
        }

        return returnValue;
    }

    getDefaultId(fieldName) {
        if (fieldName == "lowerLockCylinder" || fieldName == "topLockCylinder") {
            fieldName = "lockCylinder";
        }
        let returnValue = null;
        if (this.restrictionFields && this.restrictionFields[fieldName] && this.restrictionFields[fieldName].length > 0) {
            let foundList = this.restrictionFields[fieldName].filter(item => item.default == true);
            if (foundList && foundList.length > 0) {
                returnValue = foundList[0].itemId;
            }
            return returnValue;
        }
    }
}
