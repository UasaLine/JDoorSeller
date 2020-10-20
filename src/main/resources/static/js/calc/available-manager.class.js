class AvailableManager {
  constructor(doorTemplate, availableFurnitureList) {
    this.doorTemplate = doorTemplate;
    this.availableFurnitureList = availableFurnitureList;
  }

  makeFieldsAvailable() {
    if (this.doorTemplate != null) {
      AvailableManager.makeAvailable("comment");
      AvailableManager.makeAvailable("handle");
      AvailableManager.makeAvailable("shieldKit");
      AvailableManager.makeAvailable("doorColor");
      AvailableManager.makeAvailable("additionalDoorSettings");
      AvailableManager.makeAvailable("sideDoorOpen");
    }
    if(availableFurnitureList.topLock && availableFurnitureList.topLock.length > 0){
      AvailableManager.makeAvailable("topLock"+"kit");
    }
    if(availableFurnitureList.lowerLock && availableFurnitureList.lowerLock.length > 0){
      AvailableManager.makeAvailable("lowerLock"+"kit");
    }
  }
  static makeAvailable(name) {
    $("#name" + name).attr("available", "yes");
  }
  static makeUnavailable(name) {
    $("#name" + name).attr("available", "no");
  }
}
