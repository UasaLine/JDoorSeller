class SizeCostBlock {

    static WIDTH_JAVA_OBJECT= 'sizeCostWidth';
    static HEIGHT_JAVA_OBJECT = 'sizeCostHeight';

    static domSelect = {
        widthMin:'widthMinCost',
        widthMax:'widthMaxCost',
        heightMin:'heightMinCost',
        heightMax:'heightMaxCost',
        cost:'sizeCost'
    }

    static init(){
        $(".size-cost-block").change(SizeCostBlock.change);
    }

    static change(){
        SizeCostBlock.saveField();
    }

    static saveField(nameFieldJavaObject) {

        SizeCostBlock.deleteLineIntoTemplate(SizeCostBlock.HEIGHT_JAVA_OBJECT);
        SizeCostBlock.deleteLineIntoTemplate(SizeCostBlock.WIDTH_JAVA_OBJECT);

        let selector = ".sizeCostLine";
        let elem = $(selector);

        for (var i = 0; i < elem.length; ++i) {

                let index = $(elem[i]).attr("data");
                let cost = SizeCostBlock.getVal(index,SizeCostBlock.domSelect.cost);
                if (cost){
                    template[SizeCostBlock.HEIGHT_JAVA_OBJECT].push({
                        itemId:index,
                        startRestriction: SizeCostBlock.getVal(index,SizeCostBlock.domSelect.heightMin),
                        stopRestriction: SizeCostBlock.getVal(index,SizeCostBlock.domSelect.heightMax),
                        step:0,
                        pairOfValues:0,
                        cost: cost
                    });
                    template[SizeCostBlock.WIDTH_JAVA_OBJECT].push({
                        itemId:index,
                        startRestriction: SizeCostBlock.getVal(index,SizeCostBlock.domSelect.widthMin),
                        stopRestriction: SizeCostBlock.getVal(index,SizeCostBlock.domSelect.widthMax),
                        step:0,
                        pairOfValues:0,
                        cost: cost
                    });
                }
        }
    }

    static deleteLineIntoTemplate(nameFieldJavaObject){
        let size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);
    }

    static getVal(index, idNmae) {
        return $("#"+idNmae + index).val();
    }

    static setVal(index, idNmae, val) {
        $("#"+idNmae + index).val(val);
    }

    static setValueToDom(){
        template[SizeCostBlock.WIDTH_JAVA_OBJECT].forEach(
            function(item, i, arr) {
                SizeCostBlock.setVal(i,SizeCostBlock.domSelect.cost,item.cost);
                SizeCostBlock.setVal(i,SizeCostBlock.domSelect.widthMin,item.startRestriction);
                SizeCostBlock.setVal(i,SizeCostBlock.domSelect.widthMax,item.stopRestriction);
        });
        template[SizeCostBlock.HEIGHT_JAVA_OBJECT].forEach(
            function(item, i, arr) {
                SizeCostBlock.setVal(i,SizeCostBlock.domSelect.cost,item.cost);
                SizeCostBlock.setVal(i,SizeCostBlock.domSelect.heightMin,item.startRestriction);
                SizeCostBlock.setVal(i,SizeCostBlock.domSelect.heightMax,item.stopRestriction);
            });
    }
}
