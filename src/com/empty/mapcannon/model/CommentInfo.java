
package com.empty.mapcannon.model;

public class CommentInfo {

    public static final String TYPE_COMMENT = "comment";
    public static final String TYPE_MESSAGE = "message";
    public static final String TYPE_JOINED = "joined";
    private int postId;
    private String commentName;
    private String time;
    private String content;
    private String type;

    public String getType() {return type; }

    public void setType(String type) { this.type = type;}

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
