import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;

/**
 * By: Sebastian Nilsson
 * Date: 16-02-23
 * Project: imat26
 */
public class Cart {

    public static ShoppingCart instance = null;

    public Cart() {

    }

    public static ShoppingCart getInstance() {
        if(instance == null) {

        }
        return instance;
    }

}
