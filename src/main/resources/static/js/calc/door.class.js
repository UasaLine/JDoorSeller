class Door {
  static set(fieldName, value) {
    door[fieldName] = value;
    failureToSetValue = false;
  }

  static draw(door, i) {
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

    if (door.isDoorGlass == 1 && door.doorGlass != null) {
      config.glassHeight = (door.doorGlass.glassHeight * 2) / 10;
      config.glassWidth = (door.doorGlass.glassWidth * 2) / 10;

      config.topGlassPosition = 0;
      if (door.doorGlass.bottomGlassPosition == 0) {
        config.topGlassPosition = (height - glassHeight) / 2;
      } else {
        config.topGlassPosition =
          height - glassHeight - (door.doorGlass.bottomGlassPosition * 2) / 10;
      }

      config.leftGlassPosition;
      if (door.doorGlass.leftGlassPosition == 0) {
        config.leftGlassPosition = (width - glassWidth) / 2;
      } else {
        config.leftGlassPosition = (door.doorGlass.leftGlassPosition * 2) / 10;
      }
    }

    this.delete(i);

    if (!!config.color && !!config.width && !!config.height) {
      //draw door L
      let container = Door.createContainer(i, "L");

      Door.createСolor(container, config);
      Door.createTrims(container, config, door);
      Door.createFanlight(container, config, "L");

      let containerLeaf = Door.createLeafContainer(container, config, "L");


      Door.createCloser(containerLeaf, config, door);
      Door.createHandle(containerLeaf, config, door, "L");
      Door.createStep(containerLeaf, config, door);
      Door.createGlass(containerLeaf, config, door);
      Door.createLogo(containerLeaf, door);
      Door.createHinges(containerLeaf, door);
      Door.createTopOutLockDecor(containerLeaf, door);
      Door.createLowerOutLockDecor(containerLeaf, door);

      //draw door R
      let containerR = Door.createContainer(i, "R");
      Door.createСolor(containerR, config);
      Door.createFanlight(containerR, config, "R");

      let containerLeafR = Door.createLeafContainer(containerR, config, "R");

      Door.dinamicRelief(containerLeaf, containerLeafR, config);
      //Door.createRelief(containerLeafR, config, "R");
      Door.createHandle(containerLeafR, config, door, "R");
      Door.createStep(containerLeafR, config, door);
      Door.createGlass(containerLeafR, config, door);
      Door.createNightLock(containerLeafR, door);
      Door.createTopInLockDecor(containerLeafR, door);
      Door.createLowerInLockDecor(containerLeafR, door);
      Door.createShieldColor(containerLeafR, door);
      Door.createShieldDesign(containerLeafR, door);
    }
  }
  static delete(i) {
    $("#picture_doorL" + i).remove();
    $("#picture_doorR" + i).remove();
  }
  static getSidesOpening(door) {
    let result = { left: "", right: "", leaf: "" };
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

  static dinamicRelief(containerLeaf, containerLeafR, config){
    if (!door.template.design.length == 0) {
      Door.createReliefDesign(containerLeaf, config, door.template.design[0].picturePath, "L");
    }else{
      Door.createRelief(containerLeaf, config, "L");
    }
    Door.createRelief(containerLeafR, config, "R");
  }

  static createСolor(container, config) {
    $("<img>")
      .attr("class", "color_door")
      .attr("src", "images/Door/AColor1/" + config.color + ".jpg")
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
        .attr("src", "images/Door/fanlight_in.png")
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

  static createReliefDesign(containerLeaf, config, path, side) {
    $("<img>")
        .attr("class", "opening_side_images")
        .attr("src", path)
        .attr(
            "style",
            "width:" + config.width + "px; height:" + config.height + "px;"
        )
        .appendTo(containerLeaf);
  }

  static createRelief(containerLeaf, config, side) {
      $("<img>")
          .attr("class", "opening_side_images")
          .attr("src", "images/Door/" + config["sideOpening" + side] + ".png")
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
        .attr("src", "" + door.furnitureKit.closer.sketchPathFirst)
        .appendTo(containerLeaf);
    }
  }
  static createHandle(containerLeaf, config, door, side) {
    if (side === "L") {
      if (door.furnitureKit != null && door.furnitureKit.handle != null) {
        if (door.sideDoorOpen == "LEFT") {
          $("<img>")
            .attr(
              "class",
              "handle_images left_side" + config.leafCount + "_handle"
            )
            .attr("src", "" + door.furnitureKit.handle.sketchPathFirst)
            .appendTo(containerLeaf);
        } else {
          $("<img>")
            .attr(
              "class",
              "handle_images rigth_side" + config.leafCount + "_handle mirrorX"
            )
            .attr("src", "" + door.furnitureKit.handle.sketchPathFirst)
            .appendTo(containerLeaf);
        }
      }
    } else {
      if (door.furnitureKit != null && door.furnitureKit.handle != null) {
        if (door.sideDoorOpen == "LEFT") {
          $("<img>")
            .attr(
              "class",
              "handle_images rigth_side" + config.leafCount + "_handle mirrorX"
            )
            .attr("src", "" + door.furnitureKit.handle.sketchPathFirst)
            .appendTo(containerLeaf);
        } else {
          $("<img>")
            .attr(
              "class",
              "handle_images left_side" + config.leafCount + "_handle"
            )
            .attr("src", "" + door.furnitureKit.handle.sketchPathFirst)
            .appendTo(containerLeaf);
        }
      }
    }
  }
  static createStep(containerLeaf, config, door) {
    if (
      door.furnitureKit != null &&
      door.stainlessSteelDoorstep != null &&
      door.stainlessSteelDoorstep != 0
    ) {
      $("<img>")
        .attr("class", "stainlessSteelDoorstepL_images")
        .attr("src", "images/findings/door handles sketch/000000001_R.png")
        .appendTo(containerLeaf);
    }
  }
  static createGlass(containerLeaf, config, door) {
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
  static createLogo(containerLeaf, door) {
    let side = "R";
    if (door.sideDoorOpen == "LEFT") {
      side = "L";
    }

    $("<img>")
      .attr("class", "logo_brand_images_" + side)
      .attr("src", "images/findings/logo.png")
      .appendTo(containerLeaf);
  }
  static createHinges(containerLeaf, door) {
    let side = "R";
    if (door.sideDoorOpen == "LEFT") {
      side = "L";
    }

    $("<img>")
      .attr("class", "upper_hinge hinge_images_" + side)
      .attr("src", "images/findings/sharnir.png")
      .appendTo(containerLeaf);

    $("<img>")
      .attr("class", "middle_hinge hinge_images_" + side)
      .attr("src", "images/findings/sharnir.png")
      .appendTo(containerLeaf);

    $("<img>")
      .attr("class", "lower_hinge hinge_images_" + side)
      .attr("src", "images/findings/sharnir.png")
      .appendTo(containerLeaf);
  }
  static createNightLock(containerLeaf, door) {
    let side = "R";
    if (door.sideDoorOpen == "LEFT") {
      side = "L";
    }

    $("<img>")
      .attr("class", "night_lock_images_" + side)
      .attr("src", "images/findings/night_lock.png")
      .appendTo(containerLeaf);
  }

  static createTopInLockDecor(containerLeaf, door) {
    let side = "R";
    if (door.sideDoorOpen == "LEFT") {
      side = "L";
    }

    $("<img>")
      .attr("class", "top_lock_decor lock_decor_" + side)
      .attr("src", "images/findings/zamok.png")
      .appendTo(containerLeaf);
  }
  static createLowerInLockDecor(containerLeaf, door) {
    let side = "R";
    if (door.sideDoorOpen == "LEFT") {
      side = "L";
    }

    $("<img>")
      .attr("class", "lower_lock_decor lock_decor_" + side)
      .attr("src", "images/findings/zamokIn.png")
      .appendTo(containerLeaf);
  }
  static createTopOutLockDecor(containerLeaf, door) {
    let side = "L";
    if (door.sideDoorOpen == "LEFT") {
      side = "R";
    }

    $("<img>")
      .attr("class", "top_lock_decor lock_decor_" + side)
      .attr("src", "images/findings/zamok.png")
      .appendTo(containerLeaf);
  }
  static createLowerOutLockDecor(containerLeaf, door) {
    let side = "L";
    if (door.sideDoorOpen == "LEFT") {
      side = "R";
    }

    $("<img>")
      .attr("class", "lower_lock_decor lock_decor_" + side)
      .attr("src", "images/findings/zamokIn.png")
      .appendTo(containerLeaf);
  }

  static createShieldColor(containerLeaf, door) {
    if (door.shieldKit != null && door.shieldKit.shieldColor != null) {
      $("<img>")
        .attr("class", "shield_color")
        .attr("src", door.shieldKit.shieldColor.picturePath)
        .appendTo(containerLeaf);
    }
  }
  static createShieldDesign(containerLeaf, door) {
    if (door.shieldKit != null && door.shieldKit.shieldDesign != null) {
      $("<img>")
        .attr("class", "shield_design")
        .attr("src", door.shieldKit.shieldDesign.picturePath)
        .appendTo(containerLeaf);
    }
  }
}
