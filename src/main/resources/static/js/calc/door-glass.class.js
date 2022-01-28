class DoorGlass {

    static getHeight(door) {
        return (door.doorGlass.glassHeight * 2) / 10;
    }

    static getWidth(door) {
        return (door.doorGlass.glassWidth * 2) / 10;
    }

    static getTopGlassPosition(door) {
        let result = 0;
        if (door.doorGlass.bottomGlassPosition == 0) {
            //glassHeight
            result = (door.heightDoor - door.doorGlass.glassHeight) / 10;
        } else {
            result = ((door.heightDoor - Number(door.doorGlass.glassHeight) - Number(door.doorGlass.bottomGlassPosition)) * 2) / 10;
        }
        return result;
    }

    static getLeftGlassPosition(door) {
        let result = 0;
        if (door.doorGlass.leftGlassPosition == 0) {
            result = DoorGlass.subtractWidth(door);
        } else {
            result = (Number(door.doorGlass.leftGlassPosition) * 2) / 10;
        }
        return result;
    }

    static getLeftGlassPositionInner(door) {
        let result = 0;
        if (door.doorGlass.leftGlassPosition == 0) {
            result = DoorGlass.subtractWidth(door);
        } else {
            result = ((door.widthDoor - door.doorGlass.glassWidth) * 2) / 10
                - (Number(door.doorGlass.leftGlassPosition) * 2) / 10;
        }
        return result;
    }

    static subtractWidth(door) {
        return (door.widthDoor - door.doorGlass.glassWidth) / 10;
    }

    static getGlassPicPath(door) {
        return door.doorGlass.typeDoorGlass.sketchPathFirst;
    }
}

class GlassPosition {

    static getSizes(door, item) {
        if (item.type === "MAXIMUM_AREA") {

            let left = item.value1;
            let bottom = item.value3;

            let width = door.widthDoor - item.value1 - item.value2;
            let height = door.heightDoor - item.value3 - item.value4;

            return {
                "width": width,
                "height": height,
                "left": left,
                "bottom": bottom
            }

        } else if (item.type === "BOTTOM") {

            let left = (door.widthDoor - item.value1) / 2;
            let bottom = item.value3;

            let width = item.value1;
            let height = item.value2;

            return {
                "width": width,
                "height": height,
                "left": left,
                "bottom": bottom
            }
        } else {
            alert("!!!error: The glass position type is not defined on the client! contact the developer");
        }
    }
}
