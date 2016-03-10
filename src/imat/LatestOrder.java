package imat;

import se.chalmers.ait.dat215.project.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * By: Sebastian Nilsson
 * Date: 16-03-10
 * Project: imat26
 */
public class LatestOrder {

    private static LatestOrder instance;
    private List<ShoppingItem> items;

    private LatestOrder() {
        this.items = new ArrayList<>();
    }

    public static LatestOrder getInstance() {
        if (instance == null) {
            instance = new LatestOrder();
        }
        return instance;
    }

    public void setItems(List<ShoppingItem> items) {
        for(int i = 0; i < items.size(); i++) {
            this.items.add(new ShoppingItem(items.get(i).getProduct(), items.get(i).getAmount()));
        }
    }

    public List<ShoppingItem> getItems() {
        System.out.println(this.items.size());
        return this.items;
    }

}
