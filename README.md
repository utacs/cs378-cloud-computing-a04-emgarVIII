# Please add your team members' names here. 

## Team members' names 

1. Student Name: Mauricio Garcia

   Student UT EID: mg64983

2. Student Name: Derek Chen

   Student UT EID: dyc377

3. Student Name: Mohamed Ilaiwi

   Student UT EID: mi5365

4. Ritu Karivaradasamy

   Student UT EID: rk27822

##  Course Name: CS378 - Cloud Computing 

##  Unique Number: 51515
    


# Add your Project REPORT HERE 
## Screenshots of our Google Cloud - Dataproc that shows the machines ran
![image](https://github.com/user-attachments/assets/3eb0aa0c-30af-4ee5-b05e-7064344cf46f)
<img width="1800" alt="image" src="https://github.com/user-attachments/assets/9b6d6179-4904-4bc6-b8c4-6cbfb4372c3e">


## For each task, for each Hadoop job you ran, include a screen shot of the Yarn History.
![image](https://github.com/user-attachments/assets/83aaa07b-0209-48f9-b2e2-0b476f9d0d2b)

## Findings on Large Dataset:
TASK 1:
0   471533
1   354126
2   266408
3   202101
4   148478
5   120110
6   242764
7   421789
8   526448
9   556483
10  541711
11  556049
12  588849
13  587648
14  612576
15  605905
16  525417
17  594932
18  737887
19  761435
20  706806
21  690853
22  663087
23  587597

TASK 2:
disemailisforschoolonly@cluster-a4v2-m:~$ gsutil cat gs://my-hadoop-bucket-mauricio/output-task2/part-r-00000
0219EB9A4C74AAA118104359E5A5914C        1.0
8D90E4C96A0C162BC9A663F7F3C45379        1.0
12CE65C3876AAB540925B368E8A0E181        1.0
14C5001FBF4706F49E6D436FA1EC8428        1.0
FE757A29F1129533CD6D4A0EC6034106        1.0

TASK 3:
disemailisforschoolonly@cluster-a4v2-m:~$ gsutil cat gs://my-hadoop-bucket-mauricio/output-task3/part-r-00000
FD2AE1C5F9F5FBE73A6D6D3D33270571        4095.0
A7C9E60EEE31E4ADC387392D37CD06B8        1260.0
D8E90D724DBD98495C1F41D125ED029A        630.0
E9DA1D289A7E321CC179C51C0C526A73        231.3
74071A673307CA7459BCF75FBD024E09        210.0
95A921A9908727D4DC03B5D25A4B0F62        210.0
42AB6BEE456B102C1CF8D9D8E71E845A        191.54999
FA587EC2731AAB9F2952622E89088D4B        180.0
28EAF0C54680C6998F0F2196F2DA2E21        180.0
E79402C516CEF1A6BB6F526A142597D4        144.54546

# Project Template

# Running on Laptop     ####

Prerequisite:

- Maven 3

- JDK 1.6 or higher

- (If working with eclipse) Eclipse with m2eclipse plugin installed


The java main class is:

edu.cs.utexas.HadoopEx.HourlyErrors 

Input file:  taxi-data-sorted-small.csv

Specify your own Output directory like 

# Running locally using the makefile:

To run task1, run:

`make run`

To run task2: run,

`make run-task2`

To run task3, run,

`make run-task3`

This assumes that taxi-data-sorted-small.csv exists in the same directoy as `Makefile`.


## Create a JAR Using Maven 

To compile the project and create a single jar file with all dependencies: 
	
```	mvn clean package ```



## Run your application
Inside your shell with Hadoop

Running as Java Application:

```java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar SOME-Text-Fiel.txt  output``` 

Or has hadoop application

```hadoop jar your-hadoop-application.jar edu.cs.utexas.HadoopEx.WordCount arg0 arg1 ... ```


## To run on Google Cloud (replace with your own information)
First, add the dataset to your Google bucket. Then,

### Task 1:
`gcloud dataproc jobs submit hadoop \
--cluster=cluster-a4v2 \
--region=us-central1 \
--class=edu.cs.utexas.HadoopEx.Driver.HourlyErrors \
--jars=gs://my-hadoop-bucket-mauricio/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar \
-- gs://my-hadoop-bucket-mauricio/taxi-data-sorted-large.csv gs://my-hadoop-bucket-mauricio/output` 

### Task 2 (Error Rates Runner):
`gcloud dataproc jobs submit hadoop \
--cluster=cluster-a4v2 \
--region=us-central1 \
--class=edu.cs.utexas.HadoopEx.Driver.HourlyErrors \
--jars=gs://my-hadoop-bucket-mauricio/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar \
-- gs://my-hadoop-bucket-mauricio/taxi-data-sorted-large.csv gs://my-hadoop-bucket-mauricio/intermediate-task2 gs://my-hadoop-bucket-mauricio/output-task2`

### Task 3 (Efficiency Runner):
`gcloud dataproc jobs submit hadoop \
  --cluster=cluster-a4v2 \
  --region=us-central1 \
  --class=edu.cs.utexas.HadoopEx.Driver.HourlyErrors \
  --jars=gs://my-hadoop-bucket-mauricio/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar \
  -- gs://my-hadoop-bucket-mauricio/taxi-data-sorted-large.csv \
  gs://my-hadoop-bucket-mauricio/intermediate-task3 \
  gs://my-hadoop-bucket-mauricio/output-task3 \
  gs://my-hadoop-bucket-mauricio/extra-task3`


## Create a single JAR File from eclipse

Create a single jar file with eclipse 

*  File export -> export  -> export as binary ->  "Extract generated libraries into generated JAR"
