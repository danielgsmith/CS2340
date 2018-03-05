package app.haven.haven;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    public static int DEFAULT_CAPACITY = -1; //this can be a number if we just want a default instead of allowing them not to specify

    private List<Shelter> shelterList;

    public CSVParser(File file) {
        shelterList = new ArrayList<>();
        try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            b.readLine();
            for (String line = ""; (line = b.readLine()) != null;) {
                line = parseLineForCommas(line.replaceAll("~", ""));
                String[] csLine = line.split("~");
                shelterList.add(new Shelter(
                        csLine[1],
                        Capacity.parseFromString(csLine[2]),
                        Restrictions.parseFrom(csLine[3]),
                        Double.parseDouble(csLine[4]),
                        Double.parseDouble(csLine[5]),
                        csLine[8],
                        csLine[6],
                        Integer.parseInt(csLine[0]),
                        csLine[7]
                ));
            }
        } catch (IOException e) {
            System.out.println("An error occured parsing file " + file);
            e.printStackTrace();
        }
    }

    public List<Shelter> getShelterList() {
        return shelterList;
    }

    /***
     * Current we do NOT have a way to use an escape character for quotation marks,
     * so any shelter with quotation marks in one of its categories will get messed up
     * @param line the line
     * @return the line parsed to replace commas not in a text field with ~ characters
     */
    private String parseLineForCommas(String line) {
        boolean ignore = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ',' && !ignore) {
                line = line.substring(0,i) + "~" + line.substring(i + 1);
            }
            if (c == '"') ignore = !ignore;
        }
        return line;
    }

}
