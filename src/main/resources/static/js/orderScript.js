let orderDiscountList = [];
let order;

jQuery("document").ready(function () {

    //getInstans order
    let orderId = getOrderIdFromDom();
    let currentDoorId = 0;

    let buttonsManager = new ButtonsManager(
        'different_availability', 'CALC',
        'ready_availability', 'CLOSED');

    getOrderDiscount();
    getOrder();

    $("#saveOrder").on("click", function () {
        saveOrder(0, 0);
        saveOrderDiscount(0, 0);
    });

    $("#toСlose").on("click", function () {
        toClose();
    });

    $("#saveOrderAndShutDown").on("click", function () {
        saveOrder(0, 1);
        saveOrderDiscount(0, 1);
    });

    $("#addDoor").on("click", function () {
        if (orderId == "0") {
            saveOrder(1, 0);
            saveOrderDiscount(1, 1);
        } else {
            addDoor(orderId);
        }
    });

    $("#toChange").on("click", function () {
        changeDoor();
    });

    $("#delete").on("click", function () {
        deleteDoor();
        deleteIfExist();
    });

    $("#deleteOrder").on("click", function () {
        deletOrder();
    });

    $("#print").on("click", function () {
        printOrder();
    });

    $("#printDoors").on("click", function () {
        printDoors();
    });

    $("#factoryOder").on("click", function () {
        $(".order_button_div").addClass("ghost");
        $(".orderToFactory_button_div").removeClass("ghost");
        $("#totalProfit").removeClass("ghost");
        setOrderNumberToHeader("Заказ на завод", order.sellerOrderId);
        fillOutOfTheObjectToFactory();
    });

    $("#backToOrder").on("click", function () {

        backToOrder();
    });

    $("#status").change(function () {
        order.status = $(this).val();
        buttonsManager.turnOn(order.status);
    });

    LineEditor.init();

    $("tbody").on("click", "tr", function () {
        currentDoorId = $(this).children(".id").text();
        oneEnableAllDisable(this);
    });

    $("tbody").on("dblclick", "td", function () {
        if (!$(this).hasClass("vary_field")) {
            changeDoor();
        }
    });

    $("#goToWork").on("click", function () {
        order.status = "TO_WORK";
        fillStatus();

        backToOrder();

        saveOrder(0, 0);
        saveOrderDiscount(0, 0);
    });

    function getOrderDiscount() {
        $.ajax({
            url: location.origin + "/orderDiscounts/",
            data: {orderId: orderId},
            dataType: "json",
            success: function (data) {
                orderDiscountList = [];
                orderDiscountList = data;
            },
            error: function (data) {
                alert("!! error: не удалось получить скидки (");
            },
        });
    }

    function getOrder() {
        $.ajax({
            url: location.origin + '/orders/' + orderId,
            dataType: "json",
            success: function (data) {
                order = data;
                setOrderNumberToHeader("Заказ", order.sellerOrderId);
                fillOutOfTheObject();

                buttonsManager.turnOff(order.status);
            },
            error: function (data) {
                alert("!! error: данные заказа получить не удалось (");
            },
        });
    }

    function fillObject() {
        order.order_id = $("#order_id").html();
        order.company = $("#company").val();
        order.partner = $("#partner").val();
        order.data = $("#data").val();
        order.releasDate = $("#releasDate").val();
        order.productionStart = $("#productionStart").val();
        //order.seller  = $('#seller').val();
        order.comment = $("#comment").val();

        order.doors.forEach(function (item, i, arr) {
            item.quantity = $("#" + item.id).text();
        })
        order.totalAmount = $("#total").text();
    }

    function fillOutOfTheObject() {
        $("#company").val(order.company);
        $("#partner").val(order.partner);
        $("#data").val(order.data);
        $("#releasDate").val(order.releasDate);
        $("#productionStart").val(order.productionStart);
        $("#comment").val(order.comment);
        $("#total").text(order.totalAmount);

        fillStatus();

        $("tr").remove();
        $(".Table > tbody").append(
            "<tr>" +
            "<th>#</th>" +
            "<th>id</th><th>" +
            "наименование</th><th>" +
            "кол-во,шт</th><th>" +
            "металл,мм</th><th>" +
            "цвет</th><th>" +
            "цена,руб</th><th>" +
            "скидка,%</th><th>" +
            "сумма,руб</th></tr>"
        );

        var doors = order.doors;
        var position = 1;
        for (var j = 0; j < doors.length; ++j) {
            $(".Table > tbody").append(
                "<tr class='edit_line'>" +
                '<td class="position">' +
                position +
                "</td>" +
                '<td class="id">' +
                doors[j].id +
                "</td>" +
                "<td>" +
                doors[j].name +
                "</td>" +
                '<td class="vary_field text_input quantity_line" id=' + doors[j].id + '>' +
                doors[j].quantity +
                "</td>" +
                "<td>" +
                doors[j].metal +
                "</td>" +
                "<td>" +
                doors[j].doorColor +
                "</td>" +
                '<td class="price_line">' +
                doors[j].priceWithMarkup +
                "</td>" +
                '<td class="vary_field text_input discount_line">' +
                Discounts.findPercent(doors[j]) +
                "</td>" +
                '<td class="total_line">' +
                (doors[j].priceWithMarkup - Discounts.findMoney(doors[j])) * doors[j].quantity +
                "</td>" +
                "</tr>"
            );
            position++;
        }
    }

    function fillOutOfTheObjectToFactory() {
        $("tr").remove();
        $(".Table > tbody").append(
            "<tr><th>" +
            "id</th><th>" +
            "Наименование</th><th>" +
            "Кол-во</th><th>" +
            "Цена завода</th><th>" +
            "Наценка</th><th>" +
            "Цена с наценкой</th></tr>"
        );

        let doors = order.doors;
        let totalAmount = 0;
        let totalProfit = 0;
        for (var j = 0; j < doors.length; ++j) {
            totalAmount = totalAmount + doors[j].discountPrice;
            const priceWithMarkup = (doors[j].priceWithMarkup - Discounts.findMoney(doors[j]));
            const ProfitByDoor = (priceWithMarkup - doors[j].discountPrice);
            totalProfit = totalProfit + (ProfitByDoor * doors[j].quantity);
            $(".Table > tbody").append(
                '<tr><td class="id">' +
                doors[j].id +
                "</td><td>" +
                doors[j].name +
                "</td><td>" +
                doors[j].quantity +
                "</td><td>" +
                doors[j].discountPrice +
                "</td><td>" +
                ProfitByDoor +
                "</td><td>" +
                priceWithMarkup +
                "</td></tr>"
            );
        }

        $("#total").text(totalAmount);
        $("#totalProfit").text(totalProfit);
    }

    function addDoor(orderId) {
        location.href = location.origin + "/doors/0/page?orderId=" + orderId;
    }

    function saveOrder(add, close) {
        fillObject();

        let strJSON = JSON.stringify(order);

        $.ajax({
            type: "POST",
            url: location.origin + "/orders",
            contentType: "application/json",
            data: strJSON,
            dataType: "json",
            success: function (data) {
                if (!data.success) {
                    alert(data.message);
                } else {
                    if (data.model != null) {
                        $("#order_id").text(data.model.orderId);
                        orderId = $("#order_id").text();
                    }
                    if (add == 1) {
                        addDoor(orderId);
                    } else {
                        toOrder();
                    }
                    if (close == 1) {
                        toClose();
                    }

                }
            },
            error: function (data) {
                alert("error: записать не удалось:(");
            },
        });
    }

    function saveOrderDiscount(add, close) {
        var strJSON = JSON.stringify(orderDiscountList);

        $.ajax({
            type: "POST",
            url: location.origin + "/orderDiscount",
            contentType: "application/json",
            data: strJSON,
            dataType: "json",
            success: function (data) {
                orderDiscountList = [];
                orderDiscountList = data;
            },
            error: function (data) {
                alert("error: даписать не удалось:(");
            },
        });
    }

    function toClose() {
        location.pathname = "/pages/orders";
    }

    function toOrder() {
        location.pathname = "/pages/orders/" + orderId;
    }

    function oneEnableAllDisable(item) {
        var elems = $('tr[pickOut="on"]');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr("pickOut", "off");
        }
        $(item).attr("pickOut", "on");
    }

    function changeDoor() {
        location.href = location.origin + '/doors/' + currentDoorId + "/page?orderId=" + orderId;
    }

    function deleteIfExist() {
        orderDiscountList.forEach(function (item, i, arr) {
            if (item.order_id == orderId & item.door_id == currentDoorId) {
                deleteOrderDiscount(item.id);
                orderDiscountList.splice(i, 1);
                return;
            }
        })

    }

    function deleteOrderDiscount(orderDiscountId) {
        $.ajax({
            type: "DELETE",
            url: location.origin + "/orderDiscount/" + orderDiscountId,
            dataType: "json",
            success: function (data) {

            },
            error: function (data) {
                alert('delete error:' + data);
            },
        });
    }

    function deleteDoor() {
        $.ajax({
            type: "DELETE",
            url: location.origin + "/doors/" + currentDoorId + "?orderId=" + orderId,
            dataType: "json",
            success: function (data) {
                alert("delete completed" + data);
                order = data;
                fillOutOfTheObject();
            },
            error: function (data) {
                //alert('delete error:' + data);
            },
        });
    }

    function deletOrder() {
        $.ajax({
            type: "DELETE",
            url: location.origin + "/orders/" + orderId,
            dataType: "json",
            success: function (data) {
                alert("delete completed" + data);
                location.href = location.origin + "/pages/orders";
            },
            error: function (data) {
                alert("delete error:" + data);
            },
        });
    }

    function deleteOrderDiscountByOrderId() {
        // todo (Salagaev) что за URL такой интересный ?
        $.ajax({
            type: "DELETE",
            url: location.origin + "/deleteOrderDiscountByOrderId?orderId=" + orderId,
            dataType: "json",
            success: function (data) {
            },
            error: function (data) {
                alert("delete error:" + data);
            },
        });
    }

    function printOrder() {
        window.open(location.origin + "/print/order/" + orderId, '_blank');
    }

    function printDoors() {
        window.open(location.origin + "/print/page/doors/" + orderId, '_blank');
    }

    function setOrderNumberToHeader(name, namber) {
        $("#orderIdName").text(name + " №: " + namber);
    }

    function fillStatus() {
        if (order.statusList == null) {
            return;
        }
        $("#status").empty();
        for (var i = 0; i < order.statusList.length; i++) {
            $("#status").append(
                $(
                    "<option value=" +
                    order.statusList[i] +
                    ">" +
                    order.statusList[i] +
                    "</option>"
                )
            );
        }
        setValueInSelectInt("#status", order.status);
    }

    function setValueInSelectInt(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }

    function getOrderIdFromDom() {
        return $("#order_id").text();
    }

    function backToOrder() {
        $(".order_button_div").removeClass("ghost");
        $(".orderToFactory_button_div").addClass("ghost");
        $("#totalProfit").addClass("ghost");
        setOrderNumberToHeader("Заказ", order.sellerOrderId);
        fillOutOfTheObject();
    }
});

class Discounts {

    static findPercent(door) {
        let discount = orderDiscountList.find(item => (item.door_id == door.id & item.order_id == order.orderId));
        if (discount) {
            return discount.discount;
        } else {
            return 0;
        }
    }

    static findMoney(door) {
        const discount = Discounts.findPercent(door);
        return Math.floor(door.priceWithMarkup * discount / 100);
    }
}
