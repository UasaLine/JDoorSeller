class AvailableManager {
    constructor(doorTemplate, availableFurnitureList) {
        this.doorTemplate = doorTemplate;
        this.availableFurnitureList = availableFurnitureList;
    }

    makeFieldsAvailable() {
       if(this.doorTemplate!=null){
           this.makeAvailable('comment');
           this.makeAvailable('handle');
           this.makeAvailable('shieldKit');
           this.makeAvailable('doorColor');
           this.makeAvailable('additionalDoorSettings');
           this.makeAvailable('sideDoorOpen');
       }
    }
    makeAvailable(name){
        $("#name"+name).attr("available", "yes");
    }

}
