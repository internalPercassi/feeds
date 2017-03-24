package it.percassi.perparser.servlet;

import it.percassi.perparser.facade.CsvFacade;
import it.percassi.perparser.facade.QueryFacade;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Daniele Sperto
 */
@WebServlet("/getDocuments")
public class GetDocumentsServlet extends HttpServlet {

	private final static Logger LOG = LogManager.getLogger(GetDocumentsServlet.class);

	@Autowired
	@Qualifier("queryFacade")
	private QueryFacade queryFacade;

	@Autowired
	@Qualifier("csvFacade")
	private CsvFacade cvsFacade;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			LOG.trace("Parameter Name - " + paramName + ", Value - " + request.getParameter(paramName));
		}
		try {
			String filters = request.getParameter("filters");
			String collectionName = request.getParameter("collectionName");
			Integer start = 0;
			if (StringUtils.isNumeric(request.getParameter("start"))) {
				start = Integer.parseInt(request.getParameter("start"));
			}
			Integer length = 10;
			if (StringUtils.isNumeric(request.getParameter("length"))) {
				length = Integer.parseInt(request.getParameter("length"));
			}
			Boolean getCsv = Boolean.parseBoolean(request.getParameter("getCsv"));

			JSONObject ret = new JSONObject();
			ret = queryFacade.getDocs(collectionName,filters, start, length);			
			if (getCsv) {
				response.setContentType("text/csv");
				response.setHeader("Content-Disposition", "attachment; filename=\"parParser.csv\"");				
				OutputStream outputStream = response.getOutputStream();
				StringBuffer buf = cvsFacade.getCvs((JSONArray) ret.get("data"));
				outputStream.write(buf.toString().getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			} else {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print(ret);
				out.flush();
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			LOG.error("", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
