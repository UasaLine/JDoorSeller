class Enum {

    list;

    static init(resource) {
        const response = this.httpGet(resource)
        return new Enum(response.responseJSON);
    }

    constructor(data) {
        this.list = data;
    }

    name(type) {
        const result = this.list.filter(listItem => listItem.type === type);
        if (result.length > 0) {
            return result[0].name
        }
        return type;
    }

    static httpGet(resource) {
        return $.ajax({
            type: "GET",
            url: resource,
            dataType: "json",
            async: false,
            success: function (data) {
                return data;
            },
            error: function (data) {
                alert("delete error:" + data);
            },
        });
    }
}
