jQuery("document").ready(function () {

    let formulaJavaObject;
    let types;
    //new instance
    getFormulaJavaObject();


    $("#save").on("click", function () {
        let formulaJson = JSON.stringify(formulaJavaObject);

        $.ajax({
            url: "/materials/formulas",
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: formulaJson,
            success: function (data) {
                location.href = "/pages/materials/formulas/" + data.id;
            },
            error: function (data) {
                alert("!ERROR: елемнет записать не удалось:");
            },
        });
    });

    $("#close").on("click", function () {
        toList()
    });

    $("#delete").on("click", function () {
        $.ajax({
            type: "DELETE",
            url: "/materials/formulas/" + getIdFromUrl(),
            dataType: "json",
            success: function (data) {
                alert("delete completed - " + data.message);
                location.href = "/pages/materials/formulas";
            },
            error: function (data) {
                alert("delete error:" + data);
            },
        });
    });

    $("#nameFormula").change(function () {
        setField("name", $("#nameFormula").val());
    });

    $("#condition").change(function () {
        setField("condition1", $("#condition").val());
    });

    $("#formula").change(function () {
        setField("calculationFormula1", $("#formula").val());
    });

    $("#typeOfFormula").change(function () {

        let type = $("#typeOfFormula").val();

        $.ajax({
            url: "/materials/formulas/types/" + type + "/options",
            dataType: "json",
            success: function (data) {
                fillOption(data);
            },
            error: function (data) {
                alert("!ERROR: типы фурнитуры получить не удалось:");
            },
        });
    });

    function getFormulaJavaObject() {
        $.ajax({
            url: "/materials/formulas/types",
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
            url: "/materials/formulas/" + getIdFromUrl(),
            dataType: "json",
            success: function (data) {
                formulaJavaObject = data;
                fillByJavaObject(formulaJavaObject);
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

    function fillByJavaObject(javaObject) {
        if (javaObject != null) {
            $("#nameFormula").val(javaObject.name);
            $("#condition").val(javaObject.condition1);
            $("#formula").val(javaObject.calculationFormula1);
        }
    }

    function toList() {
        location.href = "/pages/materials/formulas";
    }

    function setField(fieldName, value) {
        formulaJavaObject[fieldName] = value;
    }

    function fillInTypes() {
        if (types != null) {
            $("#typeOfFormula").empty();

            $("#typeOfFormula").append($("<option></option>"));

            for (var i = 0; i < types.length; ++i) {
                $("#typeOfFormula").append(
                    $("<option value=" + types[i] + ">" + types[i] + "</option>")
                );
            }
        }
    }

    function fillOption(list) {
        $("<div>")
            .attr("class", "col-sm")
            .attr("id", "optionDiv1")
            .appendTo("#option");
        $("<ul>")
            .attr("class", "list-group list-group-flush")
            .attr("id", "optionUl1")
            .appendTo("#optionDiv1");

        list.forEach(function (item, i, arr) {
            $("<li>")
                .attr("class", "list-group-item list-group-item-secondary")
                .text(item)
                .appendTo("#optionUl1");
        })
    }
});
