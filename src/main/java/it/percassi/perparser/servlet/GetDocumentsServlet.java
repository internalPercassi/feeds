package it.percassi.perparser.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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

import it.percassi.perparser.facade.CsvFacade;
import it.percassi.perparser.facade.QueryFacade;

/**
 *
 * @author Daniele Sperto
 */
//@WebServlet("/getDocuments")
public class GetDocumentsServlet extends HttpServlet {


	private static final long serialVersionUID = -3822679557857971497L;

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

	private static final int DEFAULT_ROWSET_LENGTH = 100000;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String filters = null;
			if (StringUtils.isNotBlank(request.getParameter("filters"))){
				filters = URLDecoder.decode(request.getParameter("filters"),"UTF-8");
			}					
			String[] excludes = request.getParameterValues("exclude");
			String collectionName = request.getParameter("collectionName");

			String sortField = StringUtils.isBlank(request.getParameter("sortField")) ? null : request.getParameter("sortField");
			Integer sortType = null;
			if (request.getParameter("sortType") != null) {
				try {
					sortType = Integer.parseInt(request.getParameter("sortType"));
				} catch (NumberFormatException nfe) {
					LOG.debug("Param sortType is not numeric, is " + sortType);
				}
			}

			Integer start = 0;
			if (StringUtils.isNumeric(request.getParameter("start"))) {
				start = Integer.parseInt(request.getParameter("start"));
			}
			Integer length = DEFAULT_ROWSET_LENGTH;
			if (request.getParameter("length") != null) {
				try {
					length = Integer.parseInt(request.getParameter("length"));
				} catch (NumberFormatException nfe) {
					LOG.warn("Param length is not numeric, is " + sortType+", set to default: "+DEFAULT_ROWSET_LENGTH);
				}
			}
			
			Boolean getCsv = Boolean.parseBoolean(request.getParameter("getCsv"));

			JSONObject ret = new JSONObject();
			ret = queryFacade.getDocs(collectionName, filters, excludes, sortField, sortType, start, length);
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
