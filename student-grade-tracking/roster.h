#pragma once
#include <iostream>
#include <string>
#include "degree.h"
#include "student.h"
using namespace std;

class Roster
{
public:
	int lastIndex = -1;
	Student* classRosterArray[5] = {nullptr, nullptr, nullptr, nullptr, nullptr};
	void add(string studentID, string firstName, string lastName, string emailAddress, string age, int daysInCourse1, int DaysInCourse2, int DaysInCourse3, Degree deg);
	Roster();
	void remove(string studentID);						// Added for projec based on part E:3.b
		
		// Roster (string studentID, string firstName, string lastName, string emailAddress, string age, int daysToComplete[], Degree deg);
		// void print();
		// Roster getRoster();

	void printAll();
	void printInvalidEmails();
	void printAverageDaysInCourse(string id);
	void printByDegreeProgram(Degree deg);

	~Roster();
};

