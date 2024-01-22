#pragma once
#include "degree.h"
#include "student.h"

class SoftwareStudent:public Student
{
public:
	SoftwareStudent(string studentID, string firstName, string lastName, string emailAddress, string age, int daysToComplete[], Degree deg);
	void print();
	Degree getDegree();
	~SoftwareStudent();
};

