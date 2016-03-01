package utils;

import javafx.scene.control.TextField;
import se.chalmers.ait.dat215.project.Product;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * By: Sebastian Nilsson
 * Date: 16-02-24
 * Project: imat26
 */
public class Utils {

    public static boolean isValidDouble(String text) {
        return text.matches("\\d+(\\.\\d+)*");
    }

    public void changeView() {

    }

    public static String getFormatedProductAmount(double amount, Product product) {

        String amountString = String.valueOf(amount);
        amountString = amountString.replace(',', '.');

        if (Utils.isValidDouble(amountString)) {
            amount = Double.parseDouble(amountString);
        }

        // if this is a unit that can use decimals
        if (product.getUnitSuffix().equals("kg") || product.getUnitSuffix().equals("l")) {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("0.0", dfs);
            String formatedText = df.format(amount);

            return formatedText;
        } else {
            int intAmount = (int)Math.round(amount);
            return String.valueOf(intAmount);
        }
    }

    public static String getFormatedPrice(double price) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.CEILING);

        return df.format(price);
    }

}
