package controllers;

import models.Category;
import models.Customer;
import models.Store;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by walterwoodall on 12/8/14.
 */
public class Cart extends Controller {

    public static Result index(){
        Customer customer = Customer.find.where().eq("email", session().get("email")).findUnique();
        Store walmart = Store.find.byId("1");
        Store amazon = Store.find.byId("2");
        return ok(views.html.cart.index.render(
                customer.shoppingCartList,
                Category.getAllCategories(walmart),
                Category.getAllCategories(amazon),
                customer));
    }

    public static Result checkout(){

        return null;
    }

}
