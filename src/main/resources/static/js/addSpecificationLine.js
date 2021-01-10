jQuery("document").ready(function () {

    let lineSpecifications;
    let specificationEtity;
    getSpecificationEtity();

    $("#close").on("click", function () {
        getItem($(this).children(".id").text());
    });

    function getItem(id) {
        let filtrValue = getFilterByIdScpecificationTypeFromUrl();
        location.href = location.origin + "/specification/" + filtrValue;
        //location.pathname = "specification/" + filtrValue;
}

    function getFilterByIdScpecificationTypeFromUrl() {
        let searchParams = new URLSearchParams(location.search);
        if(searchParams.has('idSpecification')){
            return searchParams.get('idSpecification');
        }
        return "";
    }

    function getSpecificationEtity() {
        $.ajax({
            url: location.origin + "/specification/item/" + getFilterByIdScpecificationTypeFromUrl(),
            dataType: "json",
            success: function (data) {
                specificationEtity = data;
            },
            error: function (data) {
                alert("!ERROR: данные о классах получить не удалось:" + data);
            },
        });
    }

    $("#save").click(function () {

            lineSpecifications = (
                newInstansLineSpecification(
                    $("#name").val(),
                    $("#valueSpec").val(),
                    $("#formula").val(),

                )
            );

        var templateJSON = JSON.stringify(lineSpecifications);

        $.ajax({
            type: "POST",
            url: "line",
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

    function newInstansLineSpecification(name, valueSpec, formula) {
        var lim = new (function () {
            this.doorType = specificationEtity.doorType;
            this.materialId = "";
            this.name = name;
            this.value = parseFloat(valueSpec);
            this.formula = formula;
            this.specification = specificationEtity;
            //this.independentName = nameObject;

            // this.id = id;
            // this.doorType = specification.doorType;
            // this.materialId = materialId;
            // this.name = name;
            // this.value = parseFloat(value);
            // this.formula = formula;


        })();
        return lim;
    }

});

