class Discounts {

    static orderDiscountList = [];
    static orderId;

    static init(orderId) {
        Discounts.orderId = orderId;
        return $.ajax({
            url: location.origin + "/orderDiscounts/",
            data: {orderId: orderId},
            dataType: "json",
            async: false,
            success: function (data) {
                Discounts.orderDiscountList = data;
            },
            error: function (data) {
                alert("!! error: не удалось получить скидки (");
            },
        });
    }

    static findPercent(door) {
        let discount = Discounts.orderDiscountList.find(item => (item.door_id == door.id & item.order_id == Discounts.orderId));
        if (discount) {
            return discount.discount;
        } else {
            return 0;
        }
    }

    static findMoney(door) {
        const discount = Discounts.findPercent(door);
        return Math.floor(door.priceWithMarkup * discount / 100);
    }
}
