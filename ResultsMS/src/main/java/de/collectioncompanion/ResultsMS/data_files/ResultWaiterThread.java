package de.collectioncompanion.ResultsMS.data_files;

import de.collectioncompanion.ResultsMS.ports.data_files.Collection;
import de.collectioncompanion.ResultsMS.ports.data_files.CollectionList;
import lombok.Getter;

@Getter
public class ResultWaiterThread extends Thread {

    private Collection collection;
    private final long ID;

    public ResultWaiterThread(long ID) {
        this.ID = ID;
        start();
    }

    @Override
    public void run() {
        while ((collection = CollectionList.popCollection(ID)) == null)
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }

}
