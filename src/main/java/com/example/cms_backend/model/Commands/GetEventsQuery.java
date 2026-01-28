package com.example.cms_backend.model.Commands;

// Create a new class for query input
public class GetEventsQuery {
    private Long parishId;

    public GetEventsQuery() {}

    public GetEventsQuery(Long parishId) {
        this.parishId = parishId;
    }

    public Long getParishId() {
        return parishId;
    }

    public void setParishId(Long parishId) {
        this.parishId = parishId;
    }
}

