/**
 * DVD class
 *
 * @author jusps
 * @version 1
 */
class DVD extends Product {
	private boolean isBluRay;
	
    /**
     * constructor
     * 
     * @param String name
     * @param String description
     * @param boolean bluRay
     */
    public DVD(String name, String description, boolean bluRay) {
        super(name, description);
        isBluRay = bluRay;
    }
	
    /**
     * getter that returns whether the DVD is bluRay or not
     * 
     * @return boolean isBluRay
     */
	public boolean isBluRay() {
		return isBluRay;
	}
	
    /**
     * Follows the 239th Rule of Acquisition: never be afraid to mislabel a product
     * changes isBluRay to its opposite, so if it is true, then it become false. if it is false, it becomes true
     * 
     */
	public void the239thRuleOfAcquisition() {
		isBluRay = !isBluRay;
	}
}