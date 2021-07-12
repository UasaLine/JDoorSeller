class TypePages {
    static init(nameClass) {
    }

    static generate(tab, bias, idMasterDiv) {

        $(".toolbarTypeButton").remove();

        if (tab.length > 0) {
            for (let i = 0; i < tab.length; ++i) {
                $("<button>")
                    .attr("type", "button")
                    .attr("class", "btn-margin btn btn-outline-dark toolbarTypeButton")
                    .attr("data", tab[i].type)
                    .text(tab[i].name)
                    .appendTo("#" + idMasterDiv);
            }
        }
        if (tab[0]){
            currentColorType = tab[0].type;
        }

    }

    static show (){
        $("#toolbarTypeDiv").removeClass("ghost");
    }

    static hide (){
        $("#toolbarTypeDiv").addClass("ghost");
    }

}
