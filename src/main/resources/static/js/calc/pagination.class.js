class PaginationPage {

    static AMOUNT_ELEMENTS_IN_PAGE = 8;

    tabSize;
    amountElements;
    amountPag;
    biasInt;

    static init(nameClass) {
    }

    static generate(tab, bias, idMasterDiv) {
        let offsetTab = new PaginationPage();
        offsetTab.tabSize = tab.length;
        offsetTab.amountElements = PaginationPage.AMOUNT_ELEMENTS_IN_PAGE;
        offsetTab.amountPag = Math.ceil(
            offsetTab.tabSize / offsetTab.amountElements
        );
        offsetTab.biasInt = Number.parseInt(bias) * offsetTab.amountElements;

        $(".toolbarPageButton").remove();

        if (offsetTab.amountPag > 0) {
            for (let i = 0; i < offsetTab.amountPag; ++i) {
                $("<button>")
                    .attr("type", "button")
                    .attr("class", "btn btn-outline-dark toolbarPageButton")
                    .attr("data", i)
                    .text(i + 1)
                    .appendTo("#" + idMasterDiv);
            }
        }
        return offsetTab;
    }

    static generateType(tab, bias, idMasterDiv) {
        let offsetTab = new PaginationPage();
        offsetTab.tabSize = tab.length;

        offsetTab.type = [];
        let typeList = [];

        //offsetTab.amountElements = PaginationPage.AMOUNT_ELEMENTS_IN_PAGE;
        offsetTab.amountPag = Math.ceil(
            offsetTab.tabSize / offsetTab.amountElements
        );
        offsetTab.biasInt = Number.parseInt(bias) * offsetTab.amountElements;

        $(".toolbarPageButton").remove();

        if (offsetTab.amountPag > 0) {
            for (let i = 0; i < offsetTab.amountPag; ++i) {
                $("<button>")
                    .attr("type", "button")
                    .attr("class", "btn btn-outline-dark toolbarPageButton")
                    .attr("data", i)
                    .text("LUX" + i + 1)
                    .appendTo("#" + idMasterDiv);
            }
        }
        return offsetTab;
    }

    static show (){
        $("#toolbarPageDiv").removeClass("ghost");
    }

    static hide (){
        $("#toolbarPageDiv").addClass("ghost");
    }

}
