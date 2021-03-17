jQuery("document").ready(function () {
    var JavaObject;
    var typeImageList;
    var typeDoorColorList;
    let colorListPictures;
    let imageMaskPathList;
    let shieldDesign;

    //new instans
    getTypeList();
    getJavaObject();

    $("#idManufacturerProgram").change(function () {
        setField("idManufacturerProgram", $("#idManufacturerProgram").val());
    });

    $("#name").change(function () {
        setField("name", $("#name").val());
    });

    $("#typeOfImage").change(function () {
        const type = $("#typeOfImage").val();

        setField("typeOfImage", type);

        if (type != "") {
            showSettings(JavaObject.typeOfImage);
            getImageListFromServer();
            getShieldGlass();
            if (type == "DOOR_COLOR") {
                getTypeListColor();
            } else if (type == "SHIELD_DESIGN") {
                getTypeListShield();
            }
        } else {
            fillInTypes("#picturePath", "");
            showPicture("#picturePath_img", "");
        }

    });

    $("#typeOfDoorColor").change(function () {

        const type = $("#typeOfImage").val();
        if (type == "DOOR_COLOR") {
            setField("typeOfDoorColor", $("#typeOfDoorColor").val());
        } else if (type == "SHIELD_DESIGN") {
            setField("typeOfShieldDesign", $("#typeOfDoorColor").val());
        }
    });

    $("#smooth").change(function () {
        if ($(this).is(":checked")) {
            setField("smooth", 1);
        } else {
            setField("smooth", 0);
        }
    });

    $("#containsDesign").change(function () {
        if ($(this).is(":checked")) {
            setField("containsDesign", 1);
        } else {
            setField("containsDesign", 0);
        }
    });

    $("#containsGlass").change(function () {
        if ($(this).is(":checked")) {
            setField("containsGlass", 1);
        } else {
            setField("containsGlass", 0);
        }
    });

    $("#picturePath").change(function () {
        if ($("#picturePath").val() != "") {
            setField("picturePath", getPictureColor($("#picturePath").val(), colorListPictures).path);
            showPicture("#picturePath_img", getPictureColor($("#picturePath").val(), colorListPictures).path);
        } else {
            setField("picturePath", "");
            showPicture("#picturePath_img", "");
        }
    });

    $("#picturePathDesign").change(function () {
        if ($("#picturePathDesign").val() != "") {
            setField("containsDesign", getPictureColor($("#picturePathDesign").val(), shieldDesign).id);
            showPicture("#picturePathDesign_img", getPictureColor($("#picturePathDesign").val(), shieldDesign).picturePath);
        } else {
            setField("containsDesign", 0);
            showPicture("#picturePathDesign_img", "");
        }
    });

    $("#outWoodPanel").change(function () {
        if ($(this).is(":checked")) {
            setField("containsWoodPanel", 1);
        } else {
            setField("containsWoodPanel", 0);
            setField("maskPath", "");
        }
        showMask();
    });

    $("#outWoodMaskPath").change(function () {
        if ($("#outWoodMaskPath").val() != "") {
            setField("maskPath", getPictureColor($("#outWoodMaskPath").val(), imageMaskPathList).path);
        } else {
            setField("maskPath", "");
        }
        showPicture("#mask_png", JavaObject.maskPath);
    });

    $("#containsOtherColor").change(function () {
        if ($(this).is(":checked")) {
            setField("containsOtherColor", 1);
        } else {
            setField("containsOtherColor", 0);
            setField("maskPath", "");
        }
        showMask();
    });


    function getPictureColor(idPicture, picturesList) {
        for (let i = 0; i < picturesList.length; i++) {
            if (picturesList[i].id == idPicture) {
                return picturesList[i];
            }
        }
    }

    function getPictureColorPath(pathPicture) {
        for (let i = 0; i < colorListPictures.length; i++) {
            if (colorListPictures[i].path == pathPicture) {
                return colorListPictures[i];
            }
        }
    }

    function getPictureMaskPath(pathPicture) {
        const defaultResponce = {id: 0};
        if (pathPicture == null) {
            return defaultResponce;
        }
        for (let i = 0; i < imageMaskPathList.length; i++) {
            if (imageMaskPathList[i].path == pathPicture) {
                return imageMaskPathList[i];
            }
        }
        return defaultResponce;
    }

    function getImageListFromServer() {
        return $.ajax({
            url: location.origin + "/color/image-file-list/" + JavaObject.typeOfImage,
            dataType: "json",
            async: false,
            success: function (data) {
                colorListPictures = data;
                fillInSelector("#picturePath", data);
                return data;
            },
            error: function (data) {
                alert("!ERROR: изображений получить не удалось:");
            },
        });
    }

    function getImageMaskListFromServer() {
        return $.ajax({
            url: location.origin + "/color/image-file-list/" + JavaObject.typeOfImage + "/masks",
            dataType: "json",
            async: false,
            success: function (data) {
                imageMaskPathList = data;
                fillInSelector("#outWoodMaskPath", imageMaskPathList);
                return data;
            },
            error: function (data) {
                alert("!ERROR: масок изображений получить не удалось:");
            },
        });
    }

    function getShieldDesignFromServer() {
        $.ajax({
            url: location.origin + "/color/shieldDesign",
            dataType: "json",
            success: function (data) {
                shieldDesign = data;
                fillInSelector("#picturePathDesign", shieldDesign);
                if (JavaObject.containsDesign != 0) {
                    showPicture("#picturePathDesign_img", getPictureShieldDesign(JavaObject.containsDesign).picturePath);
                }
            },
            error: function (data) {
                alert("!ERROR: изображений получить не удалось:");
            },
        });
    }

    $("#pricePaintingMeterOfSpace").change(function () {
        setField(
            "pricePaintingMeterOfSpace",
            $("#pricePaintingMeterOfSpace").val()
        );
    });

    $("#save").on("click", function () {
        var furniture = JSON.stringify(JavaObject);

        $.ajax({
            url: "item",
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: furniture,
            success: function (data) {
                toList();
            },
            error: function (data) {
                alert("!ERROR: елемнет записать не удалось:");
            },
        });
    });

    $("#close").on("click", function () {
        toList();
    });

    $("#delete").on("click", function () {
        $.ajax({
            url: "" + getIdFromUrl(),
            method: "DELETE",
            dataType: "json",
            success: function (data) {
                alert(data.status);
                toList();
            },
            error: function (data) {
                alert("!ERROR: елемнет удалить не удалось:");
            },
        });
    });

    function toList() {
        location.pathname = "color";
    }

    function getJavaObject() {
        $.ajax({
            url: "item/" + getIdFromUrl(),
            dataType: "json",
            success: function (data) {
                JavaObject = data;
                fillByOject();
            },
            error: function (data) {
                alert("!ERROR: елемнет фурнитуры получить не удалось:");
            },
        });
    }

    function getIdFromUrl() {
        var url = location.href;
        var id = url.substring(url.lastIndexOf("/") + 1);
        return id;
    }

    function showSettings(type) {

        hideAllSettings();

        if (type == "SHIELD_DESIGN") {
            $("#containsGlassDiv").show();
            $("#additionalTypeDiv").show();
            $("#containsOtherColorDiv").show();
        } else if (type == "SHIELD_GLASS") {
            $("#containsDesignGlass").show();
        } else if (type == "DOOR_COLOR") {
            $("#additionalTypeDiv").show();
        } else if (type == 'DOOR_DESIGN') {
            $("#outWoodPanelDiv").show();
        }
    }

    function hideAllSettings() {
        $("#containsGlassDiv").hide();
        $("#containsDesignGlass").hide();
        $("#smoothSwitchDiv").hide();
        $("#additionalTypeDiv").hide();
        $("#containsDesignSwitchDiv").hide();
        $("#outWoodPanelDiv").hide();
        $("#containsOtherColorDiv").hide();

    }

    function showMask() {
        if ($("#outWoodPanel").is(":checked") || $("#containsOtherColor").is(":checked")) {
            $('#outWoodMaskPathDiv').show();
            $('#mask_png').show();
        } else {
            $('#outWoodMaskPathDiv').hide();
            $('#mask_png').hide();
        }

    }

    function getPictureShieldDesign(idItem) {
        for (let i = 0; i < shieldDesign.length; i++) {
            if (shieldDesign[i].id == idItem) {
                return shieldDesign[i];
            }
        }
    }


    function fillByOject() {
        if (JavaObject != null) {

            if (JavaObject.typeOfImage != null) {
                showSettings(JavaObject.typeOfImage);
                let responce = getImageListFromServer();
                responce = getImageMaskListFromServer();
                getShieldGlass();
            }

            $("#id").val(JavaObject.id);
            $("#idManufacturerProgram").val(JavaObject.idManufacturerProgram);
            $("#name").val(JavaObject.name);

            setCheckBox("#smooth", JavaObject.smooth);
            setCheckBox("#containsGlass", JavaObject.containsGlass);

            setValueInSelect("#picturePath", getPictureColorPath(JavaObject.picturePath).id);
            showPicture("#picturePath_img", JavaObject.picturePath);

            $("#pricePaintingMeterOfSpace").val(JavaObject.pricePaintingMeterOfSpace);
            setValueInSelect("#typeOfImage", JavaObject.typeOfImage);

            if (JavaObject.typeOfImage) {
                const type = $("#typeOfImage").val();
                if (type == "DOOR_COLOR") {
                    getTypeListColor();
                    setValueInSelect("#typeOfDoorColor", JavaObject.typeOfDoorColor);
                } else if (type == "SHIELD_DESIGN") {
                    getTypeListShield()
                    setValueInSelect("#typeOfDoorColor", JavaObject.typeOfShieldDesign);
                }
            }

            setValueInSelect("#outWoodMaskPath", getPictureMaskPath(JavaObject.maskPath).id);
            showPicture("#mask_png", JavaObject.maskPath);

            if (JavaObject.typeOfImage == "SHIELD_GLASS") {
            } else {
                setCheckBox("#containsDesign", JavaObject.containsDesign);
            }

            setCheckBox("#outWoodPanel", JavaObject.containsWoodPanel);
            showPicture("#mask_png", JavaObject.maskPath);

            setCheckBox("#containsOtherColor", JavaObject.containsOtherColor);

            showMask();
        }
    }


    function getShieldGlass() {
        if (JavaObject.typeOfImage == "SHIELD_GLASS") {
            getShieldDesignFromServer();
        }
    }

    function setField(fieldName, value) {
        JavaObject[fieldName] = value;
    }

    function setCheckBox(name, value) {
        if (value == 1) {
            $(name).prop("checked", true);
        }
    }

    function getTypeList() {
        $.ajax({
            url: location.origin + "/image/types",
            dataType: "json",
            success: function (data) {
                typeImageList = data;
                fillInTypes("#typeOfImage", typeImageList);
            },
            error: function (data) {
                alert("!ERROR: типы изображений получить не удалось:");
            },
        });

    }

    function getTypeListColor() {
        $.ajax({
            url: location.origin + "/color/types/color",
            dataType: "json",
            success: function (data) {
                typeDoorColorList = data;
                fillInTypesName("#typeOfDoorColor", typeDoorColorList);
                setValueInSelect("#typeOfDoorColor", JavaObject.typeOfDoorColor);
            },
            error: function (data) {
                alert("!ERROR: типы дверных цветов получить не удалось:");
            },
        });
    }

    function getTypeListShield() {
        $.ajax({
            url: location.origin + "/colors/shield-designs/types/buttons",
            dataType: "json",
            success: function (data) {
                typeDoorColorList = data;
                fillInTypesName("#typeOfDoorColor", typeDoorColorList);
                setValueInSelect("#typeOfDoorColor", JavaObject.typeOfShieldDesign);
            },
            error: function (data) {
                alert("!ERROR: типы дверных цветов получить не удалось:");
            },
        });
    }

    function fillInSelector(selectName, types) {
        if (types != null) {
            $(selectName).empty();

            $(selectName).append($("<option></option>"));

            for (var i = 0; i < types.length; ++i) {
                $(selectName).append(
                    $("<option value=" + types[i].id + ">" + types[i].name + "</option>")
                );
            }
            if (selectName == "#picturePathDesign") {
                if (JavaObject.containsDesign != 0) {
                    setValueInSelect("#picturePathDesign", JavaObject.containsDesign);
                }
            }

        }
    }

    function fillInTypes(selectNmae, types) {
        if (types != null) {
            $(selectNmae).empty();

            $(selectNmae).append($("<option></option>"));

            for (var i = 0; i < types.length; ++i) {
                $(selectNmae).append(
                    $("<option value=" + types[i] + ">" + types[i] + "</option>")
                );
            }
        }
    }

    function fillInTypesName(selectNmae, types) {
        if (types != null) {
            $(selectNmae).empty();

            $(selectNmae).append($("<option></option>"));

            for (var i = 0; i < types.length; ++i) {
                $(selectNmae).append(
                    $("<option value=" + types[i].type + ">" + types[i].name + "</option>")
                );
            }
        }
    }

    function setValueInSelect(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }
});

function showPicture(divSelect, value) {
    $(divSelect).attr("src", "../" + value);
}

