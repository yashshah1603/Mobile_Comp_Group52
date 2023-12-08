####### Section 0 ###########
import subprocess
import os


def check_dependency(package_name):
    with open(os.devnull, 'w') as devnull:
        process = subprocess.Popen(["pip3", "show", package_name], stdout=devnull, stderr=devnull)
        process.communicate()

    if process.returncode != 0:
        with open(os.devnull, 'w') as devnull:
            proc = subprocess.Popen(["pip3", "install", package_name], stdout=devnull, stderr=devnull)
            proc.wait()

check_dependency("scikit-fuzzy")
check_dependency("numpy")
check_dependency("pandas")
check_dependency("scikit-learn")
check_dependency("Flask")
check_dependency("flask-cors")
check_dependency("wfdb")
check_dependency("matplotlib==3.1.3")


from flask import Flask
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

if __name__ == '__main__':
    app.run()

####### Section 1 ###########
import numpy as np
import skfuzzy as fuzz
from skfuzzy import control as ctrl
import math

def setup_fuzzy_system():
    # setting up the control system
    age = ctrl.Antecedent(np.arange(0, 101, 1), 'age')
    gender = ctrl.Antecedent(np.arange(0, 2, 1), 'gender')  # 0 for male, 1 for female
    medical_history = ctrl.Antecedent(np.arange(0, 101, 1), 'medical_history')
    exercise_intensity = ctrl.Consequent(np.arange(0, 101, 1), 'exercise_intensity')

    # creating membership functions
    age['young'] = fuzz.gauss2mf(age.universe, 10, 5, 26, 5)
    age['middle_aged'] = fuzz.gauss2mf(age.universe, 40, 10, 60, 10)
    age['old'] = fuzz.gauss2mf(age.universe, 70, 10, 90, 10)

    gender['male'] = fuzz.gauss2mf(gender.universe, 0.2, 0.1, 0.8, 0.1)
    gender['female'] = fuzz.gauss2mf(gender.universe, 0.8, 0.1, 1.2, 0.1)

    medical_history['healthy'] = fuzz.gauss2mf(medical_history.universe, 5, 5, 15, 5)
    medical_history['medium_risk'] = fuzz.gauss2mf(medical_history.universe, 20, 10, 40, 10)
    medical_history['high_risk'] = fuzz.gauss2mf(medical_history.universe, 55, 10, 85, 10)

    exercise_intensity['light'] = fuzz.gauss2mf(exercise_intensity.universe, 10, 10, 30, 10)
    exercise_intensity['moderate'] = fuzz.gauss2mf(exercise_intensity.universe, 40, 10, 70, 10)
    exercise_intensity['intense'] = fuzz.gauss2mf(exercise_intensity.universe, 60, 10, 90, 10)

    # initalizing the inference rules
    rule1 = ctrl.Rule(medical_history['high_risk'] & age['old'], exercise_intensity['light'])
    rule2 = ctrl.Rule(medical_history['medium_risk'] & age['middle_aged'] & gender['female'], exercise_intensity['moderate'])
    rule3 = ctrl.Rule(medical_history['medium_risk'] & age['middle_aged'] & gender['male'], exercise_intensity['intense'])
    rule4 = ctrl.Rule(medical_history['healthy'] & gender['female'], exercise_intensity['intense'])
    rule5 = ctrl.Rule(medical_history['healthy'] & gender['male'], exercise_intensity['moderate'])

    # executing the simulation and defuzzification
    exercise_ctrl = ctrl.ControlSystem([rule1, rule2, rule3, rule4, rule5])
    exercise_simulation = ctrl.ControlSystemSimulation(exercise_ctrl)
    return exercise_simulation


@app.route('/testExerciseSimulation')
def testExerciseSimulation(m, a, g):
    exercise_simulation = setup_fuzzy_system()
    exercise_simulation.input['medical_history'] = m
    exercise_simulation.input['age'] = a
    exercise_simulation.input['gender'] = g
    exercise_simulation.compute()
    print("Recommended Exercise Intensity:", math.floor(exercise_simulation.output['exercise_intensity']))
    return exercise_simulation


######## Section 2 ##########
import pandas as pd
import math
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_absolute_error

data = pd.read_csv('./Activity_Dataset_V1.csv')

# feature list extraction
distance = data['distance']
time = data['time']
calories = data['calories']
intensity = data['intensive(%)']
hydration = (distance * time * intensity/100) + (calories/10)
data['hydration'] = hydration
features_of_interest = data[['distance', 'time', 'calories', 'avg_heart_rate', 'max_heart_rate', 'vo2_max(%)', 'intensive(%)']]
target_feature = data[['hydration']]

# train and test split for learning model
X_train, X_test, y_train, y_test = train_test_split(features_of_interest, target_feature, test_size=0.2, random_state=42)
# training random forest model
model = RandomForestRegressor(random_state=42)
model = model.fit(X_train, y_train.values.ravel())
predictions = model.predict(X_test)
# checking mean absolute error
mae = mean_absolute_error(y_test, predictions)
# print(f'Mean Absolute Error: {mae}')

@app.route('/testHydrationNeeded')
def testHydrationNeeded(d, t, c, ahr, mhr, o):
    try:
        exercise_simulation = testExerciseSimulation(15, 26, 0)
        e = exercise_simulation.output['exercise_intensity']
    except NameError:
        e = 25
    new_activity = pd.DataFrame([[d, t, c, ahr, mhr, o, e]], columns=['distance', 'time', 'calories', 'avg_heart_rate', 'max_heart_rate', 'vo2_max(%)', 'intensive(%)'])
    predicted_hydration = model.predict(new_activity)
    print(f'Suggested Hydration Requirement for you: {math.ceil(predicted_hydration[0])} ounces')


##### Section 3 #######
from sklearn import preprocessing
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from scipy.sparse import csr_matrix
from sklearn.neighbors import NearestNeighbors
import re
import string
import random

data = pd.read_csv('./food.csv')
features = ['C_Type','Veg_Non', 'Describe']
# feature extraction
data['Describe'] = "".join([char for char in data['Describe'] if char not in string.punctuation])
data['Important'] = data['C_Type'].str.replace(" ","").str.lower() + data['Veg_Non'].str.replace('-', '')
# stop word removal
tfidf = TfidfVectorizer(stop_words='english')
tfidf_matrix = tfidf.fit_transform(data['Describe'])
# cosine similarity matrix
cosine_sim = linear_kernel(tfidf_matrix, tfidf_matrix)

@app.route('/get_recommendations')
def get_recommendations(diet, ctype, cosine_sim=cosine_sim):
    filtered_df = data[(data['Veg_Non'].str.replace('-', '') == diet) & (data['C_Type'].str.replace(" ","").str.lower() == ctype)]
    indices = pd.Series(filtered_df.index, index=(filtered_df['Important']))
    idx = indices[ctype+diet]
    min_subset_size = 1
    max_subset_size = min(5, len(idx))
    st = set()
    print(f'Suggested item for you based on your dietary preferences: ')
    for k in idx:
      sim_scores = list(enumerate(cosine_sim[k]))
      sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
      sim_scores = sim_scores[1:2]
      food_indices = [i[0] for i in sim_scores]
    subset_size = random.randint(min_subset_size, max_subset_size)
    et = set(random.sample(sorted(idx), subset_size))
    for k in et:
      df1 = (filtered_df[(filtered_df['Food_ID'] == (k+1))])
      df1 = df1['Name'].tolist()[0]
      st.add(str(df1))
    print(st)



# Testing Scenarios
def testcase():
    testExerciseSimulation(15, 26, 0)
    testHydrationNeeded(5, 25, 200, 110, 170, 39)
    get_recommendations('veg', 'healthyfood')


##### SECTION 4 #######
import smtplib
from email.mime.text import MIMEText
@app.route('/send_email')
def send_email(name, r1="", r2="", r3="", r4="", r5=""):
    subject = "IMPORTANT!!! Information about " + name + " from Health Gaurd"
    body = "Hi, this is an automated mail from Health Guard." + name + " is in distress and is reaching out to you to \
    inform you that emergency services have been informed and our on their way."
    sender = "dummy@asu.edu"
    password = "dummy"
    msg = MIMEText(body)
    msg['Subject'] = subject
    msg['From'] = sender
    recipients = []
    if r1 != "":
        recipients.append(r1)
    if r2 != "":
        recipients.append(r2)
    if r3 != "":
        recipients.append(r3)
    if r4 != "":
        recipients.append(r4)
    if r5 != "":
        recipients.append(r5)
    msg['To'] = ', '.join(recipients)
    with smtplib.SMTP_SSL('smtp.gmail.com', 465) as smtp_server:
       smtp_server.login(sender, password)
       smtp_server.sendmail(sender, recipients, msg.as_string())
    print("Message sent!")
