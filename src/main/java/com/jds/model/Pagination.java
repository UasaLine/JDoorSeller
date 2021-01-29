package com.jds.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {
    private int limit;
    private int offset;

    public Pagination(String limit, String offset) {
        this.limit = Integer.parseInt(limit);
        this.offset = Integer.parseInt(offset);
    }
}
