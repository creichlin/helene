#!/bin/bash

java -classpath "lib/build/jars/*" org.antlr.v4.Tool -package ch.kerbtier.helene.parser -visitor -no-listener src/ch/kerbtier/helene/parser/Helene.g4
