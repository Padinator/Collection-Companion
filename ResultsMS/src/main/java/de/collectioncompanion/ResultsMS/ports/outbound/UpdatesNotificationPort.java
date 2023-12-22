package de.collectioncompanion.ResultsMS.ports.outbound;

import de.collectioncompanion.ResultsMS.ports.data_files.Collection;

public interface UpdatesNotificationPort {

    /**
     * Pushes the request in the rabbitmq
     *
     * @param collection Pass the collection as request to insert into the rabbitmq
     */
    void notifyUpdate(Collection collection);

}
