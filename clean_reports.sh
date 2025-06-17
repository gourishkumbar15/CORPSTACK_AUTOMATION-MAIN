#!/bin/sh

echo "Cleaning report directories..."

# Remove test output directories
rm -rf test-output/reports/*
rm -rf test-output/screenshots/*

# Remove target directories
rm -rf target/allure-results/*
rm -rf target/allure-report/*
rm -rf target/surefire-reports/*
rm -rf target/test-classes/*
rm -rf target/classes/*

echo "Report directories cleaned successfully!" 