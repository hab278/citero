Citation
==========

Core repository for the citation project.

Readme is currently *in progress*

The citation tool is a program that allows for mapping of data outputs from various systems into one normalized metadata schema
tentatively known as *Citation Standard Form*, or *CSF*. From the normalized schema, *CSF*, it can produce another output
format for use by another system.

The citation tool takes in three pieces of data, the input format of the data (tentatively , might auto detect in the future), 
the output format of the data, and finally the actual data payload. 

The citation tool is to be tested with Primo, Xerxes, Umlaut, SFX and e-Shelf output formats. It should be modular enough to work
with other formats as well.

The citation tool will be written in Java, with ruby scripts being used interchangeably. Due to Primo's java nature and Umlaut's
ruby nature, this seems like the best choice.

There are a number of exception cases that will be tested for. The following is a list of exceptions that the citation tool will be
tested for.
- *Unexpected format* - If the format of the data is not recognized or if it does not exist, an exception should occur. Should this
					  occur, the system will output the error and terminate gracefully.
- *Format doesn't match* - If the supplied format does not match the format of the data, this exception should occur. If the system
						 is to auto detect, then this should not be a problem, however as of now the system should output the error
						 message and terminate gracefully.
- *Size limit exceeded* - If the input data size is too large this exception will occur. The system should output this error and
						terminate gracefully.