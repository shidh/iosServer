package com.nttdata.bikes.entities.datafixtures;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nttdata.bikes.constants.Constants;

/**
 * Servlet implementation class DataFixturesController.
 * You load the new DatabaseData With the URL: http://ios13nttdatabruegge.in.tum.de:8080/nttdata/loaddatafixtures
 * @author Isabel Max
 */
public class DataFixturesController
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  private static EntityManagerFactory factory;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DataFixturesController()
  {
    super();
    // TODO Auto-generated constructor stub
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
    this.loadFixtures(null);
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {

    // MIME-Typ der Antwort bestimmen
    response.setContentType("text/html");

    // Writer holen
    PrintWriter out = response.getWriter();

    // Print HTML test page
    out.println("<html>");
    out.println("<head>");
    out.println("<title>Test</title>");
    out.println("</head>");
    out.println("<body bgcolor=\"white\">");
    out.println("<h1>Loading Data Fixtures</h1>");

    this.loadFixtures(out);

    out.println("</body>");
    out.println("</html>");
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    // nothing todo
  }

  private void loadFixtures(PrintWriter out)
  {
    // don't use singleton here in order to create tables
    factory = Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT_NAME);

    EntityManager em = factory.createEntityManager();

    // Load Data Fixtures here

    try {
      (new LoadDatabaseData()).loadEntityData(em, out);
    }
    finally {
      em.close();
    }
  }
}
