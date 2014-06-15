
sudo aptitude install -y build-essential libssl-dev git-core

if [ ! -s ~/.nvm/nvm.sh ]; then
  curl https://raw.githubusercontent.com/creationix/nvm/v0.7.0/install.sh | sh
fi

