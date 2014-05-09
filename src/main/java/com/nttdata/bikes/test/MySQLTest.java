package com.nttdata.bikes.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MySQLTest
 */
public class MySQLTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MySQLTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// determine MIME-Type
		response.setContentType("text/html");

		// get writer
		PrintWriter out = response.getWriter();

		// indicate DB start
		out.println("<p>-------- MySQL "
				+ "JDBC Connection Testing ------------</p>");

		// try to get MySQL driver
		try {

			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {

			out.println("<p>Where is your MySQL JDBC Driver? "
					+ "Include in your library path!</p>");
			e.printStackTrace();
			return;
		}

		// indicate that MySQL driver is available
		out.println("<p>MySQL JDBC Driver Registered!</p>");

		// try to establish DB connection
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/nttdata",
					"nttdata", "password");
		} catch (SQLException e) {
			out.println("<p>Connection Failed! Check output console" + e.getMessage() + "</p>");
			e.printStackTrace();
			return;
		}

		// try to establish connection
		if (connection == null) {
			out.println("<p>Failed to make connection!</p>");
		} else {
			out.println("<p>You made it, take control your database now!</p>");
			try {
				
				// try to get table's matadata (column headers)
				DatabaseMetaData metadata = connection.getMetaData();
				ResultSet resultSet;
				resultSet = metadata.getColumns(null, null, "testrelation",null);
				
				//print results
				out.println("<table><tr>");
				while (resultSet.next()) {
					String name = resultSet.getString("COLUMN_NAME");
					String type = resultSet.getString("TYPE_NAME");
					out.println("<td>" + name + " ("
							+ type + ")</td>");
				}
				out.println("</tr>");
			} catch (SQLException e1) {

			}

			// get table's contents
			Statement stmt = null;
			ResultSet rs = null;
			try {
				// prepare SQL statement
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT * FROM testrelation");

				//out.println("<p>");
				
				// print results
				while (rs.next()) {
					String name = rs.getString("ID");
					String key = rs.getString("key");
					String value = rs.getString("value");
					out.println("<tr><td>" + name + "</td><td>" + key
							+ "</td><td>" + value + "</td></tr>");
				}
				out.print("</table>");

			} catch (Exception e) {
				out.println("<p>Error: " + e.toString() + "</p>");
			} finally {
				// deallocate resources
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlEx) { // ignore
						
					}

					rs = null;
				}

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) { // ignore

					}

					stmt = null;
				}
			}

			// close connection
			try {
				connection.close();
			} catch (Exception e) {
				out.println("<p>could not close connection</p>");
			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
