/** @jsx React.DOM */

Hello = require('./hello');

window.addEventListener("load", function() {
  React.renderComponent(<Hello name="John" />, document.body);
});
