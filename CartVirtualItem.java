class CartVirtualItem {
    private byte type = (byte)0;
    private String name = "";
    private int amount = 0;
    private boolean isSpecialVersion = false;


    /**
     * constructor that sets up the type, name, and amount
     * 
     * @param typ
     * @param name
     * @param amt
     * @boolean spec
     */
    public CartVirtualItem(byte typ, String nam, int amt, boolean spec) {
        type = typ;
        name = nam;
        amount = amt;
        isSpecialVersion = spec;
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

    /**
     * getter for the whwther the CartVirtualItem is the special version or not
     * 
     * @return boolean isSpecialVersion
     */
    public boolean isSpecial() {
        return isSpecialVersion;
    }
}