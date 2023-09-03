// function editCartItem(cartItemId, newCount) {
//     window.location.href = "cart.do?operate=editCartItem&cartItemId=" + cartItemId + "&newCount=" + newCount;
// }

window.onload = function () {
    var vue = new Vue({
        el: "#cart_div",
        data: {
            cart: {}
        },
        methods: {
            getCartInfo: function () {
                axios ({
                    method: "POST",
                    url: "cart.do",
                    params: {
                        "operate": "getCartInfo"
                    }
                }).then(function (value) {
                    var cart = value.data;
                    vue.cart = cart;    // 這裡不能用 this.cart，因為現在是在 axios 裡面
                }).catch(function (reason) {

                })
            },
            editCartItem: function (cartItemId, newCount) {
                axios ({
                    method: "POST",
                    url: "cart.do",
                    params: {
                        "operate": "editCartItem",
                        "cartItemId": cartItemId,
                        "newCount": newCount
                    }
                }).then(function (value) {
                    vue.getCartInfo();
                }).catch(function (reason) {

                });
            }
        },
        mounted: function () {
            this.getCartInfo();
        }
    });
}