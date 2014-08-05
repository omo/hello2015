module.exports = function(grunt) {
  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('bower.json'),
    react: {
      files: {
        expand: true,
        cwd: 'src',
        src: ['**/*.jsx'],
        dest: 'build/modules/',
        ext: '.js'
      }
    },
    browserify: {
      options: {
//        transform: [ require('grunt-react').browserify ],
      },
      app: {
        src: ['build/modules/*.js'],
        dest: 'build/javascript/main.js'
      }
    }
  });

  grunt.loadNpmTasks('grunt-react');
  grunt.loadNpmTasks('grunt-browserify');
  grunt.registerTask('default', ['react', 'browserify']);
};
