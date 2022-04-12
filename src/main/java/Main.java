import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static void main(String[] args) throws IOException {
    // create an "attendees" variable with my data structure inside
    //User Story 1
    List<HashMap<String, String>> attendees;

    File attendeesJson = new File(Main.class.getResource("attendees.json").getFile());
    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    attendees = mapper.readValue(attendeesJson, ArrayList.class);

//    User Story 2

    File lateArrivalsJson = new File(Main.class.getResource("lateArrivals.json").getFile());
    List<HashMap<String, String>> lateArrivals = mapper.readValue(lateArrivalsJson, ArrayList.class);

    System.out.println(attendees.size());

//    for(HashMap<String, String> lateArrival : lateArrivals) {
//      attendees.add(lateArrival);
//    }

    attendees.addAll(lateArrivals);

    System.out.println(attendees.size());

    // User Story 3
    // Identify the names of the characters we're looking for
    List<String> sneakyHeroes = new ArrayList<>();
    sneakyHeroes.add("Hercules");
    sneakyHeroes.add("Merida");
    sneakyHeroes.add("Maui");

    // Go through our attendees and remove the characters from the above list
    List<HashMap<String, String>> attendeesToRemove = new ArrayList<>();
    for(HashMap<String, String> attendee: attendees) {
      // get the name of the attendee
      String attendeeName = attendee.get("name");

      // see if they are one of our sneakyHeroes
      // if they are, remove them from our list
      if(sneakyHeroes.contains(attendeeName)) {
        attendeesToRemove.add(attendee);
//        attendees.remove(attendee);
      }
    }

    attendees.removeAll(attendeesToRemove);
    System.out.println(attendees.size());

    // User Story 4

    // read in our alignmentTestResults file

    ClassLoader classLoader = Main.class.getClassLoader();
    File alignmentFile = new File(classLoader.getResource("alignmentTestResults.json").getFile());
    Map<String, String> alignmentResults = mapper.readValue(alignmentFile, HashMap.class);

    // add an `alignment` key-value pair to each character with their alignment value
    for(HashMap<String, String> attendee : attendees) {
      String attendeeName = attendee.get("name");
      String alignment = alignmentResults.get(attendeeName);
      attendee.put("alignment", alignment);
    }

  // User Story 5

    List<HashMap<String, String>> copyOfAttendees = List.copyOf(attendees);

    for(HashMap<String, String> attendee: copyOfAttendees) {
      // if the character is Good,
      // remove them from the attendees
      if(attendee.get("alignment").equals("Good")) {
        attendees.remove(attendee);
      }
    }
    System.out.println(attendees.size());

    // User Story 6

    ObjectWriter objectWriter = mapper.writer(SerializationFeature.INDENT_OUTPUT);
    objectWriter.writeValue(new File("verifiedAttendees.json"), attendees);

//    System.out.println(attendees);
    System.out.println(mapper.writeValueAsString(attendees));
  }
}