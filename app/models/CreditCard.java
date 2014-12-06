package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Entity
@Table(name = "credit_card")
public class CreditCard {
    @Id
    private long number;
    @ManyToOne
    Customer customer;
    String name;
    Date expiration;
    int zipcode;
    int cvc;

    public CreditCard(long number, Customer customer, String name, Date expiration, int zipcode, int cvc){
        this.number = number;
        this.customer = customer;
        this.name = name;
        this.expiration = expiration;
        this.zipcode = zipcode;
        this.cvc = cvc;
    }

    public Model.Finder<String, CreditCard> find = new Model.Finder<String, CreditCard>(String.class, CreditCard.class);
}
