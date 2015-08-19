/// <reference path="../typings/node/node.d.ts" />
/// <reference path="../typings/github-electron/github-electron.d.ts" />

var app : GitHubElectron.App = require('app');
var BrowserWindow : typeof GitHubElectron.BrowserWindow = require('browser-window');

app.on('window-all-closed', () => {
  app.quit();
});

app.on('ready', () => {
  var mainWindow = new BrowserWindow({ width: 800, height: 600 });
  mainWindow.on('closed', () => { mainWindow = null; });
  mainWindow.openDevTools();
  mainWindow.loadUrl('file://' + __dirname + '/../index.html');
});
