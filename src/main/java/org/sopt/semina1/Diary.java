package org.sopt.semina1;

public class Diary {
    private Long id;
    private final String body;

    public Diary(Long id, String body) {
        this.id = id;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getId() {
        return id.toString();
    }
}
