package Common.Utilities;

public interface XMLTemplate extends Getter {
    /**
     *
     * @return String
     * Override this method it should return all the data members of the class it implements
     * To avoid duplication code in xml processing
     *
     */
    String getAllValues();
}
