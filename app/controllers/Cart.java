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
        Customer customer = Customer.find.where().eq("email", request().username()).findUnique();
        Store walmart = Store.find.byId("1");
        Store amazon = Store.find.byId("2");
        System.out.println(customer.creditCardList);
        return ok(views.html.cart.index.render(
                customer.shoppingCartList,
                Category.getAllCategories(walmart),
                Category.getAllCategories(amazon),
                Customer.find.where().eq("email", request().username()).findUnique()));
    }

}
