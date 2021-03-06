package controllers;

import models.Employee;
import models.Store;
import play.mvc.*;
import play.mvc.Http.*;

/**
 * Created by walterwoodall on 12/7/14.
 */
public class Secured extends Security.Authenticator{
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.login());
    }

    /*
    public static boolean isManagerOf(Store store){
        return Employee.isManager(store, Context.current().request().username());
    }
    */
}
