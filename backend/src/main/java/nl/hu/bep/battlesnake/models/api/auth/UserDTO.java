package nl.hu.bep.battlesnake.models.api.auth;

import java.util.List;

public class UserDTO {
    private String username;
    private List<String> roles;

    public UserDTO() {
    }

    public UserDTO(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}