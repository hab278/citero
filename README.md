Translator
==========

Core repository for the Translator project.

Readme is currently *in progress*

The Translator is a program that allows for mapping of data outputs from various systems into one normalized metadata schema
tentatively known as *Translator Standard Form*, or *TSF*.From the normalized schema, *TSF*, it can produce another output
format for use by another system.

The Translator takes in three pieces of data, the input format of the data (tentatively , might auto detect in the future), and
finally the actual data payload. 

The Translator is to be tested with Primo, Xerxes, Umlaut, SFX and e-Shelf output formats. It should be modular enough to work
with other formats as well.

The Translator will be written in either JRuby or Java, either one calling each other.