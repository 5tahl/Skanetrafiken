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
 * Servlet implementation class PrintBustimes
 */
@WebServlet("/PrintBustimes")
public class PrintBustimes extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintBustimes() {
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
        String station = request.getParameter("station");

        // Skapar URL:en.
        String URLtoSend = "http://www.labs.skanetrafiken.se/v2.2/stationresults.asp?selPointFrKey=" + station;

        // Omvandlar URL:en till XML.
        Document doc = ApiConverter.convertStringToXml(URLtoSend);

        // Hämtar all data som ska visas. Omvandlar och sparar den sedan i ArrayList.
        ArrayList<String> listOfBusNames = ApiConverter.convertNodeListToArrayList(doc.getElementsByTagName("Name"));
        ArrayList<String> listOfBusNumbers = ApiConverter.convertNodeListToArrayList(doc.getElementsByTagName("No"));
        ArrayList<String> listOfTime = ApiConverter
                .convertNodeListToArrayList(doc.getElementsByTagName("JourneyDateTime"));
        ArrayList<String> listOfBusType = ApiConverter
                .convertNodeListToArrayList(doc.getElementsByTagName("LineTypeName"));
        ArrayList<String> listOfTowards = ApiConverter.convertNodeListToArrayList(doc.getElementsByTagName("Towards"));

        RequestDispatcher rd = request.getRequestDispatcher("bus-times.jsp");
        rd.include(request, response);

        // Kollar om användaren har accepterat cookies.
        if (ApiConverter.getOkCookies().equals("yes")) {
            Cookie ck[] = request.getCookies();

            for (Cookie c : ck) {
                if (c.getValue().equals("dark")) {
                    out.print("<div class='container h-100 dark-mode'>");
                } else if (c.getValue().equals("light")) {
                    out.print("<div class='container h-100 light-mode'>");
                }
            }
        } else {
            out.print("<div class='container h-100 light-mode'>");
        }

        out.print("<div class='container bus-div'>");

        // Skriver ut all data som hämtades.
        for (int i = 0; i < listOfBusNames.size(); i++) {

            if (listOfBusType.get(i).equals(listOfBusNames.get(i))) {

                out.print("<div class='busTimes'>");

                out.print("<div>" + listOfBusType.get(i) + " " + listOfBusNumbers.get(i) + " mot "
                        + listOfTowards.get(i) + "</div>");
                out.print("<div>" + listOfTime.get(i) + "</div>");

                out.print("</div>");
            } else {
                out.print("<div class='busTimes'>");

                out.print("<div>" + listOfBusType.get(i) + " " + listOfBusNames.get(i) + " mot " + listOfTowards.get(i)
                        + "</div>");
                out.print("<div>" + listOfTime.get(i) + "</div>");

                out.print("</div>");
            }
        }

        out.print("</div>");

        out.print("</div>");
    }
}
