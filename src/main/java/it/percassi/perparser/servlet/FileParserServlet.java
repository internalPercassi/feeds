package it.percassi.perparser.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import it.percassi.perparser.facade.ParserFacade;

/**
 *
 * @author Daniele Sperto
 */
@WebServlet("/parseFile")
@MultipartConfig
public class FileParserServlet extends HttpServlet {


	private static final long serialVersionUID = 7206809751115899296L;
	private final static Logger LOG = LogManager.getLogger(FileParserServlet.class);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

//	@Autowired
//	@Qualifier("parserFacade")
//	private ParserService parserFacade;
	@Autowired
	@Qualifier("parserFacade")
	private ParserFacade parserFacade;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		String fileType = request.getParameter("fileType"); // Retrieves <input type="text" name="description">
		Part filePart = request.getPart("uploadedFile"); // Retrieves <input type="file" name="file">						
		String fileName = getFileName(filePart);
		try {
			byte[] bytes = IOUtils.toByteArray(filePart.getInputStream());
			if (bytes.length == 0) {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				return;
			}
			String md5 = parserFacade.parseAndSave(fileName,fileType, bytes);
			response.setStatus(HttpServletResponse.SC_OK);
			PrintWriter out = response.getWriter();
			JSONObject ret = new JSONObject();
			ret.put("md5", md5);
			out.print(ret);
			out.flush();

		} catch (Exception e) {
			LOG.error("", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(
						content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

}
