package fr.utcapitole.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.utcapitole.demo.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaderBoard {
    private String username;
    private int score;
    private String id;
    private String team;
}
