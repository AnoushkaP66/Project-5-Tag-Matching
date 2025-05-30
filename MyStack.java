/***
 * @author Anoushka Poojary
 * CMSC 256 - Intro to Data Structures Section 901
 * Project 5 - Tag Matching
 * @version 03/28/2025
 * This project implements the usage of the Node class and the Stack interface to check for matching HTML tags. The methods
 * in the Stack interface are used for a method that will process an HTML file and will check to see if the tags are properly
 * balanced. Exceptions and errors are accounted for in this project.
 */

package cmsc256;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.Scanner;

public class MyStack<E> implements StackInterface<E> {
    //Instance variable
    private Node<E> top;
    //Constructor
    public MyStack() {
        top = null;
    }

    /**
     * This method checks for balanced HTML tags in a file. It does this by processing each line in the file and when an opening
     * "<" is found, the index of the ">" character is then stored. Then the code checks if it is either an opening or closing tag,
     * which can be determined if there is a "/" character. If the tag starts with a slash, then it is a closing bracket. The closing
     * bracket is then checked if it matches the character in the opening bracket.
     * @param webpage the HTML file being checked for balanced tags
     * @return false if closing ">" is missing, there's no opening bracket, or if tags are mismatched; otherwise, true if
     * all tags are perfectly balanced and there's no errors
     * @throws FileNotFoundException if there is no file found
     * @throws EmptyStackException if stack is empty before operation
     */
    public static boolean isBalanced(File webpage) throws FileNotFoundException {
        MyStack<String> stack = new MyStack<>();
        //Try block with the Scanner allows for the scanner to close
        try (Scanner scanner = new Scanner(webpage)) {
            //Iterate through each line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    //Find the starting tag
                    if (line.charAt(i) == '<') {
                        //Get the index end tag (">")
                        int endTag = line.indexOf('>', i);
                        //Return false if there's no closing ">" found
                        if (endTag < 0) {
                            return false;
                        }
                        //Extract the character in between the "<>", next to the opening bracket
                        String tag = line.substring(i + 1, endTag);
                        //If there is a slash, then it could be a closing bracket
                        if (tag.startsWith("/")) {
                            //If the stack of opening brackets is empty, then there is an error
                            //False is returned
                            if (stack.isEmpty()) {
                                return false;
                            }
                            //If the stack is not empty, then the top opening tag is removed
                            String openingTag = stack.pop();
                            //Compares tag from beginning and the one popped from the stack
                            //Return false if the tags are mismatched (characters in opening and closing tags aren't the same)
                            if (!openingTag.equals(tag.substring(1))) {
                                return false;
                            }
                        }
                        //Add onto the stack if there is no slash
                        //It is an opening bracket
                        else {
                            stack.push(tag);
                        }
                        //The variable i is assigned endTag index before the next line is processed
                        i = endTag;
                    }
                }
            }
            //If the stack is empty and all the tags are balanced, then true is returned
            return stack.isEmpty();
        } catch (EmptyStackException e) {
            System.out.println("Error: Stack is empty");
            return false;
        }
    }

    /**
     * Adds a new entry to the top of this stack.
     * @param newEntry  An object to be added to the stack
     * @throws IllegalArgumentException when newEntry is null or empty
     */
    @Override
    public void push(E newEntry) {
        if (newEntry == null) {
            throw new IllegalArgumentException();
        }
        Node<E> newNode = new Node<>(newEntry);
        newNode.setNextNode(top);
        top = newNode;
    }

    /**
     * Removes and returns this stack's top entry.
     * @return the object on top of the stack
     * @throws EmptyStackException if stack is empty before operation
     */
    @Override
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        E topData = top.getData();
        top = top.getNextNode();
        return topData;

    }

    /**
     * Retrieves this stack's top entry.
     * @return The object at the top of the stack.
     * @throws EmptyStackException if stack is empty before operation
     */
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.getData();
    }

    /**
     * This method checks if the stack is empty by checking to see if there is a top element in the first place.
     * @return true if the stack is empty and false if not
     */
    @Override
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * This method removes all elements in the stack.
     */
    @Override
    public void clear() {
        top = null;
    }

    private static class Node<E>
    {
       private E    data; // Entry in stack
       private Node<E> next; // Link to next node

       private Node(E dataPortion)
       {
          this(dataPortion, null);
       }

       private Node(E dataPortion, Node<E> linkPortion)
       {
          data = dataPortion;
          next = linkPortion;
       }

       private E getData()
       {
          return data;
       }

       private void setData(E newData)
       {
          data = newData;
       }

       private Node<E> getNextNode()
       {
          return next;
       }

       private void setNextNode(Node<E> nextNode)
       {
          next = nextNode;
       }
    }
}
