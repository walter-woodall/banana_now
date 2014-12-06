package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by walterwoodall on 12/4/14.
 */
@Entity
public class Customer extends Model {

    @Id
    public long id;
    public String email;
    public String password;
    public String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id")
    public Address address;
    public double balance;
    @OneToMany
    private List<CreditCard> creditCardList;

    public Customer(String email, String password, String name, Address address){
        this.email = email.toLowerCase();
        this.password = password;
        this.name = name;
        this.address = address;
        this.balance = 0.0;

    }

    public static Finder<String, Customer> find = new Finder<String, Customer>(String.class, Customer.class);

    public static Customer authenticate(String email, String password) {
        return find.where().eq("email", email)
                .eq("password", password).findUnique();
    }
}