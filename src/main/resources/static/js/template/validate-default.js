let validateProblemBlok = "";
class ValidateDefault {

    static validateDefault(template) {
        validateProblemBlok = "";
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
            alert(validateProblemBlok);
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
    validateProblemBlok += fieldName + ' - не установлен по умолчанию\n';
    return true;
}