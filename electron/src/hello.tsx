/// <reference path="../typings/react/react.d.ts" />
import React = require("react");
/// <reference path="../typings/github-electron/github-electron-renderer.d.ts" />
import remote = require('remote');

var remoteWindow = remote.getCurrentWindow();

export class Hello extends React.Component<any, any> {
  render() : React.ReactElement<any> {
    return <div>
      <h1>Hello From React!</h1>
      <button onClick={this.handleFullscreen}>Fullscreen</button>
    </div>;
  }

  handleFullscreen() {
    console.log("Hello:" + remoteWindow.isFullScreen());
    remoteWindow.setFullScreen(true);
  }
};
