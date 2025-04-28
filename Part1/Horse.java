
/**
 * This class defines the data and behaviour of horses in the race,
 * 
 * @author Taima Holden
 * @version 28/04/2025
 */
public class Horse
{
    //Fields of class Horse
    private char horseSymbol;
    private String horseName;
    private double horseConfidence;
    private boolean horseFallen = false;
    private int horseDistanceTravelled = 0;
      
    //Constructor of class Horse
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
       this.horseSymbol = horseSymbol;
       this.horseName = horseName;
       this.horseConfidence = horseConfidence;
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        horseFallen = true;
    }
    
    public double getConfidence()
    {
        return horseConfidence;
    }
    
    public int getDistanceTravelled()
    {
        return horseDistanceTravelled;
    }
    
    public String getName()
    {
        return horseName;
    }
    
    public char getSymbol()
    {
        return horseSymbol;
    }
    
    public void goBackToStart()
    {
        horseDistanceTravelled = 0;
        horseFallen = false;
    }
    
    public boolean hasFallen()
    {
        return horseFallen;
    }

    public void moveForward()
    {
        horseDistanceTravelled++;
    }

    public void setConfidence(double newConfidence)
    {
        if (newConfidence >= 0 && newConfidence <= 1)
        {
            horseConfidence = newConfidence;
        }
    }
    
    public void setSymbol(char newSymbol)
    {
        horseSymbol = newSymbol;
    }
    
}
