package followTheChampions.models;

import javax.persistence.*;

@Entity
@Table(name = "Standing")
public class Standing extends BasicEntity {

    @Column(name = "standRound")
    String standRound;

    @Column(name = "standTeamId")
    Long standTeamId;

    @Column(name = "standTeamName")
    String standTeamName;

    @Column(name = "standPosition")
    String standPosition;

    @Column(name = "standOverallGp")
    String standOverallGp;

    @Column(name = "standOverallW")
    String standOverallW;

    @Column(name = "standOverallD")
    String standOverallD;

    @Column(name = "standOverallL")
    String standOverallL;

    @Column(name = "standOverallGs")
    String standOverallGs;

    @Column(name = "standOverallGa")
    String standOverallGa;

    @Column(name = "standPoints")
    String standPoints;

    @Column(name = "standDesc")
    String standDesc;

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
}
