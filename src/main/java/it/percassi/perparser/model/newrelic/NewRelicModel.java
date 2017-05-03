/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.percassi.perparser.model.newrelic;

import java.io.Serializable;
import java.util.Date;
import org.bson.Document;

/**
 *
 * @author Daniele Sperto
 */
public class NewRelicModel implements Serializable {

    private Date day;
    private String metricName;
    private String metricValue;
    private Float value;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Document toBSONDoc() {
        Document ret = new Document();
        ret.append("day", this.day);
        ret.append("metricName", this.metricName);
        ret.append("metricValue", this.metricValue);
        ret.append("value", this.value);
        return ret;
    }
}
