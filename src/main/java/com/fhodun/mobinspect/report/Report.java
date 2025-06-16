package com.fhodun.mobinspect.report;

public class Report {
    private String title;
    private String content;

    public Report(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return """
                Report{
                    title='%s',
                    content='%s'
                }""".formatted(title, content);
    }
}