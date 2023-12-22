package de.collectioncompanion.TasksMS.ports.outbound;

import de.collectioncompanion.TasksMS.data_files.CollectionRequest;

public interface UpdatesNotificationPort {

    /**
     * Pushes the request in the rabbitmq
     *
     * @param collectionRequest Pass the collection as request to insert into the rabbitmq
     */
    void notifyUpdate(CollectionRequest collectionRequest);

}
