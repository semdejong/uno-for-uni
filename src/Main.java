import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            Process p = Runtime.getRuntime().exec("xterm");
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

            w.write("ls" + System.lineSeparator());
            w.flush();
            w.close();

            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String s;
            while ((s = r.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch (IOException io) {
            io.printStackTrace();
        }
    }
}