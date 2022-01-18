import java.util.ArrayList;

public class Time {
    private int people;
    private int totalPeople;
    private boolean underMin;
    private int overMax;
    private boolean ferryComing;
    private boolean canTry;


    public Time(int npeople, boolean nFerryComing, int min, int max, int nmaxValueToConsider) {
        people = npeople;
        ferryComing = nFerryComing;
        if (npeople >= min) {
            underMin = false;
        } else {
            underMin = true;
        }

        if (npeople > max) {
            overMax = npeople - max;
        } else {
            overMax = 0;
        }

        if (npeople <= nmaxValueToConsider) {
            canTry = true;

        } else {
            canTry = false;
            ferryComing = true;
        }

        if (npeople < min) {
            canTry = false;
            ferryComing = false;
        }
        totalPeople = people;
    }


    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public boolean isUnderMin() {
        return underMin;
    }

    public void setUnderMin(boolean underMin) {
        this.underMin = underMin;
    }

    public int getOverMax() {
        return overMax;
    }

    public void setOverMax(int overMax) {
        this.overMax = overMax;
    }

    public boolean isFerryComing() {
        return ferryComing;
    }

    public void setFerryComing(boolean ferryComing) {
        this.ferryComing = ferryComing;
    }

    public boolean getCanTry() {
        return canTry;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }
}
