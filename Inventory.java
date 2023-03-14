/**
 * Inventory class
 *
 * @author jusps
 * @version 1
 */
import java.util.ArrayList;

/**
 *
 * @author jusps
 */
public class Inventory {
    private ArrayList<Product> stockBook = new ArrayList<Product>();
    private ArrayList<Product> stockCD = new ArrayList<Product>();
    private ArrayList<Product> stockDVD = new ArrayList<Product>();
    
    /**
     * gets the number of different items in stock, as in the size of the total ArrayList of the items
     * (byte) type codes:  0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @return size of ArrayList
     */
    public int getUniqueProductStock(byte type) {
    	//type 0=book, 1=CD, 2=DVD
        switch(type) {
            case 0:
            	//failsafe if empty
                if (stockBook.isEmpty()) return 0;
                
                //return the size of the arrayList
                return stockBook.size();
            case 1:
            	//failsafe if empty
                if (stockCD.isEmpty()) return 0;
                
                //return the size of the arrayList
                return stockCD.size();
            case 2:
            	//failsafe if empty
                if (stockDVD.isEmpty()) return 0;
                
                //return the size of the arrayList
                return stockDVD.size();
        }
        //failsafe if the type provided was invalid
        return 0;
    }
    
    /**
     * gets the number of products in stock, by adding the stock of each item in the ArrayList
     * (byte) type codes:  0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @return int total
     */
    public int getProductStock(byte type) {
    	//make integer total that will count up and be returned
        int total = 0;
        
        //type 0=book, 1=CD, 2=DVD
        switch(type) {
            case 0:
            	//failsafe if empty
                if (stockBook.isEmpty()) return 0;
                
                //add up total and return it
                for (Product i : stockBook) total+= i.getStock();
                return total;
                //lol I don't have to break because it already returned lets gooooo
            case 1:
            	//failsafe if empty
                if (stockCD.isEmpty()) return 0;
                
                //add up total and return it
                for (Product i : stockCD) total+= i.getStock();
                return total;
            case 2:
            	//failsafe if empty
                if (stockDVD.isEmpty()) return 0;
                
                //add up total and return it
                for (Product i : stockDVD) total+= i.getStock();
                return total;
        }
        //failsafe if the type provided was invalid
        return 0;
    }
    
    /**
     * gets the stock of a particular product of the type and at the index provided
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param int index
     * @return int stock
     */
    public int getProductStock(byte type, int index) {
    	//type 0=book, 1=CD, 2=DVD
        switch(type) {
            case 0:
                return stockBook.get(index).getStock();
            case 1:
                return stockCD.get(index).getStock();
            case 2:
                return stockDVD.get(index).getStock();
        }
        //failsafe if type provided was invalid
        return 0;
    }
    
    /**
     * gets the name of a particular product of the type and at the index provided
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param int index
     * @return String name
     */
    public String getName(byte type, int index) {
        //type 0==book, 1==CD, everything else==DVD
        if (type == 0) return stockBook.get(index).getName();
        if (type == 1) return stockCD.get(index).getName();
        return stockDVD.get(index).getName();
    }
    
    /**
     * gets the description of a particular product of the type and at the index provided
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param int index
     * @return String description
     */
    public String getDescription(byte type, int index) {
        //type 0==book, 1==CD, everything else==DVD
        if (type == 0) return stockBook.get(index).getDescription();
        if (type == 1) return stockCD.get(index).getDescription();
        return stockDVD.get(index).getDescription();
    }
    
    /**
     * gets the price of a particular product of the type and at the index provided
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param int index
     * @return int price
     */
    public int getPrice(byte type, int index) {
        //type 0==book, 1==CD, everything else==DVD
        if (type == 0) return stockBook.get(index).getPrice();
        if (type == 1) return stockCD.get(index).getPrice();
        return stockDVD.get(index).getPrice();
    }
    
    /**
     * restocks books using the name in the parameter, and the number for the amount to restock
     * 
     * @param String name
     * @param int number
     */
    public void restockBook(String name, int number) {
        //failsafe if empty
        if (stockBook.isEmpty()) {
            stockBook.add(new Product(name, "no description"));
            stockBook.get(0).addStock(number);
        } else {
            //create book as link to product
            Product book = stockBook.get(0);
            boolean found = false;

            //cycle through to find the book
            //FOR SOME REASON I CAN'T ITERATE USING book, SO I HAVE TO MAKE A NEW BOOK ITERATOR
            //a wasteful shame if you ask me.
            for (Product bookIterator : stockBook) {
                if (bookIterator.getName().equals(name)) {
                    book = bookIterator;
                    found = true;
                    break;
                }
            }

            //if it was found, increase stock, if it wasn't create new product
            if (found) book.addStock(number);
            else {
                //find where to put it in the list and keep alphabetical order
                int index = 0;
                while (index < stockBook.size() && stockBook.get(index).getName().compareTo(name) < 0) index++;
                stockBook.add(index, new Product(name, "no description"));
                stockBook.get(index).addStock(number);
            }
        }
    }
    
    /**
     * restocks CDs using the name in the parameter, and the number for the amount to restock
     * 
     * @param String name
     * @param int number
     */
    public void restockCD(String name, int number) {
        //failsafe if empty
        if (stockCD.isEmpty()) {
            stockCD.add(new Product(name, "no description"));
            stockCD.get(0).addStock(number);
        } else {
            //create CD as link to product
            Product CD = stockCD.get(0);
            boolean found = false;
            
            //cycle through to find the CD
            //FOR SOME REASON I CAN'T ITERATE USING CD, SO I HAVE TO MAKE A NEW CD ITERATOR
            //a wasteful shame if you ask me.
            for (Product CDIterator : stockCD) {
                if (CDIterator.getName().equals(name)) {
                    CD = CDIterator;
                    found = true;
                    break;
                }
            }
            
            //if it was found, increase stock, if it wasn't create new product
            if (found) CD.addStock(number);
            else {
                //find where to put it in the list and keep alphabetical order
                int index = 0;
                while (index < stockCD.size() && stockCD.get(index).getName().compareTo(name) < 0) index++;
                stockCD.add(index, new Product(name, "no description"));
                stockCD.get(index).addStock(number);
            }
        }
    }
    
    /**
     * restocks CDs using the name in the parameter, and the number for the amount to restock
     * 
     * @param String name
     * @param int number
     */
    public void restockDVD(String name, int number) {
        //failsafe if empty
        if (stockDVD.isEmpty()) {
            stockDVD.add(new Product(name, "no description"));
            stockDVD.get(0).addStock(number);
        } else {
            //create DVD as link to product
            Product DVD = stockDVD.get(0);
            boolean found = false;
            
            //cycle through to find the DVD
            //FOR SOME REASON I CAN'T ITERATE USING DVD, SO I HAVE TO MAKE A NEW DVD ITERATOR
            //a wasteful shame if you ask me.
            for (Product DVDIterator : stockDVD) {
                if (DVDIterator.getName().equals(name)) {
                    DVD = DVDIterator;
                    found = true;
                    break;
                }
            }
            
            //if it was found, increase stock, if it wasn't create new product
            if (found) DVD.addStock(number);
            else {
                //find where to put it in the list and keep alphabetical order
                int index = 0;
                while (index < stockDVD.size() && stockDVD.get(index).getName().compareTo(name) < 0) index++;
                stockDVD.add(index, new Product(name, "no description"));
                stockDVD.get(index).addStock(number);
            }
        }
    }
    
    /**
     * changes name of a particular product of the type and at the index provided, using the new name provided
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param int index
     * @param String newName
     */
    public void changeName(byte type, int index, String newName) {
        //type 0==book, 1==CD, 2==DVD
        switch(type) {
            case 0: stockBook.get(index).setName(newName); break;
            case 1: stockCD.get(index).setName(newName); break;
            case 2: stockDVD.get(index).setName(newName); break;
        }
    }
    
    /**
     * sets description of a particular product of the type and at the index provided, using the description provided
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param int index
     * @param String newDescription
     */
    public void setDescription(byte type, int index, String newDesc) {
        //type 0==book, 1==CD, 2==DVD
        switch(type) {
            case 0: stockBook.get(index).setDescription(newDesc); break;
            case 1: stockCD.get(index).setDescription(newDesc); break;
            case 2: stockDVD.get(index).setDescription(newDesc); break;
        }
    }
    
    /**
     * sets price of a particular product of the type and at the index provided, using the price provided
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param int index
     * @param int price
     */
    public void setPrice(byte type, int index, int pric) {
        //type 0==book, 1==CD, 2==DVD
        switch(type) {
            case 0: stockBook.get(index).setPrice(pric); break;
            case 1: stockCD.get(index).setPrice(pric); break;
            case 2: stockDVD.get(index).setPrice(pric); break;
        }
    }

    /**
     * deletes particular product of the type and at the index provided
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param int index
     */
    public void delete(byte type, int index) {
        //type 0==book, 1==CD, 2==DVD
        switch(type) {
            case 0: stockBook.remove(index); break;
            case 1: stockCD.remove(index); break;
            case 2: stockDVD.remove(index); break;
        }
    }
    
    /**
     * finds the index based on the type and name provided
     * (byte) type codes:   0 is book,  1 is CD,  2 is DVD
     * 
     * @param byte type
     * @param String name
     * @return int index
     */
    public int findIndex(byte type, String name) {
        int lrmid[] = new int[3];
        //type 0==book, 1==CD, 2==DVD
        switch(type) {
            case 0:
                //make the left right and middle variables for binary search
                //         [0] == L, [1] == M, [2] == R
                lrmid[2] = stockBook.size()-1;
                lrmid[0] = 0;
                while (lrmid[0] <= lrmid[2]) {
                    lrmid[1] = (lrmid[0]+lrmid[2])/2;
                    if (stockBook.get(lrmid[1]).getName().compareTo(name) < 0) {
                        //if its after this spot, set the left to the right of the mid
                        lrmid[0] = lrmid[1]+1;
                    } else if (stockBook.get(lrmid[1]).getName().compareTo(name) > 0) {
                        //if its before this spot, set the right to the left of the mid
                        lrmid[2] = lrmid[1]-1;
                    } else {
                        return lrmid[1];
                    }
                }
                break;
            case 1:
                //make the left right and middle variables for binary search
                //         [0] == L, [1] == M, [2] == R
                lrmid[2] = stockCD.size()-1;
                while (lrmid[0] <= lrmid[2]) {
                    lrmid[1] = (lrmid[0]+lrmid[2])/2;
                    if (stockCD.get(lrmid[1]).getName().compareTo(name) < 0) {
                        //if its after this spot, set the left to the right of the mid
                        lrmid[0] = lrmid[1]+1;
                    } else if (stockCD.get(lrmid[1]).getName().compareTo(name) > 0) {
                        //if its after this spot, set the right to the left of the mid
                        lrmid[2] = lrmid[1]-1;
                    } else {
                        return lrmid[1];
                    }
                }
                break;
            case 2:
                //make the left right and middle variables for binary search
                //         [0] == L, [1] == M, [2] == R
                lrmid[2] = stockDVD.size()-1;
                while (lrmid[0] <= lrmid[2]) {
                    lrmid[1] = (lrmid[0]+lrmid[2])/2;
                    if (stockDVD.get(lrmid[1]).getName().compareTo(name) < 0) {
                        //if its after this spot, set the left to the right of the mid
                        lrmid[0] = lrmid[1]+1;
                    } else if (stockDVD.get(lrmid[1]).getName().compareTo(name) > 0) {
                        //if its after this spot, set the right to the left of the mid
                        lrmid[2] = lrmid[1]-1;
                    } else {
                        return lrmid[1];
                    }
                }
                break;
        }
        return -1;
    }
}