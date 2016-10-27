package entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import security.IUser;

@Entity
@Table(name = "USER")
public class User implements IUser {

    
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "ID")
//    private Integer id;

    @Id
    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;  //Pleeeeease dont store me in plain text

    @ElementCollection
    @CollectionTable(
        name = "USER_ROLES",
        joinColumns = @JoinColumn(name = "USER_ID")
    )
    @Column (name = "ROLES")
    private List<String> roles = new ArrayList();

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User() {

    }

    public User(String userName, String password, List<String> roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public void addRole(String role) {
        roles.add(role);
    }

    @Override
    public List<String> getRolesAsStrings() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
