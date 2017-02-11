package regex;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;

class RegexCaptureNamedGroupNValues {

    public static void main(String args[]) {
        //Scanner scanner = new Scanner(System.in);

        //String regex = scanner.nextLine();
        //StringBuilder input = new StringBuilder();
        //while (scanner.hasNextLine()) {
         //   input.append(scanner.nextLine()).append('\n');
       // }

        String regex = "\\b(?<city>[A-Za-z\\s]+),\\s(?<state>[A-Z]{2,2}):\\s(?<areaCode>[0-9]{3,3})\\b";

        String input = "This is the list: Baytown, TX: 281, Chapel Hill, NC: 284, " +
                "Fort Myers, FL: 239";
        Set<String> namedGroups = getNamedGroupCandidates(regex);

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        int groupCount = m.groupCount();

        int matchCount = 0;

        if (m.find()) {
            // Remove invalid groups
            Iterator<String> i = namedGroups.iterator();
            while (i.hasNext()) {
                try {
                    m.group(i.next());
                } catch (IllegalArgumentException e) {
                    i.remove();
                }
            }

            matchCount += 1;
            System.out.println("Match " + matchCount + ":");
            System.out.println("=" + m.group() + "=");
            System.out.println();
            printMatches(m, namedGroups);

            while (m.find()) {
                matchCount += 1;
                System.out.println("Match " + matchCount + ":");
                System.out.println("=" + m.group() + "=");
                System.out.println();
                printMatches(m, namedGroups);
            }
        }
    }

    private static void printMatches(Matcher matcher, Set<String> namedGroups) {
        for (String name: namedGroups) {
            String matchedString = matcher.group(name);
            if (matchedString != null) {
                System.out.println("GroupName:" + name);
                System.out.println("Value:" + matchedString + "\n");
            } else {
                System.out.println(name + "_");
            }
        }


    }

    private static Set<String> getNamedGroupCandidates(String regex) {
        Set<String> namedGroups = new TreeSet<String>();

        Matcher m = Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>").matcher(regex);

        while (m.find()) {
            namedGroups.add(m.group(1));
        }

        return namedGroups;
    }
}

