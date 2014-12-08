package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by walterwoodall on 12/4/14.
 */
@Entity
public class Address extends Model{

    @Id
    public int id;
    public String address1;
    public String address2;
    public String city;
    public String state;
    public int zipcode;

    public Address(String address1, String city, String state, int zipcode){
        this.address1 = address1.toLowerCase();
        this.address2 = "";
        this.city = city.toLowerCase();
        this.state = state.toLowerCase();
        this.zipcode = zipcode;
    }

    public Address(String address1, String address2, String city, String state, int zipcode){
        this.address1 = address1.toLowerCase();
        this.address2 = address2.toLowerCase();
        this.city = city.toLowerCase();
        this.state = state.toLowerCase();
        this.zipcode = zipcode;
    }

    public static Finder<String, Address> find = new Finder<String, Address>(String.class, Address.class);
}
