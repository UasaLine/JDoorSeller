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

    save(add, close) {
        var strJSON = JSON.stringify(Discounts.orderDiscountList);

        $.ajax({
            type: "POST",
            url: location.origin + "/orderDiscount",
            contentType: "application/json",
            data: strJSON,
            dataType: "json",
            success: function (data) {
                Discounts.orderDiscountList = data;
            },
            error: function (data) {
                alert("error: даписать не удалось:(");
            },
        });
    }

    static findRubles(door){
        let discount = Discounts.orderDiscountList.find(item => (item.door_id == door.id & item.order_id == Discounts.orderId));
        if (discount) {
            return TranslationOfDiscounts.rubles(door.quantity, door.priceWithMarkup, discount.discount);
        } else {
            return 0;
        }
    }
}


class TranslationOfDiscounts {
    static percent(quantity, price, rubDiscount) {
        let summ = price * quantity;
        let percent = rubDiscount * 100 / summ;
        return percent;
    }

    static rubles(quantity, price, discount) {
        let summ = price * quantity;
        let rubDiscount = discount * summ / 100;
        return rubDiscount.toFixed(0);
    }
}
