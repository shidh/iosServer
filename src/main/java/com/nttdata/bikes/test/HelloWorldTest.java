package com.nttdata.bikes.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorldTest.
 * @author Isabel Max
 */
public class HelloWorldTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorldTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      // MIME-Typ der Antwort bestimmen
	      response.setContentType("text/html");

	      // Writer holen
	      PrintWriter out = response.getWriter();

	      // Print HTML test page
	      out.println("<html>");
	      out.println("<head>");
	      out.println("<title>NTT Data Test Page</title>");
	      out.println("</head>");
	      out.println("<body bgcolor=\"white\">");
	      out.println("<h1>Hello World from HNG</h1>");
	      out.println("</body>");
	      out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
