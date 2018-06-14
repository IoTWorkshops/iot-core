package gr.iot.iot.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @JsonProperty("uuid")
    @Column(name = "uuid")
    private String uuid;

    @JsonProperty("username")
    @Column(name = "username", updatable = false)
    private String username;

    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JsonProperty("date_created")
    @Column(name = "date_created", updatable = false)
    private Date dateCreated;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }


    @Override
    public String toString() {
        return "User{" +
            "uuid='" + uuid + '\'' +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", dateCreated=" + dateCreated +
            '}';
    }
}
