import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
import joblib
import os

# -----------------------------
# 1. Load Dataset
# -----------------------------

path = r"C:\Users\rimik\OneDrive\Desktop\VirtualStudyBuddy\src\main\java\com\studybuddy\ml\dataset\student-mat.csv"

try:
    df = pd.read_csv(path, sep=";")
    print("Dataset loaded successfully!")
except FileNotFoundError:
    print(f"Error: Could not find dataset at {path}")
    exit()


# -----------------------------
# 2. Create Missing ML Features
# -----------------------------


df["difficulty"] = df["studytime"]

# estimate study hours from studytime
df["studyhours"] = df["studytime"] * 2


# -----------------------------
# 3. Feature Selection
# -----------------------------

X = df[[
    "studytime",
    "failures",
    "absences",
    "difficulty",
    "studyhours"
]]

y = df["G3"]



y = (y >= 10).astype(int)


# -----------------------------
# 5. Split Data
# -----------------------------

X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42
)


# -----------------------------
# 6. Train Model
# -----------------------------

model = RandomForestClassifier(
    n_estimators=200,
    random_state=42
)

model.fit(X_train, y_train)


# -----------------------------
# 7. Save Model
# -----------------------------

save_dir = "ml"

if not os.path.exists(save_dir):
    os.makedirs(save_dir)
    print(f"Created directory: {save_dir}")

model_path = os.path.join(save_dir, "study_model.pkl")

joblib.dump(model, model_path)


# -----------------------------
# 8. Done
# -----------------------------

print("----- SUCCESS -----")
print("Model trained successfully!")
print(f"Model saved at: {model_path}")