jQuery("document").ready(function () {

    var javaObject;
    var typeImageList;
    let javaObjectNames;

    //new instance
    getTypeList();
    getJavaObject();

    $("#name").change(function () {
        setField("name", $("#name").val());
    });

    $("#typeOfImage").change(function () {

        InputBuilder.empty("div-for-value");
        const type = $("#typeOfImage").val();
        setField("type", type);
        if (type != "") {
            let responce = getFieldNamesFromServer();
            fillAllField();
        }
    });

    $("#div-for-value").change(".form-control", function () {
        SetAllField();
    });

    $("#save").on("click", function () {

        let res = sendJavaObjectToServer();
        let id = res.responseJSON.model.id;

        if (javaObject.id == 0) {
            toPage(id);
        } else {
            toList();
        }
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

    function getFieldNamesFromServer() {
        return $.ajax({
            url: location.origin + "/glass-position/types/value-names/" + javaObject.type,
            dataType: "json",
            async: false,
            success: function (data) {
                javaObjectNames = data;
                return data;
            },
            error: function (data) {
                alert("!ERROR: имена позиций получить не удалось:");
            },
        });
    }

    function toList() {
        location.pathname = "/pages/glass-position";
    }

    function toPage(id) {
        location.pathname = "/pages/glass-position/" + id;
    }

    function getJavaObject() {
        $.ajax({
            url: "/glass-position/" + getIdFromUrl(),
            dataType: "json",
            success: function (data) {
                javaObject = data.model;
                fillByOject();
            },
            error: function (data) {
                alert("!ERROR: main image получить не удалось:");
            },
        });
    }

    function getIdFromUrl() {
        var url = location.href;
        var id = url.substring(url.lastIndexOf("/") + 1);
        return id;
    }

    function fillByOject() {
        if (javaObject != null) {

            if (javaObject.type != null) {
                let responce = getFieldNamesFromServer();
            }

            $("#id").val(javaObject.id);
            $("#name").val(javaObject.name);

            setValueInSelect("#typeOfImage", javaObject.type);
            fillAllField();
        }
    }

    function fillAllField() {
        for (let key in javaObject) {
            if (key !== "id" && key !== "name" && key !== "type") {
                InputBuilder.build("div-for-value", key, javaObjectNames[key], javaObject[key]);
            }
        }
    }

    function SetAllField() {
        for (let key in javaObject) {
            if (key !== "id" && key !== "name" && key !== "type") {
                let val = $("#" + key).val();
                setField(key, val);
            }
        }
    }

    function setField(fieldName, value) {
        javaObject[fieldName] = value;
    }

    function getTypeList() {
        $.ajax({
            url: location.origin + "/glass-position/types",
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

    function setValueInSelect(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }

    function sendJavaObjectToServer() {
        let furniture = JSON.stringify(javaObject);

        return $.ajax({
            url: "/glass-positions",
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: furniture,
            async: false,
            success: function (data) {
                return data;
            },
            error: function (data) {
                alert("!ERROR: елемнет записать не удалось:");
            },
        });
    }

});

