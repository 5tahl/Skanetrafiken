package Classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ApiConverter {
    // Variabel som där man kan spara valet av cookies.
    static String okCookies = "";

    public static String getOkCookies() {
        return okCookies;
    }
    public static void setOkCookies(String okCookie) {
        okCookies = okCookie;
    }

    // Metod som konverterar String till XML.
    // ( Har glömt hur den fungerar då det var ett tag sen jag lyssnade på lektionen du gjorde den på ).
    public static Document convertStringToXml(String apiString) throws IOException {

        String URLtoSend = apiString;

        URL line_api_url = new URL(URLtoSend);

        HttpURLConnection linec = (HttpURLConnection) line_api_url.openConnection();
        linec.setDoInput(true);
        linec.setDoOutput(true);
        linec.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(linec.getInputStream()));

        String inputLine;

        String ApiResponse = "";

        while ((inputLine = in.readLine()) != null) {

            ApiResponse += inputLine;
        }
        in.close();
        System.out.println(ApiResponse);

        Document doc = convertStringToXMLDocument(ApiResponse);

        doc.getDocumentElement().normalize();

        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

        return doc;
    }

    // Metod som konverterar strings till XML.
    // ( Samma sak här ).
    private static Document convertStringToXMLDocument(String xmlString) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;

        try {

            builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

            return doc;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    // Metod som konverterar en NodeList till en ArrayList.
    public static ArrayList<String> convertNodeListToArrayList(NodeList nList) {
        
        ArrayList<String> arrList = new ArrayList<>();
        
        for (int i = 0; i < nList.getLength(); i++) {

            Node node = nList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) node;

                arrList.add(eElement.getTextContent());
            }
        }
        return arrList;
    }
}
