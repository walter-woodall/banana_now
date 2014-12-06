package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Entity
public class Product extends Model{
    @Id
    public long id;
    public String name;
    public Float price;
    public String category;
    public String subcategory;
    public String image_url;
    public String url;
    @ManyToOne
    private Store store;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Basket_Product> basket_product;

    public Product(String name, Float price, String category, String subcategory, String image_url, String url, Store store){
        this.name = name;
        this.price = price;
        this.category = category;
        this.subcategory = subcategory;
        this.image_url = image_url;
        this.url = url;
        this.store = store;
    }

    public static Finder<String, Product> find = new Finder<String, Product>(String.class, Product.class);

}
