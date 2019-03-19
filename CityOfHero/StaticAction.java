package CityOfHero;


/**
 * Write a description of class StaticAction here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StaticAction implements Action
{
    private Element elemento;
    private String actionType;
    private CityOfHeroes cityOfHeroes;
    private Edificio edificio;
    
    private String tipo;
    private String color;
    
    public int x,y;
    public int ancho;
    public int alto;
    public int dureza;
    public int ed;
    public int fuerza;
    public int anch;
    
    public boolean ok = false;
    public StaticAction(Element elemento, String actionType, int x, int ancho, CityOfHeroes cityOfHeroes, String tipo,int alto, int dureza){
        this.elemento = elemento;
        
        this.actionType = actionType;
        
        this.x = x;
        this.ancho = ancho;
        
        this.cityOfHeroes = cityOfHeroes;
        this.edificio = edificio;
        
        this.tipo = tipo;
        this.alto = alto;
        this.dureza = dureza;
    }
    
    public StaticAction(Element elemento, String actionType, CityOfHeroes cityOfHeroes, String tipo, String color, int ed, int fuerza, int anch,int y){
        this.elemento = elemento;
        this.actionType = actionType;
        this.cityOfHeroes = cityOfHeroes;
        this.tipo = tipo;
        this.color = color;
        this.ed = ed;
        this.fuerza = fuerza;
        this.ancho=ancho;
        this.y = y;
    }
    
    public StaticAction(Element elemento, String actionType, CityOfHeroes cityOfHeroes, String tipo, String color){
        this.elemento = elemento;
        this.actionType = actionType;
        this.cityOfHeroes = cityOfHeroes;
        this.tipo = tipo;
        this.color = color;
    }
    
    public void undo()
    {
        if (actionType.equals("crear")){
            if(tipo == ("edificio")){
                cityOfHeroes.modificarEjeX(x,x+ancho,0);
                cityOfHeroes.eliminarEdificios();
            }else{
                cityOfHeroes.removeHero(color,ok);
            }
            elemento.makeInvisible();
        }else{
            if(tipo == ("edificio")){
                cityOfHeroes.modificarEjeX(x,x+ancho,ancho);
                cityOfHeroes.agregarEdificio(x,ancho,alto,dureza);
            }else{
                cityOfHeroes.agregarHeroe(color,ed,fuerza,ok,x,y,anch);
            }
            elemento.makeVisible();
        }
    }
    
    /**
     * 
     *
     * @param  
     * @return  
     */
    public void redo()
    {

    }
}