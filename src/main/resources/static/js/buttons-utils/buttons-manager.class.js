class ButtonsManager {
    constructor(buttonsClass,valueToLock) {
        this.buttonsClass = buttonsClass;
        this.valueToLock = valueToLock;
    }

    disabled(valueForComparison) {
        if (this.valueToLock != valueForComparison) {
            $('.' + this.buttonsClass).each(function (index) {
                $(this).attr('disabled', '');
            });
        }
    }
}