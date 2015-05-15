
# Comparing different ways of concatinating strings

10,0000 iterations, N7v2, Android 5.1.1

 * `String.format()`: 653ms
 * native concat: under 56ms
 * `StringBuilder`: 59ms
 * `StringBuilder` with enough capacity: 52ms

This tells:

 * `String.format()` is x10 expensive
 * Others are similar.

What I learned

 * `javac` kindly uses `StringBuilder` for string concatinations.
