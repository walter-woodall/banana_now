package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import play.db.ebean.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by walterwoodall on 12/6/14.
 */
public class Category extends Model {
    public String mainCategory;
    public String subcategory;

   public Category(String category, String subcategory){
       this.mainCategory = category;
       this.subcategory = subcategory;
   }

   public static List<Category> getAllCategories(Store store){
       int id = store.id;
       String sql = "select category, subcategory from product where store_id = " + id + " group by subcategory order by category asc;";
       LinkedList<Category> categories = null;
       SqlQuery sqlQuery = Ebean.createSqlQuery(sql);

       List<SqlRow> list = sqlQuery.findList();
       if(list != null) {
           categories = new LinkedList<Category>();
           for (SqlRow row : list) {
               String category = row.getString("category");
               String subcategory = row.getString("subcategory");
               Category c = new Category(category, subcategory);
               categories.add(c);
           }
       }
       return categories;
   }
}
