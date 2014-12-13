package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Customer;
import models.Product;
import models.ShoppingCart;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import static play.data.Form.form;

/**
 * Created by walterwoodall on 12/8/14.
 */
@Security.Authenticated(Secured.class)
public class Item extends Controller {

    public static class CartItem{
        public String productId;
        public String quantity;

    }

    public static Result addToCart(){
        DynamicForm requestData = Form.form().bindFromRequest();
        String productId = requestData.data().get("id");
        int quantity = Integer.valueOf(requestData.data().get("qty"));
        Customer currCustomer = Customer.find.where().eq("email", session().get("email")).findUnique();
        Product product = Product.find.byId(productId);
        ShoppingCart cart = new ShoppingCart(currCustomer, product, quantity);
        cart.save();
        currCustomer.shoppingCartList.add(cart);
        return redirect(request().getHeader("referer"));
    }



}
