#!/bin/sh

# Set error handling
set -e

echo "Starting test execution and report generation..."

# Clean and compile the project
echo "Cleaning and compiling project..."
mvn clean compile

# Run the tests
echo "Running tests..."
mvn test

# Send the report
echo "Sending test report..."
mvn exec:java -Dexec.mainClass="generic.SendReport"

echo "Test execution and report generation completed successfully!" 