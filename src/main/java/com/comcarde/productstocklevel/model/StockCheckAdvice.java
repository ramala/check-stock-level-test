package com.comcarde.productstocklevel.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STOCK_CHECK_ADVICE_AUDIT")
public class StockCheckAdvice {

    @Id
    @Column(name = "advice_created_date")
    private Date adviceGivenDate;

    @Column
    private String advicePayload;

    public Date getAdviceGivenDate() {
        return adviceGivenDate;
    }

    public void setAdviceGivenDate(Date adviceGivenDate) {
        this.adviceGivenDate = adviceGivenDate;
    }

    public String getAdvicePayload() {
        return advicePayload;
    }

    public void setAdvicePayload(String advicePayload) {
        this.advicePayload = advicePayload;
    }
}
