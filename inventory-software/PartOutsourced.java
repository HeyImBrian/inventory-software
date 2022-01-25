package sample;

public class PartOutsourced extends Part{

    private String companyName;

    /**
     * Constructor.
     * When the PartOutsourced object is created, it uses the arguments and sets the corresponding variables.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public PartOutsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Company name is exclusive to outsourced parts.
     * @return comapnyName
     */
    public String getCompanyName() {
        return companyName;
    }
}