class PanelBuilder {
    static build() {
        const response = PanelHttpService.getPanel();
        MainSidePanel.draw(response.responseJSON);
    }
}

class PanelHttpService {
    static getPanel() {
        return $.ajax({
            url: location.origin + '/users/ui/panel',
            dataType: "json",
            async: false,
            success: function (data) {
                return data;
            },
            error: function (data) {
                alert("error: getting the door failed !");
            },
        });
    }
}

class Select {
    static id(id) {
        return "#" + id;
    }
}

class MainSidePanel {

    static id = 'mainSidePanel';
    static divId = 'sidePanelDiv';
    static ulId = 'sidePanelUl';

    static draw(panel) {

        //div
        $("<div>")
            .attr("class", "sidebar-sticky")
            .attr("id", MainSidePanel.divId)
            .appendTo(Select.id(MainSidePanel.id));

        //ul
        $("<ul>")
            .attr("class", "nav flex-column")
            .attr("id", MainSidePanel.ulId)
            .appendTo(Select.id(MainSidePanel.divId));


        panel.groups.forEach(function (item, i, arr) {
            PanelGroup.draw(item, MainSidePanel.ulId, i);
        })
    }


}

class PanelGroup {
    static draw(item, block, index) {

        const groupId = 'panel_group' + index;

        $("<h6>")
            .attr("class", "sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted")
            .attr("id", groupId)
            .appendTo(Select.id(block));

        $("<span>")
            .text(item.name)
            .appendTo(Select.id(groupId));

        item.items.forEach(function (item, i, arr) {
            PanelItem.draw(item, block, index, i);
        })
    };
}

class PanelItem {
    static draw(item, block, mainIndex, index) {

        const itemId = 'item' + mainIndex + '_' + index;
        $("<li>")
            .attr("class", "nav-item")
            .attr("id", itemId)
            .appendTo(Select.id(block));

        $("<a>")
            .attr("class", "nav-link active")
            .attr("href", item.link)
            .text(item.name)
            .appendTo(Select.id(itemId));
    };
}


