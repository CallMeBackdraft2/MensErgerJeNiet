package dalFactories;

import dal.interfaces.BoardStorage;
import dal.localImplementation.LocalBoardStorage;

public  class DALFactory {

    public static BoardStorage getLocalBoardStorage() {

        return new LocalBoardStorage();

    }
}
