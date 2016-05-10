package pl.parser.nbp;

/**
 *
 * @author Szymon Gasienica-Kotelnicki
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Input data
        String fromDate = args[1];
        String toDate = args[2];
        String currency = args[0];
        //end of Input data
        
        LoadCurrency currency1 = new LoadCurrency(fromDate, toDate, currency);
        String mean = Statistics.MeanofbuyingRates(currency1.currencyList);
        String standardDeviation = Statistics.StandardDeviationofsellingRates(currency1.currencyList);
        System.out.println(mean + " " + standardDeviation);

    } //end of main
} // end of MainClass
