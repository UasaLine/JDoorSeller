jQuery("document").ready(function () {
    let furnitureJavaObject;
    let types;
    let sketchPathList = [];
    let picPathList = [];

    //new instance
    getFurnitureJavaObject();

    $("#idManufacturFurniture").change(function () {
        setField("idManufacturerProgram", $("#idManufacturFurniture").val());
    });

    $("#nameFurniture").change(function () {
        setField("name", $("#nameFurniture").val());
    });

    $("#typeOfFurniture").change(function () {
        setField("typeOfFurniture", $("#typeOfFurniture").val());
        getPicPathFromServer();
        getSketchPathFromServer();
    });

    $("#itCylinderLock").on("click", function () {
        if ($(this).is(":checked")) {
            setField("itCylinderLock", 1);
        } else {
            setField("itCylinderLock", 0);
        }
    });

    $("#comment").change(function () {
        setField("comment", $("#comment").val());
    });

    $("#picturePathFirst").change(function () {
        if ($("#picturePath").val() != "") {
            setField("picturePathFirst", getPictureColor($("#picturePathFirst").val(), picPathList).path);
            showPicture("#picturePathFirstSrc", getPictureColor($("#picturePathFirst").val(), picPathList).path);
        } else {
            setField("picturePathFirst", "");
            showPicture("#picturePathFirstSrc", "");
        }
    });

    $("#sketchPathFirst").change(function () {
        if ($("#picturePath").val() != "") {
            setField("sketchPathFirst", getPictureColor($("#sketchPathFirst").val(), sketchPathList).path);
            showPicture("#sketchPathFirstSrc", getPictureColor($("#sketchPathFirst").val(), sketchPathList).path);
        } else {
            setField("sketchPathFirst", "");
            showPicture("#sketchPathFirstSrc", "");
        }
    });

    $("#price").change(function () {
        setField("price", $("#price").val());
    });

    $("#priceComit").change(function () {
        setField("priceComit", $("#priceComit").val());
    });

    $("#save").on("click", function () {
        var furniture = JSON.stringify(furnitureJavaObject);

        $.ajax({
            url: "item",
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: furniture,
            success: function (data) {
                if (data.success) {
                    toFurnirure();
                } else {
                    alert(data.message);
                }
            },
            error: function (data) {
                alert("!ERROR: елемнет записать не удалось:");
            },
        });
    });

    $("#close").on("click", function () {
        toFurnirure();
    });

    $("#delete").on("click", function () {
        $.ajax({
            url: "" + getIdFromUrl(),
            method: "DELETE",
            dataType: "json",
            success: function (data) {
                alert(data.status);
                toFurnirure();
            },
            error: function (data) {
                alert("!ERROR: елемнет удалить не удалось:");
            },
        });
    });

    function toFurnirure() {
        location.pathname = "furniture";
    }

    function getFurnitureJavaObject() {
        $.ajax({
            url: "types",
            dataType: "json",
            success: function (data) {
                types = data;
                fillInTypes();
            },
            error: function (data) {
                alert("!ERROR: типы фурнитуры получить не удалось:");
            },
        });

        $.ajax({
            url: "item/" + getIdFromUrl(),
            dataType: "json",
            success: function (data) {
                furnitureJavaObject = data;
                fillInFurniture();
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

    function fillInFurniture() {
        if (furnitureJavaObject != null) {
            $("#idFurniture").val(furnitureJavaObject.id);
            $("#idManufacturFurniture").val(
                furnitureJavaObject.idManufacturerProgram
            );
            $("#nameFurniture").val(furnitureJavaObject.name);
            setValueInSelect("#typeOfFurniture", furnitureJavaObject.typeOfFurniture);
            if (furnitureJavaObject.typeOfFurniture) {
                let responce = getPicPathFromServer();
                responce = getSketchPathFromServer();
            }
            setCheckBox("#itCylinderLock", furnitureJavaObject.itCylinderLock);
            $("#comment").val(furnitureJavaObject.comment);

            $("#price").val(furnitureJavaObject.price);
            $("#priceComit").val(furnitureJavaObject.priceComit);

            setValueInSelect("#picturePathFirst", getPictureColorPath(
                furnitureJavaObject.picturePathFirst,
                picPathList).id);
            setValueInSelect("#sketchPathFirst", getPictureColorPath(
                furnitureJavaObject.sketchPathFirst,
                sketchPathList).id);

            showPicture("#sketchPathFirstSrc", furnitureJavaObject.sketchPathFirst);
            showPicture("#picturePathFirstSrc", furnitureJavaObject.picturePathFirst);
        }
    }

    function fillInTypes() {
        if (types != null) {
            $("#typeOfFurniture").empty();

            $("#typeOfFurniture").append($("<option></option>"));

            for (var i = 0; i < types.length; ++i) {
                $("#typeOfFurniture").append(
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

    function setField(fieldName, value) {
        furnitureJavaObject[fieldName] = value;
    }

    function setCheckBox(name, value) {
        if (value == 1) {
            $(name).prop("checked", true);
        }
    }

    function showPicture(divSelect, value) {
        $(divSelect).attr("src", "../" + value);
    }

    function getPicPathFromServer() {
        let type = furnitureJavaObject.typeOfFurniture;
        if (type) {
            return $.ajax({
                url: "/furniture/pic/path/" + type,
                dataType: "json",
                async: false,
                success: function (data) {
                    picPathList = data;
                    fillInSelector("#picturePathFirst", data);
                    return data;
                },
                error: function (data) {
                    alert("!ERROR: список картинок получить не удалось:");
                },
            });
        }
    }

    function getSketchPathFromServer() {
        let type = furnitureJavaObject.typeOfFurniture;
        if (type) {
            return $.ajax({
                url: "/furniture/sketch/path/" + type,
                dataType: "json",
                async: false,
                success: function (data) {
                    sketchPathList = data;
                    fillInSelector("#sketchPathFirst", data);
                    return data;
                },
                error: function (data) {
                    alert("!ERROR: список картинок получить не удалось:");
                },
            });
        }
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

        }
    }

    function getPictureColor(idPicture, picturesList) {
        for (let i = 0; i < picturesList.length; i++) {
            if (picturesList[i].id == idPicture) {
                return picturesList[i];
            }
        }
    }

    function getPictureColorPath(pathPicture, list) {
        if (list) {
            for (let i = 0; i < list.length; i++) {
                if (list[i].path == pathPicture) {
                    return list[i];
                }
            }
        }
        return {id: 0};
    }
});
