package pl.parser.nbp;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Szymon Gasienica-Kotelnicki
 */
public class TxtParser {

    public Scanner s;

    public void openFile(int y) {
        try {
            if (y != 2016) {
                String baseUrl = "http://www.nbp.pl/kursy/xml/dir";
                String suffixTxt = ".txt";
                String url = new StringBuilder().append(baseUrl).append(y).append(suffixTxt).toString();
                URL nbpurl = new URL(url);
                s = new Scanner(nbpurl.openStream());
            } else {
                URL nbplist = new URL("http://www.nbp.pl/kursy/xml/dir.txt");
                s = new Scanner(nbplist.openStream());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    } //end of openFile method

    public String readFile(String date) {
        while (s.hasNext()) {
            String a = s.next();
            if (a.contains("c") && a.contains(date)) {
                return a;
            }
        }
        return null;
    } // end of readFile

    public void closeFile() {
        s.close();
    } //end of closeFile
} //end of TxtParser class
