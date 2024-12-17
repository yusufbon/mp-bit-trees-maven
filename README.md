# mp-bit-trees-maven

A mini-project exploring bit trees (a form of binary tree) and their use in translating between alphabets, particularly in translating to and from braille.

**Authors**

* Bonsen Yusuf
* Samuel A. Rebelsky (starter code)

**Acknowledgements**

* evening CS tutor (don't remember name)
* https://rebelsky.cs.grinnell.edu/Courses/CSC207/2024Fa/readings/list-adts.html
*

**Instructions for use**

To use this project:
1) Run it.
2) Enter this in the terminal: alias ba="java -cp target/classes edu.grinnell.csc207.main.BrailleASCII"
3) In the terminal, enter your 2 arguments, and press enter.
ex)
$ ba braille hello
110010100010111000111000101010
$ ba ascii 110010100010111000111000101010
HELLO
$ ba unicode hello
⠓⠑⠇⠇⠕
$ ba unicode "hello world"
⠓⠑⠇⠇⠕⠀⠺⠕⠗⠇⠙

$ ba braille abc123
100000110000100100
Trouble translating because No corresponding value
$ ba ascii 11001010001011100011100010101
Invalid length of bit string

---

This code may be found at <https://github.com/yusufbon/mp-bit-trees-maven>. The original code may be found at <https://github.com/Grinnell-CSC207/mp-bit-trees-maven>.
