package followTheChampions.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Malar on 2015-11-04.
 */
@Entity
@Table(name ="Team")
public class Team extends BasicEntity {

    @Column(name = "name")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
