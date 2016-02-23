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
public class GreensController implements Initializable {
    @FXML TilePane tilePane;
    IMatDataHandler dataInstance = IMatDataHandler.getInstance();
    List<Product> fruits = new ArrayList<>();
    List<Product> vegetables = new ArrayList<>();
    List<Product> herbs = new ArrayList<>();
    ObservableList<Node> products;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        //Adding all herb products to the herb-list
        herbs.addAll(dataInstance.getProducts(ProductCategory.HERB));


        //Todo... ----- Must show subcategories

        //Todo... ----- Must create separator and set title for subcategories
        //Displaying fruits
        displayProducts(fruits);

        //Displaying vegetables
        displayProducts(vegetables);

        //Displaying herbs
        displayProducts(herbs);

    }


    /** Displaying the products by creating as many nodes as there are
     * in the list, and then adding them to the tilePane.
     */
    private void displayProducts(List<Product> products){
        for(int i=0; i<products.size(); i++){
            try{
                //Loading the node itemTile and its controller
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ItemTile.fxml"));
                Node itemTile = loader.load();
                ItemTileController itemTileController = loader.getController();

                //set the title of the item
                itemTileController.setTitle(products.get(i).getName());

                // connect this product to the tile
                itemTileController.setProduct(products.get(i));

                //set the image
                itemTileController.setImage(dataInstance.getFXImage(products.get(i)));

                //set the price
                itemTileController.setPriceAndUnit(products.get(i).getPrice(), products.get(i).getUnit());

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
    private void displayGreens(){
        displayProducts(fruits);
        displayProducts(vegetables);
        displayProducts(herbs);
    }

    /**
     * Display meat products.
     */
    private void displayMeat(){
        //Todo...
    }

    /**
     * Display dairy products.
     */
    private void displayDairy(){
        //Todo...
    }

    /**
     * Display drinks.
     */
    private void displayDrinks(){
        //Todo...
    }

    /**
     * Display pantry products.
     */
    private void displayPantry(){
        //Todo...
    }

    /**
     * Display sweets.
     */
    private void displaySweets(){
        //Todo...
    }
}
