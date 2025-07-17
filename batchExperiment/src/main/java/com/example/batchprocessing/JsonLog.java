package com.example.batchprocessing;

public class JsonLog {

    private String stream;
    private String log;
    private String logs;
    private String record_date;
    private String time;
    private String kubernetes;

    public String getKubernetes() {
        return kubernetes;
    }

    public void setKubernetes(String kubernetes) {
        this.kubernetes = kubernetes;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
