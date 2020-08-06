class ValidateDefault {

    static validateDefault(template) {
        if (validateDefault("metal") |
            validateDefault('widthDoor') |
            validateDefault('heightDoor') |
            validateDefault('colors') |
            validateDefault('deepnessDoor') |
            validateDefault('thicknessDoorLeaf') |
            validateDefault('leftDoorTrim') |
            validateDefault('leftDoorTrimSize') |
            validateDefault('rightDoorTrim') |
            validateDefault('rightDoorTrimSize') |
            validateDefault('topDoorTrim') |
            validateDefault('topDoorTrimSize')) {
            return true;
        } else {
            return false;
        }
    }
}

function validateDefault(fieldName) {
    let valueList = template[fieldName];
    for (let i = 0; i < valueList.length; i++) {
        if (valueList[i].defaultValue > 0) {
            return false;
        }
    }
    alert(fieldName + ' - не установлен по умолчанию');
    return true;
}