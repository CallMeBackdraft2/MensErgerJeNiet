package client.dalfactories;

import client.dal.interfaces.BoardStorage;
import client.dal.localimplementation.LocalBoardStorage;

public class DALFactory {

    private DALFactory(){}

    public static BoardStorage getLocalBoardStorage() {

        return new LocalBoardStorage();

    }
}
