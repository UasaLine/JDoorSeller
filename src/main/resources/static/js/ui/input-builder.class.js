class InputBuilder {

    static build(parentId, id, name, value) {

        const rowId = "row_" + id;
        $("<div>")
            .attr("class", "row")
            .attr("id", rowId)
            .appendTo("#" + parentId);

        const colId = "col_" + id;
        $("<div>")
            .attr("class", "col disabled")
            .attr("id", colId)
            .appendTo("#" + rowId);

        $("<label>")
            .attr("for", "id")
            .text(name + ":")
            .appendTo("#" + colId);

        $("<input>")
            .attr("type", "text")
            .attr("class", "form-control")
            .attr("id", id)
            .attr("value", value)
            .appendTo("#" + colId);
    }

    static empty(parentId){
        $("#" + parentId).empty();
    }
}
