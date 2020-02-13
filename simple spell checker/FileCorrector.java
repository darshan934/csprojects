
import java.util.*;
import java.io.*;

/**
 * A Corrector whose spelling suggestions are given in a text file.
 * <p>
 * One way to get corrections for a misspelled word is to consult an external resource. This kind of
 * Corrector uses a file that contains pairs of words on each line (a misspelled word and a
 * correction for that misspelling) to generate corrections.
 */
public class FileCorrector extends Corrector {

    private Map<String, Set<String>> file;
    private BufferedReader b;
    /**
     * A special purpose exception class to indicate errors when reading the input for the
     * FileCorrector.
     */
    public static class FormatException extends Exception {
        public FormatException(String msg) {
            super(msg);
        }
    }

    
  
    /**
     * Constructs a FileCorrector from the argued Reader.
     *
     * Instead of using the TokenScanner to parse this input, you should read the input line by line
     * using a BufferedReader. This way you will practice with an alternative approach to working
     * with text. For methods useful in parsing the lines of the file, see the String class javadocs
     * in java.lang.String
     *
     * <p> 
     * Each line in the input should have a single comma that separates two parts in the form:
     * misspelled_word,corrected_version
     *
     * <p>
     * For example:<br>
     * <pre>
     * aligatur,alligator<br>
     * baloon,balloon<br>
     * inspite,in spite<br>
     * who'ev,who've<br>
     * ther,their<br>
     * ther,there<br>
     * </pre>
     * <p>
     * The lines are not case-sensitive, so all of the following lines should function equivalently:
     * <br>
     * <pre>
     * baloon,balloon<br>
     * Baloon,balloon<br>
     * Baloon,Balloon<br>
     * BALOON,balloon<br>
     * bAlOon,BALLOON<br>
     * </pre>
     * <p>
     * You should ignore any leading or trailing whitespace around the misspelled and corrected
     * parts of each line.  Thus, the following lines should all be equivalent:<br>
     * <pre>
     * inspite,in spite<br>
     *    inspite,in spite<br>
     * inspite   ,in spite<br>
     *  inspite ,   in spite  <br>
     * </pre>
     * Note that spaces are allowed inside the corrected word. (In general, the FileCorrector is
     * allowed to <em>suggest</em> Strings that are not words according to TokenScanner.) 
     *
     * <p>
     * You should throw a <code>FileCorrector.FormatException</code> if you encounter input that is
     * invalid. For example, the FileCorrector constructor should throw an exception if any of these
     * inputs are encountered:<br>
     * <pre>
     * ,correct<br>
     * wrong,<br>
     * wrong correct<br>
     * wrong,correct,<br>
     * </pre>
     * <p>
     *
     * @param r The sequence of characters to parse 
     * @throws IOException If error while reading
     * @throws FileCorrector.FormatException If an invalid line is encountered
     * @throws IllegalArgumentException If the provided reader is null
     */
    public FileCorrector(Reader r) throws IOException, FormatException {
        if (r == null) {
            throw new IllegalArgumentException();
        }
        
        file = new TreeMap<String, Set<String>>();
        b = new BufferedReader(r);
        String s = b.readLine();
        
        while (s != null) {
            s = s.trim();
        
            if ((s.charAt(s.length() - 1) == ',') || s.charAt(0) == ',') {
                throw new FormatException("invalid line");
            }

            int count = s.length() - s.replace(",", "").length();
            if (count < 1 || count > 1) {
                throw new FormatException("invalid format");
            }

            int first = s.indexOf(",");
            String wrong = (s.substring(0, first).trim()).toLowerCase();
            String correct = s.substring(first + 1).trim().toLowerCase();

            if (file.containsKey(wrong)) {
                file.get(wrong).add(correct);
            } else {
                file.put(wrong, new TreeSet<String>(Collections.singleton(correct)));
            }
            
            s = b.readLine();
            if (s == null) {
                break;
            } else {
                s = s.trim();
            }
        }  
    }

    /**
     * Constructs a FileCorrector from a file.
     *
     * @param filename Location of file from which to read
     * @return A FileCorrector with corrections from the argued file
     * @throws FileNotFoundException If the file does not exist
     * @throws IOException If error while reading
     * @throws FileCorrector.FormatException If an invalid line is encountered
     */
    public static FileCorrector make(String filename) throws IOException, FormatException {
        if (filename == null)  {
            throw new FileNotFoundException();
        }
        
        Reader r = new FileReader(filename);
        FileCorrector fc;
        
        try {
            fc = new FileCorrector(r);
        } finally {
            if (r != null) {
                r.close();
            }
        }

        return fc;
    }

    /**
     * Returns a set of proposed corrections for an incorrectly spelled word. The corrections should
     * match the case of the input; the matchCase method is helpful here.
     * <p>
     * For any input that is *not* a valid word, throw an IllegalArgumentException. A valid word is
     * any sequence of letters (as determined by Character.isLetter) or apostrophes characters.
     *
     * @param wrong The misspelled word
     * @return A (potentially empty) set of proposed corrections
     * @throws IllegalArgumentException If the input is not a valid word (i.e. not composed of only
     *                                  letters and/or apostrophes) 
     */
    public Set<String> getCorrections(String wrong) {
        if (!TokenScanner.isWord(wrong)) {
            throw new IllegalArgumentException();
        }
        if (file.containsKey(wrong.toLowerCase())) {
            return matchCase(wrong, file.get(wrong.toLowerCase()));
        }
        return Collections.emptySet();
    }
}
