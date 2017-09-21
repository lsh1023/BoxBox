package com.lsh.boxbox.model;

import java.util.List;

/**
 * Created by LSH on 2017/9/21.
 */

public class FindBg {


    private List<ImagesBean> images;

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * startdate : 20170302
         * fullstartdate : 201703021600
         * enddate : 20170303
         * url : /az/hprichbg/rb/SpringbokHerd_ZH-CN11603112082_1920x1080.jpg
         * urlbase : /az/hprichbg/rb/SpringbokHerd_ZH-CN11603112082
         * copyright : 在南非卡拉哈里沙漠地区的一群雄性跳羚(© Minden Pictures/Masterfile)
         * copyrightlink : http://www.bing.com/search?q=%E8%B7%B3%E7%BE%9A&form=hpcapt&mkt=zh-cn
         * quiz : /search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20170302_SpringbokHerd%22&FORM=HPQUIZ
         * wp : true
         * hsh : 5dd7d323df4e79f5f1c383876d027bb1
         * drk : 1
         * top : 1
         * bot : 1
         * hs : []
         */

        private String startdate;
        private String fullstartdate;
        private String enddate;
        private String url;
        private String urlbase;
        private String copyright;
        private String copyrightlink;
        private String quiz;

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getFullstartdate() {
            return fullstartdate;
        }

        public void setFullstartdate(String fullstartdate) {
            this.fullstartdate = fullstartdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlbase() {
            return urlbase;
        }

        public void setUrlbase(String urlbase) {
            this.urlbase = urlbase;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getCopyrightlink() {
            return copyrightlink;
        }

        public void setCopyrightlink(String copyrightlink) {
            this.copyrightlink = copyrightlink;
        }

        public String getQuiz() {
            return quiz;
        }

        public void setQuiz(String quiz) {
            this.quiz = quiz;
        }

    }
}
