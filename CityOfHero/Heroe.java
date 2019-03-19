package CityOfHero; 
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import java.lang.Double;
import java.util.Hashtable;
import Shapes.*;
/**
 * Write a description of class Heroes here.
 *
 * Esta clase crea un heroe
 */
public class Heroe implements Element
{
    private Rectangle cuerpo; 
    private Rectangle fondovida;
    private Rectangle vida;
    private int fuerza;
    private String colorh;
    private boolean fin=true;


    /**
     * Este constructor es el encargado de hacer las caracteristicas de un heroe.
     */
   public Heroe (String color, int x,int y, int strength, int ancho){
       cuerpo= new Rectangle();
       cuerpo.changeSize(10,10);
       cuerpo.changeColor(color);
       cuerpo.moveVertical(y-20);
       cuerpo.moveHorizontal(x-70+(ancho/2));
       
       fondovida= new Rectangle();
       fondovida.changeSize(20,strength+20);
       fondovida.changeColor(color);
       fondovida.setCambiarxy(cuerpo.GetxPosition()-40,cuerpo.GetyPosition()-40);
       
       vida =  new Rectangle();
       vida.changeSize(10,strength);
       vida.changeColor("cyan");
       vida.setCambiarxy(cuerpo.GetxPosition()-30,cuerpo.GetyPosition()-35);
       
       fuerza=strength;
       
       colorh= color;
       
   }
   public void jump(int velocity, int angle, boolean slow, int calle,int[] ejex,int altura,boolean ok,int ancho){
       if (ok){
          int movex = this.cuerpo.GetxPosition();
          int movey = this.cuerpo.GetyPosition();
          double g = 9.81;
          double angulo = Math.toRadians(angle);
          double vuelot = (2*velocity*Math.sin(angulo))/g;
          double voX = velocity*Math.cos(angulo);
          double voY = velocity*Math.sin(angulo); 
          double t = 0.1;
          double x = this.cuerpo.GetxPosition();
          double y = this.cuerpo.GetyPosition();
          if (slow){
           while (this.cuerpo.GetyPosition()<calle && vueloSeguro(x,-y,ejex,altura) && this.cuerpo.GetyPosition()>0 && this.cuerpo.GetxPosition()>0 && this.cuerpo.GetxPosition()<ancho){
             x = (voX*t+movex);
             y = ((-4.9*(t*t))+(voY*t)-movey);
             this.cuerpo.setCambiarxy((int) (x),(int) (-y));
             this.fondovida.setCambiarxy((int) (x)-40,(int) (-y)-40);
             this.vida.setCambiarxy((int) (x)-30,(int) (-y)-35);
             t += 0.1;
            }
          }
          else{  
           while (this.cuerpo.GetyPosition()<calle && vueloSeguro(x,-y,ejex,altura)&& this.cuerpo.GetyPosition()>0 && this.cuerpo.GetxPosition()>0 && this.cuerpo.GetxPosition()<ancho){
             x = (voX*t+movex);
             y = ((-4.9*(t*t))+(voY*t)-movey);
             this.cuerpo.setCambiarxy((int) (x),(int) (-y));
             this.fondovida.setCambiarxy((int) (x)-40,(int) (-y)-40);
             this.vida.setCambiarxy((int) (x)-30,(int) (-y)-35);
             t += 0.1;
            }
          }
        }
       else{
            fin=false;
        }
   }
   public ArrayList<Integer> simulasalto(int velocity, int angle, int calle,int[] ejex,int altura,int ancho){
          int movex = this.cuerpo.GetxPosition();
          int movey = this.cuerpo.GetyPosition();
          double g = 9.81;
          double angulo = Math.toRadians(angle);
          double vuelot = (2*velocity*Math.sin(angulo))/g;
          double voX = velocity*Math.cos(angulo);
          double voY = velocity*Math.sin(angulo); 
          double t = 0.1;
          double x = this.cuerpo.GetxPosition();
          double y = this.cuerpo.GetyPosition();
          while (this.cuerpo.GetyPosition()<calle && vueloSeguro(x,-y,ejex,altura) && this.cuerpo.GetyPosition()>0 && this.cuerpo.GetxPosition()>0 && this.cuerpo.GetxPosition()<ancho){
             x = (voX*t+movex);
             y = ((-4.9*(t*t))+(voY*t)-movey);
             t += 0.1;
            }
          ArrayList<Integer> pos = new ArrayList<Integer>();
          pos.add((int)(-y));
          pos.add((int)(x));
          return pos;
    }
   private boolean vueloSeguro(double posX, double posY, int[] ejeX,int height){
       posX = Math.floor(posX);
       posY = Math.floor(posY);
       boolean ok = true; 
       if(height -50-ejeX[(int) posX] <= (int) posY) {
        ok = false;
       }
       return ok;
   } 
   public void makeVisible(){
       this.cuerpo.makeVisible();
       this.fondovida.makeVisible();
       this.vida.makeVisible();
    }
   public void makeInvisible(){
       this.cuerpo.makeInvisible();
       this.fondovida.makeInvisible();
       this.vida.makeInvisible();
    }
   public int getstrength(){
       return this.fuerza;
    }
   public int yPosition(){
       return this.cuerpo.GetyPosition();
    }
   public int xPosition(){
       return this.cuerpo.GetxPosition();
    }
   public void changexy(int x,int y){
       this.cuerpo.setCambiarxy(x,y);
       this.fondovida.setCambiarxy(x-40,y-40);
       this.vida.setCambiarxy(x-30,y-35);
    }
   public String getColor(){
       return this.colorh;
    }
   public void changestrength(int daño){
       this.fuerza=daño;
       this.vida.changeSize(vida.altura(),daño);
    }
}
