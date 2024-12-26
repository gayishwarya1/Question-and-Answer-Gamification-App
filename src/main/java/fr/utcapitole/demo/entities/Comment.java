package fr.utcapitole.demo.entities;

import javax.persistence.Entity;

@Entity
public class Comment  extends Answer {
    private Integer answerId;

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }
}
