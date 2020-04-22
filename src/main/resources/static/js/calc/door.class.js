class Door {
    static set(fieldName, value) {
        door[fieldName] = value;
        failureToSetValue = false;
    }
    static draw(door, i) {
        var color = door.doorColor;
        var width = (door.widthDoor * 2) / 10;
        var height = (door.heightDoor * 2) / 10;
        var doorFanlightHeight = (door.doorFanlightHeight * 2) / 10;

        var leafCount = "";
        if (door.doorLeaf == 1) {
            var sideOpeningL = "InSlit";
            var sideOpeningR = "OutSlit";
        } else if (door.doorLeaf == 2) {
            leafCount = "2";

            if (!!door.sideDoorOpen) {
                if (door.sideDoorOpen == "LEFT") {
                    var sideOpeningL = "InSlit_D_L";
                    var sideOpeningR = "OutSlit_D_L";
                } else {
                    var sideOpeningL = "InSlit_D_R";
                    var sideOpeningR = "OutSlit_D_R";
                }
            } else {
                var sideOpeningL = "InSlit_D_L";
                var sideOpeningR = "OutSlit_D_L";
            }
        }

        if (door.isDoorGlass == 1 && door.doorGlass != null) {
            var glassHeight = (door.doorGlass.glassHeight * 2) / 10;
            var glassWidth = (door.doorGlass.glassWidth * 2) / 10;

            var topGlassPosition = 0;
            if (door.doorGlass.bottomGlassPosition == 0) {
                topGlassPosition = (height - glassHeight) / 2;
            } else {
                topGlassPosition =
                    height - glassHeight - (door.doorGlass.bottomGlassPosition * 2) / 10;
            }

            var leftGlassPosition;
            if (door.doorGlass.leftGlassPosition == 0) {
                leftGlassPosition = (width - glassWidth) / 2;
            } else {
                leftGlassPosition = (door.doorGlass.leftGlassPosition * 2) / 10;
            }
        }

        //delete
        $("#picture_doorL" + i).remove();
        $("#picture_doorR" + i).remove();

        //draw door L
        $("<div>")
            .attr("class", "picture_doorL")
            .attr("id", "picture_doorL" + i)
            .appendTo("#doorDiv" + i);
        if (!!color && !!width && !!height) {
            $("<img>")
                .attr("class", "color_door")
                .attr("src", "images/Door/AColor1/" + color + ".jpg")
                .attr(
                    "style",
                    "width:" + width + "px; height:" + (doorFanlightHeight + height) + "px;"
                )
                .appendTo("#picture_doorL" + i);

            //Trim
            if (door.leftDoorTrim == 0) {
                $("<div>")
                    .attr("class", "puffinLeft")
                    .attr(
                        "style",
                        "width:3.5%; height:" + (doorFanlightHeight + height) + "px;"
                    )
                    .appendTo("#picture_doorL" + i);
            }
            if (door.rightDoorTrim == 0) {
                $("<div>")
                    .attr("class", "puffinRight")
                    .attr(
                        "style",
                        "width:3.5%; height:" + (doorFanlightHeight + height) + "px;"
                    )
                    .appendTo("#picture_doorL" + i);
            }
            if (door.topDoorTrim == 0) {
                $("<div>")
                    .attr("class", "puffinUp")
                    .attr("style", "width:" + width + "px; height:2%;")
                    .appendTo("#picture_doorL" + i);
            }

            if (doorFanlightHeight > 0) {
                //draw fanlight
                $("<div>")
                    .attr("class", "fanlight")
                    .attr("id", "fanlightL" + i)
                    .attr(
                        "style",
                        "width:" + width + "px; height:" + doorFanlightHeight + "px;"
                    )
                    .appendTo("#picture_doorL" + i);

                $("<img>")
                    .attr("class", "opening_side_images")
                    .attr("src", "images/Door/fanlight_in.png")
                    .attr(
                        "style",
                        "width:" + width + "px; height:" + doorFanlightHeight + "px;"
                    )
                    .appendTo("#fanlightL" + i);
            }

            //draw Leaf
            $("<div>")
                .attr("class", "Leaf")
                .attr("id", "LeafL" + i)
                .attr("style", "width:" + width + "px; height:" + height + "px;")
                .appendTo("#picture_doorL" + i);

            $("<img>")
                .attr("class", "opening_side_images")
                .attr("src", "images/Door/" + sideOpeningL + ".png")
                .attr("style", "width:" + width + "px; height:" + height + "px;")
                .appendTo("#LeafL" + i);

            //draw closer
            if (door.furnitureKit != null && door.furnitureKit.closer != null) {
                $("<img>")
                    .attr("class", "closer_images")
                    .attr("src", "" + door.furnitureKit.closer.sketchPathFirst)
                    .appendTo("#LeafL" + i);
            }

            //draw handle
            if (door.furnitureKit != null && door.furnitureKit.handle != null) {
                if (door.sideDoorOpen == "LEFT") {
                    $("<img>")
                        .attr("class", "handle_images left_side" + leafCount + "_handle")
                        .attr("src", "" + door.furnitureKit.handle.sketchPathSecond)
                        .appendTo("#LeafL" + i);
                } else {
                    $("<img>")
                        .attr("class", "handle_images rigth_side" + leafCount + "_handle")
                        .attr("src", "" + door.furnitureKit.handle.sketchPathFirst)
                        .appendTo("#LeafL" + i);
                }
            }

            //draw doorstep
            if (
                door.furnitureKit != null &&
                door.stainlessSteelDoorstep != null &&
                door.stainlessSteelDoorstep != 0
            ) {
                $("<img>")
                    .attr("class", "stainlessSteelDoorstepL_images")
                    .attr("src", "images/findings/door handles sketch/000000001_R.png")
                    .appendTo("#LeafL" + i);
            }

            if (door.isDoorGlass == 1 && door.doorGlass != null) {
                //draw glass
                $("<img>")
                    .attr("class", "opening_side_images")
                    .attr("src", "images/Door/window.png")
                    .attr(
                        "style",
                        "width:" +
                        glassWidth +
                        "px; height:" +
                        glassHeight +
                        "px; top:" +
                        topGlassPosition +
                        "px; left:" +
                        leftGlassPosition +
                        "px;"
                    )
                    .appendTo("#LeafL" + i);
            }
        }

        //draw door R
        $("<div>")
            .attr("class", "picture_doorR")
            .attr("id", "picture_doorR" + i)
            .appendTo("#doorDiv" + i);
        if (!!color && !!width && !!height) {
            $("<img>")
                .attr("class", "color_door")
                .attr("src", "images/Door/AColor1/" + color + ".jpg")
                .attr(
                    "style",
                    "width:" + width + "px; height:" + (doorFanlightHeight + height) + "px;"
                )
                .appendTo("#picture_doorR" + i);

            if (doorFanlightHeight > 0) {
                //draw fanlight
                $("<div>")
                    .attr("class", "fanlight")
                    .attr("id", "fanlightR" + i)
                    .attr(
                        "style",
                        "width:" + width + "px; height:" + doorFanlightHeight + "px;"
                    )
                    .appendTo("#picture_doorR" + i);

                $("<img>")
                    .attr("class", "opening_side_images")
                    .attr("src", "images/Door/fanlight_out.png")
                    .attr(
                        "style",
                        "width:" + width + "px; height:" + doorFanlightHeight + "px;"
                    )
                    .appendTo("#fanlightR" + i);
            }

            //draw Leaf
            $("<div>")
                .attr("class", "Leaf")
                .attr("id", "LeafR" + i)
                .attr("style", "width:" + width + "px; height:" + height + "px;")
                .appendTo("#picture_doorR" + i);

            $("<img>")
                .attr("class", "opening_side_images")
                .attr("src", "images/Door/" + sideOpeningR + ".png")
                .attr("style", "width:" + width + "px; height:" + height + "px;")
                .appendTo("#LeafR" + i);

            //draw handle
            if (door.furnitureKit != null && door.furnitureKit.handle != null) {
                if (door.sideDoorOpen == "LEFT") {
                    $("<img>")
                        .attr("class", "handle_images rigth_side" + leafCount + "_handle")
                        .attr("src", "" + door.furnitureKit.handle.sketchPathFirst)
                        .appendTo("#LeafR" + i);
                } else {
                    $("<img>")
                        .attr("class", "handle_images left_side" + leafCount + "_handle")
                        .attr("src", "" + door.furnitureKit.handle.sketchPathSecond)
                        .appendTo("#LeafR" + i);
                }
            }

            //draw size
            //$('<span>').attr('class','sizeDoorSpan').attr('style','width:20px; height:'+height+'px; ').html(door.heightDoor).appendTo('#picture_doorR'+i);
            //$('<span>').attr('class','sizeDoorSpanBottom').attr('style','width:'+width+'px; height:20px;left:0px;top:'+height+'px;').html(door.widthDoor).appendTo('#picture_doorR'+i);

            //draw doorstep
            if (
                door.furnitureKit != null &&
                door.stainlessSteelDoorstep != null &&
                door.stainlessSteelDoorstep != 0
            ) {
                $("<img>")
                    .attr("class", "stainlessSteelDoorstepR_images")
                    .attr("src", "images/findings/door handles sketch/000000001_R.png")
                    .appendTo("#LeafR" + i);
            }

            if (door.isDoorGlass == 1 && door.doorGlass != null) {
                //draw glass
                var reflectionleftGlassPosition = width - glassWidth - leftGlassPosition;
                $("<img>")
                    .attr("class", "opening_side_images")
                    .attr("src", "images/Door/window.png")
                    .attr(
                        "style",
                        "width:" +
                        glassWidth +
                        "px; height:" +
                        glassHeight +
                        "px; top:" +
                        topGlassPosition +
                        "px; left:" +
                        reflectionleftGlassPosition +
                        "px;"
                    )
                    .appendTo("#LeafR" + i);
            }
        }
    }
}