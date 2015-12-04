package followTheChampions.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Match")
public class Match extends BasicEntity {

    @Column(name = "matchDate")
    String matchDate;

    @Column(name = "status")
    String status;

    @Column(name = "time")
    String time;

    @ManyToOne
    @JoinColumn(name = "localTeam")
    Team localTeam;

    @ManyToOne
    @JoinColumn(name = "visitorTeam")
    Team visitorTeam;

    @OneToMany(mappedBy = "match")
    List<MatchEvent> matchEventList;

    @Column(name = "matchHtScore")
    String matchHtScore;

    @Column(name = "matchFtScore")
    String matchFtScore;

    public String getMatchHtScore() {
        return matchHtScore;
    }

    public void setMatchHtScore(String matchHtScore) {
        this.matchHtScore = matchHtScore;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Team getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(Team localTeam) {
        this.localTeam = localTeam;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(Team visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public String getMatchFtScore() {
        return matchFtScore;
    }

    public void setMatchFtScore(String matchFtScore) {
        this.matchFtScore = matchFtScore;
    }

    public List<MatchEvent> getMatchEventList() {
        return matchEventList;
    }

    public void setMatchEventList(List<MatchEvent> matchEventList) {
        this.matchEventList = matchEventList;
    }
}
