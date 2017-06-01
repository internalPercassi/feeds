package it.percassi.perparser.controller.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GetDocumentsRequest {

    private Integer length = 100000;

    private String collectionName;

    private String[] exclude;

    private Integer start = 0;

    private String filters;

    private String sortField;

    private Integer sortType;

    private boolean csv;

    private boolean xls;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String[] getExclude() {
        return exclude;
    }

    public void setExclude(String[] exclude) {
        this.exclude = exclude;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getFilters() {
        try {
            if (this.filters != null) {
                return URLDecoder.decode(this.filters, "utf-8");
            } else {
                return null;
            }
        } catch (UnsupportedEncodingException ue) {
            return this.filters;
        }
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public boolean isCsv() {
        return csv;
    }

    public void setCsv(boolean csv) {
        this.csv = csv;
    }

    public boolean isXls() {
        return xls;
    }

    public void setXls(boolean xls) {
        this.xls = xls;
    }

    @Override
    public String toString() {

        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
