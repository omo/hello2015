
What I did:

 * http://facebook.github.io/react/downloads.html
 * Install bower and grunt
 * bower init, npm init
 * `$ bower install --save react`
 * Install grunt plugins.

Notes:

 * Seems like react itself cannot be loaded as a browserify-able module.
   Just use `script` tag.

See:

 * https://www.npmjs.org/package/grunt-react
 * https://github.com/jmreidy/grunt-browserify

TODO:

 * DONE:automate rebuild through grunt
   * https://github.com/gruntjs/grunt-contrib-watch
 * DONE: Syntax highlight .jsx file in atom
   * https://atom.io/packages/file-types
 * Try livereload
