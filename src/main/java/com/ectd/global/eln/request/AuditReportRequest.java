package com.ectd.global.eln.request;

import java.util.List;

import com.ectd.global.eln.audit.AuditLogDto;

public class AuditReportRequest {

    private List<AuditLogDto> logs;
    private String fromDate;
    private String toDate;
    private String userName;

    // Getters and setters
    public List<AuditLogDto> getLogs() {
        return logs;
    }

    public void setLogs(List<AuditLogDto> logs) {
        this.logs = logs;
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
}
