package client.dalFactories;

import client.dal.interfaces.BoardStorage;
import client.dal.localImplementation.LocalBoardStorage;

public class DALFactory {

    private DALFactory(){}

    public static BoardStorage getLocalBoardStorage() {

        return new LocalBoardStorage();

    }
}