package de.collectioncompanion.DatabseMS.data_files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

import static ports.CollectionFormatter.compareGameNames;

@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String username;

    private String password, email;

    private List<String> userFriendsId;

    private List<String> userFriendRequestsId;

    private List<Sammlung> sammlungen;

    public String toJSON() {
        StringBuilder sb = new StringBuilder("{ ");

        sb.append("\"username\": ").append("\"").append(username).append("\", ");
        sb.append("\"password\": ").append("\"").append(password).append("\", ");
        sb.append("\"email\": ").append("\"").append(email).append("\", ");

        if (sammlungen.isEmpty())
            sb.append("\"sammlungen\": [], ");
        else
            sb.append("\"sammlungen\": [ ").append(String.join(", ",
                    sammlungen.stream().map(Sammlung::toJSON).toList())).append(" ], ");

        if (userFriendsId.isEmpty())
            sb.append("\"userFriendsID\": [], ");
        else
            sb.append("\"userFriendsID\": [ \"").append(String.join("\", \"", userFriendsId)).append("\" ], ");

        if (userFriendRequestsId.isEmpty())
            sb.append("\"userFriendRequestsId\": []");
        else
            sb.append("\"userFriendRequestsId\": [ \"").append(String.join("\", \"", userFriendRequestsId)).append("\" ]");

        return sb.append(" }").toString();
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Password: " + ", E-Mail: " + email + ", Sammlung: " + sammlungen
                + ", Freunde: " + userFriendsId + ", Freundschaftsanfragen: " + userFriendRequestsId;
    }

    /**
     * Compares usernames and returns difference as boolean
     *
     * @param otherUserName of other user
     * @return true, if usernames differ as much as defined in function "compareUsernames(...)"
     */
    public boolean compareUsernames(String otherUserName) {
        return compareGameNames(username, otherUserName);
    }

}
