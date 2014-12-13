package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlUpdate;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by walterwoodall on 12/8/14.
 */
@Entity
@Table(name="shopping_cart")
public class ShoppingCart extends Model{
    @Id
    public int id;
    @ManyToOne
    public Customer customer;
    @ManyToOne
    public Product product;
    public int quantity;
    public Float subtotal;

    public ShoppingCart(Customer customer, Product product, int quantity){
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.subtotal = product.price * quantity;
    }

    public static Finder<String, ShoppingCart> find = new Finder<String, ShoppingCart>(String.class, ShoppingCart.class);


}
