""" TEST CASES """
def test_cases_q1(func):
  print('    Question 1')
  total=0
  if func == 'hello world':
      print('q1t1: Test Passed!')
      total+=1
  else:
      print('q1t1: Test Failed')
      print('Expected: hello world')
      print('Got: ' + str(func))

  print('')         
  print(str(total/1 * 100) , '% Correct')

              
def test_cases_q2(func):
  print('    Question 2')
  total=0
  if func == 'balloonballoon':
      print('q2t1: Test Passed!')
      total+=1
  else:
      print('q2t1: Test Failed')
      print('Expected: balloon')
      print('Got: ' + str(func))

  print('')         
  print(str(total/1 * 100) , '% Correct')
