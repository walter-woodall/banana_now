package controllers;

import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by walterwoodall on 12/8/14.
 */
@Security.Authenticated(Secured.class)
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

    public static Result checkout(Integer id){
        Double subtotal = 0.0;
        Double total = 0.0;
        Customer customer = Customer.find.byId(id.toString());
        Store walmart = Store.find.byId("1");
        Store amazon = Store.find.byId("2");

        if(!customer.email.equals(session().get("email"))){
            customer = Customer.find.where().eq("email", session().get("email")).findUnique();
        }

        for(ShoppingCart sp : customer.shoppingCartList){
            subtotal += (sp.product.price * sp.quantity);
        }
        total = (subtotal * 0.06) + subtotal;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return ok(views.html.cart.checkout.render(
                customer.shoppingCartList,
                Category.getAllCategories(walmart),
                Category.getAllCategories(amazon),
                customer,
                df.format(subtotal),
                df.format(total)
        ));
    }

    public static Result placeOrder(Integer id){
        DynamicForm requestForm = Form.form().bindFromRequest();
        Customer customer = Customer.find.byId(id.toString());

        if(!customer.email.equals(session().get("email"))){
            customer = Customer.find.where().eq("email", session().get("email")).findUnique();
        }
        SimpleDateFormat parserSDF=new SimpleDateFormat("yyyy-mm-dd");
        System.out.println(requestForm.data().get("date"));
        Date date = null;

        try {
            date = parserSDF.parse(requestForm.data().get("date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Basket b = new Basket(customer, date, requestForm.data().get("delivery-time"));
        b.save();
        for(ShoppingCart sp : customer.shoppingCartList){
            new BasketProduct(sp.product, b, sp.quantity).save();

        }

        customer.shoppingCartList.clear();
        customer.save();
        return redirect(routes.Application.index());
    }

}
