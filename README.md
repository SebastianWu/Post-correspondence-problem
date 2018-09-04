# Post correspondence problem

The POST CORRESPONDENCE PROBLEM is defined as follows: You are given a collection of dominos. Each domino has a string of characters on top and another string on the bottom. (Both strings are non-empty.) You can make as many copies of the dominos as you need. The problem is to place the dominos side by side so that the combination of the strings on the top is the same as the combination of the strings on the bottom.  

Author: Yuanxu Wu    

## Prerequisites  
1. Java 1.8.0  

## How to compile andrun this program:  
1. Put Domino.java and test.java in the same folder.  
2. open terminal and cd to this folder.  
3. Type: "javac test.java" to compile it.  
4. Type: "java test" to run it.  

## Input format:  
Just follow the instructions on the console.  
1. input max queue size.  
2. input max depth of BFS and iterative DLS.  
3. You won't need to input the name of Domino, it will generate automatically.  
4. input the top string of the Domino.   
5. input the bottom string of the Domino.  
6. if you want to continue add Domino, type "Y" then it will go back to step 4, or else, type "N".  
7. indicate the output states space or not, type "1" output states space, type "0" not output states space.  

## Example:  
5   
50  
bbb   
bb  
Y  
a   
bb  
Y  
bb   
bba  
N  
1  

  
