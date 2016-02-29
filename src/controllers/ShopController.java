package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class ShopController implements Initializable {
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


    ObservableList<Node> products;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchProducts();

        //Todo... ----- Must show subcategories

        //Todo... ----- Must create separator and set title for subcategories
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

                //add the itemTile to the tilePane
                tilePane.getChildren().add(itemTile);

            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
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
}
