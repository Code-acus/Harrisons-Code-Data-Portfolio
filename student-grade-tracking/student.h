#pragma once
#include <string>
#include "degree.h"
using std::string;
class Student
{
protected: 
	string studentID;
	string firstName;
	string lastName;
	string emailAddress;
	string age;
	int daysToComplete[3];
	Degree degree;
	
public:
	Student(string, string, string, string, string, int[3]);
	string getID();
	string getFirstName();
	string getLastName();
	string getEmailAddress();
	string getAge();
	int* getDaysToComplete();
	virtual Degree getDegree() = 0;
	void setID(string);
	void setFirstName(string);
	void setLastName(string);
	void setEmailAddress(string);
	void setAge(string);
	void setDaysToComplete(int[]);
	virtual void print() = 0;
	~Student();
};

