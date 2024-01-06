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

    private List<String> collectionId;

    @Override
    public String toString() {
        return "Username: " + username + ", Password: " + ", E-Mail: " + email + ", Sammlung: " + collectionId;
    }
}
