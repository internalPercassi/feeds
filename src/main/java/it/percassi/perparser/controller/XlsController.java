/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.percassi.perparser.controller;

import it.percassi.perparser.controller.request.GetDocumentsRequest;
import it.percassi.perparser.controller.validator.GetDocumentsRequestValidator;
import it.percassi.perparser.exception.NotValidFilterException;
import it.percassi.perparser.facade.CsvFacade;
import it.percassi.perparser.facade.QueryFacade;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author spedan
 */
@RestController
public class XlsController {

    @Autowired
    @Qualifier("queryFacade")
    private QueryFacade queryFacade;

    @Autowired
    @Qualifier("csvFacade")
    private CsvFacade cvsFacade;

    private final static Logger LOG = LogManager.getLogger(XlsController.class);

    @RequestMapping(value = "getXls", method = RequestMethod.POST)
    public void getFile(GetDocumentsRequest request, BindingResult bindingResult, HttpServletResponse response) throws IOException, NoSuchFieldException, NotValidFilterException, ParseException {
        LOG.info("Request is {}", request.toString());

        final GetDocumentsRequestValidator getDocumentsRequestValidator = new GetDocumentsRequestValidator();

        getDocumentsRequestValidator.validate(request, bindingResult);

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            final String errorMessage = ControllerUtils.generateErrorMessage(errors);
            response.sendError(HttpStatus.BAD_REQUEST.value(), errorMessage);
        }

        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String xlsFileName = request.getCollectionName() + sdf.format(new Date()) + ".xlsx";
        
        final JSONObject jsonObj = queryFacade.getDocs(request);
        XSSFWorkbook wb = cvsFacade.getXls((JSONArray) jsonObj.get("data"));
        response.setHeader("Content-Disposition", "form-data; name=\"Content-Disposition\"; filename=\""+xlsFileName+"\"");
        response.setHeader("Content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        OutputStream os = response.getOutputStream();
        wb.write(os);

        response.flushBuffer();
    }

}
