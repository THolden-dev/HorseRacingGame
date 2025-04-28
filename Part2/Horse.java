
/**
 * This class defines the data and behaviour of horses in the race,
 *
 * @author Taima Holden
 * @version 28/04/2025
 */
public class Horse
{
    //Fields of class Horse
    private String horseName;
    private String HorseBreed;
    private String HorseAccessory;
    private double horseConfidence;
    private boolean horseFallen = false;
    private int horseDistanceTravelled = 0;
      
    //Constructor of class Horse
    public Horse(String horseName, String Breed, String Accessory, double horseConfidence)
    {

       this.horseName = horseName;
       this.HorseAccessory = Accessory;
       this.HorseBreed = Breed;
       this.horseConfidence = horseConfidence;
    }
    
    public String getHorseBreed()
    {
        return HorseBreed;
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
    
    public void goBackToStart()
    {
        horseFallen = false;
        horseDistanceTravelled = 0;
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
        horseConfidence = newConfidence;
    }
    
}
