jQuery("document").ready(function () {
  var materialsList;
  var changed = false;
  var currentLine = 0;

  grtListDoorClassToSelect();

  $("#doorclassselect").change(function () {
    fillInDoorType(getDoorClassbyId($("#doorclassselect").val()));
  });

  $("#doortypeselect").change(function () {
    var doorTypeId = $("#doortypeselect").val();

    $.ajax({
      url: "specificationbyid",
      data: { typeId: doorTypeId },
      dataType: "json",
      success: function (data) {
        specification = data;
        availableValues = data.availableValues;
        lineSpecifications = data.lineSpecifications;

        fillTabLine();
        displayComponentVisibility();
      },
      error: function (data) {
        alert("!ERROR: данные шаблона получить не удалось:");
      },
    });
  });

  $("tbody").on("dblclick", ".vary_field", function (e) {
    var t = e.target || e.srcElement;

    var elm_name = t.tagName.toLowerCase();

    if (elm_name == "input") {
      return false;
    }

    addFieldInCell(this);

    changed = true;

    displayComponentVisibility();
  });

  $("tbody").on("click", "tr", function () {
    currentLine = $(this).children(".position").text();
    oneEnableAllDisable(this);
  });

  $(window).keydown(function (event) {
    if (event.keyCode == 13) {
      $("#edit").blur();
    }
  });

  $("#addLine").click(function () {
    if ($(this).hasClass("disabled")) {
      return;
    }

    addLine("", "newName", 0, "");
  });

  $("#deletLine").click(function () {
    if ($(this).hasClass("disabled")) {
      return;
    }

    $("#line" + currentLine).remove();

    displayComponentVisibility();
  });

  $("#saveSpec").click(function () {
    if ($(this).hasClass("disabled")) {
      return;
    }

    var size = materialsList.length;
    materialsList.splice(0, size);

    var lineArr = $(".line");
    for (var i = 0; i < lineArr.length; i++) {
      var indexLine = $(lineArr[i]).children(".position").text();
      var nameMaterial = $("#name" + indexLine).html();

      lineSpecifications.push(
        newInstansLineSpecification(
          $("#id" + indexLine).html(),
          0,
          nameMaterial,
          $("#value" + indexLine).html(),
          $("#formula" + indexLine).html()
        )
      );
    }

    var templateJSON = JSON.stringify(materialsList);

    $.ajax({
      type: "POST",
      url: "materials",
      contentType: "application/json",
      data: templateJSON,
      dataType: "json",
      success: function (data) {
        alert("request it OK");
      },
      error: function (data) {
        alert("error: request");
      },
    });
  });

  function addFieldInCell(jsOb) {
    if ($(jsOb).hasClass("text_select")) {
      addSelect(jsOb);
      $("#edit").focus();
      $("#edit").blur(function () {
        var val = $(this).val();
        var name = getNameSelectedValue(val);
        $(this).parent().empty().text(name).attr("val", val);
      });
    } else if ($(jsOb).hasClass("text_input")) {
      addInput(jsOb);
      $("#edit").focus();
      $("#edit").blur(function () {
        var val = $(this).val();
        $(this).parent().empty().text(val);
      });
    }
  }

  function addSelect(jsOb) {
    var val = $(jsOb).text();

    $(jsOb).empty();
    $("<select>")
      .attr("class", "form-control")
      .attr("id", "edit")
      .appendTo(jsOb);

    $("#edit").append($("<option></option>"));

    if (availableValues != null) {
      for (var i = 0; i < availableValues.length; i++) {
        $("#edit").append(
          $(
            "<option value=" +
              availableValues[i].id +
              ">" +
              availableValues[i].name +
              "</option>"
          )
        );
      }
      setValueInSelect("#edit", val);
    } else {
      if (val != "") {
        $("#edit").append($("<option>" + val + "</option>"));
        setValueInSelect("#edit", val);
      }
    }
  }

  function addInput(jsOb) {
    var val = $(jsOb).html();

    $(jsOb).empty();
    $("<input>")
      .attr("class", "form-control")
      .attr("type", "text")
      .attr("id", "edit")
      .appendTo(jsOb);

    $("#edit").val(val);
  }

  function grtListDoorClassToSelect() {
    $.ajax({
      url: "materialsList",
      dataType: "json",
      success: function (data) {
        materialsList = data;
        fillTabLine();
      },
      error: function (data) {
        alert("!ERROR: данные о классах получить не удалось:");
      },
    });
  }

  function getDoorClassbyId(id) {
    if (id == null && id == 0) {
      alert("error id is null!");
    }

    for (var i = 0; i < doorClassList.length; ++i) {
      if (doorClassList[i].id == id) {
        return doorClassList[i];
      }
    }
    alert("error doorClass no found!");
    return 0;
  }

  function getNextPosition() {
    var posElem = $(".position");
    if (posElem != null) {
      return posElem.length + 1;
    }
    return 0 + 1;
  }

  function setValueInSelect(jqSelect, value) {
    var opt = $(jqSelect + " > option");
    opt.each(function (indx, element) {
      if ($(this).text().toLowerCase() == value.toLowerCase()) {
        $(this).attr("selected", "selected");
      }
    });
  }

  function newInstansLineSpecification(id, materialId, name, value, formula) {
    var lim = new (function () {
      this.id = id;
      this.doorType = specification.doorType;
      this.materialId = materialId;
      this.name = name;
      this.value = parseFloat(value);
      this.formula = formula;
    })();
    return lim;
  }

  function getMaterialIdByName(val) {
    if (availableValues == null) {
      return 0;
    }

    for (var i = 0; i < availableValues.length; i++) {
      if (availableValues[i].name == val) {
        return availableValues[i].id;
      }
    }

    return 0;
  }

  function getNameSelectedValue(val) {
    if (availableValues == null) {
      return "";
    }

    for (var i = 0; i < availableValues.length; i++) {
      if (availableValues[i].id == val) {
        return availableValues[i].name;
      }
    }

    return "";
  }

  function fillTabLine() {
    if (materialsList == null) {
      return;
    }

    var sizelineSpec = materialsList.length;
    for (var i = 0; i < sizelineSpec; i++) {
      addLine(
        materialsList[i].id,
        materialsList[i].name,
        materialsList[i].idManufacturerProgram,
        materialsList[i].price
      );
    }
  }

  function addLine(id, newName, idManufacturerProgram, price) {
    var Position = getNextPosition();

    $(".Table > tbody").append(
      '<tr class="line newLine" id="line' +
        Position +
        '">' +
        '<td class="position">' +
        Position +
        "</td>" +
        '<td class="id" id="id' +
        Position +
        '">' +
        id +
        "</td>" +
        '<td class="vary_field text_input" id="name' +
        Position +
        '">' +
        newName +
        "</td>" +
        '<td class="vary_field text_input" id="value' +
        Position +
        '">' +
        idManufacturerProgram +
        "</td>" +
        '<td class="vary_field text_input" id="formula' +
        Position +
        '" >' +
        price +
        "</td>" +
        "</tr>"
    );

    if (id == "") {
      var elementPosition = $("#line" + Position).offset().top;
      $("body,html").animate({ scrollTop: elementPosition }, 500);
    }
  }

  function displayComponentVisibility() {
    if (materialsList != null) {
      $("#addLine").removeClass("disabled");
      $("#deletLine").removeClass("disabled");
    }
    if (changed) {
      $("#saveSpec").removeClass("disabled");
    }
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
