#pragma once
#include <string>
#include "student.h"
using std::string;

class SecurityStudent :public Student
{
public:
	SecurityStudent(string studentID, string firstName, string lastName, string emailAddress, string age, int daysToComplete[], Degree deg);
	void print();
	Degree getDegree();
	~SecurityStudent();
};

