#!/bin/sh

# Set the classpath
CLASSPATH="target/classes"
for jar in $(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout | tr ':' ' '); do
    CLASSPATH="$CLASSPATH:$jar"
done

# Run the SendReport class
java -cp "$CLASSPATH" generic.SendReport 