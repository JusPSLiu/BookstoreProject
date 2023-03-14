/**
 * Customer class
 *
 * @author jusps
 * @version 1
 */
public class Customer {
    private String name = "";
    private String paymentMethod = "";
    private String address = "";
    private String additionalInfo = "";
    //is premium. [0] is if have premium, [1] is if paid premium this month
    private boolean[] isPremium = {false, false};
    private double moneySpent = 0.0;
    private int age = 18;
    
    //constructor
    public Customer(String name, String method, int age, boolean[] premium, String address, String moreInfo) {
        this.name = name;
        paymentMethod = method;
        this.age = age;
        isPremium = premium;
        this.address = address;
        additionalInfo = moreInfo;
    }
    
    /**
     * gets the name
     * 
     * @return String name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * gets the payment method
     * 
     * @return String paymentMethod
     */
    public String getPayMethod() {
        return paymentMethod;
    }

    /**
     * gets the age
     * 
     * return int age
     */
    public int getAge() {
        return age;
    }
    
    /**
     * gets the premium status boolean array
     * [0] is if premium, [1] is if paid
     * 
     * @return boolean[] isPremium
     */
    public boolean[] getPremiumStatus() {
    	//isPremium[0] is whether premium, isPremium[1] is if paid
        //it kept giving me an error '.class expected' but for some reason this fixed it
        return new boolean[] {isPremium[0], isPremium[1]};
    }

    /**
     * gets whether premium or not
     * [0] is if premium
     * 
     * @return boolean isPremium
     */
    public boolean isPremium() {
        return isPremium[0];
    }
    
    /**
     * gets the money spent
     * 
     * @return double moneySpent
     */
    public double getMoneySpent() {
        return moneySpent;
    }

    /**
     * gets the address
     * 
     * @return String address
     */
    public String getAddress() {
        return address;
    }

    /**
     * gets additional payment info
     * 
     * @return String additionalInfo
     */
    public String getInfo() {
        return additionalInfo;
    }
    
    /**
     * changes the name
     * 
     * @param String name
     */
    public void changeName(String name) {
        this.name = name;
    }
    
    /**
     * sets the payment method
     * 
     * @param String method
     */
    public void setPayMethod(String method) {
        this.paymentMethod = method;
    }

    /**
     * sets the age
     * 
     * @param int age
     */
    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * sets isPremium[0] to whether paid or not
     * 
     * @param boolean isTrue
     */
    public void setPremium(boolean isTrue) {
        isPremium[0] = isTrue;
    }
    
    /**
     * sets isPremium[1] to whether paid this month
     * 
     * @param boolean isTrue
     */
    public void setPaidThisMonth(boolean isTrue) {
        isPremium[1] = isTrue;
    }
    
    /**
     * adds money to the total money spent
     * 
     * @param double munee
     */
    public void addMoneySpent(double munee) {
        moneySpent += munee;
    }
    
    /**
     * sets the address
     * 
     * @param String address
     */
    public void setAddress(String address) {
    	this.address = address;
    }

    /**
     * sets the additional payment information
     * 
     * @param String additionalInfo
     */
    public void setInfo(String additionalInfo) {
    	this.additionalInfo = additionalInfo;
    }
}