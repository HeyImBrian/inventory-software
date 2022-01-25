package sample;

public class PartInHouse extends Part{

    private int machineID;

    /**
     * Constructor.
     * When the PartInHouse object is created, it uses the arguments and sets the corresponding variables.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public PartInHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * MachineID is specific to in-house parts.
     * @return machineID
     */
    public int getMachineID() {
        return machineID;
    }
}
