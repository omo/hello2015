source ~/.profile
nvm install 0.10
nvm use 0.10

if [ -z `which bower` ]; then
  npm install -g bower
fi

if [ -z `which http-server` ]; then
  npm install -g http-server
fi
