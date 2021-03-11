jQuery("document").ready(function () {

    let lineSpecifications;
    let specificationEtity;
    let id = $("#id").text();
    let line_id = $("#line_id").text();
    let formulaList;
    let materialList;

    let response = getFormulas();
    response = getMaterials();
    getLineSpecification();

    $("#close").on("click", function () {
        getItem();
    });

    $("#name").change(function () {
        setField("name", $("#name").val());
    });

    $("#valueSpec").change(function () {
        setField("value", $("#valueSpec").val());
    });

    $("#formula").change(function () {
        setField("formula", fineInformulaList($("#formula").val()));
    });

    $("#material").change(function () {
        setFieldName($("#material").val());
        setField("materialId", $("#material").val());
    });

    $("#releaseOperation").change(function () {
        setField("releaseOperation", $("#releaseOperation").val());
    });

    $("#writeOffOperation").change(function () {
        setField("writeOffOperation", $("#writeOffOperation").val());
    });

    function getItem() {
        location.href = location.origin + "/pages/specifications/" + id;
    }

    function getFilterByIdScpecificationTypeFromUrl() {
        return id;
    }

    function getLineSpecification() {
        $.ajax({
            url: location.origin + "/specification/" + id + "/line/" + line_id,
            dataType: "json",
            success: function (data) {
                lineSpecifications = data;
                fillInLineSpecification();
            },
            error: function (data) {
                alert("!ERROR: данные о классах получить не удалось:" + data);
            },
        });
    }

    function getFormulas() {
        return $.ajax({
            url: location.origin + "/materials/formulas",
            dataType: "json",
            async: false,
            success: function (data) {
                formulaList = data;
                fillFormulaSelect(data);
                return data;
            },
            error: function (data) {
                alert("!ERROR: данные о формулах получить не удалось:" + data);
            },
        });
    }

    function getMaterials() {
        return $.ajax({
            url: location.origin + "/materials",
            dataType: "json",
            async: false,
            success: function (data) {
                materialList = data;
                fillMaterialSelect(data);
                return data;
            },
            error: function (data) {
                alert("!ERROR: данные о формулах получить не удалось:" + data);
            },
        });
    }

    $("#save").click(function () {

        var templateJSON = JSON.stringify(lineSpecifications);

        $.ajax({
            type: "POST",
            url: location.origin + "/specification/line/" + id,
            contentType: "application/json",
            data: templateJSON,
            dataType: "json",
            success: function (data) {
                //alert("request it OK");
                getItem();
            },
            error: function (data) {
                alert("error: request");
            },
        });
    });

    function deleteLineSpecification() {
        $.ajax({
            url: location.origin + "/specification/line/" + line_id,
            method: "DELETE",
            success: function (data) {
                getItem();
            },
            error: function (data) {
                alert("!ERROR: елемнет удалить не удалось:");
            },
        });
    }

    $("#delete").click(function () {
        if (line_id != "0") {
            deleteLineSpecification();
        }
    });

    function fillInLineSpecification() {
        $("#name").val(lineSpecifications.name);
        $("#valueSpec").val(lineSpecifications.value);
        setValueInSelect("#formula", lineSpecifications.formula ? lineSpecifications.formula.id : null);
        setValueInSelect("#material", lineSpecifications.materialId);
        $("#releaseOperation").val(lineSpecifications.releaseOperation);
        $("#writeOffOperation").val(lineSpecifications.writeOffOperation);
    }

    function setValueInSelect(jqSelect, value) {
        let option = $(jqSelect + " > option");
        option.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }

    function setField(fieldName, value) {
        lineSpecifications[fieldName] = value;
    }

    function fillFormulaSelect(list) {
        if (list != null) {
            $("#formula").empty();

            $("#formula").append($("<option></option>"));

            list.forEach(function (item, i, arr) {
                $("#formula").append(
                    $("<option value=" + item.id + ">" + item.name + "</option>")
                );
            });
        }
    }

    function fillMaterialSelect(list) {
        if (list != null) {
            $("#material").empty();

            $("#material").append($("<option></option>"));

            list.forEach(function (item, i, arr) {
                $("#material").append(
                    $("<option value=" + item.id + ">" + item.name + "</option>")
                );
            });
        }
    }

    function fineInformulaList(id) {
        return fineInListById(formulaList, id);
    }

    function fineInListById(list, id) {
        const filterList = list.filter(function (item) {
            return item.id == id;
        });

        if (filterList.length > 0) {
            return filterList[0];
        } else {
            alert('the selected position was not found');
            return null;
        }
    }

    function setFieldName(id) {
        const material = fineInListById(materialList, id);
        setField("name", material.name);
        $("#name").val(lineSpecifications.name);
    }
});

