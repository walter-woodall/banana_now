package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Entity
@Table(name = "basket_product")
public class BasketProduct extends Model {
    @EmbeddedId
    public BasketProductPk id;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="product_id")
    public Product product;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="basket_id")
    public Basket basket;

    public int quantity;
    public float subtotal;


    public BasketProduct(Product product, Basket basket, int quantity){
        this.id = new BasketProductPk(product, basket);
        this.product = product;
        this.basket = basket;
        this.quantity = quantity;
        this.subtotal = quantity * product.price;
    }
    protected BasketProduct() {}

    public static Finder<String, BasketProduct> find = new Finder<String, BasketProduct>(String.class, BasketProduct.class);
    // getters, setters
}
