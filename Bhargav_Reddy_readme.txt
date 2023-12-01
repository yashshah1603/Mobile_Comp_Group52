Steps to run the code and Info Each Page

First page is the home page, clicking on login will redirect to the next page.
Second page has 2 options: Mobile and Watch.
Clicking the Watch button will do nothing, it will be integrated by other members of the team.
 Clicking on the Phone button will take you to the login page.
Use following credentials Username: admin and Password: admin to login
Next user is presented with 4 options: 
first to measure heart and respiratory rate.
To add a new profile and save to the DB.
Track the steps taken by the person and save it to the DB
Add water consumption and workout details.
The Exercise Activity page has 5 workout buttons and selecting each button will give benefits recommended duration and amount of calories burnt. For now the recommended duration value is hardcoded but it will dynamically change based on the fuzzy logic and ML prediction algorithm.
If the heart rate reading is more than 150 we prompt an alert to the user asking if the user is ok? If No is selected then we contacted the emergency services for help. If YES is selected we ignore it.


Overview of My project:
The main purpose of this project is to ensure that lifestyle suggestions, emergency response systems, and personalized health insights are all seamlessly integrated. With Health Guard, you can get proactive alerts for important heart rate thresholds and diligent fitness tracking that tracks steps, water consumption, and activities. Health Guard is a one-stop shop that combines emergency response, preventive care, and lifestyle enhancement into one user-friendly platform.

We’re supporting this feature in 2 ways:
If a user has a smartwatch, we get health data from the watch and process it. 
Based on the user characteristics(age,weight,height) we recommend dietary and workout plans.	
If the user doesn’t have a smartwatch we provide an app for the user. Where the user can measure heart rate, respiratory rate, record water consumption, record steps and workout.
