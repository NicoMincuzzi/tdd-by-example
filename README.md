# TDD By Example
The goal is to cover all needed steps to apply TDD how suggest by Kent Beck in [Test Driven Development: By Example](https://www.amazon.com/Test-Driven-Development-Kent-Beck/dp/0321146530) and provide guide lines for the developers.

## Chapter 1
1. Write Unit Test
   1. Run test
   2. **Test doesn't compile**


2. Use IDE to create `class`, `constructor`, `methods` and `fields`

   1. Run test
   2. Bingo! **Test fails**

   **N.B.** Now we have a concrete measure of failure. 


3. The smallest change in order that our test to pass
   ```java
    public int amount = 10;
   ```

4. Remove **duplication** between the data in the test and the data in the code.
   
   1. Remove duplication, moving the setting of the `amount` from object initialization to the `times()` method.
   
   ```java
    public void times(int multiplier) {
        amount = 5 * 2;
    }
   ```

   4.2 substitute the parameters `amount` and `multiplier` for the constants

## Chapter 2
1. Find eventual implementation error

   ```java
    @Test
    public void testMultiplication(){
        Dollar five = new Dollar(5);
        five.times(2);
        assertEquals(10, five.amount);
        five.times(3);
        assertEquals(15, five.amount);
    }
   ```

   **N.B.** Test fails! After the first call to `times()` five isn't five aby more, it's really ten.


2. We return a new object from `times()`, then we can multiply our original `amount` and never have it change.

## Chapter 3 - Equity for All
Use `Value Objects` in order to use objects as value. The values of the instance variables of the object never change once they have been set in the constructor.
  - You needn't worry about *aliasing*.
  - All operations must return a new object.
  - Should implement `equals()`.
  - Use ***Triangulation*** and generalize `equals()` and other methods that need it.
    - We generalize code when we have two examples or more. 

## Chapter 4 - Privacy
1. Defined *equality*, we can use it to make our tests more *"speaking"*.
   1. `Dollar.times()` should return `Dollar`
   2. Rewrite assertions to compare `Dollar`s to `Dollar`s
2. `Dollar` is now the only class using its amount instance variable, so we can make it private

## Chapter 5 - Franc-ly Speaking
1. We need to have an object like `Dollar`, but to represent francs.
2. What short step will get us to a green bar? Copying the `Dollar` code and replacing `Dollar` with `Franc`

## Chapter 6 - Equality for All, Redux
We are going to find a common superclass for the two class.
1. Create `Money` class as superclass. 
2. Move the `amount` instance variable up to superclass and the visibility has to change from `private` to `protected. 
3. Move up `equals()` and change the declaration of the temporary variable

## Chapter 7 - Apples and Oranges
1. What happens when we compare `Francs` and `Dollars`? It fails!
2. The equality code needs to compare the class of the two objects.

## Chapter 8
1. Reconcile the two implementations of `time()`, making them both return `Money`.
2. Introduce a **Factory method** in superclass that returns subclasses.
3. Make `Money` abstract and declare `Money.times()`.

> No client code knows that there is a subclass called `Dollar` or `Franc`!

## Chapter 9
We may want to have complicated objects representing *currencies*, with flyweight factories to ensure we create no more objects than we really need.
1. Declare `currency()` in `Money`.
   ```java
   abstract String currency();
   ```
   1. Implement it in both subclasses.
2. We want the same implementation for both classes.
   1. Store `currency` in an instance variable.
   ```java
   private String currency;
   public Dollar(int amount) {
        this.amount = amount;
        currency = "USD";
    }
    public String currency() {
        return currency;
    }
   ```
   ```java
   private String currency;
   public Franc(int amount) {
        this.amount = amount;
        currency = "CHD";
    }
    public String currency() {
        return currency;
    }
   ```
   2. Push up declaration of the variable and the implementation of `currency()`.
   ```java
   protected String currency;

   String currency() {
        return currency;
   }
   ```
3. Add parameter `currency` to the constructor.
4. Move constant strings **`USD`** and **`CHF`** to the static factory methods.
   1. `times()` is calling the constructor. Clean up `times()` calling the factory method
5. The two constructors are now identical, so we can push up the implementation.

## Chapter 10
1. The two implementations of `times()` are close, but not identical.
   1. We have to go backward to go forward: 
      - inline factory methods
      - use `currency` instance variable.
      ```java
      public Money times(int multiplier) {
            return new Dollar(this.amount * multiplier, this.currency);
      }
      ```
      ```java
      public Money times(int multiplier) {
            return new Franc(this.amount * multiplier, this.currency);
      }
      ```
2. Change `Franc.times()` and `Dollar.times()` to return `Money`.
   ```java
    public Money times(int multiplier) {
        return new Money(amount * multiplier, this.currency);
    }
   ```
3. Change `Money` to concrete class and improve `equals()`.
4. Now the two implementations are identical. Push up `times()` implementation.

## Chapter 11 - The Root of All Evil
1. `Dollar` and `Franc` have only their constructors.
   1. A constructor is not reason enough to have a subclass, we want to delete the subclasses!
2. We can replace references to the subclasses with references to the superclass without changing the meaning of the code.
3. Delete duplicated tests.

## Chapter 12 - Addition, Finally
1. Add a new feature about *addition*.
   1. Start with a simple example
   ```java
    @Test
    public void testSimpleAddition() {
        Money sum = Money.dollar(5).plus(Money.dollar(5));
        assertEquals(Money.dollar(10), sum);
    }
   ```
   2. The implementation will be:
   ```java
    public Money plus(Money added) {
        return new Money(amount + added.amount, currency);
    }
   ```
2. How are we going to represent multi-currency arithmetic?
   1. The solution is to create un object that acts like a `Money` but represents the sum of two `Money`s.
   2. A metaphor is *expression*
      1. where `Money` is the atomic form of an expression.
      2. Operations result in `Expression`s, one of which will be a *Sum*.
   3. Apply this metaphor to our test
   ```java
   @Test
   public void testSimpleAddition() {
        ...
        assertEquals(Money.dollar(10), reduced);
   }
   ```
   4. The `reduced Expression` is created by applying exchange rates to an `Expression`. What in the real worl applies exchange rates? A bank
   ```java
   @Test
   public void testSimpleAddition() {
        ...
        Bank bank = new Bank();
        Money reduced = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(10), reduced);
   }
   ```
   5. The sum of two `Money`s should be an `Expression`
   ```java
   @Test
   public void testSimpleAddition() {
        ...
        Expressions sum = five.plus(five);
        Bank bank = new Bank();
        Money reduced = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(10), reduced);
   }
   ```
   6. We need an interface `Expression`.
   7. `Money.plus()` needs to return an `Expression`.
   8. We need a `Bank` class which fakes the implementation of `reduce()` method.

## Chapter 13 - Make It
1. `Money.plus()` needs to return a real `Expression` - `Sum`, not just a `Money`.
   ```java
   @Test
   public void testPlusReturnsSum() {
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        assertEquals(five, sum.augend);
        assertEquals(five, sum.addend);
   }
   ```
2. Create `Sum` class with two fields (`augend` and `addend`), a constructor and needs to be a kind of `Expression`.
3. Now `Bank.reduce()` is being passed a `Sum`
   ```java
   @Test
   public void testReduceSum() {
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(7), result);
   }
   ```
4. Implement `Bank.reduce()`
   ```java
    public Money reduce(Expression source, String to){
        Sum sum = (Sum) source;
        int amount = sum.augend.amount +  sum.addend.amount;
        return new Money(amount, to);
    }
   ```
   - The cast. This code should work with any `Expression`.
   - The public fields, and two levels of references at that.
5. Move the body of the method `Bank.reduce()` to `Sum` and get rid of some of visible fields.
6. How are we going to test/implement `Bank.reduce()` when the argument is a money?
   ```java
   @Test
   public void testReduceMoney() {
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1), "USD");
        assertEquals(Money.dollar(1), result);
   }
   ```
   1. Implement `Bank.reduce()`
   ```java
    public Money reduce(Expression source, String to){
        if(source instanceof Money) return (Money) source;
        Sum sum = (Sum) source;
        return sum.reduce(to);
    }
   ```
   > Any time we are checking classes explicitly, we should be using polymorphism instead!
   2. `Money` implements `reduce()` and add `reduce(String)`to `Expression`interface. Then Eliminate all casts and class checks.