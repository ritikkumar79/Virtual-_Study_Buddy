import sys
import joblib
import os
import numpy as np

try:
    # locate model file
    script_dir = os.path.dirname(os.path.abspath(__file__))
    model_path = os.path.join(script_dir, "study_model.pkl")

    if not os.path.exists(model_path):
        print("Model file not found")
        sys.exit(1)

    model = joblib.load(model_path)

    # check input arguments
    if len(sys.argv) < 6:
        print("Invalid input parameters")
        sys.exit(1)

    # read inputs from Java
    studytime = int(sys.argv[1])
    failures = int(sys.argv[2])
    absences = int(sys.argv[3])
    difficulty = int(sys.argv[4])
    studyhours = int(sys.argv[5])

    # create feature vector
    features = np.array([[studytime, failures, absences, difficulty, studyhours]])

    # prediction
    prediction = model.predict(features)

    if prediction[0] == 1:
        print("High success probability")
    else:
        print("Low success probability")

except Exception as e:
    # always return something so Java doesn't receive null
    print("Prediction error:", str(e))