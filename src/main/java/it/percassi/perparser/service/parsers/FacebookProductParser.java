package it.percassi.perparser.service.parsers;

import it.percassi.perparser.facade.model.AppEnum;
import it.percassi.perparser.facade.model.AppEnum.FacebookAvailability;
import it.percassi.perparser.exception.NotValidFileException;
import it.percassi.perparser.service.parsers.model.FacebookFeed;
import it.percassi.perparser.service.parsers.model.RegexPatterns;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("facebookProductParser")
public class FacebookProductParser extends BaseParser<FacebookFeed> {
    
    private final static Logger LOG = LogManager.getLogger(FacebookProductParser.class);
    
    private static final int ROW_LENGTH = 27;
    
    @Autowired
    private MessageSource googleTaxonomySource;
    
    @Override
    public List<FacebookFeed> parse(InputStream stream, Locale locale) throws IOException, NotValidFileException {
        List<FacebookFeed> ret = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        int c = 0;
        Set<String> distinctCat = new HashSet<String>();
        while ((line = bufferedReader.readLine()) != null) {
            if (c == 0) {
                isLineValid(line);
            }
            c++;
            FacebookFeed fbp = this.build(line,distinctCat,locale);
            if (fbp != null) {
                ret.add(fbp);
            }
        }
        String[] array = distinctCat.toArray(new String[0]);
        LOG.trace(Arrays.toString(array));
        return ret;
    }
    
    private FacebookFeed build(String line, Set distinctCat,Locale locale) throws NotValidFileException {
        FacebookFeed ret = new FacebookFeed();
        String[] tokens = StringUtils.splitPreserveAllTokens(line, FacebookFeed.FIELD_SEPARATOR);
        String priceTmp = "";
        String idTmp = "";
        String currencyCodeTmp = "";
        for (int c = 0; c < tokens.length; c++) {
            String tmp = tokens[c].trim();
            switch (c + 1) {
                case 1:
                    idTmp = tmp;
                    break;
                case 2:
                    ret.setTitle(tmp);
                    break;
                case 3:
                    ret.setDescription(tmp);
                    break;
                case 6:
                    priceTmp = tmp;
                    break;
                case 7:
                    ret.setBrand(tmp);
                    break;
                case 8:
                    ret.setLink(tmp);
                    break;
                case 10:
                    currencyCodeTmp = tmp;
                    break;
                case 12:
                    if (!StringUtils.equalsAnyIgnoreCase(tmp, locale.getLanguage())){
                        return null;
                    }
//                    idTmp += ("_" + tmp);
                    break;
                case 15:
                    String googleTaxonomy;
                    distinctCat.add(tmp);
                    try {
                        googleTaxonomy = googleTaxonomySource.getMessage(tmp, null, locale);
                        ret.setGoogle_product_category(googleTaxonomy);
                    } catch (Exception e) {
                        googleTaxonomy = googleTaxonomySource.getMessage("DEFAULT", null, locale);
                        ret.setGoogle_product_category(googleTaxonomy);
                    }
                    break;
                case 16:
                    ret.setProduct_type(tmp);
                    break;
                case 17:
                    ret.setImage_link(tmp);
                    break;
                case 20:
                    FacebookAvailability availabityTmp = AppEnum.FacebookAvailability.fromString(tmp);
                    if (availabityTmp == null) {
                        LOG.info("Availability is null, set OUT_OF_STOCK");
                        availabityTmp = FacebookAvailability.OUTOFSTOCK;
                    }
                    ret.setAvailability(availabityTmp.getFbCode());
                    break;
                case 23:
                    ret.setItem_group_id(tmp);
                    break;
                case 27:
                    ret.setColor(tmp);
                    break;
            }
        }
        ret.setId(idTmp);
        ret.setPrice(priceTmp + " " + currencyCodeTmp);
        return ret;
    }
    
    public void isLineValid(String line) throws NotValidFileException {
        String[] tokens = StringUtils.splitPreserveAllTokens(line, FacebookFeed.FIELD_SEPARATOR);
        int linkIdx = 7;
        int imgLinkIdx = 16;
        int availabilityIdx = 19;
        Matcher matcher;
        if (tokens.length != ROW_LENGTH) {
            throw new NotValidFileException("Row length expected " + ROW_LENGTH + ", get " + tokens.length);
        }
        
        matcher = RegexPatterns.URL_PATTERN.matcher(tokens[linkIdx].trim());
        if (!matcher.matches()) {
            throw new NotValidFileException("Invalid link URL found: ***" + tokens[linkIdx] + "***");
        }
        
        matcher = RegexPatterns.URL_PATTERN.matcher(tokens[imgLinkIdx].trim());
        if (!matcher.matches()) {
            throw new NotValidFileException("Invalid img URL found: ***" + tokens[imgLinkIdx] + "***");
        }
        
        String availability = AppEnum.FacebookAvailability.fromString(tokens[availabilityIdx].trim()).getFbCode();
        if (availability == null) {
            throw new NotValidFileException("Invalid availabity format: ***" + tokens[availabilityIdx] + "***");
        }
    }
}
