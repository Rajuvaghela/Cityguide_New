package misterro.bitnet.com.jaymetals.POJO;

/**
 * Created by Dhaval on 2/16/2018.
 */

public class FeaturedProductPOJO {

    private String product_id;
    private String name;
    private String price;
    private String rating;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getImage_full_path() {
        return image_full_path;
    }

    public void setImage_full_path(String image_full_path) {
        this.image_full_path = image_full_path;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    private String discount;
    private String image_id;
    private String image_name;
    private String image_full_path;
    private String discount_price;
}
