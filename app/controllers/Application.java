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

    public static Result signup(){
        return ok(
                signup.render(form(Signup.class))
        );
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

    public static Result doSignup() {
        Form<Signup> signUpForm = form(Signup.class).bindFromRequest();
        signUpForm.field("name");
        if (signUpForm.hasErrors()) {
            return badRequest(signup.render(signUpForm));
        } else {

        	String address1 = signUpForm.data().get("address1");
        	String address2 = signUpForm.data().get("address2");
        	String city = signUpForm.data().get("city");
        	String state = signUpForm.data().get("state");
        	Integer zipcode = Integer.valueOf(signUpForm.data().get("zipcode"));
        	Address address = new Address(address1,address2,city,state,zipcode);
            address.save();
            
            Customer c = new Customer(signUpForm.data().get("email"),signUpForm.data().get("password"),signUpForm.data().get("name"),address);
            
            c.save();
            
            SimpleDateFormat parserSDF=new SimpleDateFormat("mm / yy");
            java.util.Date expires = null;
			try {
				expires = parserSDF.parse(signUpForm.data().get("cc-expiry"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date expiry = new Date(expires.getTime());
			
			Long ccnumber = Long.valueOf(signUpForm.data().get("cc-number").replaceAll(" ", ""));
            int cvc = Integer.valueOf(signUpForm.data().get("cc-cvc"));
            String ccname = signUpForm.data().get("cc-name");
            CreditCard cc = new CreditCard(ccnumber, c, ccname, expiry, zipcode, cvc);
            cc.save();
            
        	flash("success", "thanks for signing up, " + signUpForm.data().get("name")+ " :)");
            return redirect(
                    routes.Application.login()
            );
        }
    }

    public static class Signup {
        public String name;
        public String password;
        public String email;
        public String address1;
        public String address2;
        public String city;
        public String state;
        public int zipcode;
        public CreditCard c;
        


        public String validate() {
            if (Customer.exists(email) != null) {
                return "Customer with this email already exists";
            }

            return null;
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
