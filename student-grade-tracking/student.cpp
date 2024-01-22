#include <iostream>
#include "student.h"



using std::cout;


Student::Student(string studentID, string firstName, string lastName, string emailAddress, string age, int daysToComplete[3])
{

	this->studentID = studentID;
	this->firstName = firstName;
	this->lastName = lastName;
	this->age = age;
	this->emailAddress = emailAddress;
	for (int i = 0; i < 3; ++i)
	{
		this->daysToComplete[i] = daysToComplete[i];
	}

}

void Student::print()
{
	cout << studentID << "\t";
	cout << firstName << "\t";
	cout << lastName << "\t";
	cout << emailAddress << "\t";
	cout << age << "\t";
	cout << daysToComplete[0] << "\t";
	cout << daysToComplete[1] << "\t";
	cout << daysToComplete[2] << "\t";
}


string Student::getID()
{
	return studentID;
}

string Student::getFirstName()
{
	return firstName;
}

string Student::getLastName()
{
	return lastName;
}

string Student::getEmailAddress()
{
	return emailAddress;
}

string Student::getAge()
{
	return age;
}

int* Student::getDaysToComplete()
{
	return daysToComplete;
}

void Student::setID(string studentID)
{
	this->studentID = studentID;
}

void Student::setFirstName(string firstName)
{
	this->firstName = firstName;
}

void Student::setLastName(string lastName)
{
	this->lastName = lastName;
}

void Student::setEmailAddress(string emailAddress)
{
	this->emailAddress = emailAddress;
}

void Student::setAge(string age)
{
	this->age = age;
}

void Student::setDaysToComplete(int daysToComplete[])
{
	for (int i = 0; i < 3; ++i)
	{
		this->daysToComplete[i] = daysToComplete[i];
	}
}

Student::~Student()
{
}
