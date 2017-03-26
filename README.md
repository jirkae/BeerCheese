# BeerCheese [![Build Status](https://travis-ci.org/jansyk13/BeerCheese.svg?branch=master)](https://travis-ci.org/jansyk13/BeerCheese)
* Repository for Software Project class at University of Economics in Prague ([link to sylabus](https://insis.vse.cz/auth/katalog/syllabus.pl?predmet=125366)).
* Topic: eshop with configurable packages
* Backend: Spring boot app with MySQL
* Frontend: React app

## Links
* Link to build job on Travis CI - [Travis CI](https://travis-ci.org/jansyk13/BeerCheese)
* Link to deployed application on OpenShift - [BeerApp](http://beer-jansyk13.rhcloud.com/)
* Link to API description on Apiary - [Apiary](http://docs.beercheese.apiary.io/#)

## Quick GIT guide
### How to squash commits into one or more
* `git rebase -i HEAD~#` where `#` of commits you want to work with
* rewrite `pick` to `fixup` for commits you want to squash(is squashed to commit above)
* finish and push
