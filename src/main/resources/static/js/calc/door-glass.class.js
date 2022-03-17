class DoorGlass {

    static draw(containerLeaf, config, door, key) {

        let leftPosition = key === "L" ? config.leftGlassPosition : config.leftGlassPositionInner;
        let style = "width:" +
            config.glassWidth +
            "px; height:" +
            config.glassHeight +
            "px; top:" +
            config.topGlassPosition +
            "px; left:" +
            leftPosition +
            "px;";

        $("<img>")
            .attr("class", "glass_base opening_side_images")
            .attr("src", Door.dirPath + Door.getPicturePath(config.color))
            .attr("style", style)
            .appendTo(containerLeaf);

        if (DoorGlass.getGlassPicPath(door)) {
            $("<img>")
                .attr("class", "glass_style opening_side_images")
                .attr("src", Door.dirPath + DoorGlass.getGlassPicPath(door))
                .attr("style", style)
                .appendTo(containerLeaf);
        }

        if (DoorGlass.getGlassPelliclePicPath(door)) {
            $("<img>")
                .attr("class", "glass_pellicle opening_side_images")
                .attr("src", Door.dirPath + DoorGlass.getGlassPelliclePicPath(door))
                .attr("style", style)
                .appendTo(containerLeaf);
        }
    }

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
        let result;
        if (door.doorGlass.typeDoorGlass) {
            result = door.doorGlass.typeDoorGlass.sketchPathFirst;
        }
        return result;
    }

    static getGlassPelliclePicPath(door) {
        let result;
        if (door.doorGlass.toning) {
            result = door.doorGlass.toning.sketchPathFirst
        }
        return result;
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
