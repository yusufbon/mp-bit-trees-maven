package edu.grinnell.csc207.util;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Trees intended to be used in storing mappings between fixed-length
 * sequences of bits and corresponding values.
 *
 * @author Bonsen Yusuf
 */
public class BitTree {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The depth of the tree, representing the length of the bit strings the tree can handle.
   * This value is initialized during tree creation and remains constant.
   */
  private final int depth;

   /**
   * The root node of the binary tree.
   * This node represents the entry point for all insertions and lookups.
   */
  private BitTreeNode root;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+


/**
 * Constructs a binary tree with the specified depth.
 * Initializes an empty tree capable of storing mappings from bit strings
 * of length 'n' to corresponding values.
 *
 * @param n the depth of the tree, representing the maximum bit string length supported
 */
  public BitTree(int n) {
    this.depth = n;
    this.root = null;
  } // BitTree(int)

  // +---------------+-----------------------------------------------
  // | Local helpers |
  // +---------------+

  /**
   * Helper method for recursively setting a value in the tree.
   *
   * @param node  the current node in the traversal
   * @param bits  the bit string representing the path
   * @param index the current position in the bit string
   * @param value the value to store
   * @return the updated node
   */
  private BitTreeNode setHelper(BitTreeNode node, String bits, int index, String value) {
    if (index == depth) {
      return new BitTreeLeaf(value);
    } //if
    if (node == null) {
      node = new BitTreeInteriorNode();
    } //if
    BitTreeInteriorNode interior = (BitTreeInteriorNode) node;
    if (bits.charAt(index) == '0') {
      interior.left = setHelper(interior.left, bits, index + 1, value);
    } else {
      interior.right = setHelper(interior.right, bits, index + 1, value);
    } //esle
    return interior;
  } //setHelper(BitTreeNode node, String bits, int index, String value)


  /**
     * Recursively dumps the contents of the tree.
     *
     * @param node the current node
     * @param bits the accumulated bit string representing the current path
     * @param pen  the output writer
     */
  private void dumpHelper(BitTreeNode node, String bits, PrintWriter pen) {
    if (node instanceof BitTreeLeaf leaf) {
      pen.println(bits + "," + leaf.value);
    } else if (node instanceof BitTreeInteriorNode interior) {
      dumpHelper(interior.left, bits + "0", pen);
      dumpHelper(interior.right, bits + "1", pen);
    } //else if
  } //dumpHelper(BitTreeNode node, String bits, PrintWriter pen)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Sets a value in the tree at the specified bit path.
   * If the path does not exist, it is created.
   *
   * @param bits  the bit path as a string of '0's and '1's
   * @param value the value to store at the specified path
   * @throws IndexOutOfBoundsException if the bit string is invalid or not of length depth
   */
  public void set(String bits, String value) {
    if (bits.length() != depth || !bits.matches("[01]+")) {
      throw new IndexOutOfBoundsException("Invalid bit string: " + bits);
    } //if
    root = setHelper(root, bits, 0, value);
  } // set(String, String)

  /**
   * Retrieves a value from the tree at the specified bit path.
   *
   * @param bits the bit path as a string of '0's and '1's
   * @return the value stored at the specified path
   * @throws IndexOutOfBoundsException if the bit string is invalid or the path is not found
   */
  public String get(String bits) {
    if (bits.length() != depth || !bits.matches("[01]+")) {
      throw new IndexOutOfBoundsException("Invalid bit string: " + bits);
    } //if
    BitTreeNode node = root;
    for (int i = 0; i < depth && node != null; i++) {
      node = bits.charAt(i) == '0' ? ((BitTreeInteriorNode) node).left : ((BitTreeInteriorNode) node).right;
    } //for
    if (node instanceof BitTreeLeaf leaf) {
      return leaf.value;
    } //if
    throw new IndexOutOfBoundsException("Bit string not found: " + bits);
  } // get(String, String)

  /**
   * Dumps the contents of the tree to a PrintWriter in CSV format.
   * Each line contains a bit string and the corresponding value.
   *
   * @param pen the output writer
   */
  public void dump(PrintWriter pen) {
    dumpHelper(root, "", pen);
  } // dump(PrintWriter)

  /**
   * Loads the tree contents from an InputStream.
   * Each line should be formatted as "bits,value".
   *
   * @param source the input stream to read from
   */
  public void load(InputStream source) {
    try (Scanner scanner = new Scanner(source)) {
      while (scanner.hasNextLine()) {
        String[] parts = scanner.nextLine().split(",", 2);
        if (parts.length == 2) {
          set(parts[0], parts[1]);
        } //if
      } //while
    } //try
  } // load(InputStream)

  // Interface for all tree nodes
  interface BitTreeNode {
  } //interface

  // Interior node with left and right children
  class BitTreeInteriorNode implements BitTreeNode {
    /**
     * The left child node, representing the next node when a '0' bit is encountered.
     */
    BitTreeNode left;

     /**
     * The right child node, representing the next node when a '1' bit is encountered.
     */
    BitTreeNode right;
  } // class BitTreeInteriorNode

  // Leaf node containing a value
  class BitTreeLeaf implements BitTreeNode {

    /**
     * The value associated with a specific bit string.
     * This value is stored when a full bit string path has been traversed.
     */
    String value;

    /**
     * Constructs a leaf node with the specified value.
     *
     * @param value the value to store
     */
    BitTreeLeaf(String value) {
      this.value = value;
    } // BitTreeLeaf(string)
  } //class BitTreeLeaf
} // class BitTree
