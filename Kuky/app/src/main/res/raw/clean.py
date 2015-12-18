import csv

syllables = open('syllables.txt', 'r')

# cleaned = open('cleaned.txt','a')

for line in syllables.readlines():
	newline = line.rstrip()
	[word, syl] = newline.split('=')
	numsyl = len(syl.split('\xb7'))
	newline = word+","+str(numsyl)

	if len(word) < 3: 
		print word

	# print newline
	# cleaned.write(newline + '\n')


# cleaned.close()
