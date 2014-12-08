package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by walterwoodall on 12/5/14.
 */
@Embeddable
public class BasketProductPk implements Serializable{
    @Column(name = "product_id")
    private Product product;
    @Column(name = "basket_id")
    private Basket basket;

    public BasketProductPk(Product product, Basket basket) {
        this.product = product;
        this.basket = basket;
    }
    @Override
    public boolean equals(Object otherOb) {
        if (this == otherOb) {
            return true;
        }
        if (!(otherOb instanceof BasketProductPk)) {
            return false;
        }
        BasketProductPk other = (BasketProductPk) otherOb;
        return (
                (product==null?other.product==null:product.equals
                        (other.product)
                )
                        &&
                        (basket  == other.basket)
        );
    }
    @Override
    public int hashCode() {
        return (
                (product==null?0:product.hashCode())
                        ^
                        (basket.hashCode())
        );
    }

}
