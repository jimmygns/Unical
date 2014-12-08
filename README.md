Unical
======

 unit calculator
Description
=================================================================================================================
Overview: A calculator, with units: “Unicalc”

Unicalc is a calculator program that handles numerical quantities including physical and other

units, e.g.,

420 seconds

4.20 meters / second

42.42 mile / hour + 99 km / day

Units are important to computation because they eliminate an element sometimes left to human

interpretation. In the area of engineering, failure to interpret numbers without their units specified

has been known to lead to failure of space missions. In the Wikipedia article about NASA's Mars

Climate Orbiter, it is stated:

“Twenty­four hours prior to orbital insertion, calculations placed the orbiter at an altitude

of 110 kilometers; 80 kilometers is the minimum altitude that Mars Climate Orbiter was

thought to be capable of surviving during this maneuver. Final calculations placed the

spacecraft in a trajectory that would have taken the orbiter within 57 kilometers of the

surface where the spacecraft likely disintegrated because of atmospheric stresses. The

primary cause of this discrepancy was human error. Specifically, the flight system

software on the Mars Climate Orbiter was written to calculate thruster performance using

the metric unit Newtons (N), while the ground crew was entering course correction and

thruster data using the Imperial measure Pound­force (lbf).”

A little closer to home, you have probably had to at some point in your past do calculations that

included units, and it’s quite likely that you will have to again at some point in your future. It is our

hope that this calculator will prove useful in those classes.

In this assignment you will be building a program that interprets and evaluates this simple

computer language to do unit conversions.

The Unicalc Interpreter

This section gives an overview of what a user’s interaction with the Unicalc interpreter looks like.

The Unicalc interpreter is the program you will write for the assignment. You can think of the

Unicalc interpreter as being analogous to the Interactions Pane in Dr. Java, except instead of

interpreting statements in Java, it interprets statements in the Unicalc language. When the user

types a legal Unicalc statement at the input> prompt, the Unicalc interpreter returns and displays

the result of evaluating that expression.

Example output
=================================================================================================================

> java Unicalc

input> 14m+9m

Tokens: [14, m, +, 9, m]

AST: Sum(Product(Value(14.0),Value(1.0

m)),Product(Value(9.0),Value(1.0 m)))

Result: 23.0 m

input> 60 Hz * 30s

Tokens: [60, Hz, *, 30, s]

AST: Product(Product(Value(60.0),Value(1.0

Hz)),Product(Value(30.0),Value(1.0 s)))

Result: 1800.0 Hz s

input> # 60Hz * 30s

Tokens: [#, 60, Hz, *, 30, s]

AST: Normalize(Product(Product(Value(60.0),Value(1.0

Hz)),Product(Value(30.0),Value(1.0 s))))

Result: 1800.0

input> # 364.4 smoot

Tokens: [#, 364.4, smoot]

AST: Normalize(Product(Value(364.4),Value(1.0 smoot)))

Result: 364.4 smoot

input> def smoot 67 in

Tokens: [def, smoot, 67, in]

AST: Define(smoot,Product(Value(67.0),Value(1.0 in)))

Result: 67.0 in

input> # 364.4 smoot

Tokens: [#, 364.4, smoot]

AST: Normalize(Product(Value(364.4),Value(1.0 smoot)))

Result: 620.13592 meter

The bold emphasis has been added so that you can see the core interaction happening in the

interpreter, and the user’s input is shown in blue. The user types a line in “Unicalc language” to

be evaluated at the input> prompt. The Unicalc interpreter then Tokenizes the line, parses it,

evaluates it and prints the result. The language is described in more detail below, but here’s an

annotated version of the interaction above to help you understand what’s going on. Our

annotations are in green, and are not part of the output of the Unicalc interpreter:

> java Unicalc

input> 14m+9m The user enters a unit calculation expression

Tokens: [14, m, +, 9, m] The result of tokenizing the input string

AST: Sum(Product(Value(14.0),Value(1.0

m)),Product(Value(9.0),Value(1.0 m))) The AST (split across several lines)

Result: 23.0 m The result of evaluating the sum

input> 60 Hz * 30s

Tokens: [60, Hz, *, 30, s]

AST: Product(Product(Value(60.0),Value(1.0

Hz)),Product(Value(30.0),Value(1.0 s)))

Result: 1800.0 Hz s Notice how the units don’t cancel unless we explicitly

normalize the expression, as in the next example.

input> # 60Hz * 30s

Tokens: [#, 60, Hz, *, 30, s]

AST: Normalize(Product(Product(Value(60.0),Value(1.0

Hz)),Product(Value(30.0),Value(1.0 s))))

Result: 1800.0 Because the user preceded the expression with #, the result

was normalized

input> # 364.4 smoot

Tokens: [#, 364.4, smoot]

AST: Normalize(Product(Value(364.4),Value(1.0 smoot)))

Result: 364.4 smoot If a unit is not in the database, its normalized value is just

one of itself. So 364.4 smoots normalizes to 364.4 smoots

input> def smoot 67 in Add the unit “smoot” to the database of units.

Define a smoot as 67 inches.

Tokens: [def, smoot, 67, in]

AST: Define(smoot,Product(Value(67.0),Value(1.0 in)))

Result: 67.0 in The result of this expression is defined to be the value assigned

to the variable (i.e. unit name).

input> # 364.4 smoot

Tokens: [#, 364.4, smoot]

AST: Normalize(Product(Value(364.4),Value(1.0 smoot)))

Result: 620.13592 meter Now we can represent smoots in terms of the most

basic unit of length in the database: meters.
