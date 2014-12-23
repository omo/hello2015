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
      },
    },
    connect: {
      server: {
        options: {
          livereload: false,
          base: './',
          port: 3000
        }
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
    },
    watch: {
      scripts: {
        files: '**/*.jsx',
        tasks: ['default'],
        options: {
          debounceDelay: 250,
        },
      },
    },
  });

  grunt.loadNpmTasks('grunt-react');
  grunt.loadNpmTasks('grunt-browserify');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-connect');

  grunt.registerTask('build', ['react', 'browserify']);
  grunt.registerTask('serve', ['build', 'connect', 'watch']);
  grunt.registerTask('default', ['build']);
};
