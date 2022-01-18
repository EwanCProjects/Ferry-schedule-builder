// Java Program to illustrate Reading from FileReader
// using BufferedReader Class

import org.apache.batik.svggen.SVGStylingAttributes;

// Importing input output classes
import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileReader;
import java.util.HashSet;


// Main class
public class schedule {


    static int ferries;
    static int minTrips = 13;
    static boolean check = true;
    static Map<String, Object[]> days = new TreeMap<String, Object[]>();
    static int exccount = 1;
    static int totsscheds = 0;
    static Set<ArrayList> setA = new HashSet<>();
    static ArrayList<Double> aver = new ArrayList<Double>();


    // main driver method
    public static void main(String[] args) throws Exception {


        // File path is passed as parameter
        File file = new File("/Users/ewancasandjian/IdeaProjects/ferryschedule/src/surveyschedule");
        File file1 = new File("/Users/ewancasandjian/IdeaProjects/ferryschedule/src/surveyschedule1");

        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as a escape sequence)

        // Creating an object of BuffferedReader class

        int min = 10;
        int max = 100;
        int percentage30MinAfter = 20;
        int percentage30MinBefore = 20;
        int nmaxValueToConsider = 30;
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedReader br1 = new BufferedReader(new FileReader(file1));
        String st;
        String st1;
        ArrayList<Time> schedule = new ArrayList<Time>();
        ArrayList<Integer> toChange = new ArrayList<Integer>();
        int count = 0;
        ArrayList<Integer> poll = new ArrayList<Integer>();

        while ((st = br.readLine()) != null) {

            st1 = br1.readLine();
            schedule.add(new Time(Integer.valueOf(st), false, 10, 100, 50));


            toChange.add(count);


            poll.add(Integer.valueOf(st));

            count++;

            schedule.add(new Time(Integer.valueOf(st1), false, 10, 100, 50));
            toChange.add(count);
            poll.add(Integer.valueOf(st1));
            count++;
        }
        int numFerries = toChange.size();
        ferries = numFerries;


        String atPort = "pair";


        int curr = 0;
        int currtrips = 0;


        checkRec(numFerries, schedule, toChange, max, percentage30MinBefore, percentage30MinAfter, poll, atPort, curr, currtrips);


        try (XSSFWorkbook workBook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream("/Users/ewancasandjian/IdeaProjects/ferryschedule/src/test3.xlsx")) {
            Cell cell = null;
            XSSFSheet Sheet = workBook.createSheet("schedules");


            // Create the first row corresponding to the header
            XSSFRow header = Sheet.createRow(0);
            header.createCell(0).setCellValue("departures");


            header.createCell(1).setCellValue("stations");

            boolean typestation = true;


            int xa = 7;

            for (int g = 1; g < schedule.size(); g++) {
                if (typestation) {
                    header = Sheet.createRow(g);
                    header.createCell(1).setCellValue("station A to B");
                    header.createCell(0).setCellValue(String.valueOf(xa));
                } else {
                    header = Sheet.createRow(g);
                    header.createCell(1).setCellValue("StationB to A");
                    header.createCell(0).setCellValue(xa + ":30");
                    xa++;
                }


                typestation = !typestation;
            }


            int numby = 2;
            // Iterate over all the list an create the rows of data
            for (ArrayList lil : setA) {
                numby++;


                if (numby < setA.size()) {
                    for (int x = 0; x < lil.size() - 1; x++) {
                        // Create the current starting from 1 to al.size()
                        XSSFRow row = Sheet.getRow(x + 1);
                        if (row == null) {
                            row = Sheet.createRow(x + 1);
                        }

                        // Cell of the Product Name
                        cell = row.getCell(numby);
                        if (cell == null) {
                            cell = row.createCell(numby);
                        }
                        cell.setCellValue(String.valueOf(lil.get(x)));

                        // row.createCell(numby).setCellValue(String.valueOf(lil.get(x)));
                        //     System.out.println(numby + " "+x);
                    }
                    XSSFRow row = Sheet.getRow(lil.size() + 1);
                    if (row == null) {
                        row = Sheet.createRow(lil.size() + 1);
                    }
                    cell = row.getCell(numby);
                    if (cell == null) {
                        cell = row.createCell(numby);
                    }
                    cell.setCellValue(String.valueOf(aver.get(numby - 3)));
                }


            }
            // Write the result into the file
            workBook.write(fos);
        }


    }


    public static void checkRec(int numFerries, ArrayList<Time> schedule, ArrayList<Integer> toChange, int max, int percentage30MinBefore, int percentage30MinAfter, ArrayList<Integer> poll, String atPort, int curr, int currtrips) {

        if (currtrips >= minTrips) {

        }
        //check if curr exists in tochange
        //


        else if (numFerries > 0) {

            numFerries--;

            if (atPort.equals("pair") && (curr % 2 == 0)) {
                totsscheds++;
                totsscheds++;

                curr++;
                schedule.get(toChange.get(numFerries)).setFerryComing(true);
                checkRec(numFerries, schedule, toChange, max, percentage30MinBefore, percentage30MinAfter, poll, "unpair", curr, currtrips);


                schedule.get(toChange.get(numFerries)).setFerryComing(false);
                currtrips = currtrips + 1;
                checkRec(numFerries, schedule, toChange, max, percentage30MinBefore, percentage30MinAfter, poll, "pair", curr, currtrips);

            } else if (atPort.equals("unpair") && (curr % 2 != 0)) {
                totsscheds++;
                totsscheds++;

                curr++;
                schedule.get(toChange.get(numFerries)).setFerryComing(true);
                checkRec(numFerries, schedule, toChange, max, percentage30MinBefore, percentage30MinAfter, poll, "pair", curr, currtrips);


                schedule.get(toChange.get(numFerries)).setFerryComing(false);
                currtrips = currtrips + 1;
                checkRec(numFerries, schedule, toChange, max, percentage30MinBefore, percentage30MinAfter, poll, "unpair", curr, currtrips);

            } else {
                totsscheds++;
                curr++;
                schedule.get(toChange.get(numFerries)).setFerryComing(false);
                currtrips = currtrips + 1;
                checkRec(numFerries, schedule, toChange, max, percentage30MinBefore, percentage30MinAfter, poll, atPort, curr, currtrips);


            }

        } else if (check == true) {

            //    check = false;


            // days.put(String.valueOf(exccount), new Object[] { d.getSchedule()});
            exccount++;


            int a = new daySchedule(schedule, max, percentage30MinBefore, percentage30MinAfter, poll).numBoarding();
            int b = new daySchedule(schedule, max, percentage30MinBefore, percentage30MinAfter, poll).total();
            int ratio_people_boarding = (int) (((double) a / (double) b) * 100);
            double average_People_Per_Ferry = new daySchedule(schedule, max, percentage30MinBefore, percentage30MinAfter, poll).averagePeoplePerFerry();
            double number_of_Ferries = new daySchedule(schedule, max, percentage30MinBefore, percentage30MinAfter, poll).numFerries();


            if ((ratio_people_boarding > 83) && (average_People_Per_Ferry > 60) && (number_of_Ferries  < 13)) {



                System.out.println(new daySchedule(schedule, max, percentage30MinBefore, percentage30MinAfter, poll).getSchedule());
                System.out.println(new daySchedule(schedule, max, percentage30MinBefore, percentage30MinAfter, poll).getOccupancy());
                System.out.println(ratio_people_boarding);
                System.out.println();
                setA.add(new daySchedule(schedule, max, percentage30MinBefore, percentage30MinAfter, poll).getSchedule());
                aver.add(new daySchedule(schedule, max, percentage30MinBefore, percentage30MinAfter, poll).averagePeoplePerFerry());
            }
            //System.out.println(d.getOccupancy());


        }


    }
}
