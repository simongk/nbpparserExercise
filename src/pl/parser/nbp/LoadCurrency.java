package pl.parser.nbp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Szymon Gasienica-Kotelnicki
 */
public class LoadCurrency {

    private static Document xmlDoc = null;
    private static DocumentBuilder builder = null;
    private static DocumentBuilderFactory factory = null;
    ArrayList< Currency> currencyList = null;
    private String fromDate;
    private String toDate;
    private String currencyCode;

    public LoadCurrency(String fromDate, String toDate, String currencyCode) {

        try {
            xmlDoc = getDocument();
            //Input data
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.currencyCode = currencyCode;
            //end of Input data

            currencyList = new ArrayList<>();
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFrom = dfs.parse(fromDate);
            Date dateTo = dfs.parse(toDate);

            while (dateFrom.compareTo(dateTo) <= 0) {
                currencyList.add(getCurrency(currencyCode, dateFrom));
                dateFrom = addDay(dateFrom);
            }
        } catch (ParseException ex) {
            ex.getMessage();
            System.out.println("Wrong input data");
        }

    }//end of constructor

    private static void LoadXML(String xmlCode) {
        try {
            String baseURL = "http://www.nbp.pl/kursy/xml/";
            String suffixXML = ".xml";
            String NBPURL = new StringBuilder().append(baseURL).append(xmlCode).append(suffixXML).toString();
            URL nbp = new URL(NBPURL);
            InputStream stream = nbp.openStream();
            xmlDoc = builder.parse(new InputSource(stream));
        } catch (IOException | SAXException e) {
            e.getMessage();
        }
    } // end of loadXML - its loading the xml file from the web 

    private static String getcodeofXML(Date oldDate1) throws ParseException {

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String oldDate = sdf1.format(oldDate1);

        String oldFormat = "yyyy-MM-dd";
        String newFormat = "yyMMdd";
        String newDate;

        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        Date d = sdf.parse(oldDate);
        sdf.applyPattern(newFormat);
        newDate = sdf.format(d);

        Calendar cal = new GregorianCalendar();
        cal.setTime(oldDate1);
        int year = cal.get(Calendar.YEAR);

        TxtParser t = new TxtParser();
        t.openFile(year);
        String codeofXML = t.readFile(newDate);
        t.closeFile();

        return codeofXML;
    } //end getCodeofXML - its getting id code from a txt file

    private static Document getDocument() {
        factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            ex.getMessage();
        }
        return null;
    } //end of getDocument its creating a document

    private static Currency getCurrency(String kod, Date date) {
        try {

            LoadXML(getcodeofXML(date));
            NodeList listofPositions = xmlDoc.getElementsByTagName("pozycja");

            for (int i = 0; i < listofPositions.getLength(); i++) {
                Element position = (Element) listofPositions.item(i);
                NodeList listofbuyingRates = position.getElementsByTagName("kurs_kupna");
                NodeList listofcurrencyCodes = position.getElementsByTagName("kod_waluty");
                NodeList listofsellingRates = position.getElementsByTagName("kurs_sprzedazy");

                for (int j = 0; j < listofbuyingRates.getLength(); j++) {
                    Element buyingRateElement = (Element) listofbuyingRates.item(j);
                    Element currencyCodeElement = (Element) listofcurrencyCodes.item(j);
                    Element sellingRateElement = (Element) listofsellingRates.item(j);
                    NodeList listofbuyingRateElements = buyingRateElement.getChildNodes();
                    NodeList listofcurrencyCodeElements = currencyCodeElement.getChildNodes();
                    NodeList listofsellingRateElements = sellingRateElement.getChildNodes();
                    if (listofcurrencyCodeElements.item(j).getNodeValue().equals(kod)) {
                        String buyingRate = ((Node) listofbuyingRateElements.item(j)).getNodeValue().trim();
                        String currencyCode = ((Node) listofcurrencyCodeElements.item(j)).getNodeValue().trim();
                        String sellingRate = ((Node) listofsellingRateElements.item(j)).getNodeValue().trim();
                        return new Currency(currencyCode, buyingRate, sellingRate);
                    } //fi           
                } //end for j
            } //end for i
        } //end try 
        catch (DOMException | ParseException | NullPointerException e) {
            e.getMessage();
        } //end catch
        return null;
    } //end of getCurrency - its getting a currency and its values from a xml file

    private static Date addDay(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
        return date;
    } //end addDay its an iterator for days
}// end of LoadCurrency
