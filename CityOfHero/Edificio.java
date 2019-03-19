package CityOfHero; 
import java.util.ArrayList;
import java.util.Collections;
import Shapes.*;
/**
 * Write a description of class Edificios here.
 *
 * Esta clase crea edificios.
 */
public class Edificio implements Element
{
    // instance variables - replace the example below with your own
    private String[] colores = {"white","red","pink","orange","yellow","green","magenta","blue"};
    private Rectangle edificio;
    private Rectangle puerta;
    private int ancho;
    private int alto;
    private int dureza;
    private Heroe visitante;
    
    /**
     * Este constructor genera cada edificio con ciertas caracteristicas.
     */
    public  Edificio(int x,int width,int height, int altura, int anchura,int hardness, int c)
    {
        if (!(height-50 >= altura || width >= anchura))
        {      
            edificio = new Rectangle();
            puerta = new Rectangle();
            
            edificio.changeSize(height,width);
            puerta.changeSize(height/10,width/10);
            
            edificio.moveHorizontal(x-70);
            puerta.moveHorizontal(x-70+(width/2));
            
            edificio.moveVertical(altura-55-height);
            puerta.moveVertical(altura-55-height/10);
            
            edificio.changeColor(colores[c]);
            puerta.changeColor("black");
            
            alto=height;
            ancho=width;
            dureza=hardness;
        }
    }
    public void makeVisible(){
        edificio.makeVisible();
        puerta.makeVisible();
    }
    public void makeInvisible(){
        edificio.makeInvisible();
        puerta.makeInvisible();
    }
    public void puerta(){
        this.puerta.moveRight();
        this.puerta.moveLeft();
    }
    public void changexy(int x,int y){
        this.edificio.setCambiarxy(x,y);
    }
    public int getAlto(){
        return alto;
    }
    public int getAncho(){
        return ancho;
    }
    public int getHardness(){
        return dureza;
    }
    public int yPosition(){
        return this.edificio.GetyPosition();
    }
    public int xPosition(){
        return this.edificio.GetxPosition();
    }
    public void changeSize(int newalto,int newancho){
        this.edificio.changeSize(newalto,newancho);
        alto=newalto;
        ancho=newancho;
    }
    public void setVisitante(Heroe heroe){
        visitante=heroe;
    }
    public Heroe getVisitante(){
        return visitante;
    }

}
