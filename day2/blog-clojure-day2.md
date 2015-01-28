# Clojure - day 2
Clojure learnings - tips and tricks

## Loading files into the REPL
It is common to edit files and then try them out in the REPL, because continually retyping stuff in the REPL gets REPetative after a whiLe, and while the REPL is fine for one-liners, its not quite so great for longer things. 

If you have a file called _size.clj_ then the following seem to work:

	(load "size")
	(load-file "size.clj")

The examples in my day1 blog don't work _unless_ you've defined a namespace, or have used:

	lein new app <my-app-name>

which sets up your ns in src/<my-app-name>/core.clj

This seems to be a lot less hassle than:

1. Download the Clojure executables [here](http://clojure.org/downloads)
1. Unzip to a sensible location (~/bin/clojure perhaps)
1. export $CLASSPATH=/path/to/my/clojure.jar
1. java -cp clojure.jar clojure.main /path/to/my/clojure-script.clj

## Using loop and recur to emulate tail-call optimisation

This seems to me to be a significant deficit in any functional language.

## Sequences and he standard library
We talk through the usual map, filter and reduce - nothing new here. The range, iterate, take and drop are familiar from Haskell, though I haven't seen cycle, interpose and interleave before, I'm sure that's just a lack of familiarity with Prelude however.

The concept of lazy evaluation of infinite sequences seems to be an elegant and powerful one, and one that I haven't had much experience with due to them not being a part of the core language of JS, Python, Java, Ruby etc. 

RPN still trips me up sometimes!

I had a problem with:

	(defn fib-pair [a b] [b (+ a b)])

until I realised it should have been (easily missed):

	(defn fib-pair [[a b]] [b (+ a b)])

as the argument list is in square brackets, but what I want is a single argument that is a vector with two elements, not two arguments...

## Records and Protocols
Accessing a hash value by using (:bearing c) was something that doesn't come as naturally as c.bearing or c["bearing"]

What is the reason to have a trailing period after the record name in the following? It turns out that this is the shorthand for 'invoke the constructor' - something which the book doesn't mention (and neither did any of the Clojure doco that I found).

	(def c (SimpleCompass. 0)) 

I would have liked a bit more practice with records and protocols outside of the one example given, before moving on to:

## Macros

Ugh. This is definitely an area that will need further practice for me to become proficient with. Our unless macro looks like this, with quotes to escape keywords:

	(defmacro unless [test body] (list 'if (list 'not test) body))

## Find:
### The implementation of some of the commonly used macros in the Clojure language
The best place to go for these is actually the Clojure source code iteself. A list of macros already defined in the language can be found at the bottom of [this](http://clojure.org/macros) page. There are several good examples e.g.

	(defmacro when
		"Evaluates test. If logical true, evaluates body in an implicit do."
		{:added "1.0"}
		[test & body]
		(list 'if test (cons 'do body)))

	(defmacro if-not
		"Evaluates test. If logical false, evaluates and returns then expr,
		otherwise else expr, if supplied, else nil."
		{:added "1.0"}
		([test then] `(if-not ~test ~then nil))
		([test then else]
			`(if (not ~test) ~then ~else)))

Probably the best example is the threading macro, used to tell a reader of your code that you are constructing a function pipeline and making it clearer by removing unnecessary parens. 

	(defmacro ->
		"Threads the expr through the forms. Inserts x as the
		second item in the first form, making a list of it if it is not a
		list already. If there are more forms, inserts the first form as the
		second item in second form, etc."
		{:added "1.0"}
		[x & forms]
		(loop [x x, forms forms]
			(if forms
				(let [form (first forms)
					threaded (if (seq? form)
						(with-meta `(~(first form) ~x ~@(next form)) (meta form))
						(list form x))]
					(recur threaded (next forms)))
				x)))


### An example of defining your own lazy sequence
How about a sequence of square numbers?

	(take 5 (map (fn [x] (* x x)) (iterate inc 1)))

Or to use the anonymous function shorthand:

	(take 5 (map #(* % %) (iterate inc 1)))

### The current status of the defrecord and protocol features 
_these features were still under development as this book was being developed_

As the [defrecord](https://clojuredocs.org/clojure.core/defrecord) and [defprotocol](https://clojuredocs.org/clojure.core/defprotocol) entries in the Clojure docs show, these have been available since v1.2; Clojure is currently on it's v1.6 release (stable branch).

## Do:
### Implement an unless with an else condition using macros

We already have a macro that we've used to implement _unless_: 

	(defmacro unless [test body] (list 'if (list 'not test) body))

So we'll start there. We must obviously add an else clause, so we can start with a 

	(defmacro unless [test true-clause else-clause] ())

The second part just needs to include the second clause of the if statement like so:

	(defmacro unless [test true-clause else-clause]
		(list 'if (list 'not test) true-clause else-clause))

This seems to work nicely. There's probably an obvious pitfall that I'm not seeing here - probably because I didn't fall into it...

It would be nice if we could specify the else-clause as optional. It turns out that there's a simple pattern matching way of specifying the arity of a function (or a macro) in Clojure like so:

	(defn num-args
		([] (println "no args"))
		([a] (println "one arg"))
		([a b] (println "two args")))

We can use this to combine our previous two forms of the _unless_ macro:

	(defmacro unless 
	  ([test true-clause]
	    (list 'if (list 'not test) true-clause))
	  ([test true-clause else-clause]
	    (list 'if (list 'not test) true-clause else-clause)))

### Write a type using defrecord that implements a protocol

For this exercise we'll implement an Animal protocol, with make-noise and run-speed methods. We'll then implement a Cat record that adheres to the Animal protocol, and test it by instantiating a Cat 'object' and seeing how it responds.

	(defprotocol Animal
	  (make-noise [x]))

	(defrecord Cat [name]
	  Animal
	  (make-noise [_] (str name " goes meow")))

	(deftest a-Cat-record
	  (testing "A cat should go meow"
	    (def my-cat(Cat. "Sherbet"))
	    (is (= (make-noise my-cat) "Sherbet goes meow" ))
	))

There's still some things I have to get my head around with namespaces and how they interact with leiningen, but that's just a matter of practice practice practice...

For the dojo I decided to give people a stereotype to work from, and suggested a bank account record. My protocol looks like this:

	(defprotocol Account
	  (get-balance [this])
	  (deposit [this amount]))

Adding a 'withdraw' function to this is trivial, so creating an account, checking the balance and adding to the account is the minimum viable class. With that in mind our record looks like this:

	(defrecord BankAccount [owner balance]
	  Account
	  (get-balance [this] (:balance this))

Note how we use the symbol version of the record attribute as an accessor function.

Now my first problem appeared, as I didn't want to have to specify a starting balance as part of creating the record, instead I wanted to supply an incomplete number of fields and have the record constructor fill in the default balance of &pound;0 - this proved to be more difficult than I imagined. 

As far as I can tell (other than making a new defrecord with defmacro) the idiomatic way to do this is to create a factory function that spits out new BankAccount records, like so:

	(defn new-bank-account [owner-name] (BankAccount. owner-name 0))
    (def my-bank-account (new-bank-account "Sleepy Fox"))

Despite a good trawl through the doco for defprotocol and defrecord I did not find a well documented way of overriding the record constructor in order to provide business logic around instantiating a new record instance, another example of the differences between the LISP and Java ways perhaps?

A further problem occurs when we try and implement deposit, as the naive way fails: 

	(deposit [this amount] 
		(assign this.balance (+ (:balance this) amount))))

This kind of thing can't work, as Clojure data structures are immutable. Instead we need to return a new version of the record with the updated values, which we can do with the _assoc_ function:

	(deposit [this amount] 
		(assoc this :balance (+ (:balance this) amount))))

We need to be careful when we test this however, remembering that we're not mutating the existing record in-place.

After more study of stackoverflow and Clojure documentation it seems that Bruce has led us on a bit of a wild goose chase, and that Clojure programmers only really use records in this way to do Java interop, and that normally they just use data structures (whether hashes, records with no embedded behaviour or something else) and functions that operate on those data structures. 
