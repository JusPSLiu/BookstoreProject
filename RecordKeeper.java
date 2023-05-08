import java.util.ArrayList;
import java.util.*;
import java.io.*;
import java.text.*;

public class RecordKeeper {
    private static final DecimalFormat form = new DecimalFormat("###,###,##0.00");
    private static ArrayList<CartVirtualItem> restocks = new ArrayList<>();
    private static ArrayList<CartVirtualItem> purchases = new ArrayList<>();
    private static ArrayList<String> registryRecord = new ArrayList<>();
    private static double dailyRevenue = 0.0;
    private static int numOfTransactions;

    /**
     * adds a restock to the record
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param int index
     * @param int numRestocked
     */
    public static void addRestock(byte type, int index, int numRestocked) {
        //the type, name, num restocked r to be saved in a CartVirtualItem
        String itemName = Main.inventory.getName(type, index);
        boolean found = false;
        
        //if its found alrdy in the list then add the number of restocks to the current number
        for (CartVirtualItem item : restocks) {
            if (item.getType() == type && item.getName().equals(itemName)) {
                //I don't have a mutator that adds so I just add the number to the previous number
                item.setAmount(item.getAmount()+numRestocked);
                found = true;
                break;
            }
        }

        //if it wasnt found then add it to the end of the list :D
            //(alphabetical order doesn't matter, only the order of what was restocked first)
        if (!found)
            restocks.add(new CartVirtualItem(type, Main.inventory.getName(type, index), numRestocked, Main.inventory.isSpecial(type, index)));
    }

    /**
     * adds a purchase to the record
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param int index
     * @param int numBought
     */
	public static void addPurchase(byte type, int index, int numBought) {
        String itemName = Main.inventory.getName(type, index);
        boolean found = false;
        
        //if its found alrdy in the list then add the number of restocks to the current number
        for (CartVirtualItem item : purchases) {
            if (item.getType() == type && item.getName().equals(itemName)) {
                //I don't have a mutator that adds so I just add the number to the previous number
                item.setAmount(item.getAmount()+numBought);
                found = true;
                break;
            }
        }

        //if it wasnt found then add it to the end of the list :D
            //(alphabetical order doesn't matter, only the order of what was restocked first)
        if (!found)
            purchases.add(new CartVirtualItem(type, Main.inventory.getName(type, index), numBought, Main.inventory.isSpecial(type, index)));
        dailyRevenue += ((Main.inventory.getPrice(type, index)+0.99)*numBought);
    }

    /**
     * add to the record that a customer was registered
     * 
     * @param String name
     * @param int age
     * @param String address
     * @param boolean premium
     */
	public static void addRegistered(String name, int age, String address, boolean premium) {
        //if premium, add "premium" to the end
        if (premium) registryRecord.add("Registered "+name+", "+age+", "+address+", premium");
        //if not premium, add "nonpremium" to the end
        else registryRecord.add("Registered "+name+", "+age+", "+address+", nonpremium");
    }

    /**
     * add any custom message to the registry record
     * 
     * @param String addThis
     */
    public static void registryEdit(String addThis) {
        registryRecord.add(addThis);
    }

    /**
     * add money to the daily record of obtained cash
     * yes even if the cash was obtained by selling customer data on the dark web
     * 
     * @param double amt
     */
    public static void addToDailyRevenue(double amt) {
        dailyRevenue += amt;
    }

    /**
     * adds one to the number of total transactions that day
     *
     */
    public static void addTransaction() {
        numOfTransactions++;
    }

    /**
     * write the report to the EndOfDayReport txt file
     * 
     */
    public static void writeReport() {
        try {
            PrintWriter output = new PrintWriter(new FileOutputStream("EndOfDayReport.txt"));
            output.println("                        END OF DAY REPORT:\n\nINVENTORY\n  Items Sold:");

            //list the items sold (only if it's not empty)
            if (purchases.isEmpty()) output.println("\tnone");
            else for (CartVirtualItem item : purchases) {
                switch(item.getType()) {
                    case 0: output.print("\t— Book, "); break;
                    case 1: output.print("\t— CD, "); break;
                    case 2: output.print("\t— DVD, "); break;
                }
                output.println(item.getName()+", "+item.getAmount()+", $"+form.format((Main.inventory.getPrice(item.getType(), Main.inventory.findIndex(item.getType(), item.getName()))+0.99)*item.getAmount()));
            }
            //the number of transactions now
            output.println("   Total Number of Transactions: "+numOfTransactions+"\n");

            //list the items stocked (only if it's not empty)
            output.println("  Items Stocked:");

            if (restocks.isEmpty()) output.println("\tnone");
            else for (CartVirtualItem item : restocks) {
                switch(item.getType()) {
                    case 0: output.print("\t— Book, "); break;
                    case 1: output.print("\t— CD, "); break;
                    case 2: output.print("\t— DVD, "); break;
                }
                output.println(item.getName()+", "+item.getAmount());
            }

            //print the registry info
            output.println("\nREGISTRY:");

            if (registryRecord.isEmpty()) output.println("\tnone");
            else for (String printThing : registryRecord) {
                output.println("\t— "+printThing);
            }

            //print the total revenue made during the day
            output.println("\nTotal Revenue Today: $"+form.format(dailyRevenue));

            output.close();
        } catch (FileNotFoundException e) {
            System.out.println("ono file gon");
        }/* catch (IOException e) {
            System.out.println("ono error ioexception\n"+e);
        }*/
    }

    public static void printAll() {
        for (CartVirtualItem bob : restocks) {
            System.out.print(bob.getName()+" | ");
        }
    }
}