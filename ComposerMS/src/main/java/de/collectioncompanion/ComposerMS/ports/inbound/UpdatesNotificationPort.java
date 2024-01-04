package de.collectioncompanion.ComposerMS.ports.inbound;

import data_files.CollectionRequest;

public interface UpdatesNotificationPort {

    /**
     * Notifies that the collection request was dequeued from rabbitmq
     *
     * @param collectionRequest Pass the collection request for notifying
     */
    void notifyUpdate(CollectionRequest collectionRequest);

}
