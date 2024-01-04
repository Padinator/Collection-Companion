package de.collectioncompanion.ResultsMS.ports.inbound;


import ports.Collection;

public interface UpdatesNotificationPort {

    /**
     * Notifies that the collection was dequeued from rabbitmq
     *
     * @param collection Pass the collection for notifying
     */
    void notifyUpdate(long id, Collection collection);

}
