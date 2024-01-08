package de.collectioncompanion.DatabseMS.data_files;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String username;

    private String password, email;

    private List<String> userFriendsId;

    private List<Sammlung> sammlungen;

    public String toJSON() {
        StringBuilder sb = new StringBuilder("{ ");

        sb.append("\"username\": ").append("\"" + username + "\", ");
        sb.append("\"password\": ").append("\"" + password + "\", ");
        sb.append("\"email\": ").append("\"" + email + "\", ");

        sb.append("\"sammlungen\": [\"").append(String.join("\", \"",
                sammlungen.stream().map(sammlung -> sammlung.toJSON()).toList())).append("\"]");
        sb.append("\"userFriendsID\": [\"").append(String.join("\", \"", userFriendsId)).append("\"]");

        return sb.append(" }").toString();
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Password: " + ", E-Mail: " + email + ", Sammlung: " + sammlungen
                + ", Freunde: " + userFriendsId;
    }
}
