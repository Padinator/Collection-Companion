package de.collectioncompanion.ResultsMS.data_files;

import lombok.Getter;
import ports.Collection;

import java.util.List;

@Getter
public class ResultWaiterThread extends Thread {

    private List<Collection> collections;
    private final long ID;

    public ResultWaiterThread(long ID) {
        this.ID = ID;
        start();
    }

    @Override
    public void run() {
        collections = CollectionList.popCollection(ID);
        System.out.println("\n\nPopped follwing collection: " + collections);
    }

}
