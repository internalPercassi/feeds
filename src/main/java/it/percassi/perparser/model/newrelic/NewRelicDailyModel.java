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
public class NewRelicDailyModel implements Serializable {

    private Date day;
    private String metricName;
    private String valueName;
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

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
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
        ret.append("metricValue", this.valueName);
        ret.append("value", this.value);
        return ret;
    }
}
