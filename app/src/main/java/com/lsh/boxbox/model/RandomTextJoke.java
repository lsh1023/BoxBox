package com.lsh.boxbox.model;

import java.util.List;

/**
 * Created by LSH on 2017/9/28.
 */

public class RandomTextJoke extends BaseJokeBean{
    private List<TextJokeBean> result;

    public List<TextJokeBean> getResult() {
        return result;
    }

    public void setResult(List<TextJokeBean> result) {
        this.result = result;
    }
}
