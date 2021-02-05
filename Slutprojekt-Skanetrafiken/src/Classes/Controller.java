package Classes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	    response.setContentType("text/html");
        
	    // Tar emot parametrar.
        String search = request.getParameter("search");
        String station = request.getParameter("station");
        
        // Skickar användaren till en av två sidor baserat på var användaren kom ifrån.
        // index.jsp har en search parameter och station.jsp har en station parameter.
        if (search != null) {
            RequestDispatcher rd = request.getRequestDispatcher("PrintStations");
            rd.forward(request, response);
        } else if (station != null) {
            RequestDispatcher rd = request.getRequestDispatcher("PrintBustimes");
            rd.forward(request, response);
        }
	}
}
