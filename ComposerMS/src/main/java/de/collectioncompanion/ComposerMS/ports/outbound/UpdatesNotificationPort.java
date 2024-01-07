package de.collectioncompanion.ComposerMS.ports.outbound;

import data_files.CollectionImpl;

import java.util.ArrayList;

public interface UpdatesNotificationPort {

    /**
     * Notifies that the collection was enqueued to rabbitmq
     *
     * @param collections Pass the collection for notifying
     */
    void notifyUpdate(long id, ArrayList<CollectionImpl> collections);

}
