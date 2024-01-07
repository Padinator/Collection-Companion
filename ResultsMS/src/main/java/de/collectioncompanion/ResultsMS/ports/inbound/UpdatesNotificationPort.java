package de.collectioncompanion.ResultsMS.ports.inbound;


import ports.Collection;

import java.util.List;

public interface UpdatesNotificationPort {

    /**
     * Notifies that the collection was dequeued from rabbitmq
     *
     * @param collections Pass the collection for notifying
     */
    void notifyUpdate(long id, List<Collection> collections);

}
