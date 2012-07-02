Citation
==========

Core repository for the Translator project.

Readme is currently *in progress*

The Translator is a program that allows for mapping of data outputs from various systems into one normalized metadata schema
tentatively known as *Translator Standard Form*, or *TSF*. From the normalized schema, *TSF*, it can produce another output
format for use by another system.

The Translator takes in three pieces of data, the input format of the data (tentatively , might auto detect in the future), and
finally the actual data payload. 

The Translator is to be tested with Primo, Xerxes, Umlaut, SFX and e-Shelf output formats. It should be modular enough to work
with other formats as well.

The Translator will be written in either JRuby or Java, either one calling each other. Due to Primo's java nature and Umlaut's
ruby nature, this seems like the best choice.

There are a number of exception cases that will be tested for. The following is a list of exceptions that the Translator will be
tested for.
- *Unexpected format* - If the format of the data is not recognized or if it does not exist, an exception should occur. Should this
					  occur, the system will output the error and terminate gracefully.
- *Format doesn't match* - If the supplied format does not match the format of the data, this exception should occur. If the system
						 is to auto detect, then this should not be a problem, however as of now the system should output the error
						 message and terminate gracefully.
- *Size limit exceeded* - If the input data size is too large this exception will occur. The system should output this error and
						terminate gracefully.