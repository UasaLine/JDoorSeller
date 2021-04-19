let orders;
let total;


jQuery("document").ready(function () {

    let currentId = 0;
    let urlParams = new ParamsFactory();
    urlParams.limit = 12;

    PanelBuilder.build();

    fillOrderList(urlParams.get());

    $("table").on("dblclick", 'tr', function () {
        toOrder($(this).children(".id").text());
    });

    $("table").on("click", 'tr', function () {
        currentId = $(this).children(".id").text();
        oneEnableAllDisable(this);
    });

    $("#buttonDeleteOrder").on("click", function () {

        if (currentId != 0) {
            $('#modal-id').show();
        } else {
            alert("!выбери заказ");
        }
    });

    $("#addOrder").on("click", function () {
        location.href = location.origin + "/pages/orders/0";
    });

    $(".sort-order").on("click", function () {
        urlParams.addSort($(this).attr("name"));
        fillOrderList(urlParams.get());
    });

    $("#page-bar").on("click", '.dynamic_page', function () {
        urlParams.offset = $(this).attr("data");
        fillOrderList(urlParams.get());
    });

    $("#modal_yes").on("click", function () {
        $('#modal-id').hide();
        deletOrder();
    });

    $("#modal_no").on("click", function () {
        $('#modal-id').hide();
    });

    $("#modal_close").on("click", function () {
        $('#modal-id').hide();
    });

    function toOrder(orderId) {
        location.pathname = '/pages/orders/' + orderId;
    }

    function deletOrder() {
        $.ajax({
            type: "DELETE",
            url: location.origin + "/orders/" + currentId,
            dataType: "json",
            success: function (data) {
                if (!data.success) {
                    alert(data.message);
                }
                location.href = "orders";
            },
            error: function (data) {
                alert("delete error: " + data);
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

    function fillOrderList(StringParamsUrl = "") {
        $.ajax({
            type: "GET",
            url: location.origin + '/orders' + StringParamsUrl,
            dataType: "json",
            success: function (data) {
                orders = data.list;
                total = data.total;
                deleteAllDynamicLine();
                orders.forEach(function (item, i, arr) {
                    addDynamicLine(item, i, arr);
                });
                displayAdminAndUserField();
                colorizeTheLines();
                PaginationModels.init(total, urlParams.limit, urlParams.offset);
                PaginationModels.createPages('page-bar');
            },
            error: function (data) {
                alert("!!!заказы получить не удалось error:");
            },
        });
    }

    function addDynamicLine(order, i, arr) {
        $("<tr>")
            .attr('id', i)
            .attr('class', 'dynamic_line')
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

    function deleteAllDynamicLine() {
        $('.dynamic_line').remove();
    }
});
