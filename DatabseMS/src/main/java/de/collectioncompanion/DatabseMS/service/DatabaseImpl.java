package de.collectioncompanion.DatabseMS.service;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.data_files.Sammlung;
import de.collectioncompanion.DatabseMS.data_files.User;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import de.collectioncompanion.DatabseMS.ports.service.CollectionRepo;
import de.collectioncompanion.DatabseMS.ports.service.UserRepo;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import static ports.CollectionFormatter.compareGameNames;

@Service
public class DatabaseImpl implements Database {

    /*
     * "Search result"-requests only for table "search results = collection"
     */
    @Override
    public List<Collection> selectCollections(String category, String searchTerm, CollectionRepo collectionRepo) {
        List<Collection> results = collectionRepo.findAll().stream() // Query DB
                .filter(collection -> collection.getValue("category").equals(category)
                        && compareGameNames(collection.getValue("title"), searchTerm))
                .toList();

        System.out.println("Return results:\n" + results);
        return results;
    }

    @Override
    public boolean insertCollection(Collection collection, CollectionRepo collectionRepo) {
        System.out.println("Collection to insert: " + collection);
        Collection c = collectionRepo.insert(collection);
        System.out.println("Inserted: " + c);
        return true;
    }

    /*
     * "User"-requests
     */
    @Override
    public User selectUser(String username, UserRepo userRepo) {
        Optional<User> user = userRepo.findById(username);

        if (user.isEmpty()) // if no user was found
            return null;

        return user.get();
    }

    @Override
    public User insertUser(User user, UserRepo userRepo) {
        if (selectUser(user.getUsername(), userRepo) != null)
            return null;
        User u = userRepo.insert(user);
        System.out.println("Inserted new User into DB: " + u);
        return u;
    }

    @Override
    public User updateUser(String oldUsername, String newUsername, String newPassword, String newEmail, UserRepo userRepo) {
        Optional<User> optionalOldUser = userRepo.findById(oldUsername);
        Optional<User> optionalNewUser = userRepo.findById(newUsername);

        if (optionalNewUser.isEmpty() && optionalOldUser.isPresent()) { // Old user exists and new user not
            User oldUser = optionalOldUser.get();
            userRepo.delete(oldUser); // Delete old user
            oldUser.setUsername(newUsername);
            oldUser.setPassword(newPassword);
            oldUser.setEmail(newEmail);
            return userRepo.save(oldUser); // Save updated user and return him
        }

        return null;
    }

    /*
     * "Sammlung" requests
     */
    @Override
    public boolean insertSammlungToUser(String username, String name, String visibility, String category,
                                        UserRepo userRepo) {
        Optional<User> optionalUser = userRepo.findById(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getSammlungen().add(new Sammlung(name, visibility, category, new LinkedList<>()));
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSammlungOfUser(String username, int sammlungNummer, String newVisibility,
                                        UserRepo userRepo) {
        Optional<User> optionalUser = userRepo.findById(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Sammlung> sammlungen = user.getSammlungen();

            if (sammlungNummer <= sammlungen.size()) {
                sammlungen.get(sammlungNummer - 1).setVisibility(newVisibility);
                System.out.println(user.toJSON());
                userRepo.save(user);
                return true;
            }
        }

        return false;
    }

    /*
     * "User friends"-requests
     */
    @Override
    public boolean insertFriendToUser(String username, String usernameFriend, UserRepo userRepo) {
        Optional<User> user1 = userRepo.findById(username), user2 = userRepo.findById(usernameFriend);

        if (user1.isPresent() && user2.isPresent()) {
            User user = user1.get();
            List<String> friendsId = user.getUserFriendsId();

            if (!friendsId.contains(usernameFriend)) {
                user.getUserFriendsId().add(usernameFriend);
                userRepo.save(user);
                return true;
            }
        }

        return false;
    }

    /*
     * "Collection/Search result"-requests in table "User"
     */
    @Override
    public boolean insertCollectionToUser(String username, int sammlungNummer, CollectionImpl collection,
                                          UserRepo userRepo, CollectionRepo collectionRepo) {
        Optional<User> optionalUser = userRepo.findById(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Sammlung> sammlungen = user.getSammlungen();

            if (sammlungNummer <= sammlungen.size()) {
                Sammlung sammlung = sammlungen.get(sammlungNummer - 1);

                if (sammlung.getCollectionIds().contains(collection.getId())) { // If Collection does not exist in Sammlung
                    sammlung.getCollectionIds().add(collection.getId());
                    userRepo.save(user);
                    return true;
                }
            }
        }

        return false;
    }

}
