public class Player
{
    private String Name;
    private int Cash;
    private int LaneBettedOn = -1;
    private int CashWagered = 0;
    public Player(String Name,int StartingCash)
    {
        this.Name = Name;
        this.Cash = StartingCash;
    }

    public String getName()
    {
        return Name;
    }

    public int getCash()
    {
        return Cash;
    }

    public int getLaneBettedOn()
    {
        return LaneBettedOn;
    }

    public int getCashWagered()
    {
        return CashWagered;
    }

    public boolean wagerCash(int Quantity, int Lane)
    {
        if (Quantity <=  Cash && LaneBettedOn == -1)
        {
            Cash = Cash - Quantity;
            CashWagered = Quantity;
            LaneBettedOn = Lane;
            return true;
        }

        return false;
    }

    public void returnWager()
    {
        Cash += CashWagered;
        CashWagered = 0;
    }

    public void laneWon(int Multiplier)
    {
        Cash += CashWagered * Multiplier;
    }
}