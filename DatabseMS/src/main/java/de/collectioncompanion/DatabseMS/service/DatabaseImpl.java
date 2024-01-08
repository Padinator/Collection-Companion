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

    @Override
    public List<Collection> selectCollections(String category, String searchTerm, CollectionRepo collectionRepo) {
        List<Collection> results = collectionRepo.findAll().stream() // Query DB
                .filter(collection -> collection.getValue("category").equals(category)
                        && compareGameNames(collection.getValue("title"), searchTerm))
                .toList();

        System.out.println("\n\nFound:");
        System.out.println(collectionRepo.findAll() + "\n");
        System.out.println(collectionRepo.findAll().stream() // Query DB
                .filter(collection -> collection.getValue("category").equals(category))
                .toList() + "\n");

        System.out.println(collectionRepo.findAll().stream() // Query DB
                .filter(collection -> compareGameNames(collection.getValue("title"), searchTerm))
                .toList() + "\n\n");

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

    @Override
    public User insertUser(User user, UserRepo userRepo) {
        if (selectUser(user.getUsername(), userRepo) != null)
            return null;
        User u = userRepo.insert(user);
        System.out.println("Inserted new User into DB: " + u);
        return u;
    }

    @Override
    public User selectUser(String username, UserRepo userRepo) {
        Optional<User> user = userRepo.findById(username);

        if (user.isEmpty()) // if no user was found
            return null;

        return user.get();
    }

    @Override
    public boolean insertCollectionToUser(String username, int sammlungNummer, CollectionImpl collection,
            UserRepo userRepo,
            CollectionRepo collectionRepo) {
        Optional<User> optionalUser = userRepo.findById(username);

        if (optionalUser.isEmpty())
            return false;

        User user = optionalUser.get();
        List<Sammlung> sammlungen = user.getSammlungen();

        if (sammlungen.size() <= sammlungNummer)
            return false;
        else {
            Sammlung sammlung = sammlungen.get(sammlungNummer - 1);

            if (sammlung.getCollectionIds().contains(collection.getId()))
                return false;

            sammlung.getCollectionIds().add(collection.getId());
            userRepo.save(user);
        }

        return true;

    }

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

}
