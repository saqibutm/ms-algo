% Clears screen and deletes all the variables in the workspace
clear; clc

% Asks the user for input and takes only positive numbers into account
a = input('First number: ');
b = input('Second number: ');
a = abs(a);
b = abs(b); 

 % This is the real trick, normally performed a number of times
r = a - b*floor(a/b); 

% Repeats the operation until updates of a equal updates of b
while (r ~= 0)
    a = b;
    b = r;
    r = a - b*floor(a/b);
end 

% Displays the result
GCD = b