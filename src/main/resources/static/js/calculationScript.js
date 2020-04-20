jQuery("document").ready(function () {
  var currentItem = "";
  var goTo;
  var currentItemForDisplay = "";
  var currentItemDaughterForDisplay = "";
  var currentItemForDisplayId = "";

  var doorLeaf = 1;
  var fanlight = 0;

  var door;
  var failureToSetValue = false;
  var classList;

  var colors;
  var RestrictionOfSelectionFields;
  var availableFurnitureList;

  var id = $("#id").text();
  var orderId = $("#orderId").text();
  var typeId = 0;

  var sizeMin = 0;
  var sizeMax = 0;

  var selectSizeOpen = false;
  var firstPress = true;

  var historyList = new Array();
  var currentHisPoint = 0;

  var audio = $("#mySoundClip")[0];

  if (id > 0) {
    getNewDoorInstans(false);
  } else {
    getClassList();
  }

  function FillOutForm(data, updateClassDiv) {
    displayObject(data);
    if (updateClassDiv) {
      displayDoorClass3(door.availableDoorClass);
      currentItem = "doorClass";
      hideShowField(true);
    }
    displayPrice();
  }

  //--------------------------------------
  //select
  //--------------------------------------

  $(".select_door_class").on("click", ".class_card", function () {
    fillInType($(this).attr("data"), classList);

    currentItem = "doorType";
    currentItemForDisplay = $(this).html();
    currentItemDaughterForDisplay = "";
    currentItemForDisplayId = currentItem;
    failureToSetValue = false;

    hideShowField(true);
  });

  $(".select_door_type").on("click", ".typeLine", function () {
    doorLeaf = $(this).attr("data-LeafDoorLeaf");
    typeId = $(this).attr("data");

    pickOut(this);

    getNewDoorInstans(false);
  });
  $(".select_door_type").on("mouseenter", ".typeLine", function () {
    var id = $(this).attr("data");
    $("#doorTypeDaughter" + id).removeClass("ghost");
  });
  $(".select_door_type").on("mouseleave", ".typeLine", function () {
    var id = $(this).attr("data");
    $("#doorTypeDaughter" + id).addClass("ghost");
  });

  $(".vertical_menu_button").on("click", function () {
    if ($("#name" + $(this).attr("id") + "").attr("available") === "yes") {
      assignPreviouValue();
      processItemSelection(this);
    }
  });

  $(".navigation_panel_div").on("click", ".navigation_panel", function () {
    processItemSelection(this);
  });

  $(".color_pages").on("click", ".pag", function () {
    if ($(this).attr("data") == ">") {
      var val = Number.parseInt($(".color_pages").attr("data"));
      displayColor(val + 1);
    } else {
      displayColor($(this).attr("data"));
    }
  });

  $(".div_images_Color").on("click", function () {
    setDoorField($(this).attr("Item"), $(this).attr("data"));
    representationField($(this).attr("data"));
    pickOut(this);
  });

  $(".div_images_DoorGlass").on("click", function () {
    setDoorGlassImg($(this).attr("Item"), $(this).attr("data"));
    representationField($(this).children("span").html());
    pickOut(this);
  });

  $(".div_images_furniture").on("click", function () {
    setDoorFurniture(
      $(this).attr("Item"),
      $(this).attr("data"),
      door.furnitureKit
    );
    displayObject(door);
    pickOut(this);
    if (goTo != "") {
      currentItem = goTo;
      hideShowField(true);
    }
  });

  $(".div_images_Image").on("click", function () {
    setDoorFurniture(
      $(this).attr("Item"),
      $(this).attr("data"),
      door.shieldKit
    );
    displayObject(door);
    pickOut(this);
    if (goTo != "") {
      currentItem = goTo;
      hideShowField(true);
    }
  });

  $("#commentTextarea").change(function () {
    setDoorField("comment", $(this).val());
    displayObject(door);
  });

  $(".ios-toggle").on("click", function () {
    var isRepRun = true;

    if (currentItem == "metal") {
      oneEnableAllDisable("metal", this);
      setDoorField($(this).attr("Item"), $(this).attr("data"));
    } else if (currentItem == "heightDoor") {
      if ($(this).is(":checked")) {
        fanlight = 1;
        $('[fanlight="1"]').attr("show", "is_alive_lement");
        $('[doorLeaf="' + doorLeaf + '"][fanlight="1"]').attr(
          "show",
          "ghost_lement"
        );
      } else {
        fanlight = 0;
        setDoorField("doorFanlightHeight", 0);
        $('[fanlight="1"]').attr("show", "ghost_lement");
      }
    } else if (currentItem == "deepnessDoor") {
      if ($(this).is(":checked")) {
        oneEnableAllDisable("deepnessDoor", this);
        setDoorField($(this).attr("Item"), $(this).attr("data"));
      }
    } else if (currentItem == "thicknessDoorLeaf") {
      if ($(this).is(":checked")) {
        oneEnableAllDisable("thicknessDoorLeaf", this);
        setDoorField($(this).attr("Item"), $(this).attr("data"));
      }
    } else if (currentItem == "sideDoorOpen") {
      if ($(this).is(":checked")) {
        oneEnableAllDisable($(this).attr("Item"), this);
        setDoorField($(this).attr("Item"), $(this).attr("data"));
      } else {
        setDoorField($(this).attr("Item"), "");
      }
    } else if (currentItem == "additionalDoorSettings") {
      if ($(this).attr("available") == "yes") {
        if ($(this).attr("item") == "doorstep") {
          if (!$(this).is(":checked")) {
            $("#stainlessSteelDoorstep_checkbox").prop("checked", false);
            setDoorField($(this).attr("Item"), 0);
            setDoorField($("#stainlessSteelDoorstep_checkbox").attr("Item"), 0);
          } else {
            setDoorField($(this).attr("Item"), $(this).attr("data"));
          }
        } else if ($(this).attr("item") == "stainlessSteelDoorstep") {
          if ($(this).is(":checked") & !$("#doorstepcheckbox").is(":checked")) {
            $("#doorstepcheckbox").prop("checked", true);
            setDoorField($(this).attr("Item"), $(this).attr("data"));
            setDoorField(
              $("#doorstepcheckbox").attr("Item"),
              $(this).attr("data")
            );
          } else {
            setDoorField($(this).attr("Item"), 0);
          }
        } else if ($(this).attr("item") == "doorTrim") {
          if ($(this).is(":checked")) {
            $('[Item="topDoorTrim"]').prop("checked", true);
            $('[Item="leftDoorTrim"]').prop("checked", true);
            $('[Item="rightDoorTrim"]').prop("checked", true);
          } else {
            $('[Item="topDoorTrim"]').prop("checked", false);
            $('[Item="leftDoorTrim"]').prop("checked", false);
            $('[Item="rightDoorTrim"]').prop("checked", false);
          }
        } else if ($(this).attr("item") == "topDoorTrim") {
          if ($(this).is(":checked")) {
            setDoorField($(this).attr("Item"), 1);
            $('[Item="doorTrim"]').prop("checked", true);
          } else {
            setDoorField($(this).attr("Item"), 0);
            maybeTurnItOffDoorTrim();
          }
        } else if ($(this).attr("item") == "leftDoorTrim") {
          if ($(this).is(":checked")) {
            setDoorField($(this).attr("Item"), 1);
            $('[Item="doorTrim"]').prop("checked", true);
          } else {
            setDoorField($(this).attr("Item"), 0);
            maybeTurnItOffDoorTrim();
          }
        } else if ($(this).attr("item") == "rightDoorTrim") {
          if ($(this).is(":checked")) {
            $('[Item="doorTrim"]').prop("checked", true);
            setDoorField($(this).attr("Item"), 1);
          } else {
            setDoorField($(this).attr("Item"), 0);
            maybeTurnItOffDoorTrim();
          }
        } else if ($(this).attr("item") == "firstSealingLine") {
          oneEnableAllDisable("firstSealingLine", this);
          setDoorField($(this).attr("Item"), $(this).attr("data"));
        } else if ($(this).attr("item") == "secondSealingLine") {
          oneEnableAllDisable("secondSealingLine", this);
          setDoorField($(this).attr("Item"), $(this).attr("data"));
        } else if ($(this).attr("item") == "thirdSealingLine") {
          if ($(this).is(":checked")) {
            oneEnableAllDisable("thirdSealingLine", this);
            setDoorField($(this).attr("Item"), $(this).attr("data"));
            setDoorField("sealingLine", 3);
          } else {
            setDoorField($(this).attr("Item"), 0);
            setDoorField("sealingLine", 2);
          }
        } else if ($(this).attr("item") == "filler") {
          oneEnableAllDisable("filler", this);
        }
      } else {
        if ($(this).is(":checked")) {
          $(this).prop("checked", false);
        } else {
          $(this).prop("checked", true);
        }
      }
    } else if (currentItem == "doorGlass") {
      if ($(this).is(":checked")) {
        $(".input_doorGlass_div").attr("show", "is_alive_lement");
        setDoorField("isDoorGlass", 1);
        isRepRun = false;
      } else {
        $(".input_doorGlass_div").attr("show", "ghost_lement");
        setDoorField("isDoorGlass", 0);
      }
    } else if (currentItem == "additionally") {
      if ($(this).attr("available") == "yes") {
        if ($(this).attr("item") == "nightLock") {
          if ($(this).is(":checked")) {
            setDoorFurniture($(this).attr("Item"), $(this).attr("data"));
          } else {
            setDoorFurniture($(this).attr("Item"), 0);
          }
        } else if ($(this).attr("item") == "peephole") {
          if ($(this).is(":checked")) {
            setDoorFurniture($(this).attr("Item"), $(this).attr("data"));
          } else {
            setDoorFurniture($(this).attr("Item"), 0);
          }
        } else if ($(this).attr("item") == "amplifierCloser") {
          if ($(this).is(":checked")) {
            setDoorFurniture($(this).attr("Item"), $(this).attr("data"));
          } else {
            setDoorFurniture($(this).attr("Item"), 0);
          }
        }
      } else {
        if ($(this).is(":checked")) {
          $(this).prop("checked", false);
        } else {
          $(this).prop("checked", true);
        }
      }
    }

    if (isRepRun) {
      representationField($(this).attr("data"));
    }
  });

  $("#buttonCalculate").on("click", function () {
    if (checkThCompletedFields()) {
      var strJSON = JSON.stringify(door);

      $.ajax({
        type: "POST",
        url: "data",
        contentType: "application/json",
        data: strJSON,
        dataType: "json",
        success: function (data) {
          //alert('price is: ' + data.price);
          door = data;
          displayPrice();
        },
        error: function (data) {
          alert("error:" + data);
        },
      });
    }
    audio.play();
  });

  $("#buttonSaveDoor").on("click", function () {
    var strJSON = JSON.stringify(door);

    $.ajax({
      type: "POST",
      url: "saveDoor",
      contentType: "application/json",
      data: strJSON,
      dataType: "json",
      success: function (data) {
        toOrder();
      },
      error: function (data) {
        alert("error:" + data);
      },
    });
  });

  $("#toСlose").on("click", function () {
    toOrder();
  });

  $(".to_calculate").hover(
    function () {
      $(".priceghost").attr("show", "is_alive_lement");
    },
    function () {
      $(".priceghost").attr("show", "ghost_lement");
    }
  );

  //select namber

  $("#select_set").on("click", function () {
    setSize();
  });

  $("#select_cancel").on("click", function () {
    closeSelect();
  });

  $(".select_size").on("click", function () {
    sizeMin = sizeLimMin($(this).attr("id"));
    sizeMax = sizeLimMax($(this).attr("id"));

    select_introduction(
      $(this).attr("data"),
      $(this).attr("name"),
      $(this).attr("id"),
      sizeMin,
      sizeMax
    );

    selectSizeOpen = true;
    firstPress = true;
  });

  $("#addL").on("click", function () {
    add("L", 5, null, sizeMin, sizeMax);
  });

  $("#reduceL").on("click", function () {
    reduce("L", 5, sizeMin, sizeMax);
  });

  $("#addR").on("click", function () {
    add("R", 5, null, sizeMin, sizeMax);
  });

  $("#reduceR").on("click", function () {
    reduce("R", 5, sizeMin, sizeMax);
  });

  $("#backHis").on("click", function () {
    backHistoryList();
  });
  $("#nextHis").on("click", function () {
    nextHistoryList();
  });

  $(document).keyup(function (e) {
    if (!selectSizeOpen) {
      return;
    }
    handleKeystroke(e);
    checkForLimits();
  });

  //--------------------------------------
  //setter
  //--------------------------------------

  function prepareTheNumber(number1, number2) {
    if (number2.length == 1) {
      number2 = "0" + number2;
    }
    return "" + number1 + number2;
  }

  function setDoorField(fieldName, value) {
    if (checkInstallationAvailability(fieldName, value)) {
      door[fieldName] = value;
      failureToSetValue = false;
    } else {
      failureToSetValue = true;
    }
  }

  function setDoorGlassImg(fieldName, value) {
    var furn = findObject(fieldName, value);
    door.doorGlass[fieldName] = furn;
  }

  function setDoorGlassField(fieldName, value) {
    door.doorGlass[fieldName] = value;
  }

  function setDoorFurniture(fieldName, value, doorKit) {
    var furn = findObject(fieldName, value);
    doorKit[fieldName] = furn;
  }

  function findObject(name, value) {
    if (name == "lowerLockCylinder" || name == "topLockCylinder") {
      name = "lockCylinder";
    }

    var sizeRest = availableFurnitureList[name].length;
    var tab = availableFurnitureList[name];
    for (var i = 0; i < sizeRest; ++i) {
      if (tab[i].id == value) {
        return tab[i];
      }
    }
    return null;
  }

  function checkInstallationAvailability(fieldName, value) {
    return true;
  }

  function representationField(value) {
    if (!failureToSetValue) {
      var showValue = "";

      //widthDoor
      if (currentItem == "widthDoor") {
        if (door.activeDoorLeafWidth != 0) {
          showValue =
            "" + door.widthDoor + " [" + door.activeDoorLeafWidth + "]";
        } else if (door.widthDoor && door.widthDoor != 0) {
          showValue = "" + door.widthDoor;
        }
      }
      //heightDoor
      else if (currentItem == "heightDoor") {
        if (door.doorFanlightHeight != 0) {
          showValue =
            "" + door.heightDoor + " [" + door.doorFanlightHeight + "]";
        } else if (door.heightDoor && door.heightDoor != 0) {
          showValue = "" + door.heightDoor;
        }
      }
      //sideDoorOpen
      else if (currentItem == "sideDoorOpen") {
        if (door.innerDoorOpen && door.innerDoorOpen != 0) {
          showValue = "" + door.sideDoorOpen + " [внутренняя]";
        } else if (door.sideDoorOpen && door.sideDoorOpen != 0) {
          showValue = "" + door.sideDoorOpen;
        }
      }
      //doorClass
      else if (currentItem == "doorType") {
        showValue = getTheNameOfTheDoorType(value);
        currentItem = "doorClass";
      }
      //doorGlass
      else if (currentItem == "doorGlass") {
        showValue = getNameGlass(value);
      }
      //furnitureKit
      else if (currentItem == "furnitureKit") {
        currentItem = "topLockkit";
        set(getFurniture(value, "topLock"));

        currentItem = "lowerLockkit";
        set(getFurniture(value, "lowerLock"));

        currentItem = "handle";
        showValue = getFurniture(value, "handle");
      }
      //shieldKit
      else if (currentItem == "shieldKit") {
        if (door.shieldKit != null) {
          if (door.shieldKit.shieldColor != null) {
            showValue = door.shieldKit.shieldColor.name;
          }
          if (door.shieldKit.shieldColor != null) {
            showValue = showValue + " / " + door.shieldKit.shieldDesign.name;
          }
        }
      } else if (currentItem == "comment") {
        if (value != null && value.length > 20) {
          showValue = value.slice(0, 20) + "..";
        }
      }
      //doorstep//DoorTrim
      else if (
        currentItem == "doorstep" ||
        currentItem == "stainlessSteelDoorstep" ||
        currentItem == "topDoorTrim" ||
        currentItem == "leftDoorTrim" ||
        currentItem == "rightDoorTrim"
      ) {
        fillCheckbox(currentItem, value);
      } else if (
        currentItem == "firstSealingLine" ||
        currentItem == "secondSealingLine" ||
        currentItem == "thirdSealingLine"
      ) {
        fillCheckbox(currentItem + "_" + value, 1);
      } else {
        if (value != 0 || value != "0") {
          showValue = value;
        }
      }

      set(showValue);
      drawObject(door, 1);
    }
  }

  function pickOut(item) {
    var attr = $(item).attr("class");

    var elems = $("." + attr);
    var elemsTotal = elems.length;
    for (var i = 0; i < elemsTotal; ++i) {
      $(elems[i]).attr("check", "no");
    }

    $(item).attr("check", "checkbox");
  }

  function set(Value) {
    $(".vertical_menu_button_rigtht#" + currentItem + "Show strong").html(
      Value
    );
  }

  function oneEnableAllDisable(Item, thisItem) {
    var elems = $('.ios-toggle[Item="' + Item + '"]');
    var elemsTotal = elems.length;

    for (var i = 0; i < elemsTotal; ++i) {
      if ($(thisItem).attr("id") == $(elems[i]).attr("id")) {
      } else {
        $(elems[i]).prop("checked", false);
      }
    }
  }

  function allDisable(className) {
    //all is ghost_lement
    var elems = $("." + className + "");
    for (var i = 0; i < elems.length; ++i) {
      $(elems[i]).attr("show", "ghost_lement");
    }
  }

  function addNavigation() {
    $(".navigation_panel").remove();

    $("<a>")
      .attr("class", "navigation_panel")
      .attr("href", "#")
      .attr("id", currentItemForDisplayId)
      .html(currentItemForDisplay)
      .appendTo(".navigation_panel_div");
    $("<span>")
      .attr("class", "navigation_panel")
      .html("->")
      .appendTo(".navigation_panel_div");

    if (currentItemDaughterForDisplay != "") {
      $("<a>")
        .attr("class", "navigation_panel")
        .attr("href", "#")
        .html(currentItemDaughterForDisplay)
        .appendTo(".navigation_panel_div");
    }
  }

  //--------------------------------------
  //periodic installation
  //--------------------------------------

  function displayObject(elem) {
    for (var key in elem) {
      currentItem = key;
      representationField(elem[key]);
    }
  }

  function toOrder() {
    location.href = "order?orderId=" + orderId;
  }

  function displayPrice() {
    $("#price").text("Цена: " + door.priceWithMarkup);

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

  function displayDoorClass3(classListParam) {
    $(".class_card").remove();

    for (var i = 0; i < classListParam.length; ++i) {
      var divName = classListParam[i].name;
      var divId = classListParam[i].id;

      $("<div>")
        .attr("class", "card text-dark border-dark class_card")
        .attr("data", divId)
        .attr("id", "doorClass" + divId)
        .appendTo(".select_door_class");

      $("<div>")
        .attr("class", "images_door_class_div")
        .attr("id", "doorClassDiv" + divId)
        .appendTo("#doorClass" + divId);

      $("<img>")
        .attr("class", "images_door_class")
        .attr("dataName", divName)
        .attr("src", classListParam[i].namePicture)
        .attr("Item", "doorClass")
        .appendTo("#doorClassDiv" + divId);

      $("<div>")
        .attr("class", "images_door_class_p")
        .attr("id", "doorClassDivP" + divId)
        .appendTo("#doorClassDiv" + divId);

      $("<p>")
        .text(divName)
        .appendTo("#doorClassDivP" + divId);

      $("<div>")
        .attr("class", "card-body")
        .attr("id", "card-body" + divId)
        .appendTo("#doorClass" + divId);

      $("<p>")
        .attr("class", "card-text")
        .text(classListParam[i].description)
        .appendTo("#card-body" + divId);
    }
  }

  function displayMetal(data) {
    allDisable("metal_checkbox");

    for (var i = 0; i < data.metal.length; ++i) {
      $("#metal" + i).attr("show", "is_alive_lement");
      $("#nememetal" + i).text(data.metal[i].firstItem);
      $("#checkboxmetal" + i).attr("data", data.metal[i].firstItem);
      if (data.metal[i].defaultValue == 1) {
        $("#checkboxmetal" + i).prop("checked", true);
        setDoorField(
          $("#checkboxmetal" + i).attr("Item"),
          $("#checkboxmetal" + i).attr("data")
        );
        currentItem = "metal";
        representationField($("#checkboxmetal" + i).attr("data"));
      }
    }
    $("#namemetal").attr("available", "yes");
  }


  function displayWidthDoorAndHeightDoor(data) {
    if (data.widthDoor.length > 0) {
      $("#namewidthDoor").attr("available", "yes");
    }

    if (data.heightDoor.length > 0) {
      $("#nameheightDoor").attr("available", "yes");
    }
  }

  function displayheightDoorFanlight(data) {
    if (data.heightDoorFanlight.length > 0) {
      if (
        data.heightDoorFanlight.tartRestriction == 0 &&
        data.heightDoorFanlight.stopRestriction == 0
      ) {
        $("#fanlightCheckboxDiv").addClass("ghost");
      }
    } else {
      $("#fanlightCheckboxDiv").addClass("ghost");
    }
  }

  function displayDeepnessDoorAndThicknessDoorLeaf(data) {
    allDisable("deepnessDoor_checkbox");
    writeInCheckbox("deepnessDoor", data.deepnessDoor);
    if (data.deepnessDoor.length > 1) {
      $("#namedeepnessDoor").attr("available", "yes");
    }

    allDisable("thicknessDoorLeaf_checkbox");
    writeInCheckbox("thicknessDoorLeaf", data.thicknessDoorLeaf);
    if (data.thicknessDoorLeaf.length > 1) {
      $("#namethicknessDoorLeaf").attr("available", "yes");
    }
  }

  function writeInCheckbox(nameItem, tab) {
    for (var i = 0; i < tab.length; ++i) {
      $("#" + nameItem + i).attr("show", "is_alive_lement");
      $("#neme" + nameItem + i).text(tab[i].startRestriction);
      $("#checkbox" + nameItem + i).attr("data", tab[i].startRestriction);
      if (tab[i].defaultValue == 1) {
        $("#checkbox" + nameItem + i).prop("checked", true);
        setDoorField(nameItem, tab[i].startRestriction);
        currentItem = nameItem;
        representationField(tab[i].startRestriction);
      }
    }
  }

  function processItemSelection(item) {
    currentItem = $(item).attr("id");
    currentItemForDisplay = $(item).html();
    currentItemDaughterForDisplay = "";
    currentItemForDisplayId = currentItem;
    failureToSetValue = false;

    hideShowField(true);
    addNavigation();
  }

  function assignPreviouValue() {
    if (currentItem == "widthDoor") {
      setDoorField("widthDoor", $("#inputWidthDoor").attr("data"));
      setDoorField(
        "activDoorLeafWidth",
        $("#inputActivDoorLeafWidth").attr("data")
      );
    } else if (currentItem == "heightDoor") {
      setDoorField("heightDoor", $("#inputHeightDoor").attr("data"));
      if (fanlight == 1) {
        setDoorField(
          "doorFanlightHeight",
          $("#inputHeightFanlight").attr("data")
        );
      }
    } else if (currentItem == "doorGlass") {
      setDoorGlassField("glassWidth", $("#inputWidthDoorGlass").attr("data"));
      setDoorGlassField("glassHeight", $("#inputHeightDoorGlass").attr("data"));
      setDoorGlassField(
        "leftGlassPosition",
        $("#inputleftDoorGlass").attr("data")
      );
      setDoorGlassField(
        "bottomGlassPosition",
        $("#inputbottomDoorGlass").attr("data")
      );
    }

    representationField();
  }

  function getTheNameOfTheDoorType(value) {
    if (value instanceof Object) {
      return value.name;
    } else {
      for (var i = 0; i < door.availableDoorClass.length; ++i) {
        var type = door.availableDoorClass[i].doorTypes;
        for (var j = 0; j < type.length; ++j) {
          if (type[j].id === value) {
            return door.availableDoorClass[i].name;
          }
        }
      }
    }
    return "";
  }

  function displayColor(nameJava, tab, bias) {

    var offsetTab = generatePageToolbar(tab,bias);

    for (var i = 0; i < offsetTab.amountElements; ++i) {
      if (i + offsetTab.biasInt < offsetTab.tabSize) {
        $("#images" + nameJava + "Div" + i).attr("show", "is_alive_lement");
        $("#images" + nameJava + "Div" + i).attr(
          "data",
          tab[i + offsetTab.biasInt].firstItem
        );
        $("#images" + nameJava + "Img" + i).attr(
          "src",
          tab[i + offsetTab.biasInt].picturePath
        );
        $("#images" + nameJava + "Span" + i).text(tab[i + offsetTab.biasInt].firstItem);
      } else {
        $("#images" + nameJava + "Div" + i).attr("show", "ghost_lement");
        $("#images" + nameJava + "Div" + i).attr("data", "");
        $("#images" + nameJava + "Img" + i).attr("src", "");
        $("#images" + nameJava + "Span" + i).text("");
      }
    }
  }

  function generatePageToolbar(tab,bias) {

    var offsetTab = {};
    offsetTab.tabSize = tab.length;
    offsetTab.amountElements = 15;
    offsetTab.amountPag = (offsetTab.tabSize / offsetTab.amountElements).toFixed(0);
    offsetTab.biasInt = Number.parseInt(bias) * offsetTab.amountElements;

    if (offsetTab.amountPag>0){
      for (var i = 0; i < offsetTab.amountPag; ++i) {
        $("<button>")
            .attr("type", "button")
            .attr("class", 'btn btn-outline-dark')
            .text(i+1)
            .appendTo("#toolbarPage");
      }
    }
    return offsetTab;
  }
  function displayImage(nameJava, tab, bias) {
    var tabSize = tab.length;
    var amountElements = 15;
    var amountPag = (tabSize / amountElements).toFixed(0);
    var biasInt = Number.parseInt(bias) * amountElements;

    //delete
    $(".pag").remove();

    for (var i = 0; i < amountPag; ++i) {
      $("<a>")
        .attr("class", "pag")
        .attr("data", i)
        .text("" + i + " ")
        .appendTo(".color_pages");
    }
    $("<a>")
      .attr("class", "pag")
      .attr("data", ">")
      .text(" > ")
      .appendTo(".color_pages");

    $(".color_pages").attr("data", bias);

    for (var i = 0; i < amountElements; ++i) {
      if (i + biasInt < tabSize) {
        $("#images" + nameJava + "Div" + i).attr("show", "is_alive_lement");
        $("#images" + nameJava + "Div" + i).attr("data", tab[i + biasInt].id);
        $("#images" + nameJava + "Img" + i).attr(
          "src",
          tab[i + biasInt].picturePath
        );
        $("#images" + nameJava + "Span" + i).text(tab[i + biasInt].name);
      } else {
        $("#images" + nameJava + "Div" + i).attr("show", "ghost_lement");
        $("#images" + nameJava + "Div" + i).attr("data", "");
        $("#images" + nameJava + "Img" + i).attr("src", "");
        $("#images" + nameJava + "Span" + i).text("");
      }
    }
  }

  function displayadditionalDoorSettings(data) {
    displayElement("doorstep", data);
    displayElement("stainlessSteelDoorstep", data);

    displayElement("topDoorTrim", data);
    displayElement("leftDoorTrim", data);
    displayElement("rightDoorTrim", data);

    displayElementSealingLine("firstSealingLine", data);
    displayElementSealingLine("secondSealingLine", data);
    displayElementSealingLine("thirdSealingLine", data);
  }

  function displayElement(name, data) {
    var tabSize = data[name].length;
    if (tabSize > 1) {
      $("#name" + name).attr("available", "yes");
      $("#" + name + "_checkbox").attr("available", "yes");
    }
  }

  function displayElementSealingLine(name, data) {
    var tab = data[name];
    var tabSize = tab.length;

    if (tabSize > 1) {
      for (var i = 0; i < tabSize; i++) {
        var lineName = name + "_" + tab[i].startRestriction;

        $("#name" + lineName).attr("available", "yes");
        $("#" + lineName + "_checkbox").attr("available", "yes");
      }
    }
  }

  function displayListOfItems(nameTab, tab, bias, postfixName) {
    if (tab != null) {
      var tabSize = tab.length;
      var amountElements = 4;
      var amountPag = (tabSize / amountElements).toFixed(0);
      var biasInt = Number.parseInt(bias) * amountElements;

      if (tabSize > 0) {
        $("#name" + nameTab + postfixName).attr("available", "yes");
      } else {
        $("#name" + nameTab + postfixName).attr("available", "no");
      }

      //delete
      $("." + nameTab + "pag").remove();

      for (var i = 0; i < amountPag; ++i) {
        $("<a>")
          .attr("class", "pag")
          .attr("data", i)
          .text("" + i + " ")
          .appendTo(".color_pages");
      }
      $("<a>")
        .attr("class", nameTab + "pag")
        .attr("data", ">")
        .text(" > ")
        .appendTo("." + nameTab + "_pages");

      $(".color_pages").attr("data", bias);

      for (var i = 0; i < amountElements; ++i) {
        var sel = "#" + nameTab;
        if (i + biasInt < tabSize) {
          $(sel + "Div" + i).attr("show", "is_alive_lement");
          $(sel + "Div" + i).attr("data", tab[i + biasInt].id);
          $(sel + "Img" + i).attr("src", tab[i + biasInt].picturePathFirst);
          $(sel + "Span" + i).text(tab[i + biasInt].name);
        } else {
          $(sel + "Div" + i).attr("show", "ghost_lement");
          $(sel + "Div" + i).attr("data", "");
          $(sel + "Img" + i).attr("src", "");
          $(sel + "Span" + i).text("");
        }
      }
    }
  }

  function getNameGlass(value) {
    if (value !== null) {
      if (
        typeof value == "object" &&
        value.glassWidth > 0 &&
        value.glassHeight > 0
      ) {
        return "" + value.glassWidth + " X " + value.glassHeight;
      }
    }
    return "";
  }

  function displayGlass() {
    if (door.isDoorGlass == 1) {
      $("#checkboxdoorGlass0").prop("checked", true);
      $(".input_doorGlass_div").attr("show", "is_alive_lement");

      if (door.doorGlass.typeDoorGlass !== null) {
        currentItem = "typeDoorGlass";
        representationField(door.doorGlass.typeDoorGlass.name);
      }
      if (door.doorGlass.toning !== null) {
        currentItem = "toning";
        representationField(door.doorGlass.toning.name);
      }
      if (door.doorGlass.armor !== null) {
        currentItem = "armor";
        representationField(door.doorGlass.armor.name);
      }
      currentItem = "doorGlass";
      representationField(door.doorGlass);

      $("#inputWidthDoorGlass").attr("value", door.doorGlass.glassWidth);
      $("#inputHeightDoorGlass").attr("value", door.doorGlass.glassHeight);
      $("#inputleftDoorGlass").attr("value", door.doorGlass.leftGlassPosition);
      $("#inputbottomDoorGlass").attr(
        "value",
        door.doorGlass.bottomGlassPosition
      );
    }
  }

  function displaySideDoorOpen() {
    $("[data = " + door.sideDoorOpen + "]").prop("checked", true);
  }

  function getFurniture(value, name) {
    if (value != null && value[name] !== null) {
      return value[name].name;
    }
    return "";
  }

  function checkThCompletedFields() {
    if (door.widthDoor == 0 && door.heightDoor == 0) {
      alert("please select door dimensions!");
      return false;
    }
    if (door.sideDoorOpen == null) {
      alert("please select side door open!");
      return false;
    }
    if (door.doorColor == null) {
      alert("please select door color!");
      return false;
    }
    if (
      doorLeaf == 2 &&
      (door.activeDoorLeafWidth == null || door.activeDoorLeafWidth == 0)
    ) {
      alert("please select active door leaf width!");
      return false;
    }
    return true;
  }

  function maybeTurnItOffDoorTrim() {
    if (
      !$("#additionalDoorSettings_topDoorTrim").is(":checked") &&
      !$("#additionalDoorSettings_leftDoorTrim").is(":checked") &&
      !$("#additionalDoorSettings_rightDoorTrim").is(":checked")
    )
      $('[Item="doorTrim"]').prop("checked", false);
  }

  function sizeLimMin(elemId) {
    if (elemId == "inputWidthDoor") {
      var tab = RestrictionOfSelectionFields["widthDoor"];
      for (var i = 0; i < tab.length; i++) {
        if (tab[i].pairOfValues == 1) {
          return tab[i].startRestriction;
        }
      }
      return 0;
    } else if (elemId == "inputHeightDoor") {
      var tab = RestrictionOfSelectionFields["heightDoor"];
      for (var i = 0; i < tab.length; i++) {
        if (tab[i].pairOfValues == 1) {
          return tab[i].startRestriction;
        }
      }
      return 0;
    } else if (elemId == "inputActivDoorLeafWidth") {
      var tab = RestrictionOfSelectionFields["widthDoorLeaf"];
      for (var i = 0; i < tab.length; i++) {
        if (tab[i].pairOfValues == 1) {
          return tab[i].startRestriction;
        }
      }
    } else if (elemId == "inputHeightFanlight") {
      var tab = RestrictionOfSelectionFields["heightDoorFanlight"];
      for (var i = 0; i < tab.length; i++) {
        if (tab[i].pairOfValues == 1) {
          return tab[i].startRestriction;
        }
      }
    }
    return 0;
  }

  function sizeLimMax(elemId) {
    if (elemId == "inputWidthDoor") {
      var tab = RestrictionOfSelectionFields["widthDoor"];
      for (var i = 0; i < tab.length; i++) {
        if (tab[i].pairOfValues == 1) {
          return tab[i].stopRestriction;
        }
      }
      return 5000;
    } else if (elemId == "inputHeightDoor") {
      var tab = RestrictionOfSelectionFields["heightDoor"];
      for (var i = 0; i < tab.length; i++) {
        if (tab[i].pairOfValues == 1) {
          return tab[i].stopRestriction;
        }
      }
      return 5000;
    } else if (elemId == "inputActivDoorLeafWidth") {
      var tab = RestrictionOfSelectionFields["widthDoorLeaf"];
      for (var i = 0; i < tab.length; i++) {
        if (tab[i].pairOfValues == 1) {
          return tab[i].stopRestriction;
        }
      }
      return 800;
    } else if (elemId == "inputHeightFanlight") {
      var tab = RestrictionOfSelectionFields["heightDoorFanlight"];
      for (var i = 0; i < tab.length; i++) {
        if (tab[i].pairOfValues == 1) {
          return tab[i].stopRestriction;
        }
      }
      return 800;
    }
    return 0;
  }

  function getNewDoorInstans(updateClassDiv) {
    $.ajax({
      url: "door",
      data: { id: id, orderId: orderId, typid: typeId },
      dataType: "json",
      success: function (data) {
        door = data;
        id = door.id;
        FillOutForm(door, updateClassDiv);
        getFurnitureAvailableFields();
      },
      error: function (data) {
        alert("error: getting the door failed !");
      },
    });
  }

  function getFurnitureAvailableFields() {
    $.ajax({
      url: "furniture/available-fields/" + typeId,
      dataType: "json",
      success: function (data) {
        availableFurnitureList = data;
        fillInTheFieldsToTheTemplate(door.template);
      },
      error: function (data) {
        alert("error: getting furniture available-fields failed !" + data);
      },
    });
  }

  function getClassList() {
    $.ajax({
      url: "class/list",
      dataType: "json",
      success: function (data) {
        classList = data;
        displayDoorClass3(classList);
        currentItem = "doorClass";
        hideShowField(true);
      },
      error: function (data) {
        alert("error:" + data);
      },
    });
  }

  function fillInTheFieldsToTheTemplate(data) {
    if (data != null) {
      displayMetal(data);
      displayWidthDoorAndHeightDoor(data);
      displayheightDoorFanlight(data);
      displayDeepnessDoorAndThicknessDoorLeaf(data);

      colors = data.colors;
      displayColor("doorColor", data.colors, 0);
      displayImage("shieldColor", availableFurnitureList.shieldColor, 0);
      displayImage("shieldDesign", availableFurnitureList.shieldDesign, 0);

      displayadditionalDoorSettings(data);
      displayListOfItems("topLock", availableFurnitureList.topLock, 0, "kit");
      displayListOfItems(
        "lowerLock",
        availableFurnitureList.lowerLock,
        0,
        "kit"
      );

      displayListOfItems("handle", availableFurnitureList.handle, 0, "");

      displayListOfItems(
        "lowerLockCylinder",
        availableFurnitureList.lockCylinder,
        0,
        ""
      );
      displayListOfItems(
        "topLockCylinder",
        availableFurnitureList.lockCylinder,
        0,
        ""
      );

      displayListOfItems(
        "topInLockDecor",
        availableFurnitureList.topInLockDecor,
        0,
        ""
      );
      displayListOfItems(
        "topOutLockDecor",
        availableFurnitureList.topOutLockDecor,
        0,
        ""
      );
      displayListOfItems(
        "lowerInLockDecor",
        availableFurnitureList.lowerInLockDecor,
        0,
        ""
      );
      displayListOfItems(
        "lowerOutLockDecor",
        availableFurnitureList.lowerOutLockDecor,
        0,
        ""
      );

      displayListOfItems("closer", availableFurnitureList.closer, 0, "");
      displayListOfItems(
        "endDoorLock",
        availableFurnitureList.endDoorLock,
        0,
        ""
      );
      displayListOfItems(
        "typeDoorGlass",
        availableFurnitureList.typeDoorGlass,
        0,
        ""
      );
      displayListOfItems("toning", availableFurnitureList.toning, 0, "");
      displayListOfItems("armor", availableFurnitureList.armor, 0, "");

      RestrictionOfSelectionFields = data;
    }
  }

  function fillCheckbox(name, value) {
    if (value == 1) {
      $("#" + name + "_checkbox").prop("checked", true);
    } else {
      $("#" + name + "_checkbox").prop("checked", false);
    }
  }

  function fillInType(idDoorClass, classListParam) {
    $(".typeLine").remove();

    for (var i = 0; i < classListParam.length; ++i) {
      if (classListParam[i].id == idDoorClass) {
        var doorTypes = classListParam[i].doorTypes;
        for (var j = 0; j < doorTypes.length; ++j) {
          $("<li>")
            .attr("class", "typeLine list-group-item")
            .attr("id", "doorType" + doorTypes[j].id)
            .attr("data", doorTypes[j].id)
            .text(doorTypes[j].name)
            .attr("Item", "doorType")
            .attr("data-LeafDoorLeaf", doorTypes[j].doorLeaf)
            .appendTo(".select_door_type_list");

          $("<div>")
            .attr("class", "typeLineDaughter ghost")
            .attr("id", "doorTypeDaughter" + doorTypes[j].id)
            .appendTo("#doorType" + doorTypes[j].id);

          $("<img>")
            .attr("class", "images_door_class")
            .attr("src", doorTypes[j].namePicture)
            .appendTo("#doorTypeDaughter" + doorTypes[j].id);

          $("#doorType" + doorTypes[j].id).on("hover", function () {
            var id = $(this).attr("data");
            alert(id);
            $("#doorType" + doorTypes[j].id).removeClass("ghost");
          });
        }
      }
    }
  }

  function hideShowField(addHistory) {
    goTo = "";

    if (addHistory) {
      addToTheHistoryList(currentItem);
    }

    if (currentItem == "doorClass") {
      $(".select_door_class").attr("show", "is_alive_lement");
    } else {
      $(".select_door_class").attr("show", "ghost_lement");
    }

    if (currentItem == "doorType") {
      $(".select_door_type").attr("show", "is_alive_lement");
    } else {
      $(".select_door_type").attr("show", "ghost_lement");
    }

    if (currentItem == "metal") {
      $(".select_metal").attr("show", "is_alive_lement");
    } else {
      $(".select_metal").attr("show", "ghost_lement");
    }

    if (currentItem == "widthDoor") {
      $(".select_widthDoor").attr("show", "is_alive_lement");
      if (door != null && door.widthDoor != null && door.widthDoor != 0) {
        $("#inputWidthDoor").attr("value", door.widthDoor);
      }
      if (doorLeaf == 1) {
        $('[DoorLeaf="1"]').attr("show", "is_alive_lement");
        $('[DoorLeaf="2"]').attr("show", "ghost_lement");
      } else {
        $('[DoorLeaf="1"]').attr("show", "ghost_lement");
        $('[DoorLeaf="2"]').attr("show", "is_alive_lement");
      }
    } else {
      $(".select_widthDoor").attr("show", "ghost_lement");
    }

    if (currentItem == "heightDoor") {
      $(".select_heightDoor").attr("show", "is_alive_lement");
      if (door != null && door.heightDoor != null && door.heightDoor != 0) {
        $("#inputHeightDoor").attr("value", door.heightDoor);
      }

      if (doorLeaf == 1) {
        $('[DoorLeaf="1"]').attr("show", "is_alive_lement");
        $('[DoorLeaf="2"]').attr("show", "ghost_lement");
      } else {
        $('[DoorLeaf="1"]').attr("show", "ghost_lement");
        $('[DoorLeaf="2"]').attr("show", "is_alive_lement");
      }

      if (fanlight == 1) {
        $('[fanlight="1"]').attr("show", "is_alive_lement");
        $('[doorLeaf="' + doorLeaf + '"][fanlight="1"]').attr(
          "show",
          "ghost_lement"
        );
      } else {
        $('[fanlight="1"]').attr("show", "ghost_lement");
      }
    } else {
      $(".select_heightDoor").attr("show", "ghost_lement");
    }

    if (currentItem == "deepnessDoor") {
      $(".select_deepnessDoor").attr("show", "is_alive_lement");
    } else {
      $(".select_deepnessDoor").attr("show", "ghost_lement");
    }

    if (currentItem == "thicknessDoorLeaf") {
      $(".select_thicknessDoorLeaf").attr("show", "is_alive_lement");
    } else {
      $(".select_thicknessDoorLeaf").attr("show", "ghost_lement");
    }

    if (currentItem == "sideDoorOpen") {
      $(".select_sideDoorOpen").attr("show", "is_alive_lement");
      displaySideDoorOpen();
    } else {
      $(".select_sideDoorOpen").attr("show", "ghost_lement");
    }

    if (currentItem == "additionalDoorSettings") {
      $(".select_additionalDoorSettings").attr("show", "is_alive_lement");
    } else {
      $(".select_additionalDoorSettings").attr("show", "ghost_lement");
    }

    if (currentItem == "doorColor") {
        setСurrentColor();
      $(".select_doorColor").attr("show", "is_alive_lement");
      $("#toolbarPageDiv").removeClass("ghost");
    } else {
      $(".select_doorColor").attr("show", "ghost_lement");
      $("#toolbarPageDiv").addClass("ghost");
    }

    if (currentItem == "doorGlass") {
      $(".select_doorGlass").attr("show", "is_alive_lement");
      displayGlass();
    } else {
      $(".select_doorGlass").attr("show", "ghost_lement");
    }

    if (currentItem == "typeDoorGlass") {
      $(".select_typeDoorGlass").attr("show", "is_alive_lement");

      currentItemForDisplay = $("#namedoorGlass").html();
      currentItemDaughterForDisplay = $(item).html();
      currentItemForDisplayId = "doorGlass";
    } else {
      $(".select_typeDoorGlass").attr("show", "ghost_lement");
    }

    if (currentItem == "toning") {
      $(".select_toning").attr("show", "is_alive_lement");

      currentItemForDisplay = $("#namedoorGlass").html();
      currentItemDaughterForDisplay = $(item).html();
      currentItemForDisplayId = "doorGlass";
    } else {
      $(".select_toning").attr("show", "ghost_lement");
    }

    if (currentItem == "armor") {
      $(".select_armor").attr("show", "is_alive_lement");

      currentItemForDisplay = $("#namedoorGlass").html();
      currentItemDaughterForDisplay = $(item).html();
      currentItemForDisplayId = "doorGlass";
    } else {
      $(".select_armor").attr("show", "ghost_lement");
    }


    if (currentItem == "topLockkit") {
      fillChildBlockFurniture("topLockkit", "topLock", "top");
      $(".select_topLockkit").attr("show", "is_alive_lement");
    } else {
      $(".select_topLockkit").attr("show", "ghost_lement");
    }

    if (currentItem == "topLock") {
      setСurrentItem("topLock");
      $(".select_topLock").attr("show", "is_alive_lement");
      goTo = "topLockkit";
      currentItemForDisplay = $("#nametopLockkit").html();
      //currentItemDaughterForDisplay = $(item).html();
      currentItemForDisplayId = "topLockkit";
    } else {
      $(".select_topLock").attr("show", "ghost_lement");
    }

    if (currentItem == "topInLockDecor") {
      $(".select_topInLockDecor").attr("show", "is_alive_lement");
      goTo = "topLockkit";
      currentItemForDisplay = $("#nametopLockkit").html();
      //currentItemDaughterForDisplay = $(item).html();
      currentItemForDisplayId = "topLockkit";
    } else {
      $(".select_topInLockDecor").attr("show", "ghost_lement");
    }

    if (currentItem == "topOutLockDecor") {
      $(".select_topOutLockDecor").attr("show", "is_alive_lement");
      goTo = "topLockkit";
      currentItemForDisplay = $("#nametopLockkit").html();
      //currentItemDaughterForDisplay = $(item).html();
      currentItemForDisplayId = "topLockkit";
    } else {
      $(".select_topOutLockDecor").attr("show", "ghost_lement");
    }


    if (currentItem == "lowerLockkit") {
      fillChildBlockFurniture("lowerLockkit", "lowerLock", "lower");
      $(".select_lowerLockkit").attr("show", "is_alive_lement");
    } else {
      $(".select_lowerLockkit").attr("show", "ghost_lement");
    }

    if (currentItem == "lowerLock") {
      setСurrentItem("lowerLock");
      $(".select_lowerLock").attr("show", "is_alive_lement");
      goTo = "lowerLockkit";
      currentItemForDisplay = $("#namelowerLockkit").html();
      //currentItemDaughterForDisplay = $(item).html();
      currentItemForDisplayId = "lowerLockkit";
    } else {
      $(".select_lowerLock").attr("show", "ghost_lement");
    }

    if (currentItem == "lowerInLockDecor") {
      $(".select_lowerInLockDecor").attr("show", "is_alive_lement");
      goTo = "lowerLockkit";
      currentItemForDisplay = $("#namelowerLockkit").html();
      currentItemForDisplayId = "lowerLockkit";
    } else {
      $(".select_lowerInLockDecor").attr("show", "ghost_lement");
    }

    if (currentItem == "lowerOutLockDecor") {
      $(".select_lowerOutLockDecor").attr("show", "is_alive_lement");
      goTo = "lowerLockkit";
      currentItemForDisplay = $("#namelowerLockkit").html();
      currentItemForDisplayId = "lowerLockkit";
    } else {
      $(".select_lowerOutLockDecor").attr("show", "ghost_lement");
    }


    if (currentItem == "lowerInLockDecor") {

            $('.select_lowerInLockDecor').attr('show', 'is_alive_lement');
            goTo = 'lowerLockkit';
            currentItemForDisplay = $('#namelowerLockkit').html();
            //currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'lowerLockkit';
        } else {
            $('.select_lowerInLockDecor').attr('show', 'ghost_lement');
        }

    if (currentItem == "lowerOutLockDecor") {

            $('.select_topOutLockDecor').attr('show', 'is_alive_lement');
            goTo = 'topLockkit';
            currentItemForDisplay = $('#nametopLockkit').html();
            //currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = 'topLockkit';
        } else {
            $('.select_topOutLockDecor').attr('show', 'ghost_lement');
        }if (currentItem == "handle") {
      setСurrentItem("handle");
      $(".select_handle").attr("show", "is_alive_lement");
    } else {
      $(".select_handle").attr("show", "ghost_lement");
    }

    if (currentItem == "additionally") {
      $(".select_additionally").attr("show", "is_alive_lement");
    } else {
      $(".select_additionally").attr("show", "ghost_lement");
    }

    if (currentItem == "closer") {
      $(".select_closer").attr("show", "is_alive_lement");
      currentItemForDisplay = $("#nameadditionally").html();
      currentItemDaughterForDisplay = $(item).html();
      currentItemForDisplayId = "additionally";
    } else {
      $(".select_closer").attr("show", "ghost_lement");
    }

    if (currentItem == "endDoorLock") {
      $(".select_endDoorLock").attr("show", "is_alive_lement");
      currentItemForDisplay = $("#nameadditionally").html();
      currentItemDaughterForDisplay = $(item).html();
      currentItemForDisplayId = "additionally";
    } else {
      $(".select_endDoorLock").attr("show", "ghost_lement");
    }


    if (currentItem == "topLockCylinder") {
      $(".select_topLockCylinder").attr("show", "is_alive_lement");
      goTo = "topLockkit";
      currentItemForDisplay = $("#nametopLockkit").html();
    } else {
      $(".select_topLockCylinder").attr("show", "ghost_lement");
    }

    if (currentItem == "lowerLockCylinder") {
      $(".select_lowerLockCylinder").attr("show", "is_alive_lement");
      goTo = "lowerLockkit";
      currentItemForDisplay = $("#namelowerLockkit").html();
    } else {
      $(".select_lowerLockCylinder").attr("show", "ghost_lement");
    }

    //shield

    if (currentItem == "shieldKit") {
      fillChildBlockShield("shieldColor");
      fillChildBlockShield("shieldDesign");
      $(".select_shieldKit").attr("show", "is_alive_lement");
    } else {
      $(".select_shieldKit").attr("show", "ghost_lement");
    }

    if (currentItem == "shieldColor") {
      $(".select_shieldColor").attr("show", "is_alive_lement");
      goTo = "shieldKit";
      currentItemForDisplay = $("#nameshieldKit").html();

      currentItemForDisplayId = "shieldKit";
    } else {
      $(".select_shieldColor").attr("show", "ghost_lement");
    }

    if (currentItem == "shieldDesign") {
      $(".select_shieldDesign").attr("show", "is_alive_lement");
      goTo = "shieldKit";
      currentItemForDisplay = $("#nameshieldKit").html();

      currentItemForDisplayId = "shieldKit";
    } else {
      $(".select_shieldDesign").attr("show", "ghost_lement");
    }

    if (currentItem == "comment") {
      $(".select_comment").attr("show", "is_alive_lement");
    } else {
      $(".select_comment").attr("show", "ghost_lement");
    }
  }

  function addToTheHistoryList(val) {
    historyList[currentHisPoint] = val;
    currentHisPoint++;
  }

  function backHistoryList() {
    if (historyList == null) {
      return;
    }
    var sizeHis = historyList.length;
    var index = currentHisPoint - 2;
    if (sizeHis > 0 && index < sizeHis && !(index < 0)) {
      currentItem = historyList[index];
      hideShowField(false);
      currentHisPoint--;
    }
  }

  function nextHistoryList() {
    if (historyList == null) {
      return;
    }
    var sizeHis = historyList.length;
    var index = currentHisPoint;
    if (sizeHis > 0 && index < sizeHis && !(index < 0)) {
      currentItem = historyList[index];
      hideShowField(false);
      currentHisPoint++;
    }
  }

  function addNumberToSize(umber) {
    var currentNamber = $(".line").text();
    if (firstPress) {
      currentNamber = "";
    }
    $(".line").text(currentNamber + umber);
    firstPress = false;
  }

  function deleteLastNumberInSize() {
    var currentNamber = $(".line").text();
    var length = currentNamber.length;
    var newNamber = currentNamber.slice(0, length - 1);
    $(".line").text(newNamber);
    firstPress = false;
  }

  function handleKeystroke(e) {
    if (e.which == 48) {
      addNumberToSize(0);
    } else if (e.which == 49) {
      addNumberToSize(1);
    } else if (e.which == 50) {
      addNumberToSize(2);
    } else if (e.which == 51) {
      addNumberToSize(3);
    } else if (e.which == 52) {
      addNumberToSize(4);
    } else if (e.which == 53) {
      addNumberToSize(5);
    } else if (e.which == 54) {
      addNumberToSize(6);
    } else if (e.which == 55) {
      addNumberToSize(7);
    } else if (e.which == 56) {
      addNumberToSize(8);
    } else if (e.which == 57) {
      addNumberToSize(9);
    } else if (e.which == 8) {
      deleteLastNumberInSize(); //backspace
    }
  }

  function checkForLimits() {
    var namber = parseInt($(".line").text());
    if (namber < sizeMin || namber > sizeMax) {
      $(".line").addClass("redColor");
      $("#select_set").addClass("notAvailable");
    } else {
      $(".line").removeClass("redColor");
      $("#select_set").removeClass("notAvailable");
    }
  }

  function setSize() {
    if ($("#select_set").hasClass("notAvailable")) {
      return;
    }

    var number = prepareTheNumber(
      $(".counter_line.numberL.line").text(),
      $(".counter_line.numberR.line").text()
    );
    var idElement = $("#nameSelectForm").attr("data");

    $("#" + idElement).attr("data", number);
    $("#" + idElement).text(
      "" +
        $("#" + idElement).attr("name") +
        " " +
        $("#" + idElement).attr("data")
    );

    closeSelect();
    selectSizeOpen = false;
    assignPreviouValue();
  }

  function fillChildBlockFurniture(grupName, name, position) {
    //set Child field
    var val = $("#" + grupName + "Show").html();
    $("#" + name + "Show").html(val);

    //show
    var javaFurniture = door.furnitureKit[name];
    var cylinder = door.furnitureKit[name + "Cylinder"];
    var inLockDecor = door.furnitureKit[position + "InLockDecor"];
    var outLockDecor = door.furnitureKit[position + "OutLockDecor"];
        var inLockDecor = door.furnitureKit[position+'InLockDecor'];
        var outLockDecor = door.furnitureKit[position+'OutLockDecor'];

    if (javaFurniture != null) {
      //Cylinder
      showCylinderLock(name, javaFurniture.itCylinderLock, cylinder);
      //Decoration
      availableLockDecoration(position, "In", inLockDecor, true);
      availableLockDecoration(position, "Out", outLockDecor, true);
    } else {
      showCylinderLock(name, false);
      availableLockDecoration(position, "In", inLockDecor, false);
      availableLockDecoration(position, "Out", outLockDecor, false);
    }
  }

  function fillChildBlockShield(name) {
    //set Child field
    var jObject = door.shieldKit[name];
    if (jObject != null) {
      $("#" + name + "Show").html(jObject.name);
    }
  }

  function showCylinderLock(name, itCylinderLock, cylinder) {
    if (itCylinderLock) {
      $("#" + name + "Cylinder").removeClass("ghost");
    } else {
      $("#" + name + "Cylinder").addClass("ghost");
    }
    if (cylinder instanceof Object) {
      $("#" + name + "CylinderShow").text(cylinder.name);
    } else {
      $("#" + name + "CylinderShow").text("");
    }
  }

  function showDecorationLock(position, side, decor) {
    if (decor instanceof Object) {
      $("#" + position + side + "LockDecorShow").text(decor.name);
    } else {
      $("#" + position + side + "LockDecorShow").text("");
    }
  }

  function availableLockDecoration(position, side, decor, show) {
    var value = "no";
    if (show) {
      value = "yes";
      showDecorationLock(position, side, decor);
    }
    $("#name" + position + side + "LockDecor").attr("available", value);
    $("#name" + position + side + "LockDecor").attr("available", value);
  }

  function setСurrentItem(name) {
    var elem = $(".div_images_furniture[Item=" + name + "]");
    var furnitureItem = door.furnitureKit[name];
    var id = 0;
    if (furnitureItem != null) {
      id = furnitureItem.id;
    }
    elem
      .filter(function (index) {
        return $(this).attr("data") == id;
      })
      .attr("check", "checkbox");
  }

    function setСurrentColor() {
        var elem = $(".div_images_Color");
        var furnitureItem = door.doorColor;
        var id = 0;
        if (furnitureItem != null) {
            id = furnitureItem;
        }
        elem
            .filter(function (index) {
                return $(this).attr("data") == id;
            })

      .attr("check", "checkbox");
    }
});
