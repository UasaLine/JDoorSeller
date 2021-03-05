class PanelBuilder {
    static build() {
        const panel = PanelHttpService.getPanel();

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

class PanelDrawer {
    static gpoup
}
