import org.junit.Test;
import java.util.NoSuchElementException;
import org.junit.Before;
import static org.junit.Assert.*;

public class WDGraphTest {
    private WDGraph empty; 
    private WDGraph two; 
    
    @Before 
    public void setup() {
        empty = new WDGraph(0); 
        two = new WDGraph(2); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void graphNeg() {
        new WDGraph(-3); 
    }
    
    @Test
    public void emptyGraphSize() {
        assertEquals(0, empty.getSize()); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoHasEdgeError1() {
        two.hasEdge(0, 3); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoHasEdgeError2() {
        two.hasEdge(-1, 0); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoHasEdgeError3() {
        two.hasEdge(0, 5); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoHasEdgeError4() {
        two.hasEdge(0, -1); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoAddEdgeError1() {
        two.addEdge(0, 3, 0); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoAddEdgeError2() {
        two.addEdge(-1, 0, 0); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoAddEdgeError3() {
        two.addEdge(0, 5, 0); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoAddEdgeError4() {
        two.addEdge(0, -1, 0); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoGetWeight1() {
        two.getWeight(0, 3); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoGetWeight2() {
        two.getWeight(-1, 0); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoGetWeight3() {
        two.getWeight(0, 5); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoGetWeight4() {
        two.getWeight(0, -1); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoHasEdgeSelfLoop() {
        two.addEdge(0, 0, 0); 
    }
    
    @Test
    public void twoHasEdgeRev() {
        two.addEdge(1, 0, 0); 
        assertFalse(two.hasEdge(0, 1)); 
    }
    
    @Test
    public void twoAddEdgeTrue() {
        two.addEdge(1, 0, 0);
        assertFalse(two.addEdge(1, 0, 0));
    }
    
    @Test
    public void twoAddEdgeFalse() {
        two.addEdge(1, 0, 0);
        assertTrue(two.addEdge(0, 1, 0));
    }
    
    @Test(expected = NoSuchElementException.class)
    public void twoGetWeightNoSuch() {
        two.getWeight(1, 1); 
    }
    
    @Test
    public void twoGetWeight() {
        two.addEdge(1, 0, 3); 
        assertEquals(3, two.getWeight(1, 0), 0); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoGetOutNeighborsError1() {
        two.outNeighbors(-1); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoGetOutNeighborsError2() {
        two.outNeighbors(3); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoInNeighborsError1() {
        two.outNeighbors(-1); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void twoInNeighborsError2() {
        two.outNeighbors(3); 
    }
    
    @Test
    public void twoGetOutNeighbors() {
        two.addEdge(0, 1, 3); 
        Integer[] correct = {1}; 
        assertArrayEquals(correct, two.outNeighbors(0).toArray());
    }
    
    @Test
    public void twoGetInNeighbors() {
        two.addEdge(0, 1, 3); 
        Integer[] correct = {0}; 
        assertArrayEquals(correct, two.inNeighbors(1).toArray());
    }
}