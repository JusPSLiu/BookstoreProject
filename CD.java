/**
 * CD class
 *
 * @author jusps
 * @version 1
 */
public class CD extends Product {
	private boolean isRom;

    /**
     * constructor
     * 
     * @param String name
     * @param String description
     * @param boolean rom
     */
	public CD(String name, String description, boolean rom) {
        super(name, description);
        isRom = rom;
    }
	
    /**
     * getter that returns whether the CD is secretly a ROM of a custom PC port of E.T. for Atari 2600 or not
     * 
     */
	public boolean isRom() {
		return isRom;
	}

    /**
     * changes whether rom
     * changes isRom to its opposite, so if it is true, then it become false. if it is false, it becomes true
     * 
     */
    public void changeWhetherRom() {
        isRom = !isRom;
    }
}