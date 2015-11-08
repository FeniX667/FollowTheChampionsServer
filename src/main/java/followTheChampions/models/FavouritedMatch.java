package followTheChampions.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FavouritedMatch")
public class FavouritedMatch extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "match")
    Match match;

    @ManyToOne
    @JoinColumn(name = "registeredDevice")
    RegisteredDevice registeredDevice;

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public RegisteredDevice getRegisteredDevice() {
        return registeredDevice;
    }

    public void setRegisteredDevice(RegisteredDevice registeredDevice) {
        this.registeredDevice = registeredDevice;
    }
}
