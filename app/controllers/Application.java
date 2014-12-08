package controllers;

import models.Category;
import models.Customer;
import models.Product;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import static play.data.Form.*;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {

        return ok(index.render(
                Product.getTopProducts(),
                Category.getAllCategories(),
                Customer.find.where().eq("email", request().username()).findUnique()
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
        } else {
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
