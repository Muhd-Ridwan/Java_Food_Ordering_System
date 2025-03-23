package FoodOrderingSystem;

/**
 *
 * @author Teoh Peng Kee
 */
public class FieldValidator {
    
    public static String checkEmpty(String value) {
	return (value == null || value.trim().isEmpty()) ? "-" : value.trim();
}
}
