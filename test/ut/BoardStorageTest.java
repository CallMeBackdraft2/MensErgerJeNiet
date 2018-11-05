package ut;

import dal.localImplementation.LocalBoardStorage;
import dalFactories.DALFactory;
import domain.Classes.Pawn;
import domain.Enums.GameMode;
import domain.Enums.PawnState;
import org.junit.Assert;
import org.junit.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class BoardStorageTest {
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
    public void getTilesTest(){
        Assert.assertNotNull(local.getTiles());
    }

    @Test
    public void getPawnsTest(){
        Assert.assertNotNull(local.getPawns());
    }

    @Test
    public void getTileTest(){
        Assert.assertThat(local.getPawn("REP01"), instanceOf(Pawn.class));
    }
}