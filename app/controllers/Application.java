package controllers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.Address;
import models.Category;
import models.CreditCard;
import models.Customer;
import models.Store;
import models.Product;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import static play.data.Form.*;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
        Store walmart = Store.find.byId("1");
        Store amazon = Store.find.byId("2");
        return ok(views.html.index.render(
                Product.getTopProducts(),
                Category.getAllCategories(walmart),
                Category.getAllCategories(amazon),
                Customer.find.where().eq("email", request().username()).findUnique()
        ));
    }

    public static Result getFood(String category, String subcategory){
        Store walmart = Store.find.byId("1");
        Store amazon = Store.find.byId("2");
        return ok(views.html.products.index.render(
                Product.find.where().eq("category", category).eq("subcategory", subcategory).findList(),
                Category.getAllCategories(walmart),
                Category.getAllCategories(amazon),
                Customer.find.where().eq("email", request().username()).findUnique(),
                category,
                subcategory
        ));
    }

    public static Result login(){
        return ok(
                login.render(form(Login.class))
        );
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        }else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                    routes.Application.index()
            );
        }
    }

    public static class Login{
        public String email;
        public String password;

        public String validate() {
            if (Customer.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }
}
