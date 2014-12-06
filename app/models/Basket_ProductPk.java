package models;

import javax.persistence.Embeddable;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Embeddable
public class Basket_ProductPk {
    private final int product_id;
    private final int basket_id;

    public Basket_ProductPk(int productId, int basketId) {
        this.product_id = productId;
        this.basket_id = basketId;
    }

}
