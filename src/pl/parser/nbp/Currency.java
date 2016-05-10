package pl.parser.nbp;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 *
 * @author Szymon Gasienica-Kotelnicki
 */
public class Currency {

    private String codeofCurrency;
    private String buyingRate;
    private String sellingRate;
    private static NumberFormat nf = NumberFormat.getInstance();
    
    public Currency(String codeofCurrency, String buyingRate, String sellingRate) {
        this.codeofCurrency = codeofCurrency;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
    }

    public float getbuyingRate() throws ParseException {
        return nf.parse(buyingRate).floatValue();
    }

    public float getsellingRate() throws ParseException {
        return nf.parse(sellingRate).floatValue();
    }

    @Override
    public String toString() {
        return buyingRate + " " + sellingRate;
    }

}
