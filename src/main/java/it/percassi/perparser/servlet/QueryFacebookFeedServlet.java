package it.percassi.perparser.servlet;

import it.percassi.perparser.facade.QueryFacade;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Daniele Sperto
 */
@WebServlet("/getFacebookFeed")
public class QueryFacebookFeedServlet extends HttpServlet {

	private final static Logger LOG = LogManager.getLogger(QueryFacebookFeedServlet.class);

	@Autowired
	@Qualifier("queryFacade")
	private QueryFacade queryFacade;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Enumeration params = request.getParameterNames();
//		while (params.hasMoreElements()) {
//			String paramName = (String) params.nextElement();
//			LOG.trace("Parameter Name - " + paramName + ", Value - " + request.getParameter(paramName));
//		}
		try {
			String filters = getBody(request);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");			

			Integer start = 0;
			try {
				start = Integer.parseInt(request.getParameter("start"));
			} catch (Exception e) {
				LOG.warn("Unable to parse start parameter");
			}
			Integer length = 10;
			try {
				length = Integer.parseInt(request.getParameter("length"));
			} catch (Exception e) {
				LOG.warn("Unable to parse length parameter");
			}

			PrintWriter out = response.getWriter();

			JSONObject ret = new JSONObject();
			ret = queryFacade.getFacebookFeed(filters, start, length);
			response.setStatus(HttpServletResponse.SC_OK);
			out.print(ret);
			out.flush();

		} catch (Exception e) {
			LOG.error("", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/*Se lo chiami due volte scoppia!*/
	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			LOG.warn("",ex);
			return "";
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}

}
