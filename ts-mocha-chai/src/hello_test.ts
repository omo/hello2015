/// <reference path="../typings/mocha/mocha.d.ts" />
/// <reference path="../typings/chai/chai.d.ts" />
import chai = require('chai');
import hello = require('./hello');

var expect = chai.expect;

describe('Hello', () => {
  it('mention your name', () => {
    expect(hello.hello("Alice")).to.equal("Hello, Alice");
  });
});
