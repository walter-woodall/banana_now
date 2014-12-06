package models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import play.test.WithApplication;

import java.util.LinkedList;

import static play.test.Helpers.fakeApplication;

/**
 * Created by walterwoodall on 12/4/14.
 */
public class ModelsTest extends WithApplication{

    @Before
    public void setUp(){
        start(fakeApplication());
    }


    public void createAndRetrieveCustomer(){
        Address address =  new Address("694 Pond Neck rd", "Earleville", "MD", 21919);
        address.save();
        new Customer("test@test.com", "pass", "Walter", address).save();
        Customer walter = Customer.find.where().eq("email", "test@test.com").findUnique();
        assertNotNull(address);
        assertEquals("MD", address.state);
    }
    //TODO
    public void createAndRetrieveCreditCard(){

    }
    //TODO
    public void createAndRetrieveStore(){

    }
    // TODO
    public void createAndRetrieveEmployee(){

    }
    @Test
    public void createOrder(){
        Customer customer = Customer.find.where().eq("email", "test@test.com").findUnique();
        Product p1 = Product.find.where().idEq(1325).findUnique();
        Product p2 = Product.find.where().idEq(1326).findUnique();
        new Basket(customer).save();
        Basket basket = Basket.find.where().eq("id", 1).findUnique();
        new Basket_Product(p1, basket, 2).save();
        new Basket_Product(p2, basket, 1).save();

    }


    public void tryAuthenticateUser() {
        Address address = Address.find.where().eq("address1", "694 Pond Neck rd").findUnique();
        new Customer("bob@gmail.com", "secret", "Bob", address).save();

        assertNotNull(Customer.authenticate("bob@gmail.com", "secret"));
        assertNull(Customer.authenticate("bob@gmail.com", "badpassword"));
        assertNull(Customer.authenticate("tom@gmail.com", "secret"));
    }
}
