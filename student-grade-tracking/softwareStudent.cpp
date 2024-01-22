#include <iostream>
#include "degree.h"
#include "student.h"
#include "softwareStudent.h"
using std::cout;
using std::endl;


SoftwareStudent::SoftwareStudent(string studentID, string firstName, string lastName, string emailAddress, string age, int daysToComplete[], Degree deg):Student(studentID, firstName, lastName, emailAddress, age, daysToComplete)
{
	degree = deg;
}

void SoftwareStudent::print()
{
	this->Student::print();
	cout << "SOFTWARE" << endl;
}

Degree SoftwareStudent::getDegree()
{
	return SOFTWARE;
}

SoftwareStudent::~SoftwareStudent()
{
}
