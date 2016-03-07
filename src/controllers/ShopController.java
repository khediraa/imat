package controllers;

import imat.IObservable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import se.chalmers.ait.dat215.project.*;
import sun.dc.pr.PRError;
import utils.Utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class ShopController implements Initializable, IObservable, ShoppingCartListener {
    @FXML TilePane tilePane;
    IMatDataHandler dataInstance = IMatDataHandler.getInstance();
    List<Product> fruits = new ArrayList<>();
    List<Product> vegetables = new ArrayList<>();
    List<Product> herbs = new ArrayList<>();

    List<Product> meat = new ArrayList<>();
    List<Product> fish = new ArrayList<>();

    List<Product> dairy = new ArrayList<>();


    List<Product> bread = new ArrayList<>();
    List<Product> flourSugarSalt = new ArrayList<>();
    List<Product> nuts = new ArrayList<>();
    List<Product> pasta = new ArrayList<>();
    List<Product> potatoRice = new ArrayList<>();

    List<Product> coldDrinks = new ArrayList<>();
    List<Product> hotDrinks = new ArrayList<>();

    List<Product> sweets = new ArrayList<>();

    List<ItemTileController> visibleControllers = new ArrayList<>();

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchProducts();
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);
    }


    /** Displaying the products by creating as many nodes as there are
     * in the list, and then adding them to the tilePane.
     */
    private void displayProducts(List<Product> products){
        for(Product product : products){
            try{
                //Loading the node itemTile and its controller
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ItemTile.fxml"));
                Node itemTile = loader.load();
                ItemTileController itemTileController = loader.getController();

                //set the title of the item
                itemTileController.setTitle(product.getName());

                // connect this product to the tile
                itemTileController.setProduct(product);

                //set the image
                itemTileController.setImage(dataInstance.getFXImage(product));

                //set the price
                itemTileController.setPriceAndUnit(product.getPrice(), product.getUnit());

                itemTileController.setUnitSuffix(product.getUnitSuffix());

                itemTileController.setAmountFormat();

                //add the itemTile to the tilePane
                tilePane.getChildren().add(itemTile);

                visibleControllers.add(itemTileController);

            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }

        checkForAmountUpdate(visibleControllers);
    }

    /**
     * Clears the tile pane.
     */
    private void clearTilePane(){
        tilePane.getChildren().clear();
    }

    /**
     * Display greens.
     */
    public void displayGreens(){
        clearTilePane();
        displayProducts(fruits);
        displayProducts(vegetables);
        displayProducts(herbs);
    }

    /**
     * Display meat products.
     */
    public void displayMeat(){
        clearTilePane();

        displayProducts(meat);
        displayProducts(fish);
    }

    /**
     * Display dairy products.
     */
    public void displayDairy(){
        clearTilePane();
        displayProducts(dairy);
    }

    /**
     * Display drinks.
     */
    public void displayDrinks(){
        clearTilePane();
        displayProducts(coldDrinks);
        displayProducts(hotDrinks);
    }

    /**
     * Display pantry products.
     */
    public void displayPantry(){
        clearTilePane();

        displayProducts(bread);
        displayProducts(pasta);
        displayProducts(flourSugarSalt);
        displayProducts(nuts);
        displayProducts(potatoRice);
    }

    /**
     * Display sweets.
     */
    public void displaySweets(){
        clearTilePane();
        displayProducts(sweets);
    }

    public void displayMostBought() {
        clearTilePane();
        displayProducts(getMostBoughtProducts());
    }

    private List<Product> getMostBoughtProducts() {
        Map<Product, Integer> boughtProducts = new HashMap<>();

        List<Order> orderList = dataInstance.getOrders();
        for(Order order : orderList) {
            for (ShoppingItem item : order.getItems()) {
                if (boughtProducts.containsKey(item.getProduct())) {
                    int currentCount = boughtProducts.get(item.getProduct());
                    boughtProducts.put(item.getProduct(), currentCount + (int)item.getAmount());
                } else {
                    boughtProducts.put(item.getProduct(), (int)item.getAmount());
                }
            }
        }

        Map<Product, Integer> boughtProductsSorted = Utils.sortByValue(boughtProducts);

        Iterator it = boughtProductsSorted.entrySet().iterator();

        int max_products = 12;
        int i = 0;

        List<Product> mostBoughtProducts = new ArrayList<>();

        while (it.hasNext() && i < max_products) {
            Map.Entry pair = (Map.Entry)it.next();
            mostBoughtProducts.add((Product) pair.getKey());
            i++;
        }

        return mostBoughtProducts;
    }

    private void fetchProducts() {
        //Adding all fruit products to the fruit-list
        fruits.addAll(dataInstance.getProducts(ProductCategory.CITRUS_FRUIT));
        fruits.addAll(dataInstance.getProducts(ProductCategory.BERRY));
        fruits.addAll(dataInstance.getProducts(ProductCategory.EXOTIC_FRUIT));
        fruits.addAll(dataInstance.getProducts(ProductCategory.FRUIT));
        fruits.addAll(dataInstance.getProducts(ProductCategory.MELONS));
        fruits.addAll(dataInstance.getProducts(ProductCategory.POD));

        //Adding all vegetable products to the vegetable-list
        vegetables.addAll(dataInstance.getProducts(ProductCategory.ROOT_VEGETABLE));
        vegetables.addAll(dataInstance.getProducts(ProductCategory.CABBAGE));
        vegetables.addAll(dataInstance.getProducts(ProductCategory.VEGETABLE_FRUIT));

        //Adding all herb products to the herb-list
        herbs.addAll(dataInstance.getProducts(ProductCategory.HERB));

        // Add meats
        meat.addAll(dataInstance.getProducts(ProductCategory.MEAT));
        fish.addAll(dataInstance.getProducts(ProductCategory.FISH));

        // Add dairy
        dairy.addAll(dataInstance.getProducts(ProductCategory.DAIRIES));

        // Add pantry
        bread.addAll(dataInstance.getProducts(ProductCategory.BREAD));
        flourSugarSalt.addAll(dataInstance.getProducts(ProductCategory.FLOUR_SUGAR_SALT));
        nuts.addAll(dataInstance.getProducts(ProductCategory.NUTS_AND_SEEDS));
        pasta.addAll(dataInstance.getProducts(ProductCategory.PASTA));
        potatoRice.addAll(dataInstance.getProducts(ProductCategory.POTATO_RICE));

        // Add drinks
        coldDrinks.addAll(dataInstance.getProducts(ProductCategory.COLD_DRINKS));
        hotDrinks.addAll(dataInstance.getProducts(ProductCategory.HOT_DRINKS));

        // Add sweets
        sweets.addAll(dataInstance.getProducts(ProductCategory.SWEET));
    }

    public void search(String searchTerm) {
        clearTilePane();
        List<Product> results = dataInstance.findProducts(searchTerm);

        if (results.size() < 1) {
            pcs.firePropertyChange("no-search-results", true, false);
        }

        clearTilePane();
        displayProducts(results);
    }

    @Override
    public void addObserver(PropertyChangeListener observer) {
        pcs.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        pcs.removePropertyChangeListener(observer);
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        checkForAmountUpdate(visibleControllers);
    }

    private void checkForAmountUpdate(List<ItemTileController> controllerList) {
        for(ItemTileController itemTileController : controllerList) {
            itemTileController.refreshAmountField();
        }
    }

}
