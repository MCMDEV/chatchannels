#!/bin/sh
set -e

# Check style for main and test
./gradlew check

# Check the exit status from gradle command
if [ $? -eq 0 ]; then
  echo "Your code has been validated successfully!"
else
  echo "Validation failed, please check your code and try again..."
  exit 1
fi