This python repository comprises of a codebase for the recommendation engine of HealthGuard called Sanjeevani.
It has majorly three subcomponents.

1. Exercise Intensity Calculation
Based on past medical history and user profile, we create a fuzzy system with rules and inference to associate a numerical value of 
what is the recommended exercise intensity the user should target

2. Hydration Requirement
Based on the users current step count, calories burned and other health metrics we give a personalized suggestion of what is the ideal
water intake of the user

3. Food Dish Recommendation
With the help of user's dietary preference with construct a cosine similarity matrix and suggest food options which can be consumed
by the user

Testing Stratergy:
There is a separate function called `testcase` which has the three function calls to individually test the subcomponents, one testcase has been showcased.
More can be simulated as per need. 