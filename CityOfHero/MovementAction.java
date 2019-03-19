package CityOfHero;

/**
 * Write a description of class Movement here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovementAction implements Action
{
    private Heroe heroe;
    private String actionType;
    private Edificio edificio;
    private int posy;
    private int posx;
    
    public MovementAction(Heroe heroe, Edificio edificio, String actionType, int posy, int posx){
        this.heroe = heroe;
        this.actionType = actionType;
        this.edificio = edificio;
        this.posy=posy;
        this.posx=posx;
    }
    /**
     * 
     *
     * @param  
     * @return  
     */
    public void undo()
    {
       heroe.changexy(posx,posy);
       
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
