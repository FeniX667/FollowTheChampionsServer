package followTheChampions.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FavouritedTeam")
public class FavouritedTeam extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "team")
    Team team;

    @ManyToOne
    @JoinColumn(name = "registeredDevice")
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
