#!/bin/bash

path=$(find ~/ -type d -name FBLA_Quiz-master 2>/dev/null)

cd "$path"

test -d /Library/Java/JavaVirtualMachines/jdk-15.0.2.jdk && java_path="/Library/Java/JavaVirtualMachines/jdk-15.0.2.jdk" || java_path=$(find / -type d -name jdk-15.0.2.jdk 2>/dev/null)

$java_path"/Contents/Home/bin/java" -classpath "sqlite-jdbc-3.7.2.jar" --module-path "javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web -jar "FBLA_Quiz.jar"
