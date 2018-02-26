package app.haven.haven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CSVParser {

    public static int DEFAULT_CAPACITY = -1; //this can be a number if we just want a default instead of allowing them not to specify

    private List<ShelterInfo> shelterInfoList;

    Shelter(String name, long capacityType, int capacity, int subCapacity, boolean acceptsMale, boolean acceptsFemale, boolean acceptsAdults, boolean acceptsNewBorns,
//            boolean acceptsChildUnder5, boolean acceptsFamilies, boolean acceptsChild, boolean acceptsVeterans, double longitude,
//            double latitude, String phone, String address, int uniqueKey, String notes) {
    public CSVParser(String fileName) throws IOException{
        shelterInfoList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.filter(line -> line.charAt(0) <= '9' && line.charAt(0) >= 0).forEach(line -> {
                line = parseLineForCommas(line.replace("~", ""));
                String[] csLine = line.split("~");
                shelterInfoList.add(new ShelterInfo(
                        csLine[1],
                        parseCapacity(csLine[2]), //currently sums all numbers found in the line
                        new Restrictions(csLine[3]),
                        Double.parseDouble(csLine[4]),
                        Double.parseDouble(csLine[5]),
                        csLine[6],
                        csLine[8]
                ));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ShelterInfo> getShelterInfoList() {
        return shelterInfoList;
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

    /***
     * we will have to make this better if we want to care about families/singles etc.
     *
     * Currently this sums all numbers found in the line
     */
    private int parseCapacity(String line) {
        if (line.isEmpty()) return DEFAULT_CAPACITY;
        int count = 0;
        for (String s : line.replaceAll("[^0-9,]", ",").split(",")) {
            if (!s.isEmpty()) count += Integer.parseInt(s);
        }
        return count;
    }

}
