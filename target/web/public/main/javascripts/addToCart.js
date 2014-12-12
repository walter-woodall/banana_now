$("#addSubmit").click(function(e) {

    var qty = $("#qty").val()
    var product = $("#productId").val()
    var json = new Object();
    json.productId = productId;
    json.quantity = qty;

    myJsRoutes.controllers.Item.addToCart().ajax({
        type : "POST",
        data : json,
        success : function(data){alert("Your Item was added to the Cart")};

    });
});