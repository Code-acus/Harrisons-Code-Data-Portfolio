#pragma once
#include <iostream>
#include "degree.h"
#include "securityStudent.h"
#include "student.h"
using std::cout;
using std::endl;


SecurityStudent::SecurityStudent(string studentID, string firstName, string lastName, string emailAddress, string age, int daysToComplete[], Degree deg) :Student(studentID, firstName, lastName, emailAddress, age, daysToComplete)
{
	degree = deg;
}

void SecurityStudent::print()
{
	this->Student::print();
	cout << "SECURITY" << endl;
}

Degree SecurityStudent::getDegree()
{
	return SECURITY;
}

SecurityStudent::~SecurityStudent()
{
}

