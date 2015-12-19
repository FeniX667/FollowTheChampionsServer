package followTheChampions.dto;

import followTheChampions.models.Match;
import followTheChampions.models.MatchEvent;
import followTheChampions.models.Team;

import java.util.List;

public class MatchPersonalizedDTO {

    private Long id;
    private String matchDate;
    private String status;
    private String time;
    private Team localTeam;
    private Team visitorTeam;
    private List<MatchEvent> matchEventList;
    private String matchHtScore;
    private String matchFtScore;
    private Boolean isFavourite;

    public MatchPersonalizedDTO(){}

    public MatchPersonalizedDTO(Match match, Boolean isFavourite){
        id = match.getId();
        matchDate = match.getMatchDate();
        status = match.getStatus();
        time = match.getTime();
        localTeam = match.getLocalTeam();
        visitorTeam = match.getVisitorTeam();
        matchEventList = match.getMatchEventList();
        matchHtScore = match.getMatchHtScore();
        matchFtScore = match.getMatchFtScore();
        this.isFavourite = isFavourite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<MatchEvent> getMatchEventList() {
        return matchEventList;
    }

    public void setMatchEventList(List<MatchEvent> matchEventList) {
        this.matchEventList = matchEventList;
    }

    public String getMatchHtScore() {
        return matchHtScore;
    }

    public void setMatchHtScore(String matchHtScore) {
        this.matchHtScore = matchHtScore;
    }

    public String getMatchFtScore() {
        return matchFtScore;
    }

    public void setMatchFtScore(String matchFtScore) {
        this.matchFtScore = matchFtScore;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}
