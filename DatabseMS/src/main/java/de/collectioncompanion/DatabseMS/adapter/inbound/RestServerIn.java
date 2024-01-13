package de.collectioncompanion.DatabseMS.adapter.inbound;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.adapter.outbound.DatabaseOut;
import de.collectioncompanion.DatabseMS.data_files.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import ports.Collection;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
                .getHandlerMethods();
        map.forEach((key, value) -> System.out.println("key: " + key  + ", value: " + value));
    }

    @Autowired
    private DatabaseOut databaseOut;

    /*
     * "Search result"-requests only for table "search results = collection"
     */

    /**
     * Accepts get requests with exactly two parameters named like parameters below
     *
     * @param category   The category of the collection
     * @param searchTerm The search term of the request
     * @return the resulting collections as response-JSON-string
     */
    @GetMapping
    public ResponseEntity<String> getCollections(@RequestParam String category, @RequestParam String searchTerm) {
        List<Collection> results = databaseOut.requestCollectionsFromDB(category, searchTerm);

        System.out.println("\n\nResults as JSON:\n" + results.stream().map(Collection::toJSON).toList());

        if (results.isEmpty()) // Found no collection
            return ResponseEntity.status(204).body("No collections found in DB!");
        else // Found a valid collection
            return ResponseEntity.status(200).body(results.stream().map(Collection::toJSON).toList().toString());
    }

    /**
     * Inserts a passed collection into DB
     *
     * @param collection The collection to insert into DB
     * @return Returns the success/response as string
     */
    @PostMapping
    public ResponseEntity<String> addNewCollection(@RequestBody CollectionImpl collection) {
        System.out.println("Received collection to insert: " + collection);
        if (databaseOut.addCollection(collection))
            return ResponseEntity.status(200).body(collection.getId());
        return ResponseEntity.status(200).body("-1");
    }

    /*
     * "User"-requests
     */
    @GetMapping("/users")
    public ResponseEntity<String> getUser(@RequestParam String username) {
        User user = databaseOut.requestUserFromDB(username);

        if (user == null) // No user was found
            return ResponseEntity.status(404).body("No user was found!");

        user.setPassword("");
        return ResponseEntity.status(200).body(user.toJSON());
    }

    @PostMapping("/users")
    public ResponseEntity<String> addNewUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        User user = databaseOut.addUser(new User(username, password, email, new LinkedList<>(), new LinkedList<>()));
        if (user != null)
            return ResponseEntity.status(200).body("Inserted successfully user into DB!");
        return ResponseEntity.status(403).body("User already exists!");
    }

    @PatchMapping("/users")
    public ResponseEntity<String> updateUser(@RequestParam String oldUsername, @RequestParam String newUsername, @RequestParam String newPassword, @RequestParam String newEmail) {
        User user = databaseOut.updateUser(oldUsername, newUsername, newPassword, newEmail);
        if (user != null)
            return ResponseEntity.status(200).body("Updating successfully user into DB!");
        return ResponseEntity.status(403).body("Could not update user!");
    }

    /*
     * "Sammlung" requests
     */
    @PostMapping("/users/sammlung")
    public ResponseEntity<String> addSammlungToUser(@RequestParam String username, @RequestParam String name, @RequestParam String visibility, @RequestParam String category) {
        if (databaseOut.addSammlungToUser(username, name, visibility, category))
            return ResponseEntity.status(200).body("Added successfully Sammlung to User!");
        return ResponseEntity.status(200).body("Could not add Sammlung to User!");

    }

    @PatchMapping("/users/sammlung")
    public ResponseEntity<String> updateSammlungOfUser(@RequestParam String username, @RequestParam int sammlungNummer, @RequestParam String newVisibility) {
        if (databaseOut.updateSammlungOfUser(username, sammlungNummer, newVisibility))
            return ResponseEntity.status(200).body("Updated successfully Sammlung of User!");
        return ResponseEntity.status(200).body("Could not update Sammlung of User!");
    }

    /*
     * "Collection/Search result"-requests in table "User"
     */
    @PostMapping("/users/sammlung/collections")
    public ResponseEntity<String> addCollectionToUsersSammlung(@RequestParam String username, @RequestParam int sammlungNummer, @RequestBody CollectionImpl collection) {
        if (databaseOut.addCollectionToUsersSammlung(username, sammlungNummer, collection))
            return ResponseEntity.status(200).body("Added successfully collection to user in DB!");
        return ResponseEntity.status(403).body("Could not add Collection to Users Sammlung!");
    }

    /*
     * "User friends"-requests
     */
    @PostMapping("/users/friends")
    public ResponseEntity<String> addFriendToUser(@RequestParam String username, @RequestParam String usernameFriend) {
        if (databaseOut.addFriendToUser(username, usernameFriend))
            return ResponseEntity.status(200).body("Added successfully Friend to User in DB!");
        return ResponseEntity.status(403).body("Could not add Friend to User!");
    }
}
