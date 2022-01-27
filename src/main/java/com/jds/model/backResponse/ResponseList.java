package com.jds.model.backResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseList<T> extends Response {
    List<T> list;
    long total;

    public ResponseList(boolean success, String message, List<T> list, long total) {
        super(success, message);
        this.list = list;
        this.total = total;
    }

    public ResponseList(List<T> list) {
        super(true, "ok");
        this.list = list;
        this.total = list.size();
    }
}
