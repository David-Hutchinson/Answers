import unittest

def main():
	unittest.main() 

def flatten(inputList):
	flattened = []
	for n in inputList:
		if type(n) is list:
			flattened.extend(flatten(n))
		else:
			flattened.append(n)

	return flattened

class flattenTest(unittest.TestCase):
	def testBaseCase(self):
		self.assertEquals(flatten([[1, 2, [3]], 4]), [1, 2, 3, 4])
	
	def testElementsAllLists(self):
		self.assertEquals(flatten([[1], [2], [3], [4]]), [1, 2, 3, 4])

main()