/// <reference path="../typings/node/node.d.ts" />
/// <reference path="../typings/github-electron/github-electron-main.d.ts" />

import E = GitHubElectron;
var app : E.App = require('app');
var BrowserWindow : typeof E.BrowserWindow = require('browser-window');

app.on('window-all-closed', () => {
  app.quit();
});

app.on('ready', () => {
  var mainWindow = new BrowserWindow({ width: 800, height: 600 });
  mainWindow.on('closed', () => { mainWindow = null; });
  mainWindow.openDevTools();
  mainWindow.loadUrl('file://' + __dirname + '/../index.html');
});
