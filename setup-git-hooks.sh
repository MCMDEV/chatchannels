#!/bin/sh

if [ -f .git/hooks/pre-commit ]; then
  read -p "A pre-commit hook has been set up. Do you want to overwrite it? (y/n) " yn
  case $yn in
  [Yy]*) rm .git/hooks/pre-commit ;;
  [Nn]*) exit ;;
  esac
fi

chmod +x git-hooks/pre-commit.sh
ln git-hooks/pre-commit.sh .git/hooks/pre-commit
echo "Your pre-commit hook has been set up successfully"
chmod +x .git/hooks/pre-commit