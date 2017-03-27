package it.percassi.perparser.servlet;

/**
 *
 * @author Daniele Sperto
 */
////@WebServlet("/getDocuments")
public class CsvServlet{

//	private final static Logger LOG = LogManager.getLogger(GetDocumentsServlet.class); extends HttpServlet 
//
//	@Autowired
//	@Qualifier("queryFacade")
//	private QueryFacade queryFacade;
//
//	@Autowired
//	@Qualifier("csvFacade")
//	private CsvFacade cvsFacade;
//
//	public void init(ServletConfig config) throws ServletException {
//		super.init(config);
//		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		try {
//			String filters = request.getParameter("filters");
//			String[] excludes = request.getParameterValues("exclude");
//			String collectionName = request.getParameter("collectionName");
//
//			String sortField = StringUtils.isBlank(request.getParameter("sortField")) ? null : request.getParameter("sortField");
//			Integer sortType = null;
//			if (request.getParameter("sortType") != null) {
//				try {
//					sortType = Integer.parseInt(request.getParameter("sortType"));
//				} catch (NumberFormatException nfe) {
//					LOG.warn("Param sortType is not numeric, is " + sortType);
//				}
//			}
//
//			Integer start = 0;
//			if (StringUtils.isNumeric(request.getParameter("start"))) {
//				start = Integer.parseInt(request.getParameter("start"));
//			}
//			Integer length = 10;
//			if (StringUtils.isNumeric(request.getParameter("length"))) {
//				length = Integer.parseInt(request.getParameter("length"));
//			}
//
//			JSONObject ret = new JSONObject();
//			ret = queryFacade.getDocs(collectionName, filters, excludes, null, null, 0, -1);
//
//			response.setContentType("text/csv");
//			response.setHeader("Content-Disposition", "attachment; filename=\"parParser.csv\"");
//			OutputStream outputStream = response.getOutputStream();
//			StringBuffer buf = cvsFacade.getCvs((JSONArray) ret.get("data"));
//			outputStream.write(buf.toString().getBytes("UTF-8"));
//			outputStream.flush();
//			outputStream.close();
//
//			response.setStatus(HttpServletResponse.SC_OK);
//		} catch (Exception e) {
//			LOG.error("", e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//		}
//	}

}
