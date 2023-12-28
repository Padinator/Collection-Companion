package de.collectioncompanion.ComposerMS.ports.outbound;

import de.collectioncompanion.ComposerMS.ports.data_files.Collection;

public interface UpdatesNotificationPort {

    /**
     * Notifies that the collection was enqueued to rabbitmq
     *
     * @param collection Pass the collection for notifying
     */
    void notifyUpdate(long id, Collection collection);

}
