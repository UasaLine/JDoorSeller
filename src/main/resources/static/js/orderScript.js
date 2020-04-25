jQuery("document").ready(function () {

  //getInstans order
  var orderId = $("#order_id").text();
  var currentDoorId = 0;
  var order;
  let buttonsManager = new ButtonsManager('different_availability','CALC');
  setOrderId("Заказ");


  $.ajax({
    url: "getOrder",
    data: { orderId: orderId },
    dataType: "json",
    success: function (data) {
      order = data;
      fillOutOfTheObject();

      buttonsManager.disabled(order.status);
    },
    error: function (data) {
      alert("error:" + data);
    },
  });

  $("#saveOrder").on("click", function () {
    saveOrder(0, 0);
  });

  $("#toСlose").on("click", function () {
    toClose();
  });

  $("#saveOrderAndShutDown").on("click", function () {
    saveOrder(0, 1);
  });

  $("#addDoor").on("click", function () {
    if (orderId == "0") {
      saveOrder(1, 0);
    } else {
      addDoor(orderId);
    }
  });

  $("#toChange").on("click", function () {
    changeDoor();
  });

  $("#delete").on("click", function () {
    deleteDoor();
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
    setOrderId("Заказ на завод");
    fillOutOfTheObjectToFactory();
  });

  $("#backToOrder").on("click", function () {
    $(".order_button_div").removeClass("ghost");
    $(".orderToFactory_button_div").addClass("ghost");
    $("#totalProfit").addClass("ghost");
    setOrderId("Заказ");
    fillOutOfTheObject();
  });

  $("#status").change(function () {
    order.status = $(this).val();
  });

  LineEditor.init();

  $("tbody").on("click", "tr", function () {
    currentDoorId = $(this).children(".id").text();
    oneEnableAllDisable(this);
  });

  $("tbody").on("dblclick", "td", function () {
    if (!$(this).hasClass("vary_field")){
      changeDoor();
    }
  });

  $("#goToWork").on("click", function () {
    order.status = "TO_WORK";
    fillStatus();
  });

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
      item.quantity = $("#"+item.id).text();
    })

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
        "кол-во</th><th>" +
        "металл</th><th>" +
        "цвет</th><th>" +
        "цена</th></tr>"
    );

    var doors = order.doors;
    var position = 1;
    for (var j = 0; j < doors.length; ++j) {
      $(".Table > tbody").append(
        "<tr>" +
          '<td class="position">' +
          position +
          "</td>" +
          '<td class="id">' +
          doors[j].id +
          "</td>" +
          "<td>" +
          doors[j].name +
          "</td>" +
          '<td class="vary_field text_input" id='+doors[j].id+'>' +
          doors[j].quantity +
          "</td>" +
          "<td>" +
          doors[j].metal +
          "</td>" +
          "<td>" +
          doors[j].doorColor +
          "</td>" +
          "<td>" +
          doors[j].priceWithMarkup +
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

    var doors = order.doors;
    var totalAmount = 0;
    var totalProfit = 0;
    for (var j = 0; j < doors.length; ++j) {
      totalAmount = totalAmount + doors[j].discountPrice;
      totalProfit =
        totalProfit + (doors[j].priceWithMarkup - doors[j].discountPrice);
      $(".Table > tbody").append(
        '<tr><td class="id">' +
          doors[j].id +
          "</td><td>" +
          doors[j].name +
          "</td><td>" +
          1 +
          "</td><td>" +
          doors[j].discountPrice +
          "</td><td>" +
          (doors[j].priceWithMarkup - doors[j].discountPrice) +
          "</td><td>" +
          doors[j].priceWithMarkup +
          "</td></tr>"
      );
    }

    $("#total").text(totalAmount);
    $("#totalProfit").text(totalProfit);
  }

  function addDoor(orderId) {
    location.href = "calculation?orderId=" + orderId;
  }

  function saveOrder(add, close) {
    fillObject();

    var strJSON = JSON.stringify(order);

    $.ajax({
      type: "POST",
      url: "order",
      contentType: "application/json",
      data: strJSON,
      dataType: "json",
      success: function (data) {
        $("#order_id").text(data.order_id);
        orderId = $("#order_id").text();
        if (add == 1) {
          addDoor(orderId);
        }
        if (close == 1) {
          toClose();
        }
      },
      error: function (data) {
        alert("error: даписать не удалось:(");
      },
    });
  }

  function toClose() {
    location.href = "orders";
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
    location.href = "calculation?orderId=" + orderId + "&id=" + currentDoorId;
  }

  function deleteDoor() {
    $.ajax({
      type: "DELETE",
      url: "door?id=" + currentDoorId + "&orderId=" + orderId,
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
      url: "order?orderId=" + orderId,
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

  function printOrder() {
    location.href = "orderprint?orderId=" + orderId;
  }

  function printDoors() {
    location.href = "doorsprint?orderId=" + orderId;
  }

  function setOrderId(name) {
    $("#orderIdName").text(name + " №: " + orderId);
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
});
