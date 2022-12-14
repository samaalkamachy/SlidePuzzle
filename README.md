# SlidePuzzle

26/11/2022:
Questions for customer (Heath):
1. Can I make the assumption the program only accepts bmp files with the BM signature?

Sama's Note: 06/12
You dont even really need the colour - turn it into monocrhrome. that way you will have an array with each integar representing a pixel. Then you can still do the image processing based on the intensity. 

Sama's Reply to previous note: 11/12
I tried avaraging the rgbs into a single integar and storing it into an array but I decided not to go through with it. If we have an image that only contains red, blue and green (as shown in SampleInfoHeader.bmp) then the avarge will always be 85; its either 0,0,255 or 0,255,0 , etc. I really dont see the benefits of avarging out the colours. 

I think the correct steps to take from here is add unit testing. 

Sama's Update and note: 14/12 
visually the colour table seems to be all correct now that i've implemented scan type and padding. I've jumped ahead and starting looking for the black pixels but i think i need to take a step back do proper testing and think before progressing with the image processing. There also seems to be so much data that i've colelcted from the bmp raw data that is straight up not used - what should i do with it? 

Sama notes: 15/12
UML diagram, some test cases / edge cases, maybe skeleton code?
