import java.util.ArrayList;
import java.text.DecimalFormat;

public class Bookstore {
    //totalBookstoreFunds (I'm using a long but the last two digits are reserved for cents)
    private static long totalFunds;

    //a decimalformat for the receipt
    private static final DecimalFormat priceFormat = new DecimalFormat("###,###,##0.00");

    //the CartVirtualItem is simply a link to the 
    private static ArrayList<CartVirtualItem> cartItems = new ArrayList<>();
    private static boolean premiumPay = false;

    /**
     * getter for the total funds
     * 
     * @return long totalFunds
     */
    public static long getTotalFunds() {
        return totalFunds;
    }

    /**
     * setter for the total funds
     * 
     * @param long newTotalFunds
     */
    public static void setTotalFunds(long newTotalFunds) {
        totalFunds = newTotalFunds;
    }

    /**
     * adds a virtual object
     * uses the data of a real object's type and name, as well as an an amount to purchase
     * 
     * @param byte type
     * @param String name
     * @param int amt
     */
    public static void addToCart(byte typ, String name, int amt, boolean spec) {
        //first check if there's something like that already there
        int index = -2;
        for (int i=0;i<cartItems.size();i++) {
            if (cartItems.get(i).getName().equals(name) && cartItems.get(i).getType() == typ && cartItems.get(i).isSpecial() == spec) {
                index = i;
                break;
            }
        }
        
        if (index == -2 && amt > 0) {
            //if it's not there (and the number is positive) add to cart
            cartItems.add( new CartVirtualItem(typ, name, amt, spec) );
        } else {
            //if it's there then modify the value to the new number
            if (amt > 0) cartItems.get(index).setAmount(amt);
            else {
                //if the amount is zero then just remove the entire thing from the cart :p
                cartItems.remove(index);
            }
        }
    }

    /**
     * removes the item from the cart
     * removes from the list at the index provided, using the amount provided
     * 
     * @param int index
	 * @param int amt
     */
    public static void removeFromCart(int index, int amt) {
        //if amt being removed < amount in cart, subtract amt from number in cart
        if (amt < cartItems.get(index).getAmount()) cartItems.get(index).setAmount(cartItems.get(index).getAmount()-amt);
        else {
            //if amt is the same or bigger somehow than amount in cart, then remove the whole thing
            cartItems.remove(index);
        }
    }

    /**
     * gets the size of the cart by adding all the amounts of each type of virtual item
     * 
     * @return int total
     */
    public static int getCartSize() {
        int total = 0;
        for (CartVirtualItem item : cartItems) total += item.getAmount();
        return total;
    }

    /**
     * gets the number of different items in the cart
     * 
     * @return int size
     */
    public static int getUniqueCartSize() {
        return cartItems.size();
    }

    /**
     * gets 100× the price at a particular index of the cart as an integer
     * multiplies the price by the amount to get the whole price
     * 
     * @param int index
     * @return int price
     */
    public static int getPrice(int index) {
        int foundIndex = -1;

        //get index using findIndex(), because the store might have gotten more/less stock since the transaction began
        foundIndex = Main.inventory.findIndex( cartItems.get(index).getType(), cartItems.get(index).getName() );

        //return the ( (100×price)+99 ) × amount
        return (int)(100*Main.inventory.getPrice( cartItems.get(index).getType(), foundIndex )+99)*cartItems.get(index).getAmount();
    }

    /**
     * gets 100× the total price of the entirety of the cart, as an integer
     * 
     * @return total
     */
    public static int getTotalPrice() {
        int purchaseIndex = -1;
        int total = 0;
        for (int i=0;i<cartItems.size();i++) {
            //get index, because the store might have gotten more/less stock since the transaction began
            purchaseIndex = Main.inventory.findIndex( cartItems.get(i).getType(), cartItems.get(i).getName() );

            //using this new information, add it to the total
            total += (int) ( (100*Main.inventory.getPrice( cartItems.get(i).getType(),  purchaseIndex )+99)*cartItems.get(i).getAmount() );
        }
        return total;
    }

    /**
     * getter for the name at a particular index in cart
     * 
     * @param int index
     * @return String name
     */
    public static String getName(int index) {
        return cartItems.get(index).getName();
    }

    /**
     * getter for the number within the cart of a particular type and name
     * type codes: 0=book, 1=CD, 2=DVD
     * 
     * @param byte type
     * @param String name
     * @return numInCart
     */
    public static int numInCart(byte type, String name) {
        //it's not in order so I can't use binary search :(
        for (CartVirtualItem item : cartItems) {
            if (item.getName().equals(name) && item.getType() == type) return item.getAmount();
        }
        //failsafe if not found
        return 0;
    }

    /**
     * getter for the number within the cart at a particular index
     * 
     * @param int index
     * @return numInCart
     */
    public static int numInCart(int index) {
        return cartItems.get(index).getAmount();
    }

    /**
     * method to buy now and print a receipt
     * automatically adds total to total store funds, and adds total to the customer's profile
     * 
     * @return String receipt
     */
    public static String buyNow(int customerIndex) {
        //add this to the record
        RecordKeeper.addTransaction();

        String receipt = "  Order under name of: "+Main.registry.nameMem()[customerIndex]+"▓    Purchased: ▓ ";
        int money = 0;
        int total = 0;

        for (CartVirtualItem item : cartItems) {
            //get index, because the store might have gotten more/less stock since the transaction began
            int purchaseIndex = Main.inventory.findIndex( item.getType(), item.getName() );

            //add money to store
            money = (int) (100*Main.inventory.getPrice( item.getType(),  purchaseIndex )+99)*item.getAmount();
            total += money;

            //add it to the record
            RecordKeeper.addPurchase(item.getType(), purchaseIndex, item.getAmount());

            switch(item.getType()) {
                //product types: 0==book, 1==CD, 2==DVD
                //BOOKCASE
                case 0:
                    //add to receipt
                    receipt += item.getAmount()+" of "+item.getName()+" (Book, $"+priceFormat.format(money*0.01)+"),▓ ";

                    //if the number to buy is less than the number in stock, just reduce the number in stock
                    if ( item.getAmount() < Main.inventory.getProductStock( (byte)0, purchaseIndex) )
                        Main.inventory.restockBook(item.getName(), -1*item.getAmount(), item.isSpecial());//yes i just restocked a negative number
                    else {
                        //if the number is greater or equal to the number in stock, set it to 0
                        int index = Main.inventory.findIndex((byte)0, item.getName());
                        Main.inventory.restockBook(item.getName(), -1*Main.inventory.getProductStock((byte)0, index), item.isSpecial());
                    }
                    break;
                
                //CD CASE
                case 1:
                    //add to receipt
                    receipt += item.getAmount()+" of "+item.getName()+" (CD, $"+priceFormat.format(money*0.01)+"),▓ ";

                    //if the number to buy is less than the number in stock, just reduce the number in stock
                    if ( item.getAmount() < Main.inventory.getProductStock( (byte)1, purchaseIndex ) )
                        Main.inventory.restockCD(item.getName(), -1*item.getAmount(), item.isSpecial());//yes i just restocked a negative number
                    else {
                        //if the number is greater or equal to the number in stock, set it to 0
                        int index = Main.inventory.findIndex((byte)1, item.getName());
                        Main.inventory.restockCD(item.getName(), -1*Main.inventory.getProductStock((byte)1, index), item.isSpecial());
                    }
                    break;
                
                //DVD CASE
                case 2:
                    //add to receipt
                    receipt += item.getAmount()+" of "+item.getName()+" (DVD, $"+priceFormat.format(money*0.01)+"),▓ ";

                    //if the number to buy is less than the number in stock, just reduce the number in stock
                    if ( item.getAmount() < Main.inventory.getProductStock( (byte)2, purchaseIndex ) )
                        Main.inventory.restockDVD(item.getName(), -1*item.getAmount(), item.isSpecial());//yes i just restocked a negative number
                    else {
                        //if the number is greater or equal to the number in stock, set it to 0
                        int index = Main.inventory.findIndex((byte)2, item.getName());
                        Main.inventory.restockDVD(item.getName(), -1*Main.inventory.getProductStock((byte)2, index), item.isSpecial());
                    }
            }
        }

        if (premiumPay) {
            total += 3299;
            receipt += "1 Premium Membership (32.99)▓";
        }

        //increase totalFunds by the total
        totalFunds += total;

        //add the record of money spent to the account of the purchaser
        Main.registry.addMoneySpent(customerIndex, total*0.01);

        //reset the cartItems list and empty it because everything has been bought
        cartItems.clear();
        premiumPay = false;

        //cut out that last comma before returning it :p
        return receipt.substring(0, receipt.length()-2) + "▓   TOTAL: $"+priceFormat.format(total*0.01);
    } // end buy now
}