package followTheChampions.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "RegisteredDevice")
public class RegisteredDevice extends AutomatedEntity {

    @Column(name = "deviceToken")
    String deviceToken;

    @Column(name = "registrationDate")
    Date registrationDate;

    @Column(name = "isActive")
    Boolean isActive;

    @Column(name = "type")
    Type type;

    public enum Type { IOS, Android };

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
