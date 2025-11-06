#!/bin/bash
# Run the Swing GUI version

echo "🎨 Starting Student Examination System (GUI Version)..."
echo ""

cd "/Users/shivagupta/Java:ADBMS Final Project"

# Compile if needed
if [ ! -d "target/classes" ]; then
    echo "📦 Compiling..."
    javac -cp "lib/*" -d target/classes -sourcepath src src/**/*.java src/*.java
fi

# Run GUI with MySQL driver in classpath
java -cp "target/classes:lib/*" gui.LoginFrame

