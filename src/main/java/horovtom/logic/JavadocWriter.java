package horovtom.logic;

import java.util.Scanner;

/**
 * Created by Hermes235 on 23.9.2016.
 */
public class JavadocWriter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInput width: ");
        int width = sc.nextInt();
        System.out.print("\nInput height: ");
        int height = sc.nextInt();

        String[] input = new String[height];
        System.out.println("\nInput table string: \n(B) = Black; \n(W) = White; (D) = Dot; \n(C) = Cross; \n(-) = nothing; \n(> 0) = any number \n delimit by (,)!");
        for (int i = 0; i < height; i++) {
            loadLine(sc, input, i, width);
        }

        StringBuilder out = new StringBuilder("* <table>\n");
//        out.append("*   <tr>\n");
//        out.append("*       ");
//        for (int i = 0; i < width; i++) {
//            out.append("<th>").append(i).append(":</th> ");
//        }
//        out.append("\n*   </tr>\n");

        for (String line : input) {
            String[] tokens = line.split(",");
            out.append("*   <tr>\n").append("*      ");
            for (String token : tokens) {
                if (token.equals("B")) {
                    out.append("<td bgcolor=\"#000000\"></td> ");
                } else if (token.equals("W")) {
                    out.append("<td bgcolor=\"#FFFFFF\"></td> ");
                } else if (token.equals("-")) {
                    out.append("<td></td> ");
                } else if (token.equals("D")) {
                    out.append("<td>•</td> ");
                } else if (token.equals("C")) {
                    out.append("<td>X</td> ");
                } else {
                    out.append("<td>").append(token).append("</td> ");
                }
            }
            out.append("\n");
            out.append("*   </tr>\n");
        }
        out.append("* </table>");

        System.out.println(out.toString());
    }

    /**
     * <table summary="">
     * <tr>
     * <td></td> <td></td> <td></td> <td>2</td><td>2</td><td>3</td><td>1</td><td>2</td>
     * </tr>
     * <tr>
     * <td>1</td><td>1</td><td>1</td><td bgcolor="#000000"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#000000"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#000000"></td>
     * </tr>
     * <tr>
     * <td></td> <td></td> <td>5</td><td bgcolor="#000000"></td> <td bgcolor="#000000"></td> <td bgcolor="#000000"></td> <td bgcolor="#000000"></td> <td bgcolor="#000000"></td>
     * </tr>
     * <tr>
     * <td></td> <td></td> <td>2</td><td bgcolor="#FFFFFF"></td> <td bgcolor="#000000"></td> <td bgcolor="#000000"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td>
     * </tr>
     * </table>
     */
    private static void loadLine(Scanner sc, String[] array, int index, int width) {
        System.out.println("Input " + index + ". row: ");
        array[index] = sc.next();
        if (array[index].split(",").length != width) {
            System.err.println("Wrong length!");
            loadLine(sc, array, index, width);
        }
    }
}
