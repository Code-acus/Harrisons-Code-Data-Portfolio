#pragma once
#include <iostream>
#include "degree.h"
using namespace std;

class NetworkStudent:public Student
{
public:
	NetworkStudent(string studentID, string firstName, string lastName, string emailAddress, string age, int daysToComplete[], Degree deg);
	~NetworkStudent();
	void print();
	Degree getDegree();
};

