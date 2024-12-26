package fr.utcapitole.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.utcapitole.demo.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDto {
    private int id;
    private String content;
    private String authorName;
    private int likes;
    private boolean pinned;
    List<Comment> comments = new ArrayList<>();
    // todo: add photo of the user ?
}

