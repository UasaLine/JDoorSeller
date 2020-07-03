jQuery("document").ready(function () {
  var JavaObject;
  var typeImageList;
  var typeDoorColorList;

  //new instans
  getTypeList();
  getJavaObject();

  $("#idManufacturerProgram").change(function () {
    setField("idManufacturerProgram", $("#idManufacturerProgram").val());
  });

  $("#name").change(function () {
    setField("name", $("#name").val());
  });

  $("#typeOfImage").change(function () {
    setField("typeOfImage", $("#typeOfImage").val());
  });

  $("#typeOfDoorColor").change(function () {
    setField("typeOfDoorColor", $("#typeOfDoorColor").val());
  });

  $("#smooth").change(function () {
    if ($(this).is(":checked")) {
      setField("smooth", 1);
    } else {
      setField("smooth", 0);
    }
  });

  $("#containsDesign").change(function () {
    if ($(this).is(":checked")) {
      setField("containsDesign", 1);
    } else {
      setField("containsDesign", 0);
    }
  });

  $("#picturePath").change(function () {
    setField("picturePath", $("#picturePath").val());
  });

  $("#pricePaintingMeterOfSpace").change(function () {
    setField(
      "pricePaintingMeterOfSpace",
      $("#pricePaintingMeterOfSpace").val()
    );
  });

  $("#save").on("click", function () {
    var furniture = JSON.stringify(JavaObject);

    $.ajax({
      url: "item",
      method: "PUT",
      dataType: "json",
      contentType: "application/json",
      data: furniture,
      success: function (data) {
        //alert(data.status);
        toList();
      },
      error: function (data) {
        alert("!ERROR: елемнет записать не удалось:");
      },
    });
  });

  $("#close").on("click", function () {
    toList();
  });

  $("#delete").on("click", function () {
    $.ajax({
      url: "" + getIdFromUrl(),
      method: "DELETE",
      dataType: "json",
      success: function (data) {
        alert(data.status);
        toList();
      },
      error: function (data) {
        alert("!ERROR: елемнет удалить не удалось:");
      },
    });
  });

  function toList() {
    location.pathname = "color";
  }

  function getJavaObject() {
    $.ajax({
      url: "item/" + getIdFromUrl(),
      dataType: "json",
      success: function (data) {
        JavaObject = data;
        fillByOject();
      },
      error: function (data) {
        alert("!ERROR: елемнет фурнитуры получить не удалось:");
      },
    });
  }

  function getIdFromUrl() {
    var url = location.href;
    var id = url.substring(url.lastIndexOf("/") + 1);
    return id;
  }

  function fillByOject() {
    if (JavaObject != null) {
      $("#id").val(JavaObject.id);
      $("#idManufacturerProgram").val(JavaObject.idManufacturerProgram);
      $("#name").val(JavaObject.name);
      setCheckBox("#smooth", JavaObject.smooth);
      setCheckBox("#containsDesign", JavaObject.containsDesign);
      $("#picturePath").val(JavaObject.picturePath);
      $("#pricePaintingMeterOfSpace").val(JavaObject.pricePaintingMeterOfSpace);
      setValueInSelect("#typeOfImage", JavaObject.typeOfImage);
      setValueInSelect("#typeOfDoorColor", JavaObject.typeOfDoorColor);
    }
  }

  function setField(fieldName, value) {
    JavaObject[fieldName] = value;
  }

  function setCheckBox(name, value) {
    if (value == 1) {
      $(name).prop("checked", true);
    }
  }

  function getTypeList() {
    $.ajax({
      url: location.origin + "/image/types",
      dataType: "json",
      success: function (data) {
        typeImageList = data;
        fillInTypes("#typeOfImage", typeImageList);
      },
      error: function (data) {
        alert("!ERROR: типы изображений получить не удалось:");
      },
    });
    $.ajax({
      url: location.origin + "/door-color/types",
      dataType: "json",
      success: function (data) {
        typeDoorColorList = data;
        fillInTypes("#typeOfDoorColor", typeDoorColorList);
      },
      error: function (data) {
        alert("!ERROR: типы дверных цветов получить не удалось:");
      },
    });
  }

  function fillInTypes(selectNmae, types) {
    if (types != null) {
      $(selectNmae).empty();

      $(selectNmae).append($("<option></option>"));

      for (var i = 0; i < types.length; ++i) {
        $(selectNmae).append(
          $("<option value=" + types[i] + ">" + types[i] + "</option>")
        );
      }
    }
  }

  function setValueInSelect(jqSelect, value) {
    var opt = $(jqSelect + " > option");
    opt.each(function (indx, element) {
      if ($(this).val() == value) {
        $(this).attr("selected", "selected");
      }
    });
  }
});
