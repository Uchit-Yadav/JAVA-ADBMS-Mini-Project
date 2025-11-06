#!/bin/bash
# Simple script to run the console application

echo "🎓 Starting Student Examination System (Console Version)..."
echo ""

cd "/Users/shivagupta/Java:ADBMS Final Project"

# Compile if needed
if [ ! -d "target/classes" ]; then
    echo "📦 Compiling..."
    javac -cp "lib/*" -d target/classes -sourcepath src src/db/*.java src/model/*.java src/dao/*.java src/service/*.java src/util/*.java src/*.java
fi

# Run the application with MySQL driver in classpath
java -cp "target/classes:lib/*" Main

