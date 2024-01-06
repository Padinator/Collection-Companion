package de.collectioncompanion.DatabseMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import ports.Collection;

@Service
public class CollectionIdFinder {
    @Autowired
    private MongoOperations mongo;

    public int getNextSequence(String seqName) {
        Collection counter = mongo.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                Collection.class);
        return counter.getSeq();
    }
}