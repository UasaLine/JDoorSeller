let classId;

jQuery("document").ready(function () {

    grtListDoorClass();

    if ($("#priceListInt").val() == 1) {
        $("#priceList").prop("checked", true);
        drawSettingPrice("#priceList");
    }

    $("#close").on("click", function () {
        location.href = "/pages/doortype";
    });

    $("#priceList").on("click", function () {
        drawSettingPrice(this);
    });

    $("#doorclassselect").change(function () {
        classId = $("#doorclassselect").val();
        $("#classId").attr('value', classId);
    });

    function drawSettingPrice(elem) {
        if ($(elem).is(":checked")) {
            $("#priceSettingByPrice").removeClass("ghost");
        } else {
            $("#priceSettingByPrice").addClass("ghost");
        }
    }

    function grtListDoorClass() {
        $.ajax({
            url: location.origin + "/classes",
            dataType: "json",
            success: function (data) {
                doorClassList = data;
                fillInDoorClass(doorClassList);
                setCurrentClassById();
            },
            error: function (data) {
                alert("!ERROR: данные о классах получить не удалось:");
            },
        });
    }

    function fillInDoorClass(listClass) {
        $("#doorclassselect").empty();

        $("#doorclassselect").append($("<option></option>"));

        for (var i = 0; i < listClass.length; ++i) {
            $("#doorclassselect").append(
                $(
                    "<option value=" +
                    listClass[i].id +
                    ">" +
                    listClass[i].name +
                    "</option>"
                )
            );
        }
    }

    function setCurrentClassById() {
        classId = $("#classId").val();
        if (classId != "0") {
            setValueInSelectInt("#doorclassselect", classId);
        }
    }

    function setValueInSelectInt(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }
});
