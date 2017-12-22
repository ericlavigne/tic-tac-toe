# Presentation about clear, domain-focused programming at Clojure Ireland

The value of Clojure is that it allows your application’s domain to be expressed simply,
clearly, and directly. Clojure, when used well, fades into the background to let your
application’s domain shine through.

We’ll explore the domain of tic-tac-toe, and its Japanese variation gomoku. Along the way,
we’ll see how to structure a real Clojure application. We’ll also pause on occasion to
show how the features of Clojure allow it to fade so elegantly into the background.

You’ll have an opportunity to play the game and take the code home.

[Presentation announcement at Clojure Ireland](https://www.meetup.com/Clojure-Ireland/events/228029411/)

## Playing the game online

Tic-Tac-Toe game with computer player

See in action on [Github Pages](http://ericlavigne.github.io/tic-tac-toe/).

## Running the program

Even though this program is intended to be run as ClojureScript in the
browser, much of it is portable Clojure (.cljc) that can be tested
with Java-based Clojure. The following command automatically re-runs
the tests each time the code changes.

    lein test-refresh

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL.

## Publishing to Github Pages

lein run -m clojure.main script/publish.clj

View on [Github Pages](http://ericlavigne.github.io/tic-tac-toe/).

## License

Copyright © 2016 Eric Lavigne

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
