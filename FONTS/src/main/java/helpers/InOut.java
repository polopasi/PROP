/* Aquesta es una adaptacio d'un conjunt de classes
   per a fer entrada/sortida de texte amb Java.
   La versio original va ser creada per Per Brinch
   Hansen (s'inclou el copyright de la versio original)
*/

/*                 THE JAVA TEXT PROGRAM    
                       28 April 1999
            Copyright (c) 1999 Per Brinch Hansen

This program defines a set of Java classes for text
processing. These text classes enable Java programs to
output text to screen and disk files and input text from
keyboard and disk files. The present version runs on
Macintosh, Unix, and Windows 95 systems.
*/

package helpers;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class InOut {
    private static final boolean isPiped = getIsPiped();

    private final char cr = '\r', eof = '\uFFFF',
            nl = '\n', sp = ' ';
    private final String EOF = "eof";

    public InOut() { }

    private static boolean getIsPiped() {
        try {
            return System.in.available() != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /* instruccions d'escriptura */
    static final String RESET = "\033[0m";  // Text Reset
    static final String RED = "\033[0;31m";

    public void printErr(Object o) {
        System.out.println(RED + "[Error] " + o + RESET);
    }

    public void printErr(Exception e) {
        System.out.println(RED + "[Error] " + e.getMessage() + RESET);
    }

    public void write(boolean value) throws IOException {
        write(value, 1);
    }

    public void write(boolean value, int width)
            throws IOException {
        String word = (String) (value ? "true" : "false");
        writespace(width - word.length());
        write(word);
    }
    
    public void write(char value) throws IOException {
        System.out.print(value);
    }

    public void write(char value, int width) throws IOException {
        write(value);
        writespace(width - 1);
    }

    public void write(double value) throws IOException {
        write(value, 1);
    }

    public void write(double value, int width)
            throws IOException {
        String numeral = String.valueOf(value);
        writespace(width - numeral.length());
        write(numeral);
    }

    public void write(int value) throws IOException {
        write(value, 1);
    }

    public void write(int value, int width) throws IOException {
        String numeral = String.valueOf((int) value);
        writespace(width - numeral.length());
        write(numeral);
    }

    public void write(String value) throws IOException {
        write(value, 1);
    }

    public void write(String value, int width)
            throws IOException {
        int length = value.length();
        for (int i = 0; i < length; i++)
            write(value.charAt(i));
        writespace(width - length);
    }

    public void writeln() throws IOException {
        write(nl);
    }

    public void writeln(boolean value) throws IOException {
        writeln(value, 1);
    }

    public void writeln(boolean value, int width)
            throws IOException {
        write(value, width);
        writeln();
    }

    public void writeln(char value) throws IOException {
        writeln(value, 1);
    }

    public void writeln(char value, int width)
            throws IOException {
        write(value, width);
        writeln();
    }

    public void writeln(double value) throws IOException {
        writeln(value, 1);
    }

    public void writeln(double value, int width)
            throws IOException {
        write(value, width);
        writeln();
    }

    public void writeln(int value) throws IOException {
        writeln(value, 1);
    }

    public void writeln(int value, int width) throws IOException {
        write(value, width);
        writeln();
    }

    public void writeln(String value) throws IOException {
        writeln(value, 1);
    }

    public void writeln(String value, int width)
            throws IOException {
        write(value, width);
        writeln();
    }

    public void writesame(char value, int number)
            throws IOException {
        for (int i = 1; i <= number; i++)
            write(value);
    }

    public void writespace(int number) throws IOException {
        writesame(sp, number);
    }

    /* instruccions de lectura */
    
    private BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));
    private char[] buffer = new char[80];
    private int typed = 0, used = 0;

    private char readkey() throws IOException {
        if (used == typed) {
            String line = readkeyline();
            if (line.equals(EOF)) {
                typed = 0;
                buffer[typed] = eof;
            } else {
                typed = line.length();
                for (int i = 0; i < typed; i++)
                    buffer[i] = line.charAt(i);
                buffer[typed] = nl;
            }
            typed = typed + 1;
            used = 0;
        }
        char ch = buffer[used];
        if (ch == cr)
            ch = nl;
        used = used + 1;
        return ch;
    }

    private String readkeyline() throws IOException {
        String line = in.readLine();
        return (line == null ? "" : line);
    }

    private boolean ahead = false;
    private char ch /* next character (if ahead) */;

    public boolean blank() throws IOException {
        getahead();
        return (ch == cr) | (ch == nl) | (ch == sp);
    }

    public boolean digit() throws IOException {
        getahead();
        return ('0' <= ch) & (ch <= '9');
    }

    private void getahead() throws IOException {
        if (!ahead)
            readnext();
    }

    public boolean letter() throws IOException {
        getahead();
        return (('a' <= ch) & (ch <= 'z')) |
                (('A' <= ch) & (ch <= 'Z'));
    }

    public boolean more() throws IOException {
        getahead();
        return ch != eof;
    }

    public char next() throws IOException {
        getahead();
        return ch;
    }

    public char read() throws IOException {
        getahead();
        ahead = false;
        return ch;
    }

    public void readblanks() throws IOException {
        while (blank())
            readnext();
    }

    public boolean readboolean() throws Exception {
        boolean value;
        String symbol = readname();
        if (symbol.equals("false"))
            value = false;
        else if (symbol.equals("true"))
            value = true;
        else {
            String error = "boolean format: ";
            throw new Exception(error + symbol);
        }
        return value;
    }

    public double readdouble() throws IOException {
        StringBuffer symbol = new StringBuffer();
        // ----------MODIFICACIO------------
        readblanks();
        if (ch == '+')
            readnext();
        else if (ch == '-')
            symbol.append(read());
        while (digit())
            symbol.append(read());
        // ---------------------------------
        // hem substiuit el readint per aquest boci de codi.
        if (ch == '.') {
            symbol.append(read());
            while (digit())
                symbol.append(read());
        }
        if ((ch == 'e') | (ch == 'E')) {
            symbol.append(read());
            symbol.append(readint());
        }
        Double numeral = new Double(symbol.toString());
        return numeral.doubleValue();
    }

    public int readIntStdin() throws Exception {
        var line = in.readLine();
        try {
            int parsed = Integer.parseInt(line);
            if (isPiped) System.out.println(parsed);
            return parsed;
        } catch (Exception e) {
            throw new Exception("'"+ line + "' is not a number");
        }
    }

    public int readint() throws IOException {
        StringBuffer symbol = new StringBuffer();
        readblanks();
        if (ch == '+')
            readnext();
        else if (ch == '-')
            symbol.append(read());
        while (digit())
            symbol.append(read());
        
        return Integer.parseInt(symbol.toString());
    }

    public String readline() throws IOException {
        StringBuffer line = new StringBuffer();
        getahead();
        while (ch != nl) {
            line.append(ch);
            readnext();
        }
        ;
        ahead = false;
        if (isPiped) System.out.println(line.toString());
        return line.toString();
    }

    public void readln() throws IOException {
        String skipped = readline();
    }

    public void readln2() {
        try {
            String skipped = readline();
        } catch (Exception e) {
            return;
        }
    }

    public String readname() throws IOException {
        StringBuffer letters = new StringBuffer();
        readblanks();
        while (letter() | digit() | (ch == '_')) {
            letters.append(ch);
            readnext();
        }
        return letters.toString();
    }

    public void readnext() throws IOException {
        ch = (char) readkey();
        ahead = true;
    }

    public String readword() throws IOException {
        StringBuffer word = new StringBuffer();
        readblanks();
        while (!blank()) {
            word.append(ch);
            readnext();
        }
        if (isPiped) {
            System.out.println(word.toString());
        }
        return word.toString();
    }

    // Instruccions per llegir vector i matrius d'enters i reals

    public int[] read_int_array() throws IOException {
        int v[] = new int[100];
        int f[];
        int i = 0;
        readblanks();
        while (next() != ';') {
            v[i] = readint();
            readblanks();
            i = i + 1;
        }
        read();
        f = new int[i];
        for (i = 0; i < f.length; i++)
            f[i] = v[i];
        return f;
    }

    public void write(int[] v) throws IOException {
        for (int i = 0; i < v.length; i++)
            write(" " + v[i]);
    }

    public void writeln(int[] v) throws IOException {
        write(v);
        writeln();
    }

    public double[] read_double_array() throws IOException {
        double v[] = new double[100];
        double f[];
        int i = 0;
        readblanks();
        while (next() != ';') {
            v[i] = readdouble();
            readblanks();
            i = i + 1;
        }
        read();
        f = new double[i];
        for (i = 0; i < f.length; i++)
            f[i] = v[i];
        return f;
    }

    public void write(double[] v) throws IOException {
        for (int i = 0; i < v.length; i++)
            write(" " + v[i]);
    }

    public void writeln(double[] v) throws IOException {
        write(v);
        writeln();
    }

    /**
     * Reads a file from "path" and returns it as a String.
     */
    public static String readToString(String path) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
        
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    public int[][] read_int_matrix() throws IOException {
        int v[][] = new int[100][100];
        int f[][];
        int i = 0;
        readblanks();
        while (next() != ';') {
            v[i] = read_int_array();
            readblanks();
            i = i + 1;
        }
        read();
        f = new int[i][];
        for (i = 0; i < f.length; i++) {
            f[i] = new int[v[i].length];
            for (int j = 0; j < v[i].length; j++)
                f[i][j] = v[i][j];
        }
        return f;
    }

    public void write(int[][] m) throws IOException {
        for (int i = 0; i < m.length; i++) {
            write(m[i]);
            write(";");
        }
    }

    public void writeln(int[][] m) throws IOException {
        for (int i = 0; i < m.length; i++) {
            write(m[i]);
            writeln(";");
        }
    }

    public double[][] read_double_matrix() throws IOException {
        double v[][] = new double[100][100];
        double f[][];
        int i = 0;
        readblanks();
        while (next() != ';') {
            v[i] = read_double_array();
            readblanks();
            i = i + 1;
        }
        read();
        f = new double[i][];
        for (i = 0; i < f.length; i++) {
            f[i] = new double[v[i].length];
            for (int j = 0; j < v[i].length; j++)
                f[i][j] = v[i][j];
        }
        return f;
    }

    public void write(double[][] m) throws IOException {
        for (int i = 0; i < m.length; i++) {
            write(m[i]);
            write(";");
        }
    }

    public void writeln(double[][] m) throws IOException {
        for (int i = 0; i < m.length; i++) {
            write(m[i]);
            writeln(";");
        }
    }
    
    /**
     * Waits for a bit if in a terminal.
     * 
     * Useful for showing information at a specific pace.
     */
    public void shortWait() {
        try {
            if (!InOut.isPiped) Thread.sleep(750);
        } catch (InterruptedException e) {
            
        }
    }

    public static String toPrintableString(Object o) {
        // This will print the class in pretty Json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(o);
    }
}

/********* THE LAST LINE OF THE JAVA TEXT PROGRAM *********/
