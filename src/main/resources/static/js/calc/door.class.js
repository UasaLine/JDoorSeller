class Door {
    static listColorsEntity;
    static dirPath = "../../";

    static init() {
        Door.getColorInstans();
    };

    static set(fieldName, value) {
        door[fieldName] = value;
        failureToSetValue = false;
        Price.erase();
    }

    static setGlass(fieldName, value) {
        door.doorGlass[fieldName] = value;
        failureToSetValue = false;
    }

    static draw(door, i) {
        Door.drawDoor(door, i);
        Door.displayPrice(door);
    }

    static drawDoor(door, i) {
        let sideDoorOpen = door.sideDoorOpen;
        let config = new Object();
        config.color = door.doorColor;
        config.width = (door.widthDoor * 2) / 10;
        config.height = (door.heightDoor * 2) / 10;
        config.doorFanlightHeight = (door.doorFanlightHeight * 2) / 10;
        config.i = i;

        let sidesOpening = Door.getSidesOpening(door);

        config.sideOpeningL = sidesOpening.left;
        config.sideOpeningR = sidesOpening.right;
        config.leafCount = sidesOpening.leaf;

        config.glassHeight = DoorGlass.getHeight(door);
        config.glassWidth = DoorGlass.getWidth(door);
        config.topGlassPosition = DoorGlass.getTopGlassPosition(door);
        config.leftGlassPosition = DoorGlass.getLeftGlassPosition(door);
        config.leftGlassPositionInner = DoorGlass.getLeftGlassPositionInner(door);

        this.delete(i);

        if (!!config.color && !!config.width && !!config.height) {
            //draw door L
            let container = Door.createContainer(i, "L");

            Door.createHideDoor(container, config);
            Door.createWaitImages(container, config);
            Door.createСolor(container, config);
            Door.createOutShield(container, config, door);
            Door.createOutShieldDesign(container, door);
            Door.createTrims(container, config, door);
            Door.createFanlight(container, config, "L");

            let containerLeaf = Door.createLeafContainer(container, config, "L");

            Door.dinamicRelief(containerLeaf, config, door, "L");
            Door.createCloser(containerLeaf, config, door);
            Door.createHandle(containerLeaf, config, door, "L");
            Door.createStep(containerLeaf, config, door, "L");
            Door.createGlass(containerLeaf, config, door, "L");
            Door.createLogo(containerLeaf, door);
            Door.createHinges(containerLeaf, door);
            Door.createTopOutLockDecor(containerLeaf, door);
            Door.createLowerOutLockDecor(containerLeaf, door);
            Door.creatPeephole(containerLeaf, door, "L");

            //draw door R
            let containerR = Door.createContainer(i, "R");
            Door.createHideDoor(containerR, config);
            Door.createСolor(containerR, config);
            Door.createFanlight(containerR, config, "R");

            let containerLeafR = Door.createLeafContainer(containerR, config, "R");

            Door.createRelief(containerLeafR, config, "R");
            Door.createHandle(containerLeafR, config, door, "R");
            Door.createStep(containerLeafR, config, door, "R");
            Door.createGlass(containerLeafR, config, door, "R");
            Door.createNightLock(containerLeafR, door);
            Door.createTopInLockDecor(containerLeafR, door);
            Door.createLowerInLockDecor(containerLeafR, door);
            Door.createShieldColor(containerLeafR, door);
            Door.createShieldDesign(containerLeafR, door);
            Door.createShieldOverColor(containerLeafR, door);

            Door.createShieldGlass(containerLeafR, door);
            Door.creatPeephole(containerLeafR, door, "R");

            //$(".hide_door").removeClass("hide_door");
        }
    }

    static delete(i) {
        $("#picture_doorL" + i).remove();
        $("#picture_doorR" + i).remove();
    }

    static getSidesOpening(door) {
        let result = {left: "", right: "", leaf: ""};
        if (door.doorLeaf == 1) {
            result.left = "InSlit";
            result.right = "OutSlit";
        } else if (door.doorLeaf == 2) {
            result.leaf = "2";

            if (!!door.sideDoorOpen) {
                if (door.sideDoorOpen == "LEFT") {
                    result.left = "InSlit_D_L";
                    result.right = "OutSlit_D_L";
                } else {
                    result.left = "InSlit_D_R";
                    result.right = "OutSlit_D_R";
                }
            } else {
                result.left = "InSlit_D_L";
                result.right = "OutSlit_D_L";
            }
        }
        return result;
    }

    static createContainer(i, side) {
        $("<div>")
            .attr("class", "picture_door" + side)
            .attr("id", "picture_door" + side + i)
            .appendTo("#doorDiv" + i);

        return "#picture_door" + side + i;
    }

    static dinamicRelief(containerLeaf, config, door, side) {
        if (door.doorDesign != null && door.doorDesign.doorDesign != null) {
            Door.createReliefDesign(containerLeaf, config, door.doorDesign.doorDesign.picturePath, door, side);
        } else {
            Door.createRelief(containerLeaf, config, side);
        }
    }

    static getPicturePath(nameColor) {
        let listColors = Door.listColorsEntity;
        for (let i = 0; i < listColors.length; i++) {
            if (listColors[i].name == nameColor) {
                return listColors[i].picturePath;
            }
        }
    }

    static createHideDoor(container, config) {
        $("<div>")
            .attr("class", "hide_door element")
            .attr(
                "style",
                "width:" +
                config.width +
                "px; height:" +
                (config.doorFanlightHeight + config.height) +
                "px;"
            )
            .appendTo(container);
    }

    static createWaitImages(container, config) {
        //let pathPicture = Door.getPicturePath(config.color);
        //let pathPicture = door.doorDesign.doorColor.picturePath;
        $("<img>")
            .attr("class", "wait_image")
            .attr("src", Door.dirPath + "images/wait2.gif")
            .appendTo(container);
    }

    static createСolor(container, config) {
        let pathPicture = Door.getPicturePath(config.color);
        //let pathPicture = door.doorDesign.doorColor.picturePath;
        $("<img>")
            .attr("class", "color_door")
            .attr("src", Door.dirPath + pathPicture)
            .attr(
                "style",
                "width:" +
                config.width +
                "px; height:" +
                (config.doorFanlightHeight + config.height) +
                "px;"
            )
            .appendTo(container);
    }

    static createOutShield(container, config, door) {
        if (door.doorDesign.doorDesign != null) {
            if (door.doorDesign.doorDesign.containsWoodPanel == 1) {
                let pathPicture = null;
                let pathMask = null;
                if (door.doorDesign.outShieldColor != null) {
                    pathPicture = door.doorDesign.outShieldColor.picturePath;
                    pathMask = door.doorDesign.doorDesign.maskPath;
                }
                let scaleX = Door.reflectionPicture(door);

                $("<img>")
                    .attr("class", "color_mask")
                    .attr("src", Door.dirPath + pathPicture)
                    .attr(
                        "style",
                        "width:" +
                        config.width +
                        "px; height:" +
                        (config.doorFanlightHeight + config.height) +
                        "px;" + "-webkit-mask-box-image: url(" + Door.dirPath + pathMask + ");" +
                        "transform: scale(" + scaleX + ", 1)"
                    )
                    .appendTo(container);
            }
        }
    }

    static createTrims(container, config, door) {
        //Trim
        if (door.leftDoorTrim == 0) {
            $("<div>")
                .attr("class", "puffinLeft")
                .attr(
                    "style",
                    "width:3.5%; height:" +
                    (config.doorFanlightHeight + config.height) +
                    "px;"
                )
                .appendTo(container);
        }
        if (door.rightDoorTrim == 0) {
            $("<div>")
                .attr("class", "puffinRight")
                .attr(
                    "style",
                    "width:3.5%; height:" +
                    (config.doorFanlightHeight + config.height) +
                    "px;"
                )
                .appendTo(container);
        }
        if (door.topDoorTrim == 0) {
            $("<div>")
                .attr("class", "puffinUp")
                .attr("style", "width:" + config.width + "px; height:2%;")
                .appendTo(container);
        }
    }

    static createFanlight(container, config, side) {
        if (config.doorFanlightHeight > 0) {
            //draw fanlight
            $("<div>")
                .attr("class", "fanlight")
                .attr("id", "fanlight" + side + config.i)
                .attr(
                    "style",
                    "width:" +
                    config.width +
                    "px; height:" +
                    config.doorFanlightHeight +
                    "px;"
                )
                .appendTo(container);

            $("<img>")
                .attr("class", "opening_side_images")
                .attr("src", Door.dirPath + "images/Door/fanlight_in.png")
                .attr(
                    "style",
                    "width:" +
                    config.width +
                    "px; height:" +
                    config.doorFanlightHeight +
                    "px;"
                )
                .appendTo(container);
        }
    }

    static createLeafContainer(container, config, side) {
        $("<div>")
            .attr("class", "Leaf")
            .attr("id", "Leaf" + side + config.i)
            .attr(
                "style",
                "width:" + config.width + "px; height:" + config.height + "px;"
            )
            .appendTo(container);

        return "#Leaf" + side + config.i;
    }

    static reflectionPicture(door) {
        if (door.sideDoorOpen == "RIGHT") {
            return 1;
        } else return -1;
    }

    static createReliefDesign(containerLeaf, config, path, door, side) {
        let scaleX = Door.reflectionPicture(door);
        $("<img>")
            .attr("class", "opening_side_images")
            .attr("src", Door.dirPath + path)
            .attr(
                "style",
                "width:" + config.width + "px; height:" + config.height + "px; transform: scale(" + scaleX + ", 1)"
            )
            .appendTo(containerLeaf);
    }

    static createRelief(containerLeaf, config, side) {
        $("<img>")
            .attr("class", "opening_side_images")
            .attr("src", Door.dirPath + "images/Door/" + config["sideOpening" + side] + ".png")
            .attr(
                "style",
                "width:" + config.width + "px; height:" + config.height + "px;"
            )
            .appendTo(containerLeaf);

    }

    static createCloser(containerLeaf, config, door) {
        if (door.furnitureKit != null && door.furnitureKit.closer != null) {
            $("<img>")
                .attr("class", "closer_images")
                .attr("src", Door.dirPath + +door.furnitureKit.closer.sketchPathFirst)
                .appendTo(containerLeaf);
        }
    }

    static createHandle(containerLeaf, config, door, side) {
        if (side === "L") {
            if (door.furnitureKit != null && door.furnitureKit.handle != null) {
                if (door.sideDoorOpen == "RIGHT") {
                    $("<img>")
                        .attr(
                            "class",
                            "handle_images left_side" + config.leafCount + "_handle"
                        )
                        .attr("src", Door.dirPath + door.furnitureKit.handle.sketchPathFirst)
                        .appendTo(containerLeaf);
                } else {
                    $("<img>")
                        .attr(
                            "class",
                            "handle_images rigth_side" + config.leafCount + "_handle mirrorX"
                        )
                        .attr("src", Door.dirPath + door.furnitureKit.handle.sketchPathFirst)
                        .appendTo(containerLeaf);
                }
            }
        } else {
            if (door.furnitureKit != null && door.furnitureKit.handle != null) {
                if (door.sideDoorOpen == "RIGHT") {
                    $("<img>")
                        .attr(
                            "class",
                            "handle_images rigth_side" + config.leafCount + "_handle mirrorX"
                        )
                        .attr("src", Door.dirPath + door.furnitureKit.handle.sketchPathFirst)
                        .appendTo(containerLeaf);
                } else {
                    $("<img>")
                        .attr(
                            "class",
                            "handle_images left_side" + config.leafCount + "_handle"
                        )
                        .attr("src", Door.dirPath + door.furnitureKit.handle.sketchPathFirst)
                        .appendTo(containerLeaf);
                }
            }
        }
    }

    static createStep(containerLeaf, config, door, side) {
        if (
            door.furnitureKit != null &&
            door.stainlessSteelDoorstep != null &&
            door.stainlessSteelDoorstep != 0
        ) {
            $("<img>")
                .attr("class", "stainlessSteelDoorstep" + side + "_images")
                .attr("src", Door.dirPath + "images/findings/door handles sketch/000000001_R.png")
                .appendTo(containerLeaf);
        }
    }

    static createGlass(containerLeaf, config, door, key) {
        if (door.isDoorGlass == 1 && door.doorGlass != null) {
            DoorGlass.draw(containerLeaf, config, door, key);
        }
    }

    static createLogo(containerLeaf, door) {
        let side = "R";
        if (door.sideDoorOpen == "RIGHT") {
            side = "L";
        }

        $("<img>")
            .attr("class", "logo_brand_images_" + side)
            .attr("src", Door.dirPath + "images/findings/logo.png")
            .appendTo(containerLeaf);
    }

    static createHinges(containerLeaf, door) {
        let side = "R";
        if (door.sideDoorOpen == "RIGHT") {
            side = "L";
        }

        $("<img>")
            .attr("class", "upper_hinge hinge_images_" + side)
            .attr("src", Door.dirPath + "images/findings/sharnir.png")
            .appendTo(containerLeaf);

        $("<img>")
            .attr("class", "middle_hinge hinge_images_" + side)
            .attr("src", Door.dirPath + "images/findings/sharnir.png")
            .appendTo(containerLeaf);

        $("<img>")
            .attr("class", "lower_hinge hinge_images_" + side)
            .attr("src", Door.dirPath + "images/findings/sharnir.png")
            .appendTo(containerLeaf);
    }

    static createNightLock(containerLeaf, door) {
        if (door.furnitureKit.nightLock == null) {
            return;
        }
        let side = "R";
        if (door.sideDoorOpen == "RIGHT") {
            side = "L";
        }

        let pathImages = door.furnitureKit.nightLock.sketchPathFirst;

        if (pathImages == "") {
            pathImages = "images/findings/night_lock.png";
        }

        $("<img>")
            .attr("class", "night_lock_images_" + side)
            .attr("src", Door.dirPath + pathImages)
            .appendTo(containerLeaf);
    }

    static creatPeephole(containerLeaf, door, inOutDoor) {
        if (door.furnitureKit.peephole == null) {
            return;
        }
        let position = "";
        if (door.furnitureKit.peepholePosition == "CENTER") {
            position = "center_"
        }
        let side;

        if (door.sideDoorOpen == "RIGHT") {
            if (inOutDoor == "L") {
                side = "R";
            } else {
                side = "L";
            }
        } else {
            if (inOutDoor == "L") {
                side = "L";
            } else {
                side = "R";
            }
        }

        let pathImages = door.furnitureKit.peephole.sketchPathFirst;

        if (pathImages == "") {
            pathImages = "images/findings/sketch/peephole/peephole_out.png";
        }

        $("<img>")
            .attr("class", "peephole_images_" + position + side)
            .attr("src", Door.dirPath + pathImages)
            .appendTo(containerLeaf);
    }


    static createTopInLockDecor(containerLeaf, door) {
        let side = "R";
        if (door.sideDoorOpen == "RIGHT") {
            side = "L";
        }
        let imgKey;
        if (door.furnitureKit.topInLockDecor == null) {
            return;
        } else {
            imgKey = door.furnitureKit.topInLockDecor.sketchPathFirst;
        }
        //images/findings/zamok.png
        $("<img>")
            .attr("class", "top_lock_decor lock_decor_" + side)
            .attr("src", Door.dirPath + imgKey)
            .appendTo(containerLeaf);
    }

    static createLowerInLockDecor(containerLeaf, door) {
        let side = "R";
        if (door.sideDoorOpen == "RIGHT") {
            side = "L";
        }

        let imgKey;
        if (door.furnitureKit.lowerInLockDecor == null) {
            return;
        } else {
            imgKey = door.furnitureKit.lowerInLockDecor.sketchPathFirst;
        }

        $("<img>")
            .attr("class", "lower_lock_decor lock_decor_" + side)
            .attr("src", Door.dirPath + imgKey)
            .appendTo(containerLeaf);
    }

    static createTopOutLockDecor(containerLeaf, door) {
        let side = "L";
        if (door.sideDoorOpen == "RIGHT") {
            side = "R";
        }

        let imgKey;
        if (door.furnitureKit.topOutLockDecor == null) {
            return;
        } else {
            imgKey = door.furnitureKit.topOutLockDecor.sketchPathFirst;
        }

        // "images/findings/zamok.png"
        //door.furnitureKit.topOutLockDecor.sketchPathFirst;

        $("<img>")
            .attr("class", "top_lock_decor lock_decor_" + side)
            .attr("src", Door.dirPath + imgKey)
            .appendTo(containerLeaf);

    }

    static createLowerOutLockDecor(containerLeaf, door) {
        let side = "L";
        if (door.sideDoorOpen == "RIGHT") {
            side = "R";
        }

        let imgKey;
        if (door.furnitureKit.lowerOutLockDecor == null) {
            return;
        } else {
            imgKey = door.furnitureKit.lowerOutLockDecor.sketchPathFirst;
        }
        $("<img>")
            .attr("class", "lower_lock_decor lock_decor_" + side)
            .attr("src", Door.dirPath + imgKey)
            .appendTo(containerLeaf);
    }

    static createShieldColor(containerLeaf, door) {
        if (door.shieldKit != null && door.shieldKit.shieldColor != null) {
            let scaleX = Door.reflectionPicture(door);
            $("<img>")
                .attr("class", "shield_color")
                .attr("src", Door.dirPath + door.shieldKit.shieldColor.picturePath)
                .attr("style",
                    "px; transform: scale(" + scaleX + ", 1)")
                .appendTo(containerLeaf);
        }
    }

    static createShieldOverColor(containerLeaf, door) {
        if (door.shieldKit != null &&
            door.shieldKit.shieldColor != null &&
            door.shieldKit.shieldDesign &&
            door.shieldKit.shieldDesign.containsOtherColor == 1) {
            let scaleX = Door.reflectionPicture(door);

            let pathImage = null;
            let pathMask = null;
            if (door.shieldKit.shieldOverColor != null) {
                pathImage = door.shieldKit.shieldOverColor.picturePath;
                pathMask = door.shieldKit.shieldDesign.maskPath;
            }
            $("<img>")
                .attr("class", "shield_over_color")
                .attr("src", Door.dirPath + pathImage)
                .attr(
                    "style",
                    "-webkit-mask-box-image: url(" + Door.dirPath + pathMask + ");" +
                    "transform: scale(" + scaleX + ", 1)"
                )
                .appendTo(containerLeaf);
        }
    }

    static createShieldDesign(containerLeaf, door) {
        if (door.shieldKit != null && door.shieldKit.shieldDesign != null && door.shieldKit.shieldColor != null) {
            if (door.shieldKit.shieldColor.containsDesign == 0) {
                let scaleX = Door.reflectionPicture(door);
                $("<img>")
                    .attr("class", "shield_design")
                    .attr("src", Door.dirPath + door.shieldKit.shieldDesign.picturePath)
                    .attr("style",
                        "px; transform: scale(" + scaleX + ", 1)")
                    .appendTo(containerLeaf);
            }
        }
    }

    static createOutShieldDesign(containerLeaf, door) {
        if (door.doorDesign != null && door.doorDesign.outShieldDesign != null) {
            let scaleX = Door.reflectionPicture(door);
            $("<img>")
                .attr("class", "shield_out_design")
                .attr("src", Door.dirPath + door.doorDesign.outShieldDesign.picturePath)
                .attr("style",
                    "px; transform: scale(" + scaleX + ", 1)")
                .appendTo(containerLeaf);
        }
    }

    static createShieldGlass(containerLeaf, door) {
        if (door.shieldKit != null && door.shieldKit.shieldGlass != null) {
            if (door.shieldKit.shieldColor.containsDesign == 0) {
                let scaleX = Door.reflectionPicture(door);
                $("<img>")
                    .attr("class", "shield_glass")
                    .attr("src", Door.dirPath + door.shieldKit.shieldGlass.picturePath)
                    .attr("style",
                        "px; transform: scale(" + scaleX + ", 1)")
                    .appendTo(containerLeaf);
            }
        }
    }

    static getColorInstans() {
        $.ajax({
            url: location.origin + "/colors",
            dataType: "json",
            success: function (data) {
                Door.listColorsEntity = data;
            },
            error: function (data) {
                alert("!ERROR:   get colors (");
            },
        });
    }

    static displayPrice(door) {

        if (door.priceWithMarkup !== 0) {
            let price = Price.ToString(door.priceWithMarkup);
            $("#price").show();
            $("#price").text(price);
            $("<span>").attr("class", "rub").text("Р").appendTo("#price");

            $("#showCalculationCostListButton").show();
        } else {
            $("#price").hide();
            $("#showCalculationCostListButton").hide();
        }

        //door.costList.totalCost;
        $(".decryption").remove();

        if (door.costList !== null) {
            var tab = door.costList.list;
            var size = tab.length;
            for (var i = 0; i < size; ++i) {
                $("<sran>")
                    .attr("class", "decryption")
                    .text("" + tab[i].name + " - " + tab[i].cost)
                    .appendTo("#calculateResultDiv");
                $("<br>").attr("class", "decryption").appendTo("#calculateResultDiv");
            }
        }
    }

    static priceErase() {
        Price.erase();
    }

    static sideOpenAsRu(sideOpen) {
        let result = sideOpen;
        if (sideOpen === "LEFT") {
            result = "ЛЕВОЕ";
        } else if (sideOpen === "RIGHT") {
            result = "ПРАВОЕ";
        }
        return result;
    }

    static setGlassSizes(sizes) {
        Door.setGlass("glassWidth", sizes.width);
        Door.setGlass("glassHeight", sizes.height);
        Door.setGlass("leftGlassPosition", sizes.left);
        Door.setGlass("bottomGlassPosition", sizes.bottom);
    }
}

class Price {
    static ToString(price) {
        let str = String(price);
        let str3 = str.slice(str.length - 3, str.length);
        let str2 = str.slice(0, str.length - 3);
        let str1 = "";
        if (str2.length > 3) {
            str2 = str.slice(str.length - 6, str.length - 3);
            str1 = str.slice(0, str.length - 6);
            return str1 + "'" + str2 + "'" + str3;
        } else {
            return str2 + "'" + str3;
        }
    }

    static erase() {
        door.priceWithMarkup = 0;
    }
}
