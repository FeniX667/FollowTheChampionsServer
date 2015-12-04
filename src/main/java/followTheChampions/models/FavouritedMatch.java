package followTheChampions.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "FavouritedMatch")
public class FavouritedMatch extends AutomatedEntity {

    @ManyToOne
    @NotNull
    @JoinColumn(name = "match")
    Match match;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "registeredDevice")
    @JsonIgnore
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
