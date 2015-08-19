# Electron + TypeScript

Tricks:

 * The `atom-electdron` kindly creates `tsconfig.json` for me.
 * The `tsconfig.json` should be aligned to `gulpfile.js`
   so that `atom-typescript` generates file to the same directory.
 * You need `tsd` tool to pull type definition files.

TIL:

 * Electron supports reloading by default.
 * You don't need minifiers (obviously).

----

TODO:

 * Automate Electron startup
 * Automate type file installation through package.json