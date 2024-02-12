java -classpath ../javacc.jar javacc Calculator.jj
java -classpath ../javacc.jar javacc CalculatorTrig.jj
java -classpath ../javacc.jar javacc CalculatorInt.jj
java -classpath ../javacc.jar javacc CalculatorBigInt.jj
javac Calculator.java
javac CalculatorTrig.java
javac CalculatorInt.java
javac CalculatorBigInt.java
