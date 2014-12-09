package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by walterwoodall on 12/8/14.
 */
@Security.Authenticated(Secured.class)
public class Item extends Controller {

    public static Result add(){

        return null;
    }
}
