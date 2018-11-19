package ut;

import client.dal.interfaces.BoardStorage;
import client.dalFactories.DALFactory;
import client.domain.Classes.Pawn;
import client.domain.Enums.GameMode;
import client.domain.Enums.PlayerColor;
import org.junit.Assert;
import org.junit.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class BoardStorageTest {
    private BoardStorage local;

    @Before
    public void Initialize() {
        //DALFactory client.dal = new DALFactory();

        //local = (LocalBoardStorage) client.dal.getLocalBoardStorage();

        local = DALFactory.getLocalBoardStorage();
        local.init(GameMode.FOURPLAYERBOARD);
    }

    @Test
    public void initTest() {
        local.init(GameMode.FOURPLAYERBOARD);
        Assert.assertNotNull(local.getTiles());
    }

    @Test
    public void getTilesTest() {
        Assert.assertNotNull(local.getTiles());
    }

    @Test
    public void getPawnsTest() {
        Assert.assertNotNull(local.getPawns());
    }

    @Test
    public void getTileTest(){
        Assert.assertEquals("GREEN", local.getTile("GRP01").getColor());
    }

    @Test
    public void getPawnTest() {
        Assert.assertThat(local.getPawn("REP01"), instanceOf(Pawn.class));
        Assert.assertEquals(PlayerColor.RED,local.getPawn("REP01").getPlayerColor());
        Assert.assertEquals("REP01", local.getPawn("REP01").getFullId());
        Assert.assertEquals(0, local.getPawn("REP01").getStepsTaken());
        Assert.assertEquals("REP01", local.getPawn("REP01").getPawnTileId());
    }
}