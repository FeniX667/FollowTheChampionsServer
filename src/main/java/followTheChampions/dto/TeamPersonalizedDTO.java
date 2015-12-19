package followTheChampions.dto;

import followTheChampions.models.Team;

public class TeamPersonalizedDTO {

    private Long id;
    private String name;
    private Boolean isFavourite;

    public TeamPersonalizedDTO(){
    }

    public TeamPersonalizedDTO(Team team, Boolean isFavourite){
        id = team.getId();
        name = team.getName();
        this.isFavourite = isFavourite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}
