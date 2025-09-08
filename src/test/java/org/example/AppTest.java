package org.example;



import static junit.framework.Assert.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 

{
    @Test
    public void femIsFem() {
        int actual = App.femIsfem();
        assertEquals(5, actual);
    }
}
