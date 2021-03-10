jQuery("document").ready(function () {

    let currentId = 0;

    PanelBuilder.build();

    $("tr").on("dblclick", function () {
        getFormula($(this).children(".id").text());
    });

    $("tr").on("click", function () {
        currentId = $(this).children(".id").text();
        oneEnableAllDisable(this);
    });


    $("#addLine").click(function () {
        getFormula(0);
    });

    $("#deleteLine").click(function () {
        deleteLine(currentId);
    });


    function oneEnableAllDisable(item) {
        var elems = $('tr[pickOut="on"]');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr("pickOut", "off");
        }
        $(item).attr("pickOut", "on");
    }

    function getFormula(id) {
        location.href = "/pages/materials/formulas/" + id;
    }

    function deleteLine(id) {
        $.ajax({
            type: "DELETE",
            url: "/materials/formulas/" + currentId,
            dataType: "json",
            success: function (data) {
                alert("delete completed - " + data.message);
                location.href = "/pages/materials/formulas";
            },
            error: function (data) {
                alert("delete error:" + data);
            },
        });
    }

});
