package ut;

import dal.localImplementation.LocalBoardStorage;
import dalFactories.DALFactory;
import domain.Enums.GameMode;
import domain.Enums.PawnState;
import org.junit.Assert;
import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class LocalBoardStorageTest{
    private LocalBoardStorage local;
    @Before
    public void Initialize(){
        DALFactory dal = new DALFactory();

        local = (LocalBoardStorage) dal.getLocalBoardStorage();
    }

    @Test
    public void initTest(){
        local.init(GameMode.FOURPLAYERBOARD);
        Assert.assertNotNull(local.getTiles());
    }

    @Test
    public void movePawn(){
        local.getPawn("WLK01").movePawnIntoPlay();

        Assert.assertTrue(local.getPawn("WLK01").getPawnState() == PawnState.INPLAY);
    }

}