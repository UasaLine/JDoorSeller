priceGroup = null;

jQuery("document").ready(function () {


    getAllPriceGroup();


    if ($("#enabled").text() == "1") {
        $("#enabledcheckbox").prop("checked", true);
    } else {
        $("#enabledcheckbox").prop("checked", false);
    }

    $("#close").on("click", function () {
        location.href = "users";
    });

    $("#userorder").on("click", function () {
        var userId = $("#userId").val();
        location.href = "orders?userId=" + userId;
    });


    function setPriceGroup() {
        let priceGroupFromJObject = $("#priceGroupsFromJObject").val();
        PageSelector.setValueInSelect("#PriceGroups", priceGroupFromJObject);
    }

    function getAllPriceGroup() {
        $.ajax({
            url: location.origin + "/doortype/price-group",
            dataType: "json",
            success: function (data) {
                priceGroup = data;
                PageSelector.fillInByStringList("#PriceGroups", priceGroup);
                setPriceGroup();
            },
            error: function (data) {
                alert("!ERROR: ценовые группы получить не удалось:");
            },
        });
    }

});
