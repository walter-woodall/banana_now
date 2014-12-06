package models;

import com.avaje.ebean.annotation.CreatedTimestamp;
import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Entity
public class Basket extends Model {
    @Id
    public long id;
    @CreatedTimestamp
    Timestamp time;
    int complete;
    @ManyToOne
    Customer customer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "basket")
    private List<Basket_Product> basket_product;

    public Basket(Customer customer){
        this.customer = customer;
        this.complete = 0;
    }

    public static Finder<String, Basket> find = new Finder<String, Basket>(String.class, Basket.class);

}
