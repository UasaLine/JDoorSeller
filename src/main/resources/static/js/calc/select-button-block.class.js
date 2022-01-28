class SelectButtonBlock {

    static parentDivId;
    static idForShow;

    static init(parentDivId, idForShow) {
        SelectButtonBlock.parentDivId = parentDivId;
        SelectButtonBlock.idForShow = idForShow;
    }

    static build(list) {
        list.forEach(function (item) {
            Button.add(item.id, item.name);
        })
    }

    static delete() {
        $("#" + SelectButtonBlock.parentDivId).empty();
    }

    static show() {
        $("#" + SelectButtonBlock.idForShow).attr("show", "is_alive_lement");
    }

    static hide() {
        $("#" + SelectButtonBlock.idForShow).attr("show", "ghost_lement");
        SelectButtonBlock.delete();
    }
}

class Button {
    static add(id, name) {
        $("<div>")
            .attr("class", "vertical_menu_button")
            .attr("id", id)
            .appendTo("#" + SelectButtonBlock.parentDivId);

        let leftCellId = id + "left_cell";
        $("<div>")
            .attr("class", "left_cell")
            .attr("id", leftCellId)
            .appendTo("#" + id);

        $("<span>")
            .attr("class", "vertical_menu_button_left")
            .attr("id", "name" + leftCellId)
            .text(name)
            .appendTo("#" + leftCellId);

        let rightCellId = id + "right_cell";
        $("<div>")
            .attr("class", "right_cell")
            .attr("align", "right")
            .attr("id", rightCellId)
            .appendTo("#" + id);

        $("<span>")
            .attr("class", "vertical_menu_button_rigtht")
            .attr("id", rightCellId + "Show")
            .appendTo("#" + rightCellId);
    }
}
