package CityOfHero; 
import java.util.*;
/**
 * Write a description of class CityOfHeroesContest here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CityOfHeroesContest
{

    private static int[][] city;
    private static int pr;
    private static int edificio;
    private static int anchura;
    private static int v;
    private static ArrayList<ArrayList<Integer>> paso;
    final public static double g = 9.80665;
    
    /**
    *Metodo solve, soluciona el problema "Getting a Jump on Crime"
    *@param configuration lista que contiene la primera linea de entrada de la arena, dimensiones de la ciudad, velocidad
     largo de los edificios, y posicion del heroe
    *@param buildings lista que contiene la altura de los edificos
    @return matriz con la solucion al problema indicando la cantidad de saltos que un heroe debe hacer para llegar a otro
    */
    public  String[][] solve(int [] configuration, int [][] buildings) {
        edificio = configuration[0];
        pr = configuration[1];
        anchura = configuration[2];
        v = configuration[3];
        int y = configuration[4]-1;
        int x = configuration[5]-1; 
        
        city = new int[pr][edificio];
        for (int i=0; i<pr; i++)
            for (int j=0; j<edificio; j++)
                city[i][j] = buildings[i][j];

        paso=new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<edificio;i++){
            paso.add(new ArrayList<Integer>());
        }
 
        String[][] res = bfs(x, y);

        return res;
    }
    /**
    *Metodo bfs realiza la busqueda en anchura de los edificios  a los cuales podria saltar un heroe
    *@param sx cordenada de inicio del heroe en profundidad ( para cityofheroes siempre sera de valor 0 )
    *@param sy coordenada de inicio del heroe a lo largo ( numero de edificio)
    *@return matriz solucion
    */
    private static String[][] bfs(int sx, int sy) {
        String[][] res = new String [pr][edificio];

        for (int i=0; i<pr; i++){
            Arrays.fill(res[i],"-1");
        }
        res[sx][sy] = "0";
        LinkedList<Integer> q = new LinkedList<Integer>();
        q.offer(sx*edificio+sy);
        while (q.size() > 0) {
            int cur = q.poll();
            int cx = cur/edificio;
            int cy = cur%edificio;
            for (int i=0; i<pr; i++) {
                for (int j=0; j<edificio; j++) {
                    if (res[i][j] != "-1"){
                        continue;
                    } 
                    if (canJump(cx, cy, i, j)) {
                        paso.get(j).add(cy+1);
                        int k=Integer.parseInt(res[cx][cy])+1;
                        String ko=Integer.toString(k);
                        res[i][j] = ko;
                        q.offer(i*edificio+j);
                    }
                }
            }
        }
        return res;
    }
     
    /**
    * Metodo canJump realiza los procedimientos matematicos (ecuaciones de fisica y calculo) para verificar 
    *si un heroe puede saltar a un edificio o no
    *@param sx cordenada en profundidad del edificio actual
    *@param sy cordenada a lo largo  del edificio actual (en que edificio esta )
    *@param ex coordenada en profundidad del edificio de llegada
    *@param ey coordenada a lo largo del edificio de llegada ( a que edificio llega)
    *@return boolean valor de verdad si puede o no saltar
    */
    private static boolean canJump(int sx, int sy, int ex, int ey) {
        boolean ok=true;
        double horizontal = Math.sqrt((sx-ex)*(sx-ex)+(sy-ey)*(sy-ey))*anchura;
        double maxAngle = Math.atan(v*v/(g*horizontal));
        double vertical = city[ex][ey] - city[sx][sy];
        double factor = vertical/horizontal;
        if (Math.tan(maxAngle) - g*horizontal/(2*v*v*Math.cos(maxAngle)*Math.cos(maxAngle)) < factor){ 
            ok=false;
        }
        if(ok){
            double low = maxAngle, high = Math.PI/2;
            for (int i=0; i<100; i++) {
                double mid = (low+high)/2;
                double rhs = Math.tan(mid) - g*horizontal/(2*v*v*Math.cos(mid)*Math.cos(mid));
                if (rhs > factor){
                    low = mid;
                }else{
                    high = mid;
                }
            }
            double theta = low;
            int dx = ex - sx;
            int dy = ey - sy;
            if (dx != 0) {
                int cambioX = dx/Math.abs(dx);
                int x = sx + cambioX;
                double y = sy +.5 + (ey-sy)/(2.0*Math.abs(dx));
                for (int i=0; i<Math.abs(dx) && ok; i++) {
                    int thisy = (int)(y+1e-9);
                    int alcanzarenX = city[x][thisy];
                    alcanzarenX = Math.max(alcanzarenX, city[x-cambioX][thisy]);
                    if (y - thisy < 1e-8) {
                        alcanzarenX = Math.max(alcanzarenX, city[x][thisy-1]);
                        alcanzarenX = Math.max(alcanzarenX, city[x-cambioX][thisy-1]);
                    }
                    double hmove = anchura*Math.sqrt( (i+.5)*(i+.5) + (y-sy-.5)*(y-sy-.5) );
                    double t = hmove/(v*Math.cos(theta));
                    double myh = v*Math.sin(theta)*t - .5*g*t*t;
                    if (myh < alcanzarenX-city[sx][sy] + 1e-10){
                        ok=false; 
                    }
                    x += cambioX;
                    y += (ey-sy)/(1.0*Math.abs(dx));
                }
            }
     
            if (dy != 0) {
    
                int cambioY = dy/Math.abs(dy);
                int y = sy + cambioY;
                double x = sx +.5 + (ex-sx)/(2.0*Math.abs(dy));
                for (int i=0; i<Math.abs(dy)&&ok; i++) {
     
                    int thisx = (int)(x+1e-9);
     
                    int alcanzarenX = city[thisx][y];
                    alcanzarenX = Math.max(alcanzarenX, city[thisx][y-cambioY]);
     
                    if (x - thisx < 1e-8) {
                        alcanzarenX = Math.max(alcanzarenX, city[thisx-1][y]);
                        alcanzarenX = Math.max(alcanzarenX, city[thisx-1][y-cambioY]);
                    } 
                    double hmove = anchura*Math.sqrt( (i+.5)*(i+.5) + (x-sx-.5)*(x-sx-.5) );
                    double t = hmove/(v*Math.cos(theta));
                    double myh = v*Math.sin(theta)*t - .5*g*t*t;                
                    if (myh < alcanzarenX-city[sx][sy] + 1e-10){
                        ok=false;
                    }
                    x += (ex-sx)/(1.0*Math.abs(dy));
                    y += cambioY;
                }
            }
         }
        return ok;
    }
        /**
    *Metodo simulate, simula el problema "Getting a Jump on Crime"
    *@param configuration lista que contiene la primera linea de entrada de la arena, dimensiones de la ciudad, velocidad
     largo de los edificios, y posicion del heroe
    *@param buildings lista que contiene la altura de los edificos
    *@param building indica al edificio al cual el heroe quiere llegar
    @return boolean si efectivamente puede llegar y realiza la simulacion o no.
    */
    
    public boolean simulate(int [] configuration, int [][] buildings, int building ){
        boolean ok=true;
        String [][] solucion = solve(configuration,buildings);
        ArrayList<Integer> recorrido = new ArrayList<Integer>();
        
        if (solucion[0][building-1]=="-1"){
            ok= false;
        }else{
            int padre=paso.get(building-1).get(0);
            int inicio = configuration[4];
            boolean t=true;
            CityOfHeroes ch= new CityOfHeroes(configuration[0]*configuration[2],700);
            ch.makeVisible();
            for (int i=0; i<configuration[0];i++){
                ch.addBuilding(configuration[2]*i,configuration[2],buildings[0][i],100);
            }
            ch.addHeroe("black",configuration[4],100);
    
            while(t){
                if (padre == inicio ){ 
                    t= false;
                }else{
                    recorrido.add(padre);
                    padre=paso.get(padre-1).get(0);
                }
            }
            Collections.reverse(recorrido);
            if(!recorrido.isEmpty()){
            int aux=recorrido.get(0);
            int i=0;
            while (i<recorrido.size()){
                ch.jump("black",aux);
                if (i+1<recorrido.size()){
                    aux=recorrido.get(i+1);
                }
                i+=1;
            }
            }
            ch.jump("black",building);
            ch.makeInvisible();
            ok=true;
        }
        return ok;
    }
}