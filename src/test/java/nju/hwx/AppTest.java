package nju.hwx;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testMap()
    {

        Map m = new Map();
        m.set(2,3,null);
        assertTrue( !m.isOccupied(2,3));
    }



}
