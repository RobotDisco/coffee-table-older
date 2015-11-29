### What am I?

Coffee Table is single-page web app that
 * Will ostensibly allow me to review all the cafes I visit
 * Is a project for me to learn how to write web apps using React
 * Is a project for learning Clojure and Clojurescript

### Screenshot
![Screenshot of coffee-table app](http://robotdisco.github.io/coffee-table/images/screenshot.png)

### Caveat Emptor

I apologize for how bad the code is. I'm learning :D

### DB
The backend uses [Datomic](http://datomic.com), a really neat non-SQL fact store that employ the primary designers of Clojure and ClojureScript. I'm using it primarily out of curiosity and because all the tutorials I found for my React framework used it.

It's proprietary and not the SQL everyone is used to, but there's a limited free distribution that's good enough for now. Sorry for making you learn something weird. It's really interesting, though, I like the idea and wish there was a good OSS competitor.

## Prerequisites

Since Clojure(script) is a JVM-based language and I have no idea how to properly distribute stuff, you'll need to install Java and Leiningen (which will download alll the necessary Clojurescript stuff for you.) Luckily, whatever package managers your computer uses will probably have this. In OS X's homebrew it's as easy as `brew install leiningen`.

## Installation
 1. Grab Datomic Free from https://my.datomic.com/downloads/free. I'm using version 0.9.5130
 2. Untar it somewhere safe (it's where data will live, in the `data/` dir.
 3. From the datomic directory, run `config/samples/free-transactor-template.properties`. This runs the DB.
 4. From the coffee-table working dir, run `lein repl`
 5. Type the following: (I am not good enough at Clojurescript to explain, sorry)
   a. `(require 'coffee-table.db)` This imports the necessary module
   b. `(in-ns 'coffee-table.db)` This switches you to the appropriate namespace for DB utility functions
   c. `(init-db)` This creates the database and populates the schema + some initial data.
   d. `(quit)` This quits the REPL

## Operation
 From the coffee-table directory, run `lein figwheel`. This will launch the API backend + the frontend, and auto-reload the frontend whenever you change things. I don't yet know how to auto-reload the backend, and this really isn't what you should be doing when distributing anyway (just development.)

## Next steps

  * I sort of get Reach's View architecture, but I'm still learning how Reactive apps replace the Controller and Model aspects of MVC
  * Om seems like a dead end, the author has moved on to Om.Next and there's the competing [Reagent](http://reagent-project.github.io) / [Re-frame](https://github.com/Day8/re-frame) projects which are documented better and surpassed Om in feature sets.
  * I want to give this entire project a Literate Programming sweep via [Marginalia](https://github.com/gdeer81/marginalia) because I'll be damned if someone has laid out for me a clear map of how a Clojurescript React project should be structured.

## License
This application isn't good enough to be worth distributing yet and I'm self-conscious, so standard all-rights-reserved. Eventually I'll open it up formally.
