package models;

import play.db.ebean.Model;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by walterwoodall on 12/5/14.
 */
public class Employee extends Model{
    @Id
    private long id;
    private String name;
    private int manager;
    @ManyToOne
    private Store store;

    public Employee(String name, int manager, Store store){
        this.name = name;
        this. manager = manager;
        this.store = store;
    }

    public static Finder<String, Employee> find = new Finder<String, Employee>(String.class, Employee.class);
}
