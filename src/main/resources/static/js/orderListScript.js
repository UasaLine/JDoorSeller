jQuery("document").ready(function () {

    let currentId = 0;

    displayAdminAndUserField();

    colorizeTheLines();

    $("tr").on("dblclick", function () {
        getOrder($(this).children(".id").text());
    });

    $("tr").on("click", function () {
        currentId = $(this).children(".id").text();
        oneEnableAllDisable(this);
    });

    $("#buttonDeleteOrder").on("click", function () {
        if (currentId != 0) {
            deletOrder();
        } else {
            alert("!выбери заказ");
        }
    });

    $("#addOrder").on("click", function () {
        location.href = "order";
    });

    function getOrder(orderId) {
        location.pathname  = '/orders/' + orderId + '/page';
    }

    function deletOrder() {
        $.ajax({
            type: "DELETE",
            url: "order?orderId=" + currentId,
            dataType: "json",
            success: function (data) {
                alert("delete completed" + data);
                location.href = "orders";
            },
            error: function (data) {
                alert("delete error:" + data);
            },
        });
    }

    function oneEnableAllDisable(item) {
        var elems = $('tr[pickOut="on"]');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr("pickOut", "off");
        }
        $(item).attr("pickOut", "on");
    }

    function displayAdminAndUserField() {
        const isAdmin = $("#isAdmin").text();
        if (isAdmin === "true") {
            var accessElem = $(".accessAdmin");
            for (var i = 0; i < accessElem.length; i++) {
                $(accessElem[i]).removeClass("ghost");
            }
        }

        var report = $("#report").text();
        if (report == "true") {
            $(buttonbar).addClass("ghost");
            $(buttonbar).removeClass("row");
        }
    }

    function colorizeTheLines() {
        var colorFlag = $(".colorFlag");
        for (var i = 0; i < colorFlag.length; i++) {
            if (
                "READY" == $(colorFlag[i]).text() ||
                "IN_WORK" == $(colorFlag[i]).text()
            ) {
                $(colorFlag[i]).addClass("greenFlag");
            }
        }
    }
});
