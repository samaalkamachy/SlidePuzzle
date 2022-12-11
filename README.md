# SlidePuzzle

26/11/2022:
Questions for customer (Heath):
1. Can I make the assumption the program only accepts bmp files with the BM signature?

Sama's Note: 06/12
You dont even really need the colour - turn it into monocrhrome. that way you will have an array with each integar representing a pixel. Then you can still do the image processing based on the intensity. 

Sama's Reply to previous note: 11/12
I tried avaraging the rgbs into a single integar and storing it into an array but I decided not to go through with it. If we have an image that only contains red, blue and green (as shown in SampleInfoHeader.bmp) then the avarge will always be 85; its either 0,0,255 or 0,255,0 , etc. I really dont see the benefits of avarging out the colours. 
