package followTheChampions.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "FavouritedTeam")
public class FavouritedTeam extends AutomatedEntity {

    @ManyToOne
    @NotNull
    @JoinColumn(name = "team")
    Team team;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "registeredDevice")
    @JsonIgnore
    RegisteredDevice registeredDevice;

    public followTheChampions.models.Team getTeam() {
        return team;
    }

    public void setTeam(followTheChampions.models.Team team) {
        this.team = team;
    }


    public RegisteredDevice getRegisteredDevice() {
        return registeredDevice;
    }

    public void setRegisteredDevice(RegisteredDevice registeredDevice) {
        this.registeredDevice = registeredDevice;
    }
}
