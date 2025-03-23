package FoodOrderingSystem;

/**
 *
 * @author Teoh Peng Kee
 */
public class Runner {
    private double aveRatings = 0; 
    private int reviewCount = 0; 
    

    public double calculateAveRatings(double sumRatings, int recordNum){
	return sumRatings/ recordNum; 
    }
}
