class TypePages {
    static init(nameClass) {
    }

    static generate(tab, bias, idMasterDiv) {

        $(".toolbarTypeButton").remove();

        if (tab.length > 0) {
            for (let i = 0; i < tab.length; ++i) {
                $("<button>")
                    .attr("type", "button")
                    .attr("class", "btn btn-outline-dark toolbarTypeButton")
                    .attr("data", tab[i])
                    .text(tab[i])
                    .appendTo("#" + idMasterDiv);
            }
        }
        currentColorType = tab[0];
        return tab[0];

    }

    static show (){
        $("#toolbarTypeDiv").removeClass("ghost");
    }

    static hide (){
        $("#toolbarTypeDiv").addClass("ghost");
    }

}