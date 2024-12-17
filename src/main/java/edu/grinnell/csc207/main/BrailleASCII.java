package edu.grinnell.csc207.main;

import java.io.PrintWriter;

import edu.grinnell.csc207.util.BrailleAsciiTables;

/**
 * Main class for Braille ASCII conversion.
 * This class processes command-line arguments to perform text translation
 * between ASCII, Braille, and Unicode formats based on the specified target.
 * @author Bonsen Yusuf
 */
public class BrailleASCII {
  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * The main entry point for executing the Braille ASCII translation program.
   * Takes two command-line arguments:
   * 1. The target character set ("braille", "ascii", or "unicode").
   * 2. The source text to translate.
   *
   * Based on the target, the program converts the input and prints the result.
   *
   * @param args command-line arguments specifying the target and source text
   */
  public static void main(String[] args) {
    // Create a PrintWriter for output, auto-flushing enabled
    PrintWriter pen = new PrintWriter(System.out, true);

    // Ensure correct number of arguments
    if (args.length != 2) {
      pen.println("Usage: BrailleASCII <target> <source>");
      pen.close();
      return;
    } //if

    // Extract command-line arguments
    String target = args[0].toLowerCase();
    String source = args[1];

    try {
      // Handle different target formats
      switch (target) {
        case "braille":
          // Convert each character to Braille representation
          for (char ch : source.toCharArray()) {
            String braille = BrailleAsciiTables.toBraille(ch);
            if (braille == null) {
              // Report issues if conversion fails
              pen.println("Trouble translating: " + ch);
            } else {
              // Print translated Braille bit string
              pen.print(braille);
            } //if
          } //for
          pen.println();
          break;

        case "ascii":
          try {
            // Convert the Braille bit string back to ASCII
            pen.println(BrailleAsciiTables.toAscii(source));
          } catch (Exception e) {
            // Handle invalid Braille bit string input
            pen.println("Invalid length of bit string");
          } //catch
          break;

        case "unicode":
          // Convert each character to its Unicode Braille representation
          for (char ch : source.toCharArray()) {
            String unicode = BrailleAsciiTables.toUnicode(BrailleAsciiTables.toBraille(ch));
            if (unicode == null) {
              // Report issues if conversion fails
              pen.println("Trouble translating: " + ch);
            } else {
              // Print Unicode Braille character
              pen.print(unicode);
            } //if
          } //for
          pen.println();
          break;

        default:
          // Report unknown target formats
          pen.println("Unknown target format: " + target);
      } //switch
    } catch (Exception e) {
      // Handle unexpected errors during translation
      pen.println("Error during translation: " + e.getMessage());
    } finally {
      // Close the PrintWriter to free system resources
      pen.close();
    } //catch
  } //main
} // class BrailleASCII
