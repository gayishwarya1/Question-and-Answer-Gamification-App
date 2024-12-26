package fr.utcapitole.demo.entities;

import javax.persistence.Entity;

@Entity
public class Answer extends Message {
    private int likes;
    private boolean pinned;
    private Integer questionId;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}