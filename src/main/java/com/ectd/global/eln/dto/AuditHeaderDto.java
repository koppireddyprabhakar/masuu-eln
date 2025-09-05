package com.ectd.global.eln.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

public class AuditHeaderDto {

    private String fromDate;
    private String toDate;
    private String userName;
    private String generatedOn;

    private Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
    private Font subFont    = FontFactory.getFont(FontFactory.HELVETICA, 11);

    public AuditHeaderDto(String fromDate, String toDate, String userName) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.userName = userName;
        this.generatedOn = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
    }

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGeneratedOn() {
		return generatedOn;
	}

	public void setGeneratedOn(String generatedOn) {
		this.generatedOn = generatedOn;
	}

	public Font getHeaderFont() {
		return headerFont;
	}

	public void setHeaderFont(Font headerFont) {
		this.headerFont = headerFont;
	}

	public Font getSubFont() {
		return subFont;
	}

	public void setSubFont(Font subFont) {
		this.subFont = subFont;
	}
}
