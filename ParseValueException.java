/**
 * Custom exception thrown when an invalid input is provided during the parsing
 * process
 *
 * @author AA
 * @version 1.0.0
 */
public class ParseValueException extends Exception {
    ParseValueException(String msg) {
        super(msg);
    }
}
