package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Entity
@Table(name = "basket_product")
public class Basket_Product extends Model {
    @EmbeddedId
    private Basket_ProductPk id;

    @ManyToOne(cascade= CascadeType.ALL)
    @MapsId("product_id")
    private Product product;

    @ManyToOne(cascade=CascadeType.ALL)
    @MapsId("basket_id")
    private Basket basket;

    private int quantity;

    public Basket_Product(Product product, Basket basket, int quantity){
        this.product = product;
        this.basket = basket;
        this.quantity = quantity;
    }
    protected Basket_Product() {}


    // getters, setters
}
