# Clojure - day 1
Clojure learnings - tips and tricks

## Installation
Other than making sure that the latest version of Java was installed this was really rather simple on Ubuntu 14.04 LTS

	$ sudo apt-get install leiningen 

You don't need to install Clojure itself, as leiningen recognises a Clojure project and imports all your needed dependencies including the _clojure.jar_ for you.

## REPL
	$ lein repl

## Importing files into the REPL
	> (use 'foo :reload-all)

or

	> (require 'foo :reload-all)

I'm not sure what the difference is between the two, but it may have something to do with dependencies? Perhaps we'll see more in day 2.

## Running Clojure programs from the command line
If you're using leiningen and you've created your project using _lein new app_ then just

	$ lein run

otherwise the somewhat cryptic (for non-Java programmers):

	$ java -cp clojure.jar clojure.main /path/to/my-app.clj

but the latter means that clojure.jar needs to be on your class path (oh, more Java joy!)

## Data types
There are many and various data types in Clojure, more so than in many other languages

- Numbers 0 1 1.0
- Fractions 1/2 
- Characters \a \z
- Keywords (AKA Ruby Symbols) :a :aardvark
- Strings "" "abc" -- Not ''
- Lists () (list 1 2 3) '(1 2 3)
- Vectors [] [1 2 3]
- Sets #{} #{1 2 3}
- Maps {:a 1 :b 2} -- can use commas like white-space

## def vs defn
This is the difference between 'define a thing' and 'define a function' - I wouldn't be surprised to find out that actually they are both def, and defn is a macro that just gives you some syntactic sugar. 

Note to self: _defn_ takes parameters as a vector rather than a list! If you've done any SICP/Scheme/Racket this will come as a surprise.

let and destructuring assignment

## TDD in Clojure
Run tests using:

	$ lein test

(If you started your coding with _lein new app_)

## Find:
### Examples using Clojure sequences - this is pretty easy to find, e.g.
- [The Clojure documentation](http://clojure.org/sequences)
- [The Clojure Cookbook](http://en.wikibooks.org/wiki/Clojure_Programming/Examples/Cookbook)
- [A Clojure Tutorial For the Non-Lisp Programmer](http://moxleystratton.com/clojure/clojure-tutorial-for-the-non-lisp-programmer)

The important point is demonstrated in the last linked page: "a Sequence is not a data structure. It is an interface, or view, into a data structure"

### The formal definition of a Clojure function

> Fns are first-class objects that implement the IFn interface. The IFn interface defines an invoke() function that is overloaded with arity ranging from 0-20. A single fn object can implement one or more invoke methods, and thus be overloaded on arity. One and only one overload can itself be variadic, by specifying the ampersand followed by a single rest-param. 

Interestingly enough since v1.1 Clojure functions allow for pre: and post: conditions, allowing Design by Contract!

### A script for quickly invoking the repl in your environment - simples!
	$ lein repl

## Do:

### String length bound checking
Implement a function called (big st n) that returns true if a string st is longer than n characters.

This turned out to be fairly simple, but a first look up the string library [here](https://clojure.github.io/clojure/clojure.string-api.html) didn't really help; what, no string length method? A glance at the **very useful** Clojure cheat sheet [here](http://clojure.org/cheatsheet) tells us that we can use the general function 'count' to help us. So:

	(defn big [st n] (> (count st) n))

Yes, there are no tests, for the full implementation see the following [github repo]()

### Typeof function
Write a function called (collection-type col) that returns :list , :map , or :vector based on the type of collection col.

	(class (list 1 2))

Gives us 'clojure.lang.PersistentList' which is a 'java.lang.Class' rather than a string that we can compare with the equals operator, which is an issue. Furthermore if we try

	(class (list))

We get 'clojure.lang.PersistentList$EmptyList' which is even less helpful. There are however different functions that test for type identity, namely:

	map?
	set?
	vector?
	list?

This leads us to some messy xmas-tree like code though:

	(defn collection-type [col] 
		(if (map? col) :map
			(if (list? col) :list 
				(if (vector? col) :vector 
					"Unmatched type" ))))

A better implementation is to use the _cond_ function:

> clojure.core/cond
([& clauses])
Macro
  Takes a set of test/expr pairs. It evaluates each test one at a
  time.  If a test returns logical true, cond evaluates and returns
  the value of the corresponding expr and doesn't evaluate any of the
  other tests or exprs. (cond) returns nil.

	(defn collection-type [col]
		(cond
			(map? col) :map
			(list? col) :list
			(vector? col) :vector
			true "Unmatched type" ))

This passes our tests and works much more nicely. This seemed a rather easy day's exercises, so I'm looking forward to finding out more about Clojure and more about how to do testing in Clojure.
