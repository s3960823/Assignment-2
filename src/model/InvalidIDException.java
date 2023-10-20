package model;
/**
 * Custom exception for invalid ID value for a post
 *
 * @author AA
 * @version 1.0.0
 */
public class InvalidIDException extends Exception {
    String recordType;
    int ID;

    public InvalidIDException(String msg, String recordType, int ID) {
        super(msg);
        this.recordType = recordType;
        this.ID = ID;
    }

    public InvalidIDException(String recordType, int ID) {
        super("Invalid ID");
        this.recordType = recordType;
        this.ID = ID;
    }
}
