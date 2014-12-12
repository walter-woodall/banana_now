package controllers;

import java.util.Arrays;
import java.util.List;

import models.Category;
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
    @Security.Authenticated(Secured.class)
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
    
    public static Result reroute(){
    	return redirect(routes.Application.index());
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
        if (Customer.authenticate(loginForm.field("email").value(), loginForm.field("password").value()) == null) {
            return badRequest(login.render(loginForm));
        }else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                    routes.Application.index()
            );
        }
    }
    
    @Security.Authenticated(Secured.class)
    public static Result search() {
        Form<Search> searchForm = form(Search.class).bindFromRequest();
        String search = searchForm.data().get("search");
        String section = searchForm.data().get("section");

        List<Product> productsReturned = Arrays.asList(new Product("No items found", new Float(0.0), "", "", "", "", Store.find.byId("1")));
    	if(section.equals("all")){
    		productsReturned = Product.find.where().contains("name", search).findList();
    	} else if(section.startsWith("subcat:")){
            section = section.replaceFirst("subcat:", "");    
            productsReturned = Product.find.where().eq("subcategory", section).contains("name", search).findList();
    	} else {
    		section = section.replaceFirst("cat:", "");    
    		productsReturned = Product.find.where().eq("category", section).contains("name", search).findList();
    	}
        
    	return ok(views.html.search.index.render(
        		productsReturned,
        Category.getAllCategories(Store.find.byId("1")),
        Category.getAllCategories(Store.find.byId("2")),
        Customer.find.where().eq("email", request().username()).findUnique(),
        section,
        search
        )); 
    }
    
    public static class Search {

    	public String section;
    	public String search;
    	
    	public String getSection() {
    		return section;
    	}
    	public void setSection(String section) {
    		this.section = section;
    	}
    	public String getSearch() {
    		return search;
    	}
    	public void setSearch(String search) {
    		this.search = search;
    	}
    	
        public String validate() {
            return null;
        }
        
    }

    public static Result javascriptRoutes(){
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("myJsRoutes",
                routes.javascript.Item.addToCart()

            )
        );
    }

    public static class Login{
        public String email;
        public String password;

        public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
            this.password = password;
        }

        public String validate() {
            if (Customer.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }
}
