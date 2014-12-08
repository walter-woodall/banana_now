package controllers;

import models.Category;
import models.Product;
import models.Store;
import play.*;
import play.mvc.*;

import views.html.*;
import static play.data.Form.*;

public class Application extends Controller {

    public static Result index() {

        return ok(index.render(
                Product.getTopProducts(),
                Category.getAllCategories()
        ));
    }

}
