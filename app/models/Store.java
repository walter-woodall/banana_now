package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Entity
public class Store extends Model {
    @Id
    public int id;
    public String name;
    @OneToOne
    public Address address;
    @OneToMany
    public List<Employee> employeeList;

    public Store(String name, Address address){
        this.name = name.toLowerCase();
        this.address = address;
        this.employeeList = new ArrayList<Employee>();
    }

    public static Finder<String, Store> find = new Finder<String, Store>(String.class, Store.class);
}
