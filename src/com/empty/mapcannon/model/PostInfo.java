
package com.empty.mapcannon.model;

import java.io.Serializable;

public class PostInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private String nickname;
    private String posttime;
    private String content;
    private String destination;
    private String departure;
    private String departtime;
    private String commentid;
    private int withOthers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDeparttime() {
        return departtime;
    }

    public void setDeparttime(String departtime) {
        this.departtime = departtime;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public int getWithOthers() {
        return withOthers;
    }

    public void setWithOthers(int withOthers) {
        this.withOthers = withOthers;
    }

}
