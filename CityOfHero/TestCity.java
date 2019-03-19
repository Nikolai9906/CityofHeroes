package CityOfHero; 
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class TestCity.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TestCity
{
    private CityOfHeroes t1, t2;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
        t1=new CityOfHeroes(1500,900);
    }
    @Test
    public void DeberiaCrearEdificios(){
        t1.addBuilding(0,100,100,15);
        t1.addBuilding(100,100,100,15);
        t1.addBuilding(200,100,100,15);
        t1.addBuilding(300,100,100,15);
        t1.addBuilding(400,100,100,15);
        t1.addBuilding(500,100,100,15);
        t1.addBuilding(600,100,100,15);
        t1.addBuilding(700,100,100,15);
        t1.addBuilding(800,100,100,15);
        t1.addBuilding(900,100,100,15);
        t1.addBuilding(1000,100,100,15);
        t1.addBuilding(1100,100,100,15);
        t1.addBuilding(1200,100,100,15);
        t1.addBuilding(1300,100,100,15);
        t1.addBuilding(1400,100,100,15);
        assertTrue(t1.ok());
    }
    @Test
    public void Nodeberiacrearedificios(){
        t1.addBuilding(0,500,100,15);
        t1.addBuilding(250,100,100,15);
        assertFalse(t1.ok());
    }
    @Test
        public void Nodeberiacrearedificio(){
        t1.addBuilding(1500,500,100,15);
        assertFalse(t1.ok());
    }
    @Test
    public void DeberiacrearHeroes(){
        t1.addBuilding(0,100,100,15);
        t1.addBuilding(100,100,100,15);
        t1.addBuilding(200,100,100,15);
        t1.addBuilding(300,100,100,15);
        t1.addBuilding(400,100,100,15);
        t1.addBuilding(500,100,100,15);
        t1.addBuilding(600,100,100,15);
        t1.addBuilding(700,100,100,15);
        t1.addBuilding(800,100,100,15);
        t1.addBuilding(900,100,100,15);
        t1.addBuilding(1000,100,100,15);
        t1.addBuilding(1100,100,100,15);
        t1.addBuilding(1200,100,100,15);
        t1.addBuilding(1300,100,100,15);
        t1.addBuilding(1400,100,100,15);
        t1.addHeroe("Black",1,15);
        t1.addHeroe("Black",2,15);
        t1.addHeroe("Black",3,15);
        t1.addHeroe("Black",4,15);
        t1.addHeroe("Black",5,15);
        t1.addHeroe("Black",6,15);
        t1.addHeroe("Black",7,15);
        t1.addHeroe("Black",8,15);
        t1.addHeroe("Black",9,15);
        t1.addHeroe("Black",10,15);
        t1.addHeroe("Black",11,15);
        t1.addHeroe("Black",12,15);
        t1.addHeroe("Black",13,15);
        t1.addHeroe("Black",14,15);
        t1.addHeroe("Black",15,15);
        assertTrue(t1.ok());
    }
    @Test
    public void NodeberiacrearHeroe(){
        t1.addHeroe("Black",15,15);
        assertFalse(t1.ok());
    }
    @Test
    public void DeberiaBorrarEdificio(){
        t1.addBuilding(1400,100,100,15);
        t1.addBuilding(100,100,100,15);
        t1.removeBuilding(1);
        t1.removeBuilding(2);
        assertTrue(t1.ok());
    }
    @Test
    public void NoDeberiaBorrarEdificio(){
        t1.addBuilding(0,100,100,15);
        t1.addHeroe("Black",1,15);
        t1.removeBuilding(1);
        assertFalse(t1.ok());
    }
    @Test
    public void DeberiaBorrarHeroe(){
        t1.addBuilding(0,100,100,15);
        t1.addHeroe("Black",1,15);
        t1.removeHero("Black");
        assertTrue(t1.ok());
    }
    //@Test
    public void NodeberiaBorrarHeroe(){
        t1.removeHero("Black");
        assertTrue(t1.ok());
    }
    @Test
    public void Debereiajumpplan(){
        t1.addBuilding(0,100,100,15);
        t1.addBuilding(100,100,200,15);
        t1.addBuilding(300,100,450,15);
        t1.addBuilding(400,100,100,15);
        t1.addHeroe("black",1,15);
        int[]lista=t1.jumpPlan("black",4);
        t1.jump("black",lista[1],lista[0],false);
        assertTrue(t1.ok());
    }
    @Test
    public void deberiasersaltoseguro(){
        t1.makeVisible();
        t1.addBuilding(100,100,100,15);
        t1.addHeroe("blue",1,45);
        t1.addBuilding(400,100,5,15);
        t1.makeVisible();
        assertTrue(t1.isSafejump("blue",167,87));
    }
    //@Test
    public void deberiaJumpPlan(){
        t1.addBuilding(0,100,100,100);
        t1.addHeroe("blue",1,45);
        t1.addBuilding(300,300,300,300);
        t1.makeVisible();
        int[] list=t1.jumpPlan("blue",2);
        t1.jump("blue",74,58,true);
        assertTrue(t1.strength("blue")==15);
    }
    @Test
    public void Debereiajump(){
        t1.addBuilding(0,100,100,15);
        t1.addBuilding(100,100,200,15);
        t1.addBuilding(300,100,450,15);
        t1.addBuilding(400,100,100,15);
        t1.addHeroe("black",1,15);
        t1.jump("black");
        assertTrue(t1.strength("black")==15);
    }
    @Test
    public void deberiaUndo(){
        t1.addBuilding(0,100,100,100);
        t1.addBuilding(200,100,100,100);
        t1.addBuilding(400,100,100,100);
        t1.addHeroe("red",1,45);
        t1.removeHero("red");
        t1.addHeroe("blue",1,45);
        t1.jump("blue",3);
        t1.makeVisible();
        t1.undo();
        t1.undo();
        t1.undo();
        t1.undo();
        t1.undo();
        t1.undo();
        t1.undo();
        assertTrue(t1.ok());
    }
}
