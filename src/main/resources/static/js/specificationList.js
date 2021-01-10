jQuery("document").ready(function () {

    $("tr").on("click", function () {
        curreiId = $(this).children(".id").text();
        oneEnableAllDisable(this);
    });

    $("#deletLine").on("click", function () {
        if (curreiId !== 0) {
            $.ajax({
                url: "specification/" + curreiId,
                method: "DELETE",
                dataType: "json",
                success: function (data) {

                        location.pathname = "/specificationList";


                },
                error: function (data) {
                    alert("!ERROR: елемнет удалить не удалось:");
                },
            });
        } else {
            alert("select lines");
        }
    });

    $("tr").on("dblclick", function () {
        getItem($(this).children(".id").text());
    });

    $("#addLine").click(function () {
        location.pathname = "/specification/0";
    });

    $("#deletLine").click(function () {
        $("#line" + currentLine).remove();
    });

    function getItem(id) {
        location.href = "specification/" + id;
    }

    function oneEnableAllDisable(item) {
        var elems = $('tr[pickOut="on"]');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr("pickOut", "off");
        }
        $(item).attr("pickOut", "on");
    }


});