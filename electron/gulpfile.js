var gulp = require('gulp');
var ts = require('gulp-typescript');

var tsProject = ts.createProject('./src/tsconfig.json');

gulp.task('default', function () {
  var tsResult = tsProject.src()
    .pipe(ts(tsProject));
  return tsResult.js.pipe(gulp.dest('bin'));
});
