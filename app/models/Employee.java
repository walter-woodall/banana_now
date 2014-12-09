package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Entity
@Table(name="employee")
public class Employee extends Model{
    @Id
    public int id;
    public String name;
    public int manager;
    @ManyToOne
    public Store store;

    public Employee(String name, int manager, Store store){
        this.name = name;
        this. manager = manager;
        this.store = store;
    }

    public static Finder<String, Employee> find = new Finder<String, Employee>(String.class, Employee.class);

    /*
    public static boolean isManagerOf(Store store, String username) {
        return find.where().eq("")
    }
    */
}
