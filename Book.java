/**
 * Book class
 *
 * @author jusps
 * @version 1
 */
public class Book extends Product {
	private boolean isHardCover;
	
    /**
     * constructor
     * 
     * @param String name
     * @param String description
     * @param boolean hardcover
     */
    public Book(String name, String description, boolean hardcover) {
        super(name, description);
        isHardCover = hardcover;
    }
	
    /**
     * getter that returns whether it's hardcover or not
     * 
     */
	public boolean isHardCover() {
		return isHardCover;
	}
	
    /**
     * changes whether hardcover
     * changes isHardCover to its opposite, so if it is true, then it become false. if it is false, it becomes true
     * 
     */
	public void changeWhetherHardCover() {
		isHardCover = !isHardCover;
	}
}