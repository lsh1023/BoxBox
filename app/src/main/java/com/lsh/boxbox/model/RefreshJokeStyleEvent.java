package com.lsh.boxbox.model;

/**
 * Created by LSH on 2017/9/28.
 */

public class RefreshJokeStyleEvent {
    private int JokeStyle;

    public RefreshJokeStyleEvent(int jokeStyle) {
        JokeStyle = jokeStyle;
    }

    public int getJokeStyle() {
        return JokeStyle;
    }

    public void setJokeStyle(int jokeStyle) {
        JokeStyle = jokeStyle;
    }

}
