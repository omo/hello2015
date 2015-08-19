/// <reference path="../typings/react/react.d.ts" />
import React = require("react");
import hello = require("./hello");

window.addEventListener("load", () => {
  React.render(<hello.Hello />, document.body);
});
