package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.items.*;

import nz.ac.wgtn.swen225.lc.renderer.AudioUnit;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class DomainTest {
    private static Board board;
    private static Chap chap;

    /**
     * Initialise the board and player.
     */
    @BeforeEach
    public void init() {
        AudioUnit au = new AudioUnit();
        board = new Board(new File("LarryCroftsAdventures/levels/level1.json"), au);
        chap = board.getChap();
    }

    @Test
    public void testBoard() {

    }

    @Test
    public void testMoveLeft() {
        int x = chap.getX();
        assertDoesNotThrow(() -> chap.move(Chap.Direction.LEFT));
        assertEquals(x - 1, chap.getX());
    }

    @Test
    public void testMoveLeftIllegal() {
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.LEFT));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.LEFT));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.LEFT));
        assertThrows(IllegalMove.class, () -> chap.move(Chap.Direction.LEFT));
    }

    @Test
    public void testMoveRight() {
        int x = chap.getX();
        assertDoesNotThrow(() -> chap.move(Chap.Direction.RIGHT));
        assertEquals(x + 1, chap.getX());
    }

    @Test
    public void testMoveRightIllegal() {
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.RIGHT));
        assertThrows(IllegalMove.class, () -> chap.move(Chap.Direction.RIGHT));
    }

    @Test
    public void testMoveDown() {
        int y = chap.getY();
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.DOWN));
        assertEquals(y, chap.getY());
    }

    @Test
    public void testMoveDownIllegal() {
        assertThrows(IllegalMove.class, () -> chap.move(Chap.Direction.DOWN));
    }

    @Test
    public void testMoveUp() {
        int y = chap.getY();
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertEquals(y - 1, chap.getY());
    }

    @Test
    public void testMoveUpIllegal() {
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertThrows(IllegalMove.class, () -> chap.move(Chap.Direction.UP));
    }

    @Test
    public void testPickUpKey() {
        assertFalse(chap.hasItem(new Key(Key.Colour.RED)));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.RIGHT));
        assertTrue(chap.hasItem(new Key(Key.Colour.RED)));
    }

    @Test
    public void testCanOpenDoor() {
        testPickUpKey();
        assertDoesNotThrow(() -> chap.move(Chap.Direction.LEFT));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.RIGHT));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.RIGHT));
        assertFalse(chap.hasItem(new Key(Key.Colour.RED)));
    }

    @Test
    public void testCannotOpenDoor() {
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.RIGHT));
        assertThrows(IllegalMove.class, () -> chap.move(Chap.Direction.RIGHT));
    }

    @Test
    public void testCannotOpenDoorWrongKey() {
        assertFalse(chap.hasItem(new Key(Key.Colour.BLUE)));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.RIGHT));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.DOWN));
        assertTrue(chap.hasItem(new Key(Key.Colour.RED)));
        assertFalse(chap.hasItem(new Key(Key.Colour.BLUE)));
        assertThrows(IllegalMove.class, () -> chap.move(Chap.Direction.RIGHT));
    }

    @Test
    public void testPickUpTreasure() {
        assertEquals(0, chap.getCurrentTreasure());
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.UP));
        assertDoesNotThrow(() -> chap.move(Chap.Direction.RIGHT));
        assertEquals(1, chap.getCurrentTreasure());
    }
}
