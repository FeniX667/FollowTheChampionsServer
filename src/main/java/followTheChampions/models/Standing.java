package followTheChampions.models;

import javax.persistence.*;

@Entity
@Table(name = "Standing")
public class Standing extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "competition")
    Competition competition;

    @Column(name = "standSeason")
    String standSeason;

    @Column(name = "standRound")
    String standRound;

    @Column(name = "standStageId")
    Long standStageId;

    @Column(name = "standGroup")
    String standGroup;

    @Column(name = "standCountry")
    String standCountry;

    @Column(name = "standTeamId")
    Long standTeamId;

    @Column(name = "standTeamName")
    String standTeamName;

    @Column(name = "standStatus")
    String standStatus;

    @Column(name = "standRecentForm")
    String standRecentForm;

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

    @Column(name = "standHomeGp")
    String standHomeGp;

    @Column(name = "standHomeW")
    String standHomeW;

    @Column(name = "standHomeD")
    String standHomeD;

    @Column(name = "standHomeL")
    String standHomeL;

    @Column(name = "standHomeGs")
    String standHomeGs;

    @Column(name = "standHomeGa")
    String standHomeGa;

    @Column(name = "standAwayGp")
    String standAwayGp;

    @Column(name = "standAwayW")
    String standAwayW;

    @Column(name = "standAwayD")
    String standAwayD;

    @Column(name = "standAwayL")
    String standAwayL;

    @Column(name = "standAwayGs")
    String standAwayGs;

    @Column(name = "standAwayGa")
    String standAwayGa;

    @Column(name = "standGd")
    String standGd;

    @Column(name = "standPoints")
    String standPoints;

    @Column(name = "standDesc")
    String standDesc;

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public String getStandSeason() {
        return standSeason;
    }

    public void setStandSeason(String standSeason) {
        this.standSeason = standSeason;
    }

    public String getStandRound() {
        return standRound;
    }

    public void setStandRound(String standRound) {
        this.standRound = standRound;
    }

    public Long getStandStageId() {
        return standStageId;
    }

    public void setStandStageId(Long standStageId) {
        this.standStageId = standStageId;
    }

    public String getStandGroup() {
        return standGroup;
    }

    public void setStandGroup(String standGroup) {
        this.standGroup = standGroup;
    }

    public String getStandCountry() {
        return standCountry;
    }

    public void setStandCountry(String standCountry) {
        this.standCountry = standCountry;
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

    public String getStandStatus() {
        return standStatus;
    }

    public void setStandStatus(String standStatus) {
        this.standStatus = standStatus;
    }

    public String getStandRecentForm() {
        return standRecentForm;
    }

    public void setStandRecentForm(String standRecentForm) {
        this.standRecentForm = standRecentForm;
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

    public String getStandHomeGp() {
        return standHomeGp;
    }

    public void setStandHomeGp(String standHomeGp) {
        this.standHomeGp = standHomeGp;
    }

    public String getStandHomeW() {
        return standHomeW;
    }

    public void setStandHomeW(String standHomeW) {
        this.standHomeW = standHomeW;
    }

    public String getStandHomeD() {
        return standHomeD;
    }

    public void setStandHomeD(String standHomeD) {
        this.standHomeD = standHomeD;
    }

    public String getStandHomeL() {
        return standHomeL;
    }

    public void setStandHomeL(String standHomeL) {
        this.standHomeL = standHomeL;
    }

    public String getStandHomeGs() {
        return standHomeGs;
    }

    public void setStandHomeGs(String standHomeGs) {
        this.standHomeGs = standHomeGs;
    }

    public String getStandHomeGa() {
        return standHomeGa;
    }

    public void setStandHomeGa(String standHomeGa) {
        this.standHomeGa = standHomeGa;
    }

    public String getStandAwayGp() {
        return standAwayGp;
    }

    public void setStandAwayGp(String standAwayGp) {
        this.standAwayGp = standAwayGp;
    }

    public String getStandAwayW() {
        return standAwayW;
    }

    public void setStandAwayW(String standAwayW) {
        this.standAwayW = standAwayW;
    }

    public String getStandAwayD() {
        return standAwayD;
    }

    public void setStandAwayD(String standAwayD) {
        this.standAwayD = standAwayD;
    }

    public String getStandAwayL() {
        return standAwayL;
    }

    public void setStandAwayL(String standAwayL) {
        this.standAwayL = standAwayL;
    }

    public String getStandAwayGs() {
        return standAwayGs;
    }

    public void setStandAwayGs(String standAwayGs) {
        this.standAwayGs = standAwayGs;
    }

    public String getStandAwayGa() {
        return standAwayGa;
    }

    public void setStandAwayGa(String standAwayGa) {
        this.standAwayGa = standAwayGa;
    }

    public String getStandGd() {
        return standGd;
    }

    public void setStandGd(String standGd) {
        this.standGd = standGd;
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
