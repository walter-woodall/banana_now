package controllers;

import models.Address;
import models.CreditCard;
import models.Customer;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.customers.signup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static play.data.Form.form;

/**
 * Created by walterwoodall on 12/9/14.
 */
public class User extends Controller {

    public static class Signup {
        public String name;
        public String password;
        public String email;
        public String address1;
        public String address2;
        public String city;
        public String state;
        public Integer zipcode;
        public Long ccNumber;
        public String ccName;
        public Date expiration;
        public Integer ccZipcode;
        public Integer cvc;

        public String validate() {
            if (Customer.find.where().eq("email", email).findUnique() != null) {
                return "This email already exists";
            }
            return null;
        }
    }

    public static Result signup(){
        return ok(
                signup.render(form(Signup.class))
        );
    }

    public static Result doSignup() {
        Form<Signup> signUpForm = form(Signup.class).bindFromRequest();
        if (signUpForm.hasErrors()) {
            return badRequest(signup.render(signUpForm));
        }else {

            String address1 = signUpForm.data().get("address1").toLowerCase();
            String address2 = signUpForm.data().get("address2").toLowerCase();
            String city = signUpForm.data().get("city").toLowerCase();
            String state = signUpForm.data().get("state").toLowerCase();
            Integer zipcode = Integer.valueOf(signUpForm.data().get("zipcode"));

            Address address = Address.find.where().eq("address1", address1)
                    .eq("address2", address2)
                    .eq("city", city)
                    .eq("state", state)
                    .eq("zipcode", zipcode).findUnique();
            if(address == null){
                address = new Address(address1, address2, city, state, zipcode);
                address.save();
            }

            Customer c = new Customer(signUpForm.data().get("email"),signUpForm.data().get("password"),
                    signUpForm.data().get("name"),address);
            c.save();

            SimpleDateFormat parserSDF=new SimpleDateFormat("mm/yy");
            java.util.Date expires = null;
            try {
                expires = parserSDF.parse(signUpForm.data().get("cc-expiration"));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Long ccnumber = Long.valueOf(signUpForm.data().get("cc-number").replaceAll(" ", ""));
            int cvc = Integer.valueOf(signUpForm.data().get("cc-cvc"));
            String ccname = signUpForm.data().get("cc-name");
            int cczip = Integer.valueOf(signUpForm.data().get("cc-zip"));


            CreditCard cc = new CreditCard(ccnumber, c, ccname, expires, cczip, cvc);
            cc.save();


            flash("success", "thanks for signing up, " + signUpForm.data().get("name")+ " :)");
            return redirect(
                    routes.Application.login()
            );
        }
    }
}
