1. Write Unit Test
   
   1.1 Run test 
   
   1.2 Test doesn't compile
   
2. Use IDE to create `class`, `constructor`, `methods` and `fields`

   2.1 Run test
   
   2.2 Test fails

3. The smallest change in order that our test to pass

4. Remove **duplication**
   
   4.1 here the duplication is between the data in the test and the data in the code

   4.2 substitute the parameters `amount` and `multiplier` for the constants

5. Find eventual implementation error

6. Use `Value Objects` in order to use objects as value
  - In this case, you needn't worry about aliasing
  - should implement `equals()`

7. Use ***Triangulation*** and generalize `equals()` and other methods that need it 