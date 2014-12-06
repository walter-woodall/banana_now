package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import static play.data.Form.form;

public class Application extends Controller {

    public static Result index() {

        return ok(index.render("Hellow World! Your App is working.Boom Done!"));
    }
}
