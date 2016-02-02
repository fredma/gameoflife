import static org.junit.Assert.*;

import org.junit.Test;

public class GameOfLifeTest {


    @Test
    public void testAddCell()
    {
        GameOfLife instance = new GameOfLife(10,10);
        instance.addCell(0,0);
        assertTrue("Cell must exist",instance.isCell(0,0)); 
    }
    

    @Test
    public void testAddCellAndCountNeighbours()
    {
        GameOfLife instance = new GameOfLife(10,10);
        instance.addCell(0,0);
        assertEquals(0, instance.getCountNeighbours(0,0)); 
        assertEquals("cell[1,0] must be equal 1", 1, instance.getCountNeighbours(1,0));//row 1, col 0
        assertEquals("cell[0,1] must be equal 1", 1, instance.getCountNeighbours(0,1));//row 0, col 1
        assertEquals("cell[1,1] must be equal 1", 1, instance.getCountNeighbours(1,1));//row 1, col 1
    }

    @Test
    public void testRemoveCell()
    {
        GameOfLife instance = new GameOfLife(10,10);
        instance.addCell(0,0);
        instance.removeCell(0,0);
        assertFalse("Cell must be dead", instance.isCell(0,0)); 
    }

    @Test
    public void testRemoveCellAndCountNeighbours()
    {
        GameOfLife instance = new GameOfLife(10,10);
        instance.addCell(0,0);
        instance.addCell(1,1);
        instance.removeCell(0,0);
        assertEquals(0, instance.getCountNeighbours(1,1)); 
        assertEquals("cell[1,0] must be equal 1", 1, instance.getCountNeighbours(1,0));
        assertEquals("cell[0,1] must be equal 1", 1, instance.getCountNeighbours(0,1));
        assertEquals("cell[0,0] must be equal 1", 1, instance.getCountNeighbours(0,0));
    }

    @Test
    public void testCellBirth()
    {
    }

    @Test
    public void testCellDeathBySolitude()
    {
        GameOfLife instance = new GameOfLife(10,10);
        instance.addCell(0,0);
        instance.addCell(1,1);
        instance.step();
        assertFalse("Cell must be dead", instance.isCell(0,0)); 
        assertFalse("Cell must be dead", instance.isCell(1,1)); 
    }

    
    @Test
    public void testCellDeathByOverPopulation()
    {
        GameOfLife instance = new GameOfLife(10,10);
        instance.addCell(1,3);
        instance.addCell(2,2);
        instance.addCell(2,3);
        instance.addCell(2,4);
        instance.addCell(3,3);
        instance.step();
        assertFalse("DeathOverPopulation: Cell must be dead", instance.isCell(2,3)); 
        assertTrue("DeathOverPopulation: Cell must be live", instance.isCell(1,3)); 
        assertTrue("DeathOverPopulation: Cell must be live", instance.isCell(2,2)); 
        assertTrue("DeathOverPopulation: Cell must be live", instance.isCell(2,4)); 
        assertTrue("DeathOverPopulation: Cell must be live", instance.isCell(3,3)); 
        assertTrue("DeathOverPopulation: Cell must be born", instance.isCell(1,2)); 
        assertTrue("DeathOverPopulation: Cell must be born", instance.isCell(1,4)); 
        assertTrue("DeathOverPopulation: Cell must be born", instance.isCell(3,2)); 
        assertTrue("DeathOverPopulation: Cell must be born", instance.isCell(3,4)); 
    }

    @Test
    public void testCellSurvival()
    {}
}
