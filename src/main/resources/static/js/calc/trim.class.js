class Trim {
    constructor(){}
    static init(data){

        $('.trims').click(Trim.click);

        Trim.checkAndAvailable('topDoorTrim', data);
        Trim.checkAndAvailable('leftDoorTrim', data);
        Trim.checkAndAvailable('rightDoorTrim', data);

        if(Trim.anyOn()){
            Trim.turnOffMain(true);
        }
    }
    static click(){
        if (Trim.isAvailable(this)){

            let val = $(this).is(":checked");
            let elem_id = $(this).attr('id');

            if(elem_id =='doorTrim_checkbox'){
                Trim.turnOffAll(val);
                Door.set('topDoorTrim',val?1:0);
                Door.set('leftDoorTrim',val?1:0);
                Door.set('rightDoorTrim',val?1:0);
            }
            else {
                Trim.turnOffMain(val);
                Door.set($(this).attr("Item"),val?1:0);
            }

            Door.draw(door,1);
        }
        else {
            Trim.turnOff(this)
        }
    }
    static checkAndAvailable(name, data) {
        if (data!=null) {
            var tabSize = data[name].length;
            if (tabSize > 1) {

                Trim.availableOff(name, true);

                if (! Trim.isAvailable($('#doorTrim_checkbox'))) {
                    Trim.availableOff('doorTrim', true);
                }
            }
        }
    }
    static turnOffAll(val){
        $('#topDoorTrim_checkbox').prop("checked", val);
        $('#leftDoorTrim_checkbox').prop("checked", val);
        $('#rightDoorTrim_checkbox').prop("checked", val);
    }
    static turnOffMain(val){
        if(!val && !Trim.anyOn()){
            $('#doorTrim_checkbox').prop("checked", val);
        }
        else if(val){
            $('#doorTrim_checkbox').prop("checked", val);
        }
    }
    static turnOff(item){
        $(item).prop("checked", false);
    }
    static availableOff(name,val){
        $("#name" + name).attr("available", val?"yes":"no");
        $('#'+name+'_checkbox').attr("available",val?"yes":"no");
    }
    static isAvailable(item){
        return $(item).attr("available") == "yes"
    }
    static anyOn(){

        if($('#topDoorTrim_checkbox').is(":checked")){
            return true;
        }
        else if($('#leftDoorTrim_checkbox').is(":checked")){
            return true;
        }
        else if ($('#rightDoorTrim_checkbox').is(":checked")){
            return true;
        }
        else {
            return false;
        }
    }
}