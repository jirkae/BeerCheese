#!/bin/sh

echo '\n###Backend build###\n'

cd $TRAVIS_BUILD_DIR/backend/beer-app
java -version
mvn -v

mvn clean install

rc=$?
if [ $rc -ne 0 ] ; then
  echo 'Failure during Backend build'; exit $rc
fi

echo '\n###Frontend build###\n'

cd $TRAVIS_BUILD_DIR/frontend

npm install
npm run build

rc=$?
if [ $rc -ne 0 ] ; then
  echo 'Failure during Frontend build'; exit $rc
fi

echo 'Copying built Frontend app'

cd $TRAVIS_BUILD_DIR
git config --global user.email "travis@travis-ci.com"
git config --global user.name "TravisCI"

sed -n '/build\//!p' .gitignore > _tmpfile ; mv _tmpfile .gitignore
sed -n '/static\//!p' .gitignore > _tmpfile ; mv _tmpfile .gitignore

rm -Rf $TRAVIS_BUILD_DIR/backend/beer-app/src/main/resources/static
mkdir $TRAVIS_BUILD_DIR/backend/beer-app/src/main/resources/static

cp -a $TRAVIS_BUILD_DIR/frontend/build/* $TRAVIS_BUILD_DIR/backend/beer-app/src/main/resources/static/

git add .
git commit -m "Deploy"
