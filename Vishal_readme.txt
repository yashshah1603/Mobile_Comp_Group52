

Steps to run the notebook

1. The datasets for the training and testing of the model are in the following drive : https://drive.google.com/drive/folders/195HpMLscAxMIitzyeKAR241qgvVCbn_O?usp=sharing

2. PLease download the datasets into your local system in order to run the notebook (ECG_anomaly.ipynb).

3. 'archive(1).zip' contains all the files of MIT-BIH Arrhythmia dataset.

4. 'archive(2).zip' contains all the files of mit-bih-normal-sinus-rhythm-dataset



Overview of the my project component:

The MIT-BIH Arrhythmia dataset (MIT-BIH-ARR) is mainly composed of 48 two-channel ambulatory ECG recordings sampled at 360Hz. Each recording lasts about 30 minutes. The data was gathered from 47 people. The participants were 25 men and 22 women, ranging in age from 32 to 89 years old. This dataset comprises recordings with various degrees of arrhythmias and also recordings with normal sinus rhythm. 

The mit-bih-normal-sinus-rhythm-dataset includes 18 long-term ECG recordings of subjects referred to the Arrhythmia Laboratory at Boston's Beth Israel Hospital (now the Beth Israel Deaconess Medical Center). Subjects included in this database were found to have had no significant arrhythmias; they include 5 men, aged 26 to 45, and 13 women, aged 20 to 50.

The reason for combining both these datasets is that the model can learn patterns associated with both normal and abnormal heart rhythms. This approach enables the model to generalize better and improve its ability to detect anomalies in heartbeats. As a result, any potential bias is avoided.

During training, 'train_labels' and 'test_labels' are converted to boolean values. In the context of anomaly detection, this typically means that True represents an anomalous beat, and False represents a normal beat. In this way, separate datasets are created for normal and anomalous (abnormal) beats for both training and testing.

For testing purpose, I considered the entire test dataset for detecting anomalous heartbeats. If more than 10 consecutive heartbeats are predicted as anomalous, a trigger will be recorded notifying the same. This trigger will act as an alert notification to the emergency services and close family of the user when the model is integrated with the main app.





