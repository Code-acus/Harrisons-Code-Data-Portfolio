#pragma once
#include <iostream>
#include "securityStudent.h"
#include "softwareStudent.h"
#include "networkStudent.h"
#include "roster.h"

void Roster::add(string studentID, string firstName, string lastName, string emailAddress, string age, int daysInCourse1, int daysInCourse2, int daysInCourse3, Degree deg)
{
	int days[3];
	days[0] = daysInCourse1;
	days[1] = daysInCourse2;
	days[2] = daysInCourse3;

	if (deg == SECURITY)
	{
		classRosterArray[lastIndex] = new SecurityStudent(studentID, firstName, lastName, emailAddress, age, days, deg);
	}

	else if (deg == NETWORK)
	{
		classRosterArray[lastIndex] = new NetworkStudent(studentID, firstName, lastName, emailAddress, age, days, deg);
	}

	else if (deg == SOFTWARE)
	{
		classRosterArray[lastIndex] = new SoftwareStudent(studentID, firstName, lastName, emailAddress, age, days, deg);
	}

	else
	{
		cout << "Degree type is invalid";
		exit(-1);
	}
}

void Roster::remove(string studentID)								// Added based on Part E Create a Roster class from section E:3.b
{
	bool found = false;
	for (int i = 0; i <= lastIndex; ++i)
	{
		if (studentID == classRosterArray[i]->getID())
		{
			found = true;
			delete classRosterArray[i];
			classRosterArray[i] = classRosterArray[lastIndex];
			lastIndex--;
			cout << "Student with ID: " << studentID << " was removed." << endl;
		}
	}
	if (!found)
	{
		cout << "Student with ID " << studentID << " was not found." << endl;
	}
}

void Roster::printAll()
{
	for (int i = 0; i <= lastIndex; ++i)
	{
		classRosterArray[i]->print();
	}
}

void Roster::printInvalidEmails()
{
	bool found = false;
	for (int i = 0; i <= lastIndex; ++i)
	{
		string eml = classRosterArray[i]->getEmailAddress();
		if (eml.find("@") == string::npos || eml.find(".") == string::npos || eml.find(" ") != string::npos)
		{
			found = true;
			cout << "Invalid email: " << eml << endl;
		}
	}

	if (!found)
	{
		cout << "No invalid emails. " << endl;
	}
}

void Roster::printAverageDaysInCourse(string id)
{
	for (int i = 0; i < 5; ++i)
	{
		if (classRosterArray[i]->getID() == id)
		{
			int* grades = classRosterArray[i]->getDaysToComplete();
			cout << "Average days in course for student " << id << ": ";
			cout << (grades[0] + grades[1] + grades[2]) / 3.0 << endl;
		}
	}
}

void Roster::printByDegreeProgram(Degree deg)
{
	for (int i = 0; i <= lastIndex; ++i)
	{
		if (classRosterArray[i]->getDegree() == deg)
		{
			classRosterArray[i]->print();
		}
	}
}

Roster::Roster()
{
	lastIndex = -1;
	for (int i = 0; i <= 4; ++i)
	{
		classRosterArray[i] = nullptr;
	}
}

Roster::~Roster()
{
	for (int i = 0; i <= lastIndex; ++i)
	{
		delete classRosterArray[i];
		classRosterArray[i] = nullptr;
	}
}

int main()
{
	cout << "Programmer's name: Harrison Rogers" << endl;
	cout << " " << endl;
	cout << "WGU Student ID: #000632898" << endl;
	cout << " " << endl;
	cout << "Course title is: C867 - Scripting and Programming" << endl;
	cout << " " << endl;
	cout << "The programmintg language is C++ using Visual Studio Express 2019" << endl;
	cout << " " << endl << endl;

	const string studentData[] =
	{	"A1,John,Smith,John1989@gm ail.com,20,30,35,40,SECURITY",
		"A2,Suzan,Erickson,Erickson_1990@gmailcom,19,50,30,40,NETWORK",
		"A3,Jack,Napoli,The_lawyer99yahoo.com,19,20,40,33,SOFTWARE",
		"A4,Erin,Black,Erin.black@comcast.net,22,50,58,40,SECURITY",
		"A5,Harrison,Rogers,hrogers@wgu.edu,28,465,565,665,SOFTWARE"};
	
	Roster* roster = new Roster();
	

	for (int i = 0; i < 5; ++i)
	{
		roster->lastIndex++;
		Degree degree = SECURITY;

		if (studentData[i].back() == 'Y') degree = SECURITY;
		else if (studentData[i].back() == 'K') degree = NETWORK;
		else if (studentData[i].back() == 'E') degree = SOFTWARE;

		int rhs = studentData[i].find(",");
		string sid = studentData[i].substr(0, rhs);
		
		int lhs = rhs + 1;
		rhs = studentData[i].find(",", lhs);
		string fname = studentData[i].substr(lhs, rhs - lhs);

		lhs = rhs + 1;
		rhs = studentData[i].find(",", lhs);		
		string lname = studentData[i].substr(lhs, rhs - lhs);

		lhs = rhs + 1;
		rhs = studentData[i].find(",", lhs);
		string email = studentData[i].substr(lhs, rhs - lhs);

		lhs = rhs + 1;
		rhs = studentData[i].find(",", lhs);
		string age = studentData[i].substr(lhs, rhs - lhs);

		lhs = rhs + 1;
		rhs = studentData[i].find(",", lhs);
		int d1 = stoi(studentData[i].substr(lhs, rhs - lhs));

		lhs = rhs + 1;
		rhs = studentData[i].find(",", lhs);
		int d2 = stoi(studentData[i].substr(lhs, rhs - lhs));

		lhs = rhs + 1;
		rhs = studentData[i].find(",", lhs);
		int d3 = stoi(studentData[i].substr(lhs, rhs - lhs));
	

		roster->add(sid, fname, lname, email, age, d1, d2, d3, degree);
	}

	roster->printAll();

	roster->printInvalidEmails();

	for (int i = 0; i < 5; ++i)
	{
		string id = roster->classRosterArray[i]->getID();
		roster->printAverageDaysInCourse(id);
	}
	cout << "Printing by software degree program" << endl;
	roster->printByDegreeProgram(SOFTWARE);
	roster->remove("A3");
	roster->remove("A3");

	return 0;
}

// ROSTER DESTRUCTOR IS CALLED AUTOMATICALLY AT THE END OF MAIN

