package models;

import com.avaje.ebean.annotation.CreatedTimestamp;
import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Entity
public class Basket extends Model {
    @Id
    public int id;
    @CreatedTimestamp
    public Timestamp time;
    int complete;
    @ManyToOne
    public Customer customer;
    @ManyToOne
    public Employee employee;
    @Column(name = "delivery_date")
    public Date date;
    public String deliveryTime;
    @OneToMany(mappedBy = "basket")
    public  List<BasketProduct> basket_product;

    public Basket(Customer customer){
        this.customer = customer;
        this.complete = 0;
    }
    public Basket(Customer customer, Employee employee){
        this.customer = customer;
        this.employee = employee;
    }

    public static Finder<String, Basket> find = new Finder<String, Basket>(String.class, Basket.class);

}
