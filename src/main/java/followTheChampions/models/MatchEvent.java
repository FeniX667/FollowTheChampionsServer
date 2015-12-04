package followTheChampions.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "MatchEvent")
public class MatchEvent extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "match")
    @JsonIgnore
    Match match;

    @Column(name = "type")
    String type;

    @Column(name = "minute")
    String minute;

    @Column(name = "whichTeam")
    String whichTeam;

    @Column(name = "playerName")
    String playerName;

    @Column(name = "result")
    String result;

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getWhichTeam() {
        return whichTeam;
    }

    public void setWhichTeam(String whichTeam) {
        this.whichTeam = whichTeam;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
