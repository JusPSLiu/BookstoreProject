import java.util.ArrayList;

/**
 * Registry class
 *
 * @author jusps
 * @version 1
 */
public class Registry {
    private ArrayList<Customer> registryList = new ArrayList<Customer>();
    
    
    /**
     * gets the number of members in registryList
     * 
     * @return number of members in registryList
     */
    public int numMem() {
    	//failsafe if empty
        if (registryList.isEmpty()) return 0;
        
        //return the size of the ArrayList
        return registryList.size();
    }
    
    /**
     * gets the number of members that aren't registered with a premium membership
     * 
     * @return number of regular members
     */
    public int numRegMem() {
    	//failsafe if empty
        if (registryList.isEmpty()) return 0;
        
        //count up for each member that is not premium
    	int total = 0;
        for (Customer custom : registryList) {
        	if (!custom.isPremium()) total++;
        }
        
        //return the total
        return total;
    }
    
    /**
     * gets the number of members registered for a premium membership
     * 
     * @return number of premium members
     */
    public int numPremMem() {
    	//failsafe if empty
        if (registryList.isEmpty()) return 0;
        
        //count up for each member that is premium
    	int total = 0;
        for (Customer custom : registryList) {
        	if (custom.isPremium()) total++;
        }
        
        //return the total
        return total;
    }
    
    /**
     * gets an array of the names of the members
     * 
     * @return array of Strings
     */
    public String[] nameMem() {
        //failsafe if empty
        if (registryList.isEmpty()) return new String[] {"",""};
        
        //make array of names from registry list
        String[] result = new String[registryList.size()];
        
        //set to names
        for (int i=0;i<result.length;i++) result[i] = registryList.get(i).getName();
        
        //return array of names
        return result;
    }
    
    /**
     * gets the age at a particular index in the registryList
     * 
     * @param int index
     * @return int age at index in registryList
     */
    public int getAge(int index) {
        return registryList.get(index).getAge();
    }
    
    /**
     * gets the paymentType at a particular index in the registryList
     * 
     * @param int index
     * @return String paymentType at index in registryList
     */
    public String getPaymentType(int index) {
        return registryList.get(index).getPayMethod();
    }
    
    /**
     * gets the premium status at a particular index in the registryList
     * 
     * @param int index
     * @return boolean premium status at index in registryList
     */
    public boolean[] isPremium(int index) {
    	//isPremium[0] is whether premium, isPremium[1] is if paid
        return registryList.get(index).getPremiumStatus();
    }
    
    /**
     * gets the moneySpent at a particular index in the registryList
     * 
     * @param int index
     * @return double moneySpent at index in registryList
     */
    public double getMoneySpent(int index) {
        return registryList.get(index).getMoneySpent();
    }
    
    /**
     * gets the address at a particular index in the registryList
     * 
     * @param int index
     * @return String address at index in registryList
     */
    public String getAddress(int index) {
        return registryList.get(index).getAddress();
    }
    
    /**
     * gets the private payment details at a particular index in the registryList
     * 
     * @param int index
     * @return String of private payment details at index in registryList
     */
    public String getPrivateDetails(int index) {
        return registryList.get(index).getInfo();
    }
    
    /**
     * adds member to registryList
     * 
     * @param String name
     * @param String payMethod
     * @param int age
     * @param boolean isPremium
     * @param boolean isPaidPremium
     * @param String address
     * @param String info
     */
    public void addMember(String name, String payMethod, int age, boolean isPremium, boolean isPaidPremium, String address, String info) {
        //failsafe if empty
        if (registryList.isEmpty())
        	registryList.add(new Customer(name, payMethod, age, new boolean[] {isPremium, isPaidPremium}, address, info));
        else {
            //find where to alphabetically place this
            int index = 0;
            while (index < registryList.size() && registryList.get(index).getName().compareTo(name) < 0) index++;
            registryList.add(index, new Customer(name, payMethod, age, new boolean[] {isPremium, isPaidPremium}, address, info));

        }
    }
    
    /**
     * changes name of the account of the customer at index
     * 
     * @param int index
     * @param String name
     */
    public void changeName(int index, String name) {
        registryList.get(index).changeName(name);
    }
    
    /**
     * changes the payment type of the customer at index in the registryList
     * 
     * @param int index
     * @param String type
     */
    public void changePaymentType(int index, String type) {
        registryList.get(index).setPayMethod(type);
    }
    
    /**
     * sets the member at index to premium membership status provided
     * 
     * @param int index
     * @param bool isPremium
     * @param bool hasPaidThisMonth
     */
    public void setPremMem(int index, boolean isPremium, boolean hasPaidThisMonth) {
    	//set whether premium
        registryList.get(index).setPremium(isPremium);
        
        //if premium, set if paid this month
        if (isPremium) registryList.get(index).setPaidThisMonth(hasPaidThisMonth);
    }
    
    /**
     * lowers all membership status, used at the start of a new month
     * paid members become unpaid members, and unpaid members become regular customers
     * 
     */
    public void lowerMembershipStatus() {
    	//use a for loop to go over every customer
    	for (Customer customer : registryList) {
    		//get the current status of each customer
    		boolean[] status = customer.getPremiumStatus();
    		
    		//status[0] is if premium, status[1] is if paid this month
    		if (status[0]) {
    			if (status[1]) {
    				customer.setPaidThisMonth(false);
    			} else customer.setPremium(false);
    		}
    	}
    }
    
    /**
     * adds money spent to the account of the customer at index
     * 
     * @param int index
     * @param double money
     */
    public void addMoneySpent(int index, double money) {
        registryList.get(index).addMoneySpent(money);
    }
    
    /**
     * changes the age on file of the current customer
     * 
     * @param int index
     * @param int age
     */
    public void changeAge(int index, int age) {
        registryList.get(index).setAge(age);
    }
    
    /**
     * changes the address on file of the current customer
     * 
     * @param int index
     * @param String address
     */
    public void changeAddress(int index, String address) {
        registryList.get(index).setAddress(address);
    }
    
    /**
     * changes the additional payment information on file of the current customer
     * 
     * @param int index
     * @param String additionalInfo
     */
    public void changeInfo(int index, String additionalInfo) {
        registryList.get(index).setInfo(additionalInfo);
    }
}