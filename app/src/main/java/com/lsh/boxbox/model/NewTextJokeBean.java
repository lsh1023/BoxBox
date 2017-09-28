package com.lsh.boxbox.model;

import java.util.List;

/**
 * Created by LSH on 2017/9/28.
 */

public class NewTextJokeBean extends BaseJokeBean {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<TextJokeBean> data;

        public List<TextJokeBean> getData() {
            return data;
        }

        public void setData(List<TextJokeBean> data) {
            this.data = data;
        }

    }
}
