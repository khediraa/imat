package imat;

import controllers.PaymentController;
import javafx.scene.control.Button;

/**
 * By: Sebastian Nilsson
 * Date: 16-03-07
 * Project: imat26
 */
public class PaginationButton extends Button {

    private int paginationIndex;

    public PaginationButton(String btnText, int paginationIndex) {
        super(btnText);
        this.paginationIndex = paginationIndex;
    }

    public int getPaginationIndex() {
        return paginationIndex;
    }

}
