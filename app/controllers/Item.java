package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Customer;
import models.Product;
import models.ShoppingCart;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by walterwoodall on 12/8/14.
 */
@Security.Authenticated(Secured.class)
public class Item extends Controller {

    public static Result addToCart(){
        Customer currCustomer = Customer.find.where().eq("email", request().username()).findUnique();
        JsonNode json = request().body().asJson();
        int quantity = json.get("quantity").asInt();
        String productId = json.get("id").asText();
        Product product = Product.find.byId(productId);
        ShoppingCart cart = new ShoppingCart(currCustomer, product, quantity);
        cart.save();
        currCustomer.shoppingCartList.add(cart);
        return ok();
    }


}
