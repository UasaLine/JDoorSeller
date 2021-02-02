class PaginationModels {

    static total;
    static limit = 10;
    static offset = 0;

    static init(total, limit, offset) {
        this.total = total;
        this.limit = limit;
        this.offset = offset;
    }


    static createPages(containerId) {

        this.deleteAllPage();

        const countPages = this.total / this.limit;

        for (let i = 0; i < countPages; i++) {
            this.addPageToContainer(i, containerId);
        }
    }

    static addPageToContainer(offset, containerId) {
        $("<button>")
            .attr("type", "button")
            .attr("class", "btn btn-light dynamic_page")
            .attr("data", offset)
            .text(offset + 1)
            .appendTo("#" + containerId);
    }

    static deleteAllPage() {
        $('.dynamic_page').remove();
    }
}
