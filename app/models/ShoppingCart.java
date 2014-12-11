package models;

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

    public ShoppingCart(Customer customer, Product product, int quantity){
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    public static Finder<String, ShoppingCart> find = new Finder<String, ShoppingCart>(String.class, ShoppingCart.class);


}
