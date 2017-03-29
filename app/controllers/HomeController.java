package controllers;

import controllers.security.AuthAdmin;
import controllers.security.Secured;
import play.mvc.*;
import play.data.*;
import play.db.ebean.Transactional;
import play.api.Environment;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import views.html.*;


import models.*;
import models.users.*;


public class HomeController extends Controller {

    // Declare a private FormFactory instance
    private FormFactory formFactory;

    /**
     * http://stackoverflow.com/a/37024198
     **/
    private Environment env;

    //  Inject an instance of FormFactory it into the controller via its constructor
    @Inject
    public HomeController(FormFactory f, Environment e) {
        this.env = e;
        this.formFactory = f;
    }


    // Method retuns the logged in user (or null)
    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }

    public Result index() {
        List<Product> productsList = Product.findAll();
        return ok(index.render(productsList, getUserFromSession(), env));
    }

    public Result about() {

        return ok(about.render(getUserFromSession()));
    }

    public Result products(Long cat) {

        // Get list of all categories in ascending order
        List<Category> categoriesList = Category.findAll();
        List<Product> productsList = new ArrayList<Product>();

        if (cat == 0) {
            // Get list of all categories in ascending order
            productsList = Product.findAll();
        } else {
            // Get products for selected category
            // Find category first then extract products for that cat.
            productsList = Category.find.ref(cat).getProducts();
        }

        return ok(products.render(productsList, categoriesList, getUserFromSession(), env));
    }

    public Result charts() {

        List<Chart> chartsList = Chart.findAll();

        return ok(charts.render(chartsList, getUserFromSession()));
    }

    public Result contactUs() {

        return ok(contactUs.render(getUserFromSession()));
    }

    public Result editProfile() {

        return ok(editProfile.render(getUserFromSession()));
    }

    public Result signup() {
        Form<User> addUsersForm = formFactory.form(User.class);

        return ok(signup.render(addUsersForm));
    }

    @Transactional
    public Result signupSubmit() {

        Form<User> newUsersForm = formFactory.form(User.class).bindFromRequest();

        if (newUsersForm.hasErrors()) {
            return badRequest(signup.render(newUsersForm));
        }

        User newUser = newUsersForm.get();

        newUser.save();

        flash("success", "Account " + newUser.getName() + " has been created");

        return redirect(controllers.routes.HomeController.signup());
    }

}
