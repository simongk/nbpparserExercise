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
        
        LoadCurrency loadCurrency = new LoadCurrency(fromDate, toDate, currency);
        String mean = Statistics.MeanofbuyingRates(LoadCurrency.currencyList);
        String standardDeviation = Statistics.StandardDeviationofsellingRates(LoadCurrency.currencyList);
        System.out.println(mean + " " + standardDeviation);

    } //end of main
} // end of MainClass
