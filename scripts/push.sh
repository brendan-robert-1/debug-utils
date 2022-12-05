#!/bin/sh

if [ $# -ne 1 ]
	then
	echo "Please supply exactly one commit message as an argument."
	exit 1
fi
./gradlew ktLintFormat
git add .
git commit -m "$1"
git push
echo "Finished linting, adding, committing and pushing"