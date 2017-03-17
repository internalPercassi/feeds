package it.percassi.perparser.servlet;

import it.percassi.perparser.parsers.BaseParser;
import it.percassi.perparser.parsers.ParserProvider;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Daniele Sperto
 */
@WebServlet("/parseFile")
@MultipartConfig
public class FileParserServlet extends HttpServlet {

	private final static Logger LOG = LogManager.getLogger(FileParserServlet.class);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	@Autowired
	@Qualifier("parserProvider")
	private ParserProvider parserProvider;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String fileType = request.getParameter("fileType"); // Retrieves <input type="text" name="description">
		Part filePart = request.getPart("uploadedFile"); // Retrieves <input type="file" name="file">				
		// ... (do your job here)

		try {
			JSONObject obj = new JSONObject();
			InputStream inputStream = filePart.getInputStream();

			BaseParser parser = parserProvider.getParser(fileType);
			obj = parser.parseToJson(inputStream);
			PrintWriter out = response.getWriter();
			out.print(obj);
			out.flush();

		} catch (Exception e) {
			LOG.error("", e);
		}
	}
}
