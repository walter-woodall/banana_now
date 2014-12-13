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
    public Date deliveryDate;
    @Column(name = "delivery_time")
    public String deliveryTime;
    public Double subtotal;
    public Double total;
    @OneToMany(mappedBy = "basket")
    public  List<BasketProduct> basketProductList;

    public Basket(Customer customer, Date deliveryDate, String deliveryTime){
        this.customer = customer;
        this.complete = 0;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.subtotal = 0.0;
        this.total = .06 * subtotal;

    }
    public Basket(Customer customer, Date deliveryDate, String deliveryTime, Employee employee){
        this.customer = customer;
        this.employee = employee;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.complete = 0;
        this.subtotal = 0.0;
        this.total = .06 * subtotal;


    }

    public static Finder<String, Basket> find = new Finder<String, Basket>(String.class, Basket.class);


}
