# K-Means-Clustering-Facility-Location-Problem
K-Means clustering using Sequential and Parallel methods.
## Note
The more prcise and complet report of impleming and development of the project is in the file K-means-report.

# Abstract
In this report, the solution of facility location problem using K-means clustering approach
is described. As an example, 6800 cars locations on the street are considered which the rescuers
should carry them to rescuer bases to be fixed. The problem is to find the best places for
building rescuer bases. A primary solution is finding some mean locations of the cars to form
definite number of clusters. In the present work, one extra parameter is considered. Every car
has a weight that is used to find the optimal place for the rescuer base location. This is the
parameter that changes the simple K-means clustering to facility location problem.
# Introduction
Consider a definite number of the cars on the streets which should be carried by the rescuers
to the rescuer bases to fix them. Solving a facility location problem means that finding the
best places for building the rescuer bases when the car weights and their locations are known.
It is trivial that car places are not constant but the history of previous car services are only
accessible data.
### 1. Methodology: In this section the mathematical way of center of mass calculating and
the algorithm of K-means clustering are explained.
### 2. Implementation: In implementation section the code is described. The explanation
contains the graphical interface implementation and a general documentation of K-means
calculator for both sequential and parallel parts.
### 3. Results: In this part some pictures of the application interface are given that contains
related logs to show the run time and number of cycles. This determines the final clusters
centers location.
### 4. Benchmarks: In this section the effect of changing number of data and number of
clusters on run time are considered. The results are given by graphs and tables for both
sequential and parallel mode.
### 5. Conclusion: Conclusion is the final part of the report. in this section the results are
discussed.

# K-Means Calculator
The code contains a K-means class that provide the calculation of clustering. Every object of
this class keeps the state of the data and clusters information. This class has a public function
that is named run(). This function returns a KMeansReport object that contains cycle counts
and running time and follows the algorithm:
### Algorithm
![Screenshot 2022-07-09 190556](https://user-images.githubusercontent.com/58035198/178115817-7cf1e4a9-f1de-4725-ad65-a9dd1e2352b7.png)

# Results
This section just presents the application and contains some screenshots of the application.
After clustering, the map shows the locations and determine their clusters by colors. The
bigger circles are the centers of the clusters and size of each data point shows it’s weight. The
running time of clustering and the number of cycles to finish the clustering is shown in the log
part.
### Figure 1
![image](https://user-images.githubusercontent.com/58035198/178115857-0a87ffa6-5879-4138-bfb2-83ba7a6c968b.png)
### Figure 2
![image](https://user-images.githubusercontent.com/58035198/178115867-0b8c45e4-0ecc-4556-8ea2-6284c268849b.png)
# Conclusion
The results of the present report show many considerable points. At first the k-means algorithm
is capable of running in parallel, as expected. According to the result, in parallel mode, the
execution speed was about twice that of sequential mode. In performances with fewer data
and clusters, the parallel mode advantage is less, as parallelism always has an over head. So
when the data is less, the parallel is less advantageous than the sequential. The graphs are
almost linear which indicates that the order of computations is N relative to the number of
data and the number of clusters. Of course, this is obviously independent of the type of kmeans
algorithm, being parallel or sequential, so both diagrams for sequential and parallel
performances increase linearly with the number of data and clusters.
