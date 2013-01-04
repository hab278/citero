Citero
==========
[![Build Status](http://jenkins1.bobst.nyu.edu/view/Citero/job/Citero/badge/icon)](http://jenkins1.bobst.nyu.edu/view/Citero/job/Citero/)
[![Build Status](https://travis-ci.org/NYULibraries/citero.png?branch=development)](https://travis-ci.org/NYULibraries/citero)

Core repository for the Citero project.

Citero is a program that allows for mapping of data inputs from various systems into one normalized metadata schema
tentatively known as *Citero Standard Form*, or *CSF*. From the normalized schema, *CSF*, it can produce another output
format for use by another system.

Citero is written in Java and an adapter in JRuby will be written. Due to Primo's Java nature and Umlautes ruby nature, this seems like the best choice.

Currently Supported Formats/Systems
===================================
Citero takes in three pieces of data, the input format of the data the output format of the data, and finally the actual data payload. 

Formats: PNX, Xerxes normalized XML (to RIS), OpenURL, RIS, BibTex
Systems: Primo, Xerxes, Umlaut, e-Shelf, Easy Bib ...

How to install
==============
Citero leverages Maven to build and package the application. To build as a jar simply run

	mvn install

in the root directory

There is a seperate goal and profile for the Ruby wrapper. Simply run

	mvn assembly:single -P gem clean:clean

in the root directory. This will move the citero.jar into gem/lib/citero/

Exceptions
==========
There are a number of exception cases there are tests for. The following is a list of exceptions that Citero is
tested for.
- *Unexpected format* - If the format of the data is not recognized or if it does not exist, an exception will occur. The system will output the error and terminate gracefully.
- *Format doesn't match* - If the supplied format does not match the format of the data, this exception will occur. If the system is to auto detect, then this should not be a problem, however as of now the system should output the error message and terminate gracefully.
- *Format doesn't exist* - If the supplied format is not supported or does not exist, this exception will be thrown. The error will be outputted and then Citero will terminate gracefully.

These tests are not yet tested for, but will in the future.
- *Size limit exceeded* - If the input data size is too large this exception will occur. The system should output this error and
						terminate gracefully.

