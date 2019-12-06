from typing import List


class Computer:
    def __init__(self, file):
        self.file = file
        self.memory = self.list_from_file(self.file)

    def reset(self):
        self.memory = self.list_from_file(self.file)

    def lex(self, noun, verb):
        self.memory[1] = noun
        self.memory[2] = verb

    def compute(self, noun, verb):
        self.reset()
        self.lex(noun, verb)
        head = 0
        while int(self.memory[head]) != 99:
            result = getattr(self, 'op'+str(self.memory[head]))(self.memory[self.memory[head+1]], self.memory[self.memory[head+2]])
            position = self.memory[head+3]
            self.memory[position] = result
            #print("Performed {operation} on values at {pos1} and {pos2} (values {val1}, {val2}). Saved value {result} to position {pos3}".format(
                #operation='op'+str(self.memory[head]), pos1=head+1, pos2=head+2, val1=self.memory[head+1], val2=self.memory[head+2], result=result, pos3 = position))
            head += 4
        return self.memory[0]

    def list_from_file(self, file):
        li: List
        with open(file) as inp:
            li = [int(item) for item in inp.read().split(',')]
        return li

    def op1(self, operand1, operand2):
        return operand1 + operand2

    def op2(self, operand1, operand2):
        return operand1 * operand2

def find_sentence(computer, output=19690720):
    sentence = 0
    answer = 0
    while answer != output and sentence < 10100:
        noun = int(sentence / 100)
        verb = int(sentence % 100)
        if computer.compute(noun, verb) == output:
            return sentence
        sentence += 1

