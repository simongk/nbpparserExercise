package pl.parser.nbp;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Szymon Gasienica-Kotelnicki
 */
public class Statistics {

    public static String MeanofbuyingRates(ArrayList< Currency> currencyList) {
        float add = 0;
        int count = 0;
        for (Currency c : currencyList) {
            try {
                add += c.getbuyingRate();
                count++;
            } catch (ParseException | NullPointerException ex) {
                ex.getMessage();
            }
        }
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        double a = add / count;
        return df.format(a);
    }

    public static float MeanofsellingRates(ArrayList< Currency> currencyList) {
        float add = 0;
        int count = 0;
        for (Currency c : currencyList) {
            try {
                add += c.getsellingRate();
                count++;
            } catch (ParseException | NullPointerException ex) {
                ex.getMessage();
            }
        }
        return add / count;
    }

    public static String StandardDeviationofsellingRates(ArrayList< Currency> currencyList) {
        float variance = 0;
        int count = currencyList.size();
        float mean = MeanofsellingRates(currencyList);
        float temp = 0;
        for (Currency c : currencyList) {
            try {
                temp += Math.pow((c.getsellingRate() - mean), 2);

            } catch (ParseException | NullPointerException ex) {
                ex.getMessage();
            }
        }
        variance = temp / count;
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        double a = Math.sqrt(variance);
        return df.format(a);
    }

}
