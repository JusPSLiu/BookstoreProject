class CartVirtualItem {
    byte type = (byte)0;
    private String name = "";
    int amount = 0;

    /**
     * constructor that sets up the type, name, and amount
     * 
     * @param typ
     * @param name
     * @param amt
     */
    public CartVirtualItem(byte typ, String nam, int amt) {
        type = typ;
        name = nam;
        amount = amt;
    }

    /**
     * getter for the type
     * 
     * @return byte type
     */
    public byte getType() {
        return type;
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
     * getter for the amount of the CartVirtualItem
     * 
     * @return int amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * setter for the amount in the CartVirtualItem
     * the amount is the only variable that needs to change after the constructor
     * 
     * @param int newAmt
     */
    public void setAmount(int newAmt) {
        amount = newAmt;
    }
}