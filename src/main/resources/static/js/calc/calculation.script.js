let door;
let listColorsEntity;
let failureToSetValue = false;
let currentItem = "";
let RestrictionOfSelectionFields;
let selectSizeOpen = false;
let average_widthDoorVal = 0;
let maxSize_widthDoorVal = 0;
let availableFurnitureList;
const parentDir = "../../";
let blockDoorStep = false;
let currentNumberSize = 0;
let currentColorType = null;
let javaLimitList = [];


jQuery("document").ready(function () {

    var goTo;
    var currentItemForDisplay = "";
    var currentItemDaughterForDisplay = "";
    var currentItemForDisplayId = "";

    var doorLeaf = 1;
    var fanlight = 0;

    var classList;

    var colors;


    var id = $("#id").text();
    var orderId = $("#orderId").text();
    var typeId = 0;

    var sizeMin = 0;
    var sizeMax = 0;


    var firstPress = true;

    var historyList = new Array();
    var currentHisPoint = 0;

    var audio = $("#mySoundClip")[0];

    Door.getColorInstans();

    if (id > 0) {
        getNewDoorInstance(false);
    } else {
        getClassList();
    }

    function FillOutForm(door, updateClassDiv) {
        RepresentationManager.showAllFieldsValues(door);
        if (updateClassDiv) {
            ClassCardBlock.display(door.availableDoorClass);
            currentItem = "doorClass";
            hideShowField(true);
        }
        displayPrice();
    }

    //--------------------------------------
    //select
    //--------------------------------------

    $(".select_door_class").on("click", ".class_card", function () {
        TypeCardBlock.display($(this).attr("data"), classList);

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
        getNewDoorInstance(false);

    });

    $(".vertical_menu_button").on("click", function () {
        if ($("#name" + $(this).attr("id") + "").attr("available") === "yes") {
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

        let javaObject = findColorObjectByName($(this).attr("data"));
        setDoorFurnitureByObject(javaObject, "doorColor", door.doorDesign);
        RepresentationManager.showFieldValue($(this).attr("data"));
        Door.draw(door, 1);
        pickOut(this);
    });

    $(".div_images_design").on("click", function () {
        let nameJavaField = $(this).attr("Item");
        let idJavaObject = $(this).attr("data");
        let javaObject = findObjectById(nameJavaField, idJavaObject);

        setDoorDesignField(javaObject, nameJavaField);

        RepresentationManager.showAllFieldsValues(door);
        pickOut(this);
        if (goTo != "") {
            currentItem = goTo;
            hideShowField(true);
        }
    });

    $(".div_images_over").on("click", function () {
        let nameJavaField = $(this).attr("Item");
        let idJavaObject = $(this).attr("data");
        let javaObject = findObjectById("shieldColor", idJavaObject);

        setDoorFurnitureByObject(javaObject, nameJavaField, door.shieldKit);

        RepresentationManager.showAllFieldsValues(door);
        pickOut(this);
        if (goTo != "") {
            currentItem = goTo;
            hideShowField(true);
        }
    });


    $(".div_images_DoorGlass").on("click", function () {
        setDoorGlassImg($(this).attr("Item"), $(this).attr("data"));
        RepresentationManager.showFieldValue($(this).children("span").html());
        if ($("#typeDoorGlassShow").text() == "" || $("#typeDoorGlassShow").text() == "нет") {
            resetDoorGlassField();
        }
        Door.draw(door, 1);
        pickOut(this);
        if (goTo != "") {
            currentItem = goTo;
            hideShowField(true);
        }

    });

    $(".div_images_furniture").on("click", function () {
        setDoorFurnitureById(
            $(this).attr("Item"),
            $(this).attr("data"),
            door.furnitureKit);

        RepresentationManager.showAllFieldsValues(door);
        pickOut(this);
        if (goTo != "") {
            currentItem = goTo;
            hideShowField(true);
        }
    });

    $(".div_images_Image").on("click", function () {

        let nameJavaField = $(this).attr("Item");
        let idJavaObject = $(this).attr("data");
        let javaObject = findObjectById(nameJavaField, idJavaObject);

        setDoorFurnitureByObject(javaObject, nameJavaField, door.shieldKit);

        if (nameJavaField == "shieldColor") {
            clearRelatedFieldsForImage(javaObject);
        } else if (nameJavaField == "shieldDesign") {
            clearGlass(javaObject);
            clearShieldKitField(javaObject, "shieldOverColor");
        }

        if (javaObject && javaObject.containsLimit == 1) {
            fillColorsByLimit(javaObject.id);
        }

        RepresentationManager.showAllFieldsValues(door);
        pickOut(this);
        if (goTo != "") {
            currentItem = goTo;
            hideShowField(true);
        }
    });

    $("#commentTextarea").change(function () {
        setDoorField("comment", $(this).val());
        RepresentationManager.showAllFieldsValues(door);
    });

    SideOpen.init();
    InnerOpen.init();

    $(".ios-toggle").on("click", function () {

        let needRepresentationRun = true;
        const item = $(this).attr("Item");
        const available = $('#name' + item).attr('available');

        if (item == "innerOpen") {
            if (InnerOpen.checkAndHide()) {
                $(this).prop("checked", false);
            }
            return;
        }

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
                needRepresentationRun = true;
            }
        } else if (currentItem == "peepholeMenu") {

            if ($(this).attr("item") == "peepholePosition") {
                if ($(this).is(":checked")) {
                    setDoorFurnitureByObject(
                        'CENTER',
                        'peepholePosition',
                        door.furnitureKit);
                } else {
                    setDoorFurnitureByObject(
                        'NOT_CENTER',
                        'peepholePosition',
                        door.furnitureKit);
                }
            }

        } else if (currentItem == "stainlessSteelDoorstepMenu") {

            if (item == "doorstep") {
                if ($(this).is(":checked")) {
                    addOrDelleteGhost("#additionalDoorSettings_stainlessSteelDoorstep", true);
                    setDoorField($(this).attr("Item"), $(this).attr("data"));
                } else {
                    setDoorField($(this).attr("Item"), 0);
                    setDoorField("stainlessSteelDoorstep", 0);
                    $("#stainlessSteelDoorstep_checkbox").prop("checked", false);
                    addOrDelleteGhost("#additionalDoorSettings_stainlessSteelDoorstep", false);
                }
            }

            if (item == "stainlessSteelDoorstep") {
                if (blockDoorStep) {
                    $(this).prop("checked", true);
                }
                let boolValue = false;

                if ($(this).is(":checked")) {
                    setDoorField($(this).attr("Item"), 1);
                    boolValue = true;
                } else {
                    setDoorField($(this).attr("Item"), 0);
                    boolValue = false;
                }

                RepresentationManager.showFieldValue(boolValue);
                Door.draw(door, 1);
                needRepresentationRun = false;
            }

        } else if (currentItem == "additionally") {

            if ($(this).attr("item") == "nightLock") {
                if ($(this).is(":checked")) {
                    setDoorFurnitureById($(this).attr("Item"), $(this).attr("data"));
                } else {
                    setDoorFurnitureById($(this).attr("Item"), 0);
                }
            } else if ($(this).attr("item") == "amplifierCloser") {
                if ($(this).is(":checked")) {
                    setDoorFurnitureById($(this).attr("Item"), $(this).attr("data"));
                } else {
                    setDoorFurnitureById($(this).attr("Item"), 0);
                }
            }
        }

        if (needRepresentationRun) {
            RepresentationManager.showFieldValue($(this).attr("data"));
            Door.draw(door, 1);
        }
    });

    $("#buttonCalculateCostShow").on("click", function () {
        PriceComponent.deleteRow();
        let cost = door.costList.costList;
        if (cost.length == 0) return;
        let costOutput = "";
        let idRow = "";
        let idColName = "";
        for (let i = 0; i < cost.length; i++) {
            idRow = PriceComponent.addLineRow("containerToast", "row lineForDelete", "row" + i);
            idColName = PriceComponent.addLineColumn(idRow, "col-9", "colName" + i);
            costOutput = cost[i].name;
            $("#" + idColName).text(costOutput);
            idColName = PriceComponent.addLineColumn(idRow, "col", "colCost" + i);
            costOutput = cost[i].cost;
            $("#" + idColName).text(costOutput);

        }
        idRow = PriceComponent.addLineRow("containerToast", "row lineForDelete totalColorLine", "rowTotalCost");
        idColName = PriceComponent.addLineColumn(idRow, "col-9", "colNameTotalCost");
        costOutput = "TotalCost";
        $("#" + idColName).text(costOutput);
        idColName = PriceComponent.addLineColumn(idRow, "col", "colCostTotalCost");
        costOutput = door.costList.totalCost;
        $("#" + idColName).text(costOutput);

        $('.toast').toast('show');
    });

    function calculateCostHide() {
        $('.toast').toast('hide');
        PriceComponent.deleteRow();
    }

    $("#buttonCalculate").on("click", function () {
        if (checkThCompletedFields()) {
            const doorJSON = JSON.stringify(door);

            $.ajax({
                type: "POST",
                url: location.origin + "/doors/price",
                contentType: "application/json",
                data: doorJSON,
                dataType: "json",
                success: function (data) {
                    door = data;
                    displayPrice();
                    calculateCostHide();
                },
                error: function (data) {
                    alert("error:" + data);
                },
            });
        }
        audio.play();
    });

    $("#buttonSaveDoor").on("click", function () {
        if (checkThCompletedFields()) {
            const doorJSON = JSON.stringify(door);

            $.ajax({
                type: "POST",
                url: location.origin + "/doors",
                contentType: "application/json",
                data: doorJSON,
                dataType: "json",
                success: function (data) {
                    if (door.id == 0) {
                        toDoor(data.id);
                    }
                    door.id = data.id;
                    displayPrice();
                    calculateCostHide();
                },
                error: function (data) {
                    alert("error:" + data);
                },
            });
        }
    });

    $("#SaveAndСlose").on("click", function () {
        if (checkThCompletedFields()) {
            const doorJSON = JSON.stringify(door);

            $.ajax({
                type: "POST",
                url: location.origin + "/doors",
                contentType: "application/json",
                data: doorJSON,
                dataType: "json",
                success: function (data) {
                    toOrder();
                },
                error: function (data) {
                    alert("error:" + data);
                },
            });
        }
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

    //select number
    $("#select_set").on("click", function () {
        SizingDrum.setSize();
    });

    $("#select_cancel").on("click", function () {
        closeSelect();
    });

    $(".select_size").on("click", function () {
        if ($(this).attr("available") == "no") {
            return;
        }

        sizeMin = SizingDrum.sizeLimMin($(this).attr("id"));
        sizeMax = SizingDrum.sizeLimMax($(this).attr("id"));

        maxSize_widthDoorVal = RestrictionOfSelectionFields["widthDoor"].length - 1;
        average_widthDoorVal = average_Size_widthDoor(maxSize_widthDoorVal);

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

        average_widthDoorVal += 1;
        add("L", 5, null, sizeMin, sizeMax);

    });

    $("#reduceL").on("click", function () {
        average_widthDoorVal -= 1;

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
        processKey(e);
        checkForLimits();
    });


    $(".toolbarPage").on("click", ".toolbarPageButton", function () {
        if (currentItem == "doorColor" && currentColorType) {
            getColorByTypeOfDoor(currentColorType, parseInt($(this).attr('data')));
        } else if (currentItem == "shieldDesign") {
            getShieldByTypeOfDoor(currentColorType, parseInt($(this).attr('data')));
        } else if (currentItem == "shieldColor") {
            displayImage(currentItem, availableFurnitureList[currentItem], parseInt($(this).attr('data')));
        } else if (currentItem == "topLock" ||
            currentItem == "lowerLock" ||
            currentItem == "handle" ||
            currentItem == "lowerLockCylinder" ||
            currentItem == "topLockCylinder" ||
            currentItem == "topInLockDecor" ||
            currentItem == "topOutLockDecor" ||
            currentItem == "lowerInLockDecor" ||
            currentItem == "lowerOutLockDecor" ||
            currentItem == "closer" ||
            currentItem == "typeDoorGlass" ||
            currentItem == "toning" ||
            currentItem == "armor"
        ) {
            displayListOfItems(currentItem, availableFurnitureList[currentItem], parseInt($(this).attr('data')), '');
        }

    });

    $(".toolbarType").on("click", ".toolbarTypeButton", function () {

        if (currentItem == "doorColor") {
            currentColorType = $(this).attr('data');
            getColorByTypeOfDoor($(this).attr('data'));
        } else if (currentItem == "shieldDesign") {
            currentColorType = $(this).attr('data');
            getShieldByTypeOfDoor($(this).attr('data'));
        }

    });

//--------------------------------------
//setter
//--------------------------------------

    function setDoorField(fieldName, value) {
        door[fieldName] = value;
        failureToSetValue = false;
    }

    function setDoorDesignField(value, fieldName) {
        door.doorDesign[fieldName] = value;
        failureToSetValue = false;
    }

    function setDoorGlassImg(fieldName, value) {
        var furn = findObjectById(fieldName, value);
        door.doorGlass[fieldName] = furn;
    }

    function setDoorGlassField(fieldName, value) {
        door.doorGlass[fieldName] = value;
    }

    function resetDoorGlassField() {
        Door.setGlass("glassWidth", 0);
        Door.setGlass("glassHeight", 0);
        Door.setGlass("leftGlassPosition", 0);
        Door.setGlass("bottomGlassPosition", 0);
        setDoorGlassField("toning", null);
        setDoorGlassField("armor", null);
        $(".vertical_menu_button_rigtht#" + "toning" + "Show strong").html("");
        $(".vertical_menu_button_rigtht#" + "armor" + "Show strong").html("");
        $("#doorGlassShow").text("");
        door.doorGlass.id = 0;
        door.doorGlass.space = 0;
    }

    function setDoorFurnitureById(fieldName, id, doorKit) {
        setDoorFurnitureByObject(findObjectById(fieldName, id), fieldName, doorKit)
    }

    function setDoorFurnitureByObject(object, fieldName, doorKit) {
        doorKit[fieldName] = object;
    }

    function findObjectById(tabName, id) {
        if (tabName == "lowerLockCylinder" || tabName == "topLockCylinder") {
            tabName = "lockCylinder";
        }

        let sizeRest = availableFurnitureList[tabName].length;
        let tab = availableFurnitureList[tabName];
        for (var i = 0; i < sizeRest; ++i) {
            if (tab[i].id == id) {
                return tab[i];
            }
        }

        sizeRest = javaLimitList.length;
        tab = javaLimitList;
        for (var i = 0; i < sizeRest; ++i) {
            if (tab[i].id == id) {
                return tab[i];
            }
        }
        return null;
    }

    function findColorObjectByName(name) {
        let tab = [];

        if (Door.listColorsEntity && Door.listColorsEntity.length > 0) {
            tab = Door.listColorsEntity.filter(function (color) {
                return color.name == name;
            })
        }

        return tab.length > 0 ? tab[0] : null;
    }

    function getFirstAttr(attr) {
        if (attr.indexOf(' ') > 0) {
            return attr.substring(0, attr.indexOf(' '));
        } else {
            return attr;
        }
    }

    function pickOut(item) {
        var attr = $(item).attr("class");
        let attrFirst = getFirstAttr(attr);

        var elems = $("." + attrFirst);
        var elemsTotal = elems.length;
        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr("check", "no");
        }

        $(item).attr("check", "checkbox");
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

    function toOrder() {
        location.href = location.origin + '/pages/orders/' + orderId;
    }

    function toDoor(doorId) {
        location.href = location.origin + '/doors/' + doorId + "/page?orderId=" + orderId;
    }

    function priceToString(price) {
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

    function displayPrice() {
        if (door.priceWithMarkup != 0) {
            let price = priceToString(door.priceWithMarkup);
            $("#price").text(price);
            $("<span>").attr("class", "rub").text("Р").appendTo("#price");
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
                RepresentationManager.showFieldValue($("#checkboxmetal" + i).attr("data"));
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
        //Door.draw(door, 1);
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
                RepresentationManager.showFieldValue(tab[i].startRestriction);
            }
        }
    }

    function processItemSelection(item) {
        currentItem = $(item).attr("id");

        if ($("#" + currentItem).attr("available") == "no") {
            return;
        }
        currentItemForDisplay = $(item).html();
        currentItemDaughterForDisplay = "";
        currentItemForDisplayId = currentItem;
        failureToSetValue = false;

        hideShowField(true);
        addNavigation();
    }

    function displayColor(nameJava, tab, bias) {

        let offsetTab = PaginationPage.generate(tab, bias, 'toolbarPage');

        for (var i = 0; i < offsetTab.amountElements; ++i) {
            if (i + offsetTab.biasInt < offsetTab.tabSize) {
                $("#images" + nameJava + "Div" + i).attr("show", "is_alive_lement");
                $("#images" + nameJava + "Div" + i).attr(
                    "data",
                    tab[i + offsetTab.biasInt].name
                );
                for (let a = 0; a < tab.length; a++) {
                    if (tab[a].id == tab[i + offsetTab.biasInt].id) {
                        $("#images" + nameJava + "Img" + i).attr(
                            "src",
                            parentDir + tab[a].picturePath
                        )
                    }
                }

                $("#images" + nameJava + "Span" + i).text(
                    tab[i + offsetTab.biasInt].name
                );
            } else {
                $("#images" + nameJava + "Div" + i).attr("show", "ghost_lement");
                $("#images" + nameJava + "Div" + i).attr("data", "");
                $("#images" + nameJava + "Img" + i).attr("src", "");
                $("#images" + nameJava + "Span" + i).text("");
            }
        }
    }

    function displayImage(nameJava, tab, bias) {

        let offsetTab = PaginationPage.generate(tab, bias, 'toolbarPage');

        for (var i = 0; i < offsetTab.amountElements; ++i) {
            if (i + offsetTab.biasInt < offsetTab.tabSize) {
                $("#images" + nameJava + "Div" + i).attr("show", "is_alive_lement");
                $("#images" + nameJava + "Div" + i).attr("data", tab[i + offsetTab.biasInt].id);
                $("#images" + nameJava + "Img" + i).attr(
                    "src",
                    parentDir + tab[i + offsetTab.biasInt].picturePath
                );
                $("#images" + nameJava + "Span" + i).text(tab[i + offsetTab.biasInt].name);
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

        Trim.init(data);

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

    function displayListOfItems(nameTab, tab, bias, postfixName, item = nameTab) {
        if (tab != null) {
            let offsetTab = PaginationPage.generate(tab, bias, 'toolbarPage');
            $('#itemNo').attr("Item", item);
            for (var i = 0; i < offsetTab.amountElements; ++i) {
                var sel = "#" + nameTab;
                if (i + offsetTab.biasInt < offsetTab.tabSize) {
                    $(sel + "Div" + i).attr("show", "is_alive_lement");
                    $(sel + "Div" + i).attr("data", tab[i + offsetTab.biasInt].id);
                    $(sel + "Div" + i).attr("Item", item);
                    $(sel + "Img" + i).attr("src", parentDir + tab[i + offsetTab.biasInt].picturePathFirst);
                    $(sel + "Span" + i).text(tab[i + offsetTab.biasInt].name);
                } else {
                    $(sel + "Div" + i).attr("show", "ghost_lement");
                    $(sel + "Div" + i).attr("data", "");
                    $(sel + "Img" + i).attr("src", "");
                    $(sel + "Span" + i).text("");
                }
            }
        }
    }

    function displayGlass() {
        //if (door.isDoorGlass == 1) {
        //$("#checkboxdoorGlass0").prop("checked", true);
        $(".input_doorGlass_div").attr("show", "is_alive_lement");

        if (door.doorGlass.typeDoorGlass !== null) {
            currentItem = "typeDoorGlass";
            RepresentationManager.showFieldValue(door.doorGlass.typeDoorGlass.name);
        }
        if (door.doorGlass.toning !== null) {
            currentItem = "toning";
            RepresentationManager.showFieldValue(door.doorGlass.toning.name);
        }
        if (door.doorGlass.armor !== null) {
            currentItem = "armor";
            RepresentationManager.showFieldValue(door.doorGlass.armor.name);
        }

        currentItem = "glassWidth";
        RepresentationManager.showFieldValue(door.doorGlass.glassWidth);
        currentItem = "glassHeight";
        RepresentationManager.showFieldValue(door.doorGlass.glassHeight);
        currentItem = "leftGlassPosition";
        RepresentationManager.showFieldValue(door.doorGlass.leftGlassPosition);
        currentItem = "bottomGlassPosition";
        RepresentationManager.showFieldValue(door.doorGlass.bottomGlassPosition);
        // }
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

    function getNewDoorInstance(updateClassDiv) {
        $.ajax({
            url: location.origin + '/doors/' + (id == "" ? "0" : id),
            data: {orderId: orderId, typeId: typeId},
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
        if (typeId === 0) {
            typeId = door.doorType.id;
        }

        $.ajax({
            url: location.origin + "/furniture/available-fields/" + typeId,
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
            url: location.origin + "/classes",
            dataType: "json",
            success: function (data) {
                classList = data;
                ClassCardBlock.display(classList);
                hideShowField(true, "doorClass");
            },
            error: function (data) {
                alert("error:" + data);
            },
        });
    }

    function glassShowIfIsExist(data) {

        if (data.typeDoorGlass.length > 0) {

            $("#doorGlass").attr("show", "is_alive_lement");
        } else {
            $("#doorGlass").attr("show", "ghost_lement");
        }
    }

    function fillInTheFieldsToTheTemplate(data) {
        glassShowIfIsExist(data);
        const makeAvailable = new AvailableManager(data, availableFurnitureList);
        makeAvailable.makeFieldsAvailable();

        if (data != null) {

            displayInnerOpen(data);
            displayDoorStep(data);
            displayMetal(data);
            displayWidthDoorAndHeightDoor(data);
            displayheightDoorFanlight(data);
            displayDeepnessDoorAndThicknessDoorLeaf(data);

            colors = data.colors;
            displayImage("outShieldColor", availableFurnitureList.outShieldColor, 0);
            displayImage("shieldColor", availableFurnitureList.shieldColor, 0);
            displayImage("shieldGlass", addToShieldGlassList(), 0);
            displayImage("shieldOverColor", availableFurnitureList.shieldColor, 0);

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

    function hideShowField(addHistory, newCurrentItem = null) {
        if (newCurrentItem != null) {
            currentItem = newCurrentItem;
        }

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
            InnerOpen.updateView();
            SideOpen.updateView();
        } else {
            $(".select_sideDoorOpen").attr("show", "ghost_lement");
        }

        if (currentItem == "additionalDoorSettings") {
            fillChildBlockAdditionalSettings();
            $(".select_additionalDoorSettings").attr("show", "is_alive_lement");
        } else {
            $(".select_additionalDoorSettings").attr("show", "ghost_lement");
        }

        if (currentItem == "doorColor") {
            setСurrentColor();
            $(".select_doorColor").attr("show", "is_alive_lement");

            getAllColorTypeOfDoor(door.doorType.id);

            //displayColor("doorColor", door.template.colors, 0);
            PaginationPage.show();
        } else {
            $(".select_doorColor").attr("show", "ghost_lement");
        }

        if (currentItem == "doorGlass") {
            $(".select_doorGlass").attr("show", "is_alive_lement");
            displayGlass();
            setAvailableChildrenAttr();
        } else {
            $(".select_doorGlass").attr("show", "ghost_lement");
        }

        if (currentItem == "typeDoorGlass") {
            $(".select_typeDoorGlass").attr("show", "is_alive_lement");
            goTo = "doorGlass";

            currentItemForDisplay = $("#namedoorGlass").html();
            //currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = "doorGlass";
            displayListOfItems(currentItem, availableFurnitureList[currentItem], 0, '');
            PaginationPage.show();
        } else {
            $(".select_typeDoorGlass").attr("show", "ghost_lement");
        }

        if (currentItem == "toning") {
            if ($("#toning").attr("available") == "no") {
                return;
            }

            $(".select_toning").attr("show", "is_alive_lement");
            goTo = "doorGlass";

            currentItemForDisplay = $("#namedoorGlass").html();
            //currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = "doorGlass";
            displayListOfItems(currentItem, availableFurnitureList[currentItem], 0, '');
            PaginationPage.show();
        } else {
            $(".select_toning").attr("show", "ghost_lement");
        }

        if (currentItem == "armor") {
            if ($("#armor").attr("available") == "no") {
                return;
            }
            $(".select_armor").attr("show", "is_alive_lement");
            goTo = "doorGlass";

            currentItemForDisplay = $("#namedoorGlass").html();
            currentItemForDisplayId = "doorGlass";
            displayListOfItems(currentItem, availableFurnitureList[currentItem], 0, '');
            PaginationPage.show();
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
            currentItemForDisplayId = "topLockkit";
            displayListOfItems("topLock", availableFurnitureList.topLock, 0, "kit");
            PaginationPage.show();
        } else {
            $(".select_topLock").attr("show", "ghost_lement");
        }

        if (currentItem == "topInLockDecor") {
            $(".select_topInLockDecor").attr("show", "is_alive_lement");
            goTo = "topLockkit";
            currentItemForDisplay = $("#nametopLockkit").html();
            currentItemForDisplayId = "topLockkit";
            displayListOfItems("topInLockDecor", availableFurnitureList.topInLockDecor, 0, '');
            PaginationPage.show();
        } else {
            $(".select_topInLockDecor").attr("show", "ghost_lement");
        }

        if (currentItem == "topOutLockDecor") {
            $(".select_topOutLockDecor").attr("show", "is_alive_lement");
            goTo = "topLockkit";
            currentItemForDisplay = $("#nametopLockkit").html();
            currentItemForDisplayId = "topLockkit";
            displayListOfItems("topOutLockDecor", availableFurnitureList.topOutLockDecor, 0, '');
            PaginationPage.show();
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
            currentItemForDisplayId = "lowerLockkit";
            displayListOfItems("lowerLock", availableFurnitureList.lowerLock, 0, '');
            PaginationPage.show();
        } else {
            $(".select_lowerLock").attr("show", "ghost_lement");
        }

        if (currentItem == "lowerInLockDecor") {
            $(".select_lowerInLockDecor").attr("show", "is_alive_lement");
            goTo = "lowerLockkit";
            currentItemForDisplay = $("#namelowerLockkit").html();
            currentItemForDisplayId = "lowerLockkit";
            displayListOfItems("lowerInLockDecor", availableFurnitureList.lowerInLockDecor, 0, '');
            PaginationPage.show();
        } else {
            $(".select_lowerInLockDecor").attr("show", "ghost_lement");
        }

        if (currentItem == "lowerOutLockDecor") {
            $(".select_lowerOutLockDecor").attr("show", "is_alive_lement");
            goTo = "lowerLockkit";
            currentItemForDisplay = $("#namelowerLockkit").html();
            currentItemForDisplayId = "lowerLockkit";
            displayListOfItems("lowerOutLockDecor", availableFurnitureList.lowerOutLockDecor, 0, '');
            PaginationPage.show();
        } else {
            $(".select_lowerOutLockDecor").attr("show", "ghost_lement");
        }

        if (currentItem == "handle") {
            setСurrentItem("handle");
            $(".select_handle").attr("show", "is_alive_lement");
            displayListOfItems("handle", availableFurnitureList.handle, 0, '');
            PaginationPage.show();
        } else {
            $(".select_handle").attr("show", "ghost_lement");
        }

        if (currentItem == "additionally") {
            $(".select_additionally").attr("show", "is_alive_lement");
        } else {
            $(".select_additionally").attr("show", "ghost_lement");
        }

        if (currentItem == "closer" || currentItem == "peephole" || currentItem == "nightLock") {
            $(".select_item").attr("show", "is_alive_lement");
            goTo = currentItem == "closer" ? 'additionalDoorSettings' : currentItem;
            currentItemForDisplayId = 'additionalDoorSettings';
            displayListOfItems("item", availableFurnitureList[currentItem], 0, '', currentItem);
            PaginationPage.show();
        } else {
            $(".select_item").attr("show", "ghost_lement");
        }

        if (currentItem == "peepholeMenu") {
            //peephole
            let peephole = door.furnitureKit['peephole'];
            Container2fields.setValueToFieldByItem('peephole', peephole ? peephole.name : '');

            //peephole position
            let peepholePosition = door.furnitureKit['peepholePosition'];
            $('#peepholePosition_checkbox').prop("checked", peepholePosition == 'CENTER' ? true : false);

            tab = RestrictionOfSelectionFields.peepholePosition;
            if (tab.length <= 1 || door.furnitureKit.peephole == null) {
                hideField("#additionalDoorSettings_peepholePosition");
            } else {
                showField("#additionalDoorSettings_peepholePosition");
            }

            $(".select_peepholeMenu").attr("show", "is_alive_lement");
        } else {
            $(".select_peepholeMenu").attr("show", "ghost_lement");
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
            displayListOfItems(currentItem, availableFurnitureList[currentItem], 0, '');
            PaginationPage.show();
        } else {
            $(".select_topLockCylinder").attr("show", "ghost_lement");
        }

        if (currentItem == "lowerLockCylinder") {
            $(".select_lowerLockCylinder").attr("show", "is_alive_lement");
            goTo = "lowerLockkit";
            currentItemForDisplay = $("#namelowerLockkit").html();
            displayListOfItems(currentItem, availableFurnitureList[currentItem], 0, '');
            PaginationPage.show();
        } else {
            $(".select_lowerLockCylinder").attr("show", "ghost_lement");
        }

        if (currentItem == "stainlessSteelDoorstepMenu") {
            $(".select_doorstep").attr("show", "is_alive_lement");
        } else {
            $(".select_doorstep").attr("show", "ghost_lement");
        }

        //outShieldKit
        if (currentItem == "outShieldKit") {
            fillChildBlockOutShield("outShieldColor");
            fillChildBlockOutShield("outShieldDesign");
            displayOutShieldChildFields();
            $(".select_outShieldKit").attr("show", "is_alive_lement");
        } else {
            $(".select_outShieldKit").attr("show", "ghost_lement");
        }

        //outShieldColor
        if (currentItem == "outShieldColor") {
            goTo = "outShieldKit";
            currentItemForDisplay = $("#nameoutShieldKit").html();

            //currentItemForDisplayId = "outShieldKit";
            $(".select_outShieldColor").attr("show", "is_alive_lement");
            displayImage("outShieldColor", availableFurnitureList.outShieldColor, 0);
            PaginationPage.show();
        } else {
            $(".select_outShieldColor").attr("show", "ghost_lement");
        }

        //outShieldDesign
        if (currentItem == "outShieldDesign") {
            goTo = "outShieldKit";
            currentItemForDisplay = $("#nameoutShieldKit").html();

            $(".select_outShieldDesign").attr("show", "is_alive_lement");
            displayImage("outShieldDesign", availableFurnitureList.outShieldDesign, 0);
            PaginationPage.show();
        } else {
            $(".select_outShieldDesign").attr("show", "ghost_lement");
        }

        //shield

        if (currentItem == "shieldKit") {
            fillChildBlockShield("shieldColor");
            fillChildBlockShield("shieldDesign");
            fillChildBlockShield("shieldGlass");
            fillChildBlockShield("shieldOverColor");
            displayChildFields();
            showShieldGlass();

            $(".select_shieldKit").attr("show", "is_alive_lement");
        } else {
            $(".select_shieldKit").attr("show", "ghost_lement");
        }

        if (currentItem == "shieldColor") {
            $(".select_shieldColor").attr("show", "is_alive_lement");
            goTo = "shieldKit";
            currentItemForDisplay = $("#nameshieldKit").html();

            currentItemForDisplayId = "shieldKit";
            let colorsList = [];
            if (door.shieldKit &&
                door.shieldKit.shieldDesign &&
                door.shieldKit.shieldDesign.containsLimit == 1) {
                colorsList = javaLimitList;
            } else {
                colorsList = availableFurnitureList.shieldColor;
            }

            displayImage("shieldColor", colorsList, 0);
            PaginationPage.show();
        } else {
            $(".select_shieldColor").attr("show", "ghost_lement");
        }

        if (currentItem == "shieldDesign") {
            $(".select_shieldDesign").attr("show", "is_alive_lement");
            goTo = "shieldKit";
            currentItemForDisplay = $("#nameshieldKit").html();

            currentItemForDisplayId = "shieldKit";
            getAllTypeShieldDesign();
            //displayImage("shieldDesign", availableFurnitureList.shieldDesign, 0);
            PaginationPage.show();
        } else {
            $(".select_shieldDesign").attr("show", "ghost_lement");
        }

        if (currentItem == "shieldOverColor") {
            $(".select_shieldOverColor").attr("show", "is_alive_lement");
            goTo = "shieldKit";
            currentItemForDisplay = $("#nameshieldKit").html();

            currentItemForDisplayId = "shieldKit";
            displayImage("shieldOverColor", availableFurnitureList.shieldColor, 0);
            PaginationPage.show();
        } else {
            $(".select_shieldOverColor").attr("show", "ghost_lement");
        }

        if (currentItem == "shieldGlass") {
            $(".select_shieldGlass").attr("show", "is_alive_lement");
            goTo = "shieldKit";
            currentItemForDisplay = $("#nameshieldKit").html();

            currentItemForDisplayId = "shieldKit";

            displayImage("shieldGlass", addToShieldGlassList(), 0);


            PaginationPage.show();
        } else {
            $(".select_shieldGlass").attr("show", "ghost_lement");
        }

        if (currentItem == "comment") {
            $(".select_comment").attr("show", "is_alive_lement");
            $("#commentTextarea").text(door.comment);
        } else {
            $(".select_comment").attr("show", "ghost_lement");
        }

        if (!hasAParination(currentItem)) {
            PaginationPage.hide();
            TypePages.hide();
        }
    }

    function setAvailableChildrenAttr() {
        if ($("#typeDoorGlassShow").text() == "" || $("#typeDoorGlassShow").text() == "нет") {
            $("#toning").attr("available", "no");
            $("#armor").attr("available", "no");
            $("#input_glassWidth").attr("available", "no");
            $("#input_glassHeight").attr("available", "no");
            $("#input_leftGlassPosition").attr("available", "no");
            $("#input_bottomGlassPosition").attr("available", "no");
        } else {
            $("#toning").attr("available", "yes");
            $("#armor").attr("available", "yes");
            $("#input_glassWidth").attr("available", "yes");
            $("#input_glassHeight").attr("available", "yes");
            $("#input_leftGlassPosition").attr("available", "yes");
            $("#input_bottomGlassPosition").attr("available", "yes");
        }

    }

    function hasAParination() {
        if ("shieldColor" == currentItem ||
            "shieldDesign" == currentItem ||
            "shieldGlass" == currentItem ||
            "doorColor" == currentItem ||
            'topLock' == currentItem ||
            "lowerLock" == currentItem ||
            'closer' == currentItem ||
            'topLockCylinder' == currentItem ||
            'lowerLockCylinder' == currentItem ||
            'handle' == currentItem ||
            'lowerOutLockDecor' == currentItem ||
            'lowerInLockDecor' == currentItem ||
            'topOutLockDecor' == currentItem ||
            'topInLockDecor' == currentItem ||
            'typeDoorGlass' == currentItem ||
            'toning' == currentItem ||
            'armor' == currentItem ||
            'doorGlass' == currentItem
        ) {
            return true;
        }
        return false;
    }

    function addToShieldGlassList() {
        let glassList = [];
        if (door.shieldKit != null && door.shieldKit.shieldDesign != null) {
            for (let i = 0; i < availableFurnitureList.shieldGlass.length; i++) {
                if (availableFurnitureList.shieldGlass[i].containsDesign == door.shieldKit.shieldDesign.id) {
                    glassList.push(availableFurnitureList.shieldGlass[i]);
                }
            }
        }
        return glassList;
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
        currentNumberSize = $(".line").text();
        if (firstPress) {
            currentNumberSize = "";
        }
        currentNumberSize = currentNumberSize + umber;
        $(".line").text(currentNumberSize);
        firstPress = false;
    }

    function deleteLastNumberInSize() {
        var currentNamber = $(".line").text();
        var length = currentNamber.length;
        var newNamber = currentNamber.slice(0, length - 1);
        $(".line").text(newNamber);
        firstPress = false;
    }

    function processKey(e) {
        if (e.which == 48 || e.which == 96) {
            addNumberToSize(0);
        } else if (e.which == 49 || e.which == 97) {
            addNumberToSize(1);
        } else if (e.which == 50 || e.which == 98) {
            addNumberToSize(2);
        } else if (e.which == 51 || e.which == 99) {
            addNumberToSize(3);
        } else if (e.which == 52 || e.which == 100) {
            addNumberToSize(4);
        } else if (e.which == 53 || e.which == 101) {
            addNumberToSize(5);
        } else if (e.which == 54 || e.which == 102) {
            addNumberToSize(6);
        } else if (e.which == 55 || e.which == 103) {
            addNumberToSize(7);
        } else if (e.which == 56 || e.which == 104) {
            addNumberToSize(8);
        } else if (e.which == 57 || e.which == 105) {
            addNumberToSize(9);
        } else if (e.which == 8) {
            deleteLastNumberInSize(); //backspace
        } else if (e.which == 13) {
            SizingDrum.setSizeByParam(currentNumberSize);
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

    function fillChildBlockFurniture(grupName, name, position) {
        //set Child field
        var val = $("#" + grupName + "Show").html();
        $("#" + name + "Show").html(val);

        //show
        var javaFurniture = door.furnitureKit[name];
        var cylinder = door.furnitureKit[name + "Cylinder"];
        var inLockDecor = door.furnitureKit[position + "InLockDecor"];
        var outLockDecor = door.furnitureKit[position + "OutLockDecor"];
        var inLockDecor = door.furnitureKit[position + "InLockDecor"];
        var outLockDecor = door.furnitureKit[position + "OutLockDecor"];

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
        var jObject = door.shieldKit[name];
        if (jObject != null) {
            $("#" + name + "Show").html(jObject.name);
        } else {
            $("#" + name + "Show").html("");
        }
    }

    function fillChildBlockOutShield(name) {
        var jObject = door.doorDesign[name];
        if (jObject != null) {
            $("#" + name + "Show").html(jObject.name);
        } else {
            $("#" + name + "Show").html("");
        }
    }


    function fillChildBlockAdditionalSettings() {
        //closer
        let closer = door.furnitureKit['closer'];
        Container2fields.setValueToFieldByItem('closer', closer ? closer.name : '')


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

    function clearRelatedFieldsForImage(javaObject) {
        if (javaObject == null || (javaObject.typeOfImage == "SHIELD_COLOR" && javaObject.containsDesign == 1)) {
            setDoorFurnitureByObject(null, "shieldDesign", door.shieldKit)
            setDoorFurnitureByObject(null, "shieldOverColor", door.shieldKit)
        }
    }

    function displayChildFields() {
        let shieldColor = door.shieldKit.shieldColor;
        if (shieldColor == null || (shieldColor != null && shieldColor.containsDesign == 1)) {
            $("#shieldDesign").attr("show", "ghost_lement");
        } else {
            $("#shieldDesign").attr("show", "is_alive_lement");
        }
        let shieldDesign = door.shieldKit.shieldDesign;
        if (shieldDesign == null || (shieldDesign != null && shieldDesign.containsOtherColor == 0)) {
            $("#shieldOverColor").attr("show", "ghost_lement");
            setDoorFurnitureByObject(null, "shieldOverColor", door.shieldKit);
        } else {
            $("#shieldOverColor").attr("show", "is_alive_lement");
        }
    }

    function displayOutShieldChildFields() {
        if (door.doorDesign.outShieldDesign ||
            (availableFurnitureList &&
                availableFurnitureList.outShieldDesign &&
                availableFurnitureList.outShieldDesign.length > 0)) {
            $("#outShieldDesign").attr("show", "is_alive_lement");
        } else {
            $("#outShieldDesign").attr("show", "ghost_lement");
        }
    }

    function showShieldGlass() {
        let shieldDesign = door.shieldKit.shieldDesign;
        if (shieldDesign == null || (shieldDesign != null && shieldDesign.containsGlass == 0)) {
            $("#shieldGlass").attr("show", "ghost_lement");
            setDoorFurnitureByObject(null, "shieldGlass", door.shieldKit);
        } else {
            $("#shieldGlass").attr("show", "is_alive_lement");
        }
    }

    function clearGlass(javaObject) {
        if (javaObject == null || javaObject.containsDesign == 0) {
            setDoorFurnitureByObject(null, "shieldGlass", door.shieldKit);
        }
    }

    function clearShieldKitField(javaObject, name) {
        if (javaObject == null || javaObject.containsWoodPanel == 0) {
            setDoorFurnitureByObject(null, name, door.shieldKit);
        }
    }

    function addOrDelleteGhost(id, onOrOff) {
        if (onOrOff) {
            $(id).removeClass("ghost");
        } else {
            $(id).addClass("ghost");
        }

    }

    function displayDoorStep(template) {
        let stainlessList = template.stainlessSteelDoorstep;
        let doorStepNot = false;
        let doorStepNotDefault = false;
        let doorStepYes = false;
        let doorStepYesDefault = false;
        if (stainlessList.length > 0) {
            for (let i = 0; i < stainlessList.length; i++) {
                if (searchValue(stainlessList[i], "startRestriction", 0)) {
                    doorStepNot = true;
                    if (searchValue(stainlessList[i], "defaultValue", 1)) {
                        doorStepNotDefault = true;
                    }
                }
                if (searchValue(stainlessList[i], "startRestriction", 1)) {
                    doorStepYes = true;
                    if (searchValue(stainlessList[i], "defaultValue", 1)) {
                        doorStepYesDefault = true;
                    }
                }
            }

            if (doorStepYes && doorStepYesDefault && !doorStepNot && !doorStepNotDefault) {
                blockDoorStep = true;
            }

            if (!doorStepYes && !doorStepYesDefault && doorStepNot && doorStepNotDefault) {
                addOrDelleteGhost("#additionalDoorSettings_stainlessSteelDoorstep", false);
            }

            if (doorStepYes || doorStepNot) {
                addOrDelleteGhost("#additionalDoorSettings_stainlessSteelDoorstep", true);
            }
        }
    }

    function displayInnerOpen(template) {
        if (!InnerOpen.checkAndHide()) {
            addOrDelleteGhost("#innerOpenDiv", true);
        } else {
            addOrDelleteGhost("#innerOpenDiv", false);
        }

    }

    function searchValue(object, fieldName, value) {
        if (object[fieldName] == value) {
            return true;
        } else return false;
    }

    function hideField(nameField) {
        $(nameField).hide();
    }

    function showField(nameField) {
        $(nameField).show();
    }

    function getAllColorTypeOfDoor(doorTypeId) {
        $.ajax({
            url: location.origin + '/doorType/' + doorTypeId + '/colors/types/buttons',
            dataType: "json",
            success: function (data) {
                let listTypeOfDoorColor = data;
                TypePages.generate(listTypeOfDoorColor, 0, 'toolbarType');
                TypePages.show();
                getColorByTypeOfDoor(currentColorType);

            },
            error: function (data) {
                alert("error: getting the EnumColor failed !");
            },
        });
    }

    function getColorByTypeOfDoor(type, bias = 0) {
        doorTypeId = door.doorType.id;
        $.ajax({
            url: location.origin + '/doorType/' + doorTypeId + '/' + type + '/colors',
            dataType: "json",
            success: function (data) {
                displayColor("doorColor", data, bias);
            },
            error: function (data) {
                alert("error: getting the EnumColor failed !");
            },
        });
    }

    function getAllTypeShieldDesign() {
        $.ajax({
            url: location.origin + '/colors/shield-designs/types/buttons',
            dataType: "json",
            success: function (data) {
                let listTypeOfDoorColor = data;
                TypePages.generate(listTypeOfDoorColor, 0, 'toolbarType');
                TypePages.show();
                getShieldByTypeOfDoor(currentColorType);

            },
            error: function (data) {
                alert("error: getting the EnumColor failed !");
            },
        });
    }

    function getShieldByTypeOfDoor(type, bias = 0) {
        doorTypeId = door.doorType.id;
        $.ajax({
            url: location.origin + '/doorType/' + doorTypeId + '/' + type + '/shield-design',
            dataType: "json",
            success: function (data) {
                displayImage("shieldDesign", data, bias);
            },
            error: function (data) {
                alert("error: getting the shieldType failed !");
            },
        });
    }

    function fillColorsByLimit(id) {
        getLimitsFromServer(id);
    }

    function getLimitsFromServer(id) {
        if (id && id != 0) {
            $.ajax({
                url: location.origin + "/colors/" + id + "/limitations",
                dataType: "json",
                success: function (data) {
                    javaLimitList = data;
                    if (javaLimitList && javaLimitList.length > 0) {
                        const colorFromLimit = javaLimitList[0];
                        setDoorFurnitureByObject(colorFromLimit, "shieldColor", door.shieldKit);
                        clearRelatedFieldsForImage(colorFromLimit);
                        RepresentationManager.showAllFieldsValues(door);
                        alert("Для выбраного дизайна ограничен выбор цветов!");
                        fillChildBlockShield("shieldColor");
                    }
                },
                error: function (data) {
                    alert("!ERROR: ограничения получить не удалось:");
                },
            });
        }

    }
});
