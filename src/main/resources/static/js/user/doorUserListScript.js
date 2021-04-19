jQuery("document").ready(function () {
    let curreiId = 0;

    PanelBuilder.build();

    $("tr").on("dblclick", function () {
        getUser($(this).children(".id").text());
    });

    $("tr").on("click", function () {
        curreiId = $(this).children(".id").text();
        oneEnableAllDisable(this);
    });

    $("#addClass").on("click", function () {
        getUser(0);
    });

    $("#buttonDelete").on("click", function () {
        if (curreiId != 0) {
            deleteUser();
        } else {
            alert("!выбери класс");
        }
    });

    function getUser(userId) {
        location.href = "user?userId=" + userId;
    }

    function oneEnableAllDisable(item) {
        var elems = $('tr[pickOut="on"]');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr("pickOut", "off");
        }
        $(item).attr("pickOut", "on");
    }

    function deleteUser() {
        $.ajax({
            type: "DELETE",
            url: "/users/" + curreiId,
            success: function (data) {
                alert("delete completed " + data);
                location.href = "/users";
            },
            error: function (data) {
                alert("delete error: " + data);
            },
        });
    }
});
