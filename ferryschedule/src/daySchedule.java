import java.util.ArrayList;
import java.util.List;

public class daySchedule {
    private ArrayList<Time> schedule = new ArrayList<Time>();
    private int max;
    private int percentage30MinBefore;
    private int percentage30MinAfter;
    private int totalComing;
    private int total;
    private int totalNotComing;
    private int totalOnSc;
    private int totalNotOnSc;
    private int totalFerries;
    private int averagePerFerry;
    private int mostFilled;
    private int leastFilled;
    private ArrayList<Integer> poll;


    public daySchedule(ArrayList<Time> schedule, int max, int percentage30MinBefore, int percentage30MinAfter, ArrayList<Integer> poll) {
        this.schedule = schedule;

        this.max = max;
        this.percentage30MinBefore = percentage30MinBefore;
        this.percentage30MinAfter = percentage30MinAfter;
        totalComing = 0;
        total = 0;
        totalNotComing = 0;
        totalOnSc = 0;
        totalNotOnSc = 0;
        totalFerries = 0;
        averagePerFerry = 0;
        mostFilled = 0;
        leastFilled = max;
        this.poll = poll;

    }

    public void resetTP() {

        for (int i = 0; i < poll.size(); i++) {

            schedule.get(i).setTotalPeople(poll.get(i));
        }
    }

    public ArrayList<String> getSchedule() {
        resetTP();


        ArrayList<String> list = new ArrayList<>();

        String cop1 = "";
        String cop2 = "";
        for (int i = 0; i < schedule.size(); i++) {

            list.add(String.valueOf(schedule.get(i).isFerryComing()));


            if (i % 2 == 0) {
                cop1 = cop1 + "      " + schedule.get(i).isFerryComing();
                if (schedule.get(i).isFerryComing() == true) {
                    cop2 = cop2 + "      " + "    ";
                } else {
                    cop2 = cop2 + "      " + "     ";
                }
            } else {
                cop2 = cop2 + "      " + schedule.get(i).isFerryComing();
                if (schedule.get(i).isFerryComing() == true) {
                    cop1 = cop1 + "      " + "    ";
                } else {
                    cop1 = cop1 + "      " + "     ";
                }
            }

        }
        resetTP();
        //     System.out.println(cop1);
        //   System.out.println("");
        // System.out.println(cop2);

        for (int i = 0, j = list.size() - 1; i < j; i++) {
            list.add(i, list.remove(j));
        }

        return list;
    }

    public String getOccupancy() {
        resetTP();

        String sch = "";
        for (int i = 0; i < schedule.size(); i++) {

            sch = sch + " " + String.valueOf(numBoardingAtX(i));

        }
        return sch;
    }

    public int numBoarding() {
        return allInOne(1);
    }

    public int total() {
        return allInOne(2);
    }

    public int numBoardingOnPreferredSchedule() {
        return allInOne(3);
    }

    public int numBoardingNotOnPreferredSchedule() {
        return allInOne(4);
    }

    //number of trips for the day
    public int numFerries() {
        return allInOne(5);
    }


    public double averagePeoplePerFerry() {

        return ((double) allInOne(6) / 1000);
    }

    public int mostFilledFerry() {
        return allInOne(7);
    }

    public int leastFilledFerry() {
        return allInOne(8);
    }

    public double ratioPreferedSch() {
        return (double) allInOne(9) / 1000;
    }

    //number of people boarding at specific schedule
    public int numBoardingAtX(int y) {
        return allInOne(y + 10);
    }

    private int allInOne(int x) {

        resetTP();

        for (int i = 0; i < schedule.size(); i++) {

            total = total + schedule.get(i).getPeople();

            if (!schedule.get(i).isFerryComing()) {

                if (i <= 1) {
                    schedule.get(i + 2).setTotalPeople((int) Math.ceil((double) schedule.get(i + 1).getTotalPeople() + ((double) percentage30MinAfter / 100) * (double) schedule.get(i).getPeople()));
                } else if (i == schedule.size() - 1) {
                    schedule.get(i - 2).setTotalPeople((int) Math.ceil((double) schedule.get(i - 2).getTotalPeople() + ((double) percentage30MinBefore / 100) * (double) schedule.get(i).getPeople()));
                } else if (i == schedule.size() - 2) {
                    schedule.get(i - 2).setTotalPeople((int) Math.ceil((double) schedule.get(i - 2).getTotalPeople() + ((double) percentage30MinBefore / 100) * (double) schedule.get(i).getPeople()));
                } else if (i == schedule.size()) {
                    schedule.get(i - 2).setTotalPeople((int) Math.ceil((double) schedule.get(i - 2).getTotalPeople() + ((double) percentage30MinBefore / 100) * (double) schedule.get(i).getPeople()));
                } else {
                    schedule.get(i + 2).setTotalPeople((int) Math.ceil((double) schedule.get(i + 2).getTotalPeople() + ((double) percentage30MinBefore / 100) * (double) schedule.get(i).getPeople()));
                    schedule.get(i - 2).setTotalPeople((int) Math.ceil((double) schedule.get(i - 2).getTotalPeople() + ((double) percentage30MinAfter / 100) * (double) schedule.get(i).getPeople()));
                }
            }

        }

        for (int i = 0; i < schedule.size(); i++) {

            if (schedule.get(i).isFerryComing() && (schedule.get(i).getPeople() > max)) {

                if (i == 0) {
                    schedule.get(i + 1).setTotalPeople((int) Math.ceil(schedule.get(i + 1).getTotalPeople() + ((double) percentage30MinAfter / 100) * (double) schedule.get(i).getOverMax()));

                } else if (i == schedule.size() - 1) {
                    schedule.get(i - 1).setTotalPeople((int) Math.ceil(schedule.get(i - 1).getTotalPeople() + ((double) percentage30MinBefore / 100) * (double) schedule.get(i).getOverMax()));

                } else {
                    schedule.get(i + 1).setTotalPeople((int) Math.ceil((double) schedule.get(i + 1).getTotalPeople() + ((double) percentage30MinBefore / 100) * (double) schedule.get(i).getOverMax()));
                    schedule.get(i - 1).setTotalPeople((int) Math.ceil((double) schedule.get(i - 1).getTotalPeople() + ((double) percentage30MinAfter / 100) * (double) schedule.get(i).getOverMax()));


                }
                schedule.get(i).setTotalPeople(max);

            }
        }

        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).isFerryComing() && (schedule.get(i).getTotalPeople() > max)) {
                schedule.get(i).setTotalPeople(max);

            }

        }


        for (int i = 0; i < schedule.size(); i++) {

            if (schedule.get(i).isFerryComing()) {

                if (schedule.get(i).getTotalPeople() > mostFilled) {
                    mostFilled = schedule.get(i).getTotalPeople();
                }

                if (schedule.get(i).getTotalPeople() < leastFilled) {
                    leastFilled = schedule.get(i).getTotalPeople();
                }


                totalFerries = totalFerries + 1;
                totalComing = totalComing + schedule.get(i).getTotalPeople();

                if (schedule.get(i).getPeople() <= max) {
                    totalOnSc = totalOnSc + schedule.get(i).getPeople();
                } else {
                    totalOnSc = totalOnSc + max;
                }

            }

        }

        totalNotOnSc = totalComing - totalOnSc;

        averagePerFerry = (int) ((((double) totalComing / (double) totalFerries)) * 1000);


        if (x == 1) {
            return totalComing;
        }

        if (x == 2) {
            return total;
        }

        if (x == 3) {
            return totalOnSc;
        }

        if (x == 4) {
            return totalNotOnSc;
        }

        if (x == 5) {
            return totalFerries;
        }

        if (x == 6) {
            return averagePerFerry;
        }

        if (x == 7) {
            return mostFilled;
        }

        if (totalFerries == 0) {
            leastFilled = 0;
        }

        if (x == 8) {
            return leastFilled;
        }
        if (x == 9) {
            return (int) (((double) totalOnSc / (double) totalComing) * 1000);
        }

        if (x - 10 >= 0 && x - 10 <= schedule.size()) {

            if (schedule.get(x - 10).isFerryComing()) {
                return (schedule.get(x - 10).getTotalPeople());
            } else {
                return 0;
            }

        }

        return -1;
    }


}
