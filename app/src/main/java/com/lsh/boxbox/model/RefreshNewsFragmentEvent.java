package com.lsh.boxbox.model;

/**
 * Created by LSH on 2017/9/15.
 */

public class RefreshNewsFragmentEvent {

    private int mark_code;

    public int getMark_code() {
        return mark_code;
    }

    public void setMark_code(int mark_code) {
        this.mark_code = mark_code;
    }

    public RefreshNewsFragmentEvent(int mark_code) {
        this.mark_code = mark_code;
    }

    public RefreshNewsFragmentEvent() {
    }
}
