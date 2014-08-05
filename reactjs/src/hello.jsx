/** @jsx React.DOM */

var HelloMessage = React.createClass({
  render: function() {
    return <div>Hello {this.props.name}</div>;
  }
});

window.addEventListener("load", function() {
  React.renderComponent(<HelloMessage name="John" />, document.body);
});
