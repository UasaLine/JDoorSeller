let orders;


jQuery("document").ready(function () {

    let currentId = 0;

    fillOrderList();

    $("table").on("dblclick", 'tr', function () {
        toOrder($(this).children(".id").text());
    });

    $("table").on("click", 'tr', function () {
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
        location.href = location.origin + "/pages/orders/0";
    });

    function toOrder(orderId) {
        location.pathname = '/pages/orders/' + orderId;
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

    function fillOrderList() {
        $.ajax({
            type: "GET",
            url: location.origin + '/orders',
            dataType: "json",
            success: function (data) {
                orders = data;
                orders.forEach(function (item, i, arr) {
                    addLine(item, i, arr);
                });
                displayAdminAndUserField();
                colorizeTheLines();
            },
            error: function (data) {
                alert("!!!заказы получить не удалось error:");
            },
        });
    }

    function addLine(order, i, arr) {
        $("<tr>")
            .attr('id', i)
            .appendTo('.Table');

        const line = '#' + i;

        addCell(order.sellerOrderId, 'seller_id', line);
        addCell(order.orderId, 'id ghost accessAdmin', line);
        addCell(order.status, 'colorFlag', line);
        addCell(order.partner, '', line);
        addCell(order.data, '', line);
        addCell(order.releasDate, '', line);
        addCell(order.totalQuantity, '', line);
        addCell(order.totalAmount, '', line);
    }

    function addCell(text, className, line) {
        $("<td>")
            .attr('class', className)
            .text(text)
            .appendTo(line);
    }
});
