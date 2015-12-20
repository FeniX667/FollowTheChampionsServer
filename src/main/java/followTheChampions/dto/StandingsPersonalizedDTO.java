package followTheChampions.dto;

import followTheChampions.models.Standing;

public class StandingsPersonalizedDTO {

    Long id;
    String standRound;
    Long standTeamId;
    String standTeamName;
    String standPosition;
    String standOverallGp;
    String standOverallW;
    String standOverallD;
    String standOverallL;
    String standOverallGs;
    String standOverallGa;
    String standPoints;
    String standDesc;

    Boolean isFavourite;

    public StandingsPersonalizedDTO(){}

    public StandingsPersonalizedDTO(Standing standing, Boolean isFavourite) {
        id = standing.getId();
        standRound = standing.getStandRound();
        standTeamId = standing.getStandTeamId();
        standTeamName = standing.getStandTeamName();
        standPosition = standing.getStandPosition();
        standOverallGp = standing.getStandOverallGp();
        standOverallW = standing.getStandOverallW();
        standOverallD = standing.getStandOverallD();
        standOverallL = standing.getStandOverallL();
        standOverallGs = standing.getStandOverallGs();
        standOverallGa = standing.getStandOverallGa();
        standPoints = standing.getStandPoints();
        standDesc = standing.getStandDesc();

        this.isFavourite = isFavourite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStandRound() {
        return standRound;
    }

    public void setStandRound(String standRound) {
        this.standRound = standRound;
    }

    public Long getStandTeamId() {
        return standTeamId;
    }

    public void setStandTeamId(Long standTeamId) {
        this.standTeamId = standTeamId;
    }

    public String getStandTeamName() {
        return standTeamName;
    }

    public void setStandTeamName(String standTeamName) {
        this.standTeamName = standTeamName;
    }

    public String getStandPosition() {
        return standPosition;
    }

    public void setStandPosition(String standPosition) {
        this.standPosition = standPosition;
    }

    public String getStandOverallGp() {
        return standOverallGp;
    }

    public void setStandOverallGp(String standOverallGp) {
        this.standOverallGp = standOverallGp;
    }

    public String getStandOverallW() {
        return standOverallW;
    }

    public void setStandOverallW(String standOverallW) {
        this.standOverallW = standOverallW;
    }

    public String getStandOverallD() {
        return standOverallD;
    }

    public void setStandOverallD(String standOverallD) {
        this.standOverallD = standOverallD;
    }

    public String getStandOverallL() {
        return standOverallL;
    }

    public void setStandOverallL(String standOverallL) {
        this.standOverallL = standOverallL;
    }

    public String getStandOverallGs() {
        return standOverallGs;
    }

    public void setStandOverallGs(String standOverallGs) {
        this.standOverallGs = standOverallGs;
    }

    public String getStandOverallGa() {
        return standOverallGa;
    }

    public void setStandOverallGa(String standOverallGa) {
        this.standOverallGa = standOverallGa;
    }

    public String getStandPoints() {
        return standPoints;
    }

    public void setStandPoints(String standPoints) {
        this.standPoints = standPoints;
    }

    public String getStandDesc() {
        return standDesc;
    }

    public void setStandDesc(String standDesc) {
        this.standDesc = standDesc;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}
