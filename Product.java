/**
 * Product class
 *
 * @author jusps
 * @version 1
 */
public class Product {
    private String name = "";
    private String description = "";
    private int stock = 0;
    int price = 2;
    
    /**
     * constructor for the product object
     * 
     * @param String name
     * @param String description
     */
    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    /**
     * getter for the name
     * 
     * @return String name
     */
    public String getName() {
        return name;
    }
    
    /**
     * getter for the description
     * 
     * @return String description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * getter for the stock
     * 
     * @return int stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * getter for the price
     * 
     * @return int price
     */
    public int getPrice() {
        return price;
    }
    
    /**
     * setter for the name
     * 
     * @param String name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * setter for the description
     * 
     * @param String description
     */
    public void setDescription(String desc) {
        description = desc;
    }
    
    /**
     * mutator to add stock, adding the number provided
     * 
     * @param int add
     */
    public void addStock(int add) {
        stock += add;
    }
    
    /**
     * setter to set the price
     * 
     * @param int nuPrice
     */
    public void setPrice(int nuPric) {
        price = nuPric;
    }
}