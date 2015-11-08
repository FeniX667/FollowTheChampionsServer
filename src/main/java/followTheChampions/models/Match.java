package followTheChampions.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "Match")
public class Match extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "competition")
    Competition competition;

    @Column(name = "matchDate")
    @NotEmpty
    DateTime matchDate;

    @Column(name = "status")
    @NotEmpty
    String status;

    @Column(name = "time")
    @NotEmpty
    String time;

    @Column(name = "isCommentaryAvailable")
    @NotEmpty
    Boolean isCommentaryAvailable;

    @ManyToOne
    @JoinColumn(name = "localTeam")
    Team localTeam;

    @Column(name = "localTeamScore")
    @NotEmpty
    String localTeamScore;

    @ManyToOne
    @JoinColumn(name = "visitorTeam")
    @NotEmpty
    Team visitorTeam;

    @Column(name = "visitorTeamScore")
    @NotEmpty
    String visitorTeamScore;

    @Column(name = "matchHtScore")
    @NotEmpty
    String matchHtScore;

    public String getMatchHtScore() {
        return matchHtScore;
    }

    public void setMatchHtScore(String matchHtScore) {
        this.matchHtScore = matchHtScore;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public DateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(DateTime matchDate) {
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

    public Boolean getIsCommentaryAvailable() {
        return isCommentaryAvailable;
    }

    public void setIsCommentaryAvailable(Boolean isCommentaryAvailable) {
        this.isCommentaryAvailable = isCommentaryAvailable;
    }

    public Team getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(Team localTeam) {
        this.localTeam = localTeam;
    }

    public String getLocalTeamScore() {
        return localTeamScore;
    }

    public void setLocalTeamScore(String localTeamScore) {
        this.localTeamScore = localTeamScore;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(Team visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public String getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(String visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }
}
