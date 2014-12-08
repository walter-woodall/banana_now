package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Entity
@Table(name = "credit_card")
public class CreditCard extends Model{
    @Id
    public long number;
    @ManyToOne
    public Customer customer;
    public String name;
    public Date expiration;
    public int zipcode;
    public int cvc;

    public CreditCard(long number, Customer customer, String name, Date expiration, int zipcode, int cvc){
        this.number = number;
        this.customer = customer;
        this.name = name;
        this.expiration = expiration;
        this.zipcode = zipcode;
        this.cvc = cvc;
    }

    public static Finder<String, CreditCard> find = new Model.Finder<String, CreditCard>(String.class, CreditCard.class);
}
