package it.percassi.perparser.facade.model;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

/**
 *
 * @author Daniele Sperto
 */
public class UploadedFileModel implements Serializable {

    private final static Logger LOG = LogManager.getLogger(UploadedFileModel.class);

    private String md5;
    private String fileName;
    private String type;
//	@JsonSerialize(using = IsoDateSerializer.class)
    private Date date;
//	private Integer rowCount;

    public UploadedFileModel() {
    }

    public UploadedFileModel(String fileName, byte[] bytes, String type) throws IOException {
        String md5 = this.getMD5(bytes);
        this.date = new Date();
        this.md5 = md5;
//		this.rowCount = 0;
        this.type = type;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//	public Integer getRowCount() {
//		return rowCount;
//	}
//
//	public void setRowCount(Integer rowCount) {
//		this.rowCount = rowCount;
//	}
    private static String getMD5(byte[] bytes) throws IOException {
        String md5 = DigestUtils.md5Hex(bytes);
        LOG.debug("getMD5, buffer length: "+bytes.length);
        return md5;
    }

    private Date getUploadDate() {
        Date ret = new Date();
        try {
            if (AppEnum.FileType.FACEBOOK.getCode().equalsIgnoreCase(this.type)) {
                // es. nome file: KIK_YYYYMMDDhhmm
                String inFileNameDate = StringUtils.substring(this.fileName, 4, 12); 
                DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                ret = formatter.parse(inFileNameDate);
            } else if (AppEnum.FileType.GL.getCode().equalsIgnoreCase(this.type)) {
                //GL_KIK_2016100102000309
                String inFileNameDate = StringUtils.substring(this.fileName, 7, 15);
                DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                ret = formatter.parse(inFileNameDate);
            } else if (AppEnum.FileType.OS.getCode().equalsIgnoreCase(this.type)) {
                //Hybris_OversellingExt_KIK_20170516060024.txt_20170516090921550
                String inFileNameDate = StringUtils.substring(this.fileName, 26, 34); 
                DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                ret = formatter.parse(inFileNameDate);
            }
        } catch (Exception e) {
            LOG.warn("Unable to get date from fileName for the file: ***"+this.fileName+"***");
            ret = new Date();
        }
        return ret;
    }

    public Document toBSONDoc() {
        Document ret = new Document();
        ret.append("md5", this.md5);
        ret.append("fileName", this.fileName);
        ret.append("type", this.type);
        ret.append("date", this.getUploadDate());
        return ret;
    }
}
