#pragma once
#include "degree.h"
#include "student.h"
#include "networkStudent.h"


NetworkStudent::NetworkStudent(string studentID, string firstName, string lastName, string emailAddress, string age, int daysToComplete[], Degree deg):Student(studentID, firstName, lastName, emailAddress, age, daysToComplete)
{
	degree = deg;
}

NetworkStudent::~NetworkStudent()
{
}

void NetworkStudent::print()
{
	this->Student::print();
	cout << "NETWORK" << endl;
}

Degree NetworkStudent::getDegree()
{
	return NETWORK;
}

