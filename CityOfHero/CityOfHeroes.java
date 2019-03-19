package CityOfHero; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import javax.swing.JOptionPane;
import java.lang.Math;
import java.util.Stack;
import Shapes.*;
/**
 * .
 *
 * @authors jose Luis Gomez C, Nikolai Bermudez
 * Esta clase permite crear edificios y heroes.
 * 
 */
public class CityOfHeroes
{
    private int ancho;
    private int c;
    private int[] ejex;
    private int[] aux;
    private int altura, anchura;
    
    
    private ArrayList<Integer> posx;
    private ArrayList<String> colores;
    private ArrayList<String> muertos;
    private Canvas canvas;


    private Hashtable<Integer, Edificio> Edificios;
    private Hashtable<String,Heroe> Heroes;
    
    private Rectangle calle;
    private Rectangle city;
    private Circle sol;
    
    private boolean visible=false;
    private boolean fin, veri;
    
    private Stack<Action> stack = new Stack();
    
    
    /**j
     * Constructor de objetos de la clase CityOfHeroes el cual pide una altura 
     * y ancho de la ciudad. 
     */
    public CityOfHeroes(int width,int height)
    {
        ejex = new int[width+1];
        aux = new int[width+1];
        
        altura = height;
        anchura = width+1;
        
        canvas = Canvas.getCanvas();
        
        city = new Rectangle();
        city.changeSize(height,width);
        city.changeColor("cyan");
        city.makeVisible();
        city.setCambiarxy(0,0);
        
        calle= new Rectangle();
        calle.changeSize(40,width);
        calle.moveHorizontal(-70);
        calle.moveVertical(height-55);
        calle.changeColor("gray");
        
        sol = new Circle();
        sol.changeColor("yellow");
        
        c=0;
        posx= new ArrayList<Integer>();
        colores= new ArrayList<String>();
        muertos= new ArrayList<String>();
        Edificios =new Hashtable<Integer, Edificio>();
        Heroes = new Hashtable<String,Heroe>();
        fin=true;
    }
    public void eliminarEdificios(){
        int x = posx.get(Edificios.size()-1);
        Edificios.remove(x);
    }
    
    public void agregarEdificio(int x,int ancho,int alto,int dureza){
        Edificio edificio= new Edificio(x,ancho,alto,altura,anchura,dureza,c);
        Edificios.put(x,edificio);
    }
    
    public void agregarHeroe(String color, int ed, int fuerza, boolean ok, int x, int y, int ancho){
        Heroe heroe = new Heroe(color, x,y, fuerza,ancho);
        
    }
    
    public void undo(){
        System.out.println(Edificios.size());
        Action action = stack.pop();
        action.undo();
        System.out.println(Edificios.size());
    }
    public void redo(){
    
    }
    /**
     * Metodo que añade un edificios, con las caracteristicas que el usuario nos de como:
     * posicion, anchura, altura y dureza; todos de tipo entero.
     */
    public void addBuilding(int x, int width, int height,int hardness)
    {  
        boolean ok = true;
        if (width<0 || height<0 || height>altura-150 || x+width>anchura){
            ok=false;
        }
        if (ok){
            for(int i=x;i<=x+width;++i){
                if (ejex[i] !=0)
                    ok=false;
            }
         }
        if (ok)
        {
           if (c>7){
               c = 0;
           }
           Edificio edificio;
           for (int i=x;i<x+width;++i){
                ejex[i]= height;
                aux[i] = x;
             }
           Edificios.put(x,edificio= new Edificio(x,width,height,altura,anchura,hardness,c));
           if(visible){
               edificio.makeVisible();
            }
           posx.add(x);
           Collections.sort(posx);
           c+=1;
           
           stack.push(new StaticAction(edificio,"crear",x,width,this,"edificio",height,hardness));
           
        }
        else
        {
            fin=false;
        }
        
    }
    /**
     * Metodo que añade un heroe, con respecto a las caracteristicas que nos envie el usuario como:
     * color de tipo String
     * edificio donde el heroe inicia su aventura y su fuerza, de tipo entero.
     */
    public void addHeroe(String color, int hidingBuilding, int strength)
    {
       boolean ok= posx.size()>hidingBuilding-1 && posx.size()!=0 ;
       if (ok){
           Heroe heroe;
           int x = posx.get(hidingBuilding-1);
           int y = altura-45-Edificios.get(x).getAlto();
           colores.add(color);
           heroe = new Heroe(color,x,y,strength,Edificios.get(x).getAncho());
           if(visible){
               heroe.makeVisible();
            }
           Heroes.put(color,heroe);
           Edificios.get(x).setVisitante(heroe);
           stack.push(new StaticAction(heroe,"crear",this,"heroe",heroe.getColor(),hidingBuilding,strength,Edificios.get(x).getAncho(),y));
        }else{
           fin=false;
         }  
       }
    

    public void addHeroe(String color, int hidingBuilding, int strength, boolean veri)
    {
       boolean ok= posx.size()>hidingBuilding-1 && posx.size()!=0 ;
       int temp = hidingBuilding;
       if (ok){
           Heroe heroe;
           int x = posx.get(hidingBuilding-1);
           int y = altura-45-Edificios.get(x).getAlto();
           colores.add(color);
           heroe = new Heroe(color,x,y,strength,Edificios.get(x).getAncho());
           if(visible){
            heroe.makeVisible();
            
           Heroes.put(color,heroe);
           Edificios.get(x).setVisitante(heroe);
           
            if(veri == true){
           stack.push(new StaticAction(heroe,"crear",this,"heroe",heroe.getColor(),hidingBuilding,strength,Edificios.get(x).getAncho(),y));
           }
        }else{
           fin=false;
           }  
       }
    }
    /**
     * Metodo que elimina un edificio con respecto al orden visual del usuario
     */

    public void removeBuilding(int position){
        int x = posx.get(position-1);
        if (Edificios.get(x).getVisitante()==null){
                Edificios.get(x).makeInvisible();
            for (int i=x;i<x+Edificios.get(x).getAncho();++i){
                    ejex[i]= 0;
            }
            Edificio temp = Edificios.get(x);
            Edificios.remove(x);
            stack.push(new StaticAction(temp,"eliminar",x,temp.getAncho(),this,"edificio",temp.getAlto(),temp.getHardness()));
        }else{
            fin=false;
        }
    }
    /**
     * Este metodo remueve un heroe, el que el usuario indique con su color respectivo.
     */
    public void removeHero(String color){
      boolean ok=false;
      for(String i:colores){
          if (i==color){
              ok=true;
              break;
            }
        }
      if (colores.size()>0 && ok){
          Heroes.get(color).makeInvisible();
          quitarVisitante(color,aux[Heroes.get(color).xPosition()]);
          
          stack.push(new StaticAction(Heroes.get(color),"eliminar",this,"heroe",color));
       }else{
           fin=false;
         }
    }
    
        /**
     * Este metodo remueve un heroe, el que el usuario indique con su color respectivo.
     */
    public void removeHero(String color, boolean veri){
      boolean ok=false;
      for(String i:colores){
          if (i==color){
              ok=true;
              break;
            }
       }
      if (colores.size()>0 && ok){
          Heroes.get(color).makeInvisible();
          quitarVisitante(color,aux[Heroes.get(color).xPosition()]);
          if (veri == true){
          stack.push(new StaticAction(Heroes.get(color),"eliminar",this,"heroe",color));
          }
       }
      else{
           fin=false;
        }
    }
    public String[] deads(){
        String[] deads= new String[muertos.size()];
        return muertos.toArray(deads);
    }
    public void finish(){
        System.exit(0);
    }
    public void zoom(char z){
        if (z =='+' || z =='-'){
            canvas.acercar(z);
            if (z =='+'){
                stack.push(new ZoomAction("acercarse"));
            }else{
                stack.push(new ZoomAction("alejarse"));  
            }
       }
       else{
            fin=false;
        }
    }
    /**
     * Este metodo genera el salto de un heroe, el usuario escoge cual heroe quiere que salte.
     */
    public void jump(String heroe, int velocity, int angle, boolean slow ){
      boolean ok=false;
      int temp1 = Heroes.get(heroe).yPosition();
      int temp2 = Heroes.get(heroe).xPosition();
      for(String i:colores){
          if (i==heroe){
              ok=true;
              break;
            }
        }
      if (colores.size()>0 && ok){
            if (velocity !=0 && angle != 0){
                Heroes.get(heroe).jump(velocity,angle,slow,calle.GetyPosition(),ejex,altura,ok,anchura);
                cortaredificio(heroe,aux[Heroes.get(heroe).xPosition()]);
                stack.push(new MovementAction(Heroes.get(heroe),Edificios.get(aux[Heroes.get(heroe).xPosition()]),"salto",temp1,temp2));
            }
       }
    }
    public void jump(String heroe,int building){
        int[] lista = jumpPlan(heroe,building);
        jump(heroe,lista[1],lista[0],true);
    }
    public void jump(String heroe){
        boolean ok=true;
        for(int i=posx.size(); i>0 && ok;--i){
            
            int[]salto=jumpPlan(heroe,i);
            if(salto[0]!=0&&salto[1]!=0){
                jump(heroe,salto[1],salto[0],true);
                ok=false;
            }
        }
    }
    public int[] jumpPlan(String heroe,int building){
        int[] fin = new int[2];
        int llave = posx.get(building-1);
        int llave2 = aux[Heroes.get(heroe).xPosition()];
        int Xmax =  llave - Heroes.get(heroe).xPosition();
        double angulo;
        double v0;
        double div;
        if (Edificios.get(llave2).getAlto() < Edificios.get(llave).getAlto()){
            int Ymax = Edificios.get(llave).getAlto()-Edificios.get(aux[Heroes.get(heroe).xPosition()]).getAlto();
            div = (4*(double)(Ymax))/(2*(double)(Xmax));
            angulo= Math.atan(div);
            v0 = Math.sqrt((Ymax*2*(9.8))/Math.pow((Math.sin(angulo)),2));
        }else{
            int Ymax = Edificios.get(aux[Heroes.get(heroe).xPosition()]).getAlto();
            div = (4*(double)(Ymax))/(2*(double)(Xmax));
            angulo = Math.atan(div);
            v0 = Math.sqrt((Xmax*(9.8))/(Math.sin(2*angulo)));
        }
        int cont=0;
        int angulos=(int)((angulo*180)/Math.PI);
        while(angulos<90){
            if (isSafejump(heroe,(int)(v0),angulos,llave)){
                fin[0] =angulos ;
                fin[1] = (int)(v0);
                angulos=91;
            }else if(cont==0){
                int ymax=0;
                for(int i:posx){
                    ymax=Edificios.get(i).getAlto()>ymax?Edificios.get(i).getAlto():ymax;
                }
                ymax+=200;
                div=(4*(double)(ymax)/(2*(double)(Xmax)));
                angulo=Math.atan(div);
                v0=Math.sqrt((ymax*2*9.8)/Math.pow((Math.sin(angulo)),2));
                angulos=(int)((angulo*180)/Math.PI);
                cont+=1;
            }else{
                angulos+=1;
            }
        }
        return fin;
        }
    public boolean isSafejump(String heroe, int velocity, int angle){
        boolean fin=false;
        ArrayList<Integer> list;
        list=Heroes.get(heroe).simulasalto(velocity,angle,calle.GetyPosition(),ejex,altura,anchura);
        if(calle.GetyPosition()-10>list.get(0) && list.get(0)>0){
            if (list.get(0)-10<Edificios.get(aux[list.get(1)]).yPosition()){
                fin=true;
             }
        }
        return fin;
    }
    private boolean isSafejump(String heroe, int velocity, int angle,int edificio){
        boolean fin=false;
        ArrayList<Integer> list;
        list=Heroes.get(heroe).simulasalto(velocity,angle,calle.GetyPosition(),ejex,altura,anchura);
        if(calle.GetyPosition()-10>list.get(0) && list.get(0)>0){
            if (list.get(0)-10<Edificios.get(edificio).yPosition()&& list.get(1)>edificio && list.get(1)<edificio+Edificios.get(edificio).getAncho()){
                fin=true;
             }
        }
        return fin;
    }
    public int strength(String color){
        return Heroes.get(color).getstrength();
    }
    public void makeVisible(){
        canvas.setVisible(true);
        calle.makeVisible();
        sol.makeVisible();
        for(int i:posx){
            Edificios.get(i).makeVisible();
          }
        for(String i:colores){
            Heroes.get(i).makeVisible();
          }
        visible=true;
    }
    public void makeInvisible(){
        canvas.setVisible(true);
        calle.makeInvisible();
        sol.makeInvisible();
        for(int i:posx){
            Edificios.get(i).makeInvisible();
         }
        for(String i:colores){
            Heroes.get(i).makeInvisible();
         }
        visible=false;
    }
    public boolean ok(){
        return fin;
    }
    private void cortaredificio(String color,int x){
        if (calle.GetyPosition()-10>Heroes.get(color).yPosition()&& Heroes.get(color).yPosition()>0){
            if(Heroes.get(color).yPosition()-10>Edificios.get(x).yPosition()){
                Edificios.get(x).changeSize(altura-50-Heroes.get(color).yPosition(),Edificios.get(x).getAncho());
                Edificios.get(x).changexy(x,Heroes.get(color).yPosition()+10);
                newaltura(Edificios.get(x).getAlto(),x,Edificios.get(x).getAncho());
                Edificios.get(x).puerta();
                Heroes.get(color).changexy(x+(Edificios.get(x).getAncho()/2),Heroes.get(color).yPosition());
                vida(x,color);
                pelea(x,color);
            }else{
                    System.out.println(Heroes.get(color).yPosition());
                    Heroes.get(color).changexy(x+(Edificios.get(x).getAncho()/2),Edificios.get(x).yPosition()-10);
                    pelea(x,color);
                }
        }else{
            removeHero(color);
        }
      }
    private void quitarVisitante(String color,int x){
        Edificios.get(x).setVisitante(null);
     }
    private void pelea(int x, String color){
        if (Edificios.get(x).getVisitante()!=null){
            if(Edificios.get(x).getVisitante().getstrength()>=Heroes.get(color).getstrength()){
                removeHero(color);
                Edificios.get(x).setVisitante(Heroes.get(color));
            }else{
                 removeHero(Edificios.get(x).getVisitante().getColor());
                 Edificios.get(x).setVisitante(Heroes.get(color));
              }
         }else{
            Edificios.get(x).setVisitante(Heroes.get(color));
         }
    }
    private void newaltura(int alto,int x,int width){
        for (int i=x;i<x+width;++i){
                ejex[i]= alto;
         }
    }
    private void vida(int x,String color){
        int vida = Heroes.get(color).getstrength();
        int daño = Edificios.get(x).getHardness();
        Heroes.get(color).changestrength(vida-daño);
    }
    
    public int[][] city(){
        int [][] city = new int[2][];
        city[0] = new int [4*Edificios.size()];
        city[1] = new int [2*Edificios.size()];
        int aux = 0, aux1 = 0;
        for (int x: posx){
            Edificio tempo=Edificios.get(x);
            city[0][aux] = x;
            city[0][aux+1] = tempo.getAncho();
            city[0][aux+2] = tempo.getAlto();
            city[0][aux+3] = tempo.getHardness();
            aux+=4;
        }
        //System.out.println(city[1].length + " LONGITUD ");
        for(String i:colores){
            //System.out.println(i + " HEROE");<
            Heroe tempo = Heroes.get(i);
            city[1][aux1*2] = tempo.xPosition();
            //System.out.println(city[1][aux1] + " POS EN X" );
            city[1][aux1*2+1] = tempo.getstrength();
            //System.out.println(city[1][aux1+1] + " Posicion");
            aux1+=1;
        }
        
        return city;
    }
    public void modificarEjeX(int x1, int x2, int n)
    {
        for(int i = x1; i<=x2;++i){
            ejex[i]=n;
            //System.out.println(ejex[i]);
        }
    }
}