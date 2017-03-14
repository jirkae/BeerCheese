package edu.vse.dtos;

public class Paging {

    private final String next;
    private final Integer offset;
    private final Integer limit;

    public Paging(String next, Integer offset, Integer limit) {
        this.next = next;
        this.offset = offset;
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }
}
