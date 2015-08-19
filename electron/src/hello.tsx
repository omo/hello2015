/// <reference path="../typings/github-electron/github-electron.d.ts" />
/// <reference path="../typings/react/react.d.ts" />
import React = require("react");

var remote = require('remote');
var remoteWindow : GitHubElectron.BrowserWindow = remote.getCurrentWindow();

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
