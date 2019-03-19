package CityOfHero;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
public class CityOfHeroesContestTest {
    private int[] configuracion; private int[][] building;
    private String correct [][]; private String solved [][];
    @Test
    public void SegunAWshouldSolve(){
        CityOfHeroesContest ch = new CityOfHeroesContest();
        configuracion = new int[] {20 ,1 ,1000 ,1000, 7 ,1};
        int building [][] = new int [][]{{1000 ,1 ,1000 ,1 ,1000 ,1000 ,1000 ,1 ,1 ,1000 ,1 ,1 ,1000 ,1 ,1000 ,1000 ,1 ,1000 ,1000 ,1}};
        solved = ch.solve(configuracion,building);
        correct= new String [][] {{"1","1","1","1","1","1","0","1","1","1","1","1","1","1","1","1","1","1","1","1"}};
        System.out.println(solved);
        assertTrue(Arrays.deepEquals(solved,correct));
        
        configuracion = new int[] {1, 20, 1 ,1000 ,1 ,14};
        building = new int [][] {{1000},{1},{1},{1000},{1},{1000},{1},{1},{1000},{1},{1},{1},{1},{1},{1},{1},{1},{1000},{1000},{1000}};
        solved = ch.solve(configuracion,building);
        correct = new String [][] {{"1"},{"1"},{"1"},{"1"},{"1"} ,{"1"},{"1"},{"1"},{"1"},{"1"},{"1"},{"1"},{"1"},{"0"},{"1"},{"1"},{"1"},{"1"},{"1"},{"1"}};
        assertTrue(Arrays.deepEquals(solved,correct));

        configuracion = new int[] {20,1,2,500,1,1};
        building = new int [][] {{1,1000,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1000,1}};
        solved = ch.solve(configuracion,building);
        correct = new String [][] {{"0","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"}};
        assertTrue(Arrays.deepEquals(solved,correct));
    }
    @Test
    public void SegunAWshouldSimulate(){
        CityOfHeroesContest ch = new CityOfHeroesContest();
        configuracion = new int[] {4,1,100,55,1,1};
        building = new int [][] {{10,1000,60,10}};
        assertFalse(ch.simulate(configuracion,building,3));
        assertFalse(ch.simulate(configuracion,building,2));
        assertFalse(ch.simulate(configuracion,building,4));
        
        configuracion = new int[] {4,1,100,55,1,1};
        building = new int [][] {{0,10,300,30}};
        assertFalse(ch.simulate(configuracion,building,3));
        assertFalse(ch.simulate(configuracion,building,4));
    }
    @Test
    public void segunJMDeberiaHacerSimulate(){
        CityOfHeroesContest ch = new CityOfHeroesContest();
        configuracion = new int[] {4,1,100,55,1,1};
        building = new int [][] {{30,40,50,60}};
        assertTrue(ch.simulate(configuracion,building,2));
    }
}