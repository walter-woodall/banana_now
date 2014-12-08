package models;

import com.avaje.ebean.*;
import com.avaje.ebean.Query;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Entity
public class Product extends Model{
    @Id
    public int id;
    public String name;
    public Float price;
    public String category;
    public String subcategory;
    @Column(name = "image_url")
    public String imageUrl;
    public String url;
    @ManyToOne
    public Store store;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<BasketProduct> basket_product;


    public Product(String name, Float price, String category, String subcategory, String imageUrl, String url, Store store){
        this.name = name;
        this.price = price;
        this.category = category;
        this.subcategory = subcategory;
        this.imageUrl = imageUrl;
        this.url = url;
        this.store = store;
    }

    public Product(int id, String name, Float price, String category, String subcategory, String imageUrl, String url, Store store){
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.subcategory = subcategory;
        this.imageUrl = imageUrl;
        this.url = url;
        this.store = store;
    }

    public static Finder<String, Product> find = new Finder<String, Product>(String.class, Product.class);

    public static List<Product> getTopProducts(){
        String sql = "select * from top_product";
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> sqlRows = sqlQuery.findList();
        List<Product> productList = null;
        if(sqlRows != null){
            productList = new LinkedList<Product>();
            for(SqlRow row : sqlRows){
                int id = row.getInteger("id");
                String name = row.getString("name");
                float price = row.getFloat("price");
                String category = row.getString("category");
                String subcategory = row.getString("subcategory");
                String imageUrl = row.getString("image_url");
                String url = row.getString("url");
                String storeId = row.getString("store_id");
                productList.add(new Product(id, name, price, category, subcategory, imageUrl, url, Store.find.byId(storeId)));
            }
        }
        return productList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", image_url='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
