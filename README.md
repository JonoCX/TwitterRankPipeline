#Twitter Rank Pipeline#

##Produce bayes.model##

The package uk.ac.ncl.cc contains all the code needed to produce the bayes.model. 
The steps to do so are the following:
* Create a .txt file named 'tweetsForTraining.txt' whereby they are in the following format:
  * noise "RT @frasesdebebadas: Os gringos achando q o maior problema do Brasil � zika virus e viol�ncia, s� v�o descobrir a vdd qnd chegarem aqui htt�"
* Once you've done this, put the text file within the project workspace and run the Preprocess.java class. This class produces the 'tweets.train' file.
* Now to convert the 'tweets.train' file into the 'tweets.arff' file; to do this, run the ArffConverter.java class. The result should be a 'tweets.arff' file.
* To produce the 'bayes.model' run the TrainBuildClassifier.java class.
* Done.
