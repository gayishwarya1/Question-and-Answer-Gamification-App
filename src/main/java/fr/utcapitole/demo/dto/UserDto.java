package fr.utcapitole.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.utcapitole.demo.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String username;
    private String roles;
    private OffsetDateTime birthday;
    private Boolean locked;
    private String email;
    private Integer score;
    private String badge;
    private String team;
}
