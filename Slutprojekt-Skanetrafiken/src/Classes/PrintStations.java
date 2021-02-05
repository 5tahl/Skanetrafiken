package Classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

/**
 * Servlet implementation class PrintStations
 */
@WebServlet("/PrintStations")
public class PrintStations extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintStations() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Tar emot parametrar.
        String search = request.getParameter("search");
        String okCookie = request.getParameter("okCookie");
        
        // Sparar okCookie svaret i en ApiConverter variabel.
        ApiConverter.setOkCookies(okCookie);

        // Skapar URL:en.
        String URLtoSend = "http://www.labs.skanetrafiken.se/v2.2/querystation.asp?inpPointfr=" + search;

        // Skapar ett document med XML. ApiConverter är en custom class med
        // en metod som konverterar en URL till XML.
        Document doc = ApiConverter.convertStringToXml(URLtoSend);

        // Skapar ArrayLists med API:ns ID:s och namn. ID:sen kommer att användas för
        // att få
        // fram alla bussar som kommer gå vid vald station.
        // ApiConverter har även en custom class som konverterar en NodeList till
        // ArrayList.
        ArrayList<String> listOfID = ApiConverter.convertNodeListToArrayList(doc.getElementsByTagName("Id"));
        ArrayList<String> listOfStationNames = ApiConverter
                .convertNodeListToArrayList(doc.getElementsByTagName("Name"));

        // Skickar användaren till nästa sida.
        RequestDispatcher rd = request.getRequestDispatcher("stations.jsp");
        rd.include(request, response);

        // Kollar om användaren har accepterat cookies på föregående sida.
        if (okCookie.equals("yes")) {
            Cookie ck = new Cookie("theme", request.getParameter("theme"));

            // Sätter cookiesen så de bara varar i 5 minuter.
            ck.setMaxAge(60 * 5);
            response.addCookie(ck);

            // Om de har accepterat så kan de få ett mörk tema om de har valt det. yay
            if (ck.getValue().equals("dark")) {
                out.print("<div class='container dark-mode'>");
            } else {
                out.print("<div class='container light-mode'>");
            }
        } else {
            out.print("<div class='container light-mode'>");
        }

        // Skapar resterande av HTML:en som kommer att skickas till nästa sida.

        out.print("<div class='search-div'>");

        out.print("<div class='search-text'>Välj sökalternativ</div>");

        out.print("<form action='Controller' class='search-form'>");

        out.print("<select name='station' id='station' class='search-field'>");

        // Gör så att alla bussar som dyker upp vid sökning hamnar i en select lista.
        for (int i = 0; i < listOfID.size(); i++) {

            out.print(" <option value='" + listOfID.get(i) + "'>" + listOfStationNames.get(i) + "</option>");

        }

        out.print("</select>");
        out.print("<button type='submit' class='search-btn'>");
        out.print("<i class='fa fa-search'></i>");
        out.print("</button>");

        out.print("</form>");

        out.print("</div>");

        out.print("</div>");
    }
}
