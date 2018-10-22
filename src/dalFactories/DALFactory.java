package dalFactories;

import dal.interfaces.BoardStorage;
import dal.localImplementation.LocalBoardStorage;

public class DALFactory {

    public BoardStorage getLocalBoardStorage() {

        return new LocalBoardStorage();
    }
}
