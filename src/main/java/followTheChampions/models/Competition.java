package followTheChampions.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Competition")
public class Competition extends BasicEntity  {

    @Column(name = "name")
    String name;

    @Column(name = "region")
    String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Competition[id=%d, name='%s', region='%s']",
                id, name, region);
    }
}
