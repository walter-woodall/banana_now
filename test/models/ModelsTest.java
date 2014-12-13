package models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import play.test.WithApplication;

import java.util.Date;
import java.util.List;

import static play.test.Helpers.fakeApplication;

/**
 * Created by walterwoodall on 12/4/14.
 */
public class ModelsTest extends WithApplication{

    @Before
    public void setUp(){
        start(fakeApplication());
    }

    /*
    PASSED
     */
    //@Test
    public void createAndRetrieveCustomer(){
        //Address address =  new Address("696 Pond Neck Rd", "Earleville", "MD", 21919);
        //address.save();
        //new Customer("test@b.com", "password", "Walter", address).save();
        Customer walter = Customer.find.where().eq("email", "test@b.com").findUnique();
        System.out.println(walter.address.address1);
        assertNotNull(walter.address);
        assertEquals("md", walter.address.state);
    }

    /*
    PASSED
     */
    //@Test
    public void tryAuthenticateUser() {
        Address address = Address.find.where().eq("address1", "694 Pond Neck rd").findUnique();
        new Customer("bob@gmail.com", "secret", "Bob", address).save();

        assertNotNull(Customer.authenticate("bob@gmail.com", "secret"));
        assertNull(Customer.authenticate("bob@gmail.com", "badpassword"));
        assertNull(Customer.authenticate("tom@gmail.com", "secret"));
    }

    /*
    PASSED
     */
    //@Test
    public void createAndRetrieveStore(){
        //Address walmartAddress = new Address("6210 Annapolis Rd", "Capital Plaza Mall", "Landover Hills", "MD", 20784);
        //walmartAddress.save();
        //new Store("Walmart", walmartAddress).save();
        Store walmart = Store.find.where().eq("name", "walmart").findUnique();
        assertNotNull(walmart);

    }

    /*
    PASSED
     */
    //@Test
    public void createAndRetrieveEmployee(){
        Store walmart = Store.find.where().eq("name", "walmart").findUnique();
        assertNotNull(walmart);
        new Employee("Leandra", 0, walmart).save();
        Employee leandra = Employee.find.where().eq("name", "Leandra").findUnique();

        assertNotNull(leandra);
    }

    /*
    PASSED
     */
    //@Test
    public void createAndAddCreditCard(){
        Customer walter = Customer.find.where().eq("email", "ready@b.com").findUnique();
        //Date date = new Date(2017, 10, 1);
        //new CreditCard(1234567812345679L, walter, "walter wooodall", date, 21919, 123).save();
        for(CreditCard card : walter.creditCardList){
           System.out.println(card.number);
        }
    }
    /*
    PASSED. Fix on stack overflow
     */
    @Test
    public void createAndRetrieveOrder(){
        Customer walter = Customer.find.where().eq("email", "test@banananow.com").findUnique();
        Product p1 = Product.find.where().idEq(1).findUnique();
        Product p2 = Product.find.where().idEq(2).findUnique();
        Basket b = new Basket(walter, new Date(), "morning");

        b.save();
        new BasketProduct(p1, b, 2).save();
        new BasketProduct(p2, b, 2).save();

        for(Basket ba : walter.basketList){
            for(BasketProduct bp : ba.basketProductList){
                System.out.println(bp.product.name);
            }
        }
    }
    /*
    PASSED
     */
    //@Test
    public void selectTopProducts(){
        List<Product> productList = Product.getTopProducts();
        assertNotNull(productList);
        System.out.println(productList);
    }
    /*
    PASSED
     */
    //@Test
    public void createAndRetrieveCart(){
        Customer walter = Customer.find.where().eq("email", "test@banananow.com").findUnique();
        Product p1 = Product.find.where().idEq(1).findUnique();
        Product p2 = Product.find.where().idEq(2).findUnique();

        new ShoppingCart(walter, p1, 2).save();
        new ShoppingCart(walter, p2, 3).save();

        if(walter.shoppingCartList.size() < 2){
            assert(false);
        }
        for(ShoppingCart cart : walter.shoppingCartList){
            assertNotNull(cart.product.name);
            assertNotNull(cart.product.price);
            System.out.println(cart.product.name);
            System.out.println(cart.product.price);
            System.out.println(cart.quantity);

        }
    }

}
