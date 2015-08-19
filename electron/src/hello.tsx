/// <reference path="../typings/react/react.d.ts" />
import React = require("react");

export class Hello extends React.Component<any, any> {
  render() : React.ReactElement<any> {
    return <div>Hello From React!</div>;
  }
};
