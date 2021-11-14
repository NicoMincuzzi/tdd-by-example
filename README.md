# TDD By Example
The goal is to cover all needed steps to apply TDD by Kent Beck and provide guide lines for the developers.

## Chapter 1
1. Write Unit Test
   
   1.1 Run test 
   
   1.2 **Test doesn't compile**
   

2. Use IDE to create `class`, `constructor`, `methods` and `fields`

   2.1 Run test
   
   2.2 Bingo! **Test fails**

**N.B.** Now we have a concrete measure of failure 


3. The smallest change in order that our test to pass


4. Remove **duplication**
   
   4.1 here the duplication is between the data in the test and the data in the code

   4.2 substitute the parameters `amount` and `multiplier` for the constants

## Chapter 2
5. Find eventual implementation error


6. Use `Value Objects` in order to use objects as value
  - In this case, you needn't worry about aliasing
  - all operations must return a new object
  - should implement `equals()`


7. Use ***Triangulation*** and generalize `equals()` and other methods that need it 

## Chapter 4 - Privacy
1. Defined *equality*, we can use it to make our tests more *"speaking"*
2. Dollar is now the only class using its amount instance variable, so we can make it private

## Chapter 5
1. We need to have an object like Dollar, but to represent francs
2. What short step will get us to a green bar? Copying the Dollar code and replacing Dollar with Franc

## Chapter 6

1. Now it is time to clean up
2. We are going to find a common superclass for the two class
  - Move the amount instance variable up to superclass and the visibility has to change from private to protected
  - Move up equals() and change the declaration of the temporary variable

## Chapter 7

1. What happens when we compare Francs and Dollars? It fails
2. The equality code needs to compare the class of the two objects

## Chapter 8
1. Reconcile the two implementations of time(), making them both return Money
2. Introduce a Factory method in superclass that returns subclasses

## Chapter 9
1. We may want to have complicated objects representing currencies, with flyweight factories to ensure we create no more objects than we really need
2. Store currency in an instance variable and push up declaration of the same and the implementation of currency()
3. Add parameter currency to the constructor
4. Move constant strings USD nad CHF to the static factory methods
5. The two constructors are now identical, so we can push up the implementation

## Chapter 10
1. Go backward to go forward: inline factory methods, use currency instance variable
2. Change Franc.times() and Dollar.times() to return Money, change Money to concrete class and improve equals()
3. Push up times() implementation

## Chapter 11
1. Dollar and Franc have only their constructors
2. A constructor is not reason enough to have a subclass, we want to delete the subclasses
3. We can replace references to the subclasses with references to the superclass without changing the meaning of the code
4. Delete duplicated tests