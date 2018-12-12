#include "myDrink.h"
#include <iostream>
#include <string>
#include <time.h>  // Could reduce runtime by preincluding SD value of non-custom drinks
#include <iomanip> // adds setprecision function so full value of times can be printed
#include <fstream>

myDrink::myDrink(int hasRun) {
    if (hasRun==0) {
        ofstream myfile2(path);
        myfile2.close();
    }
    ifstream myfile(path);
    if (myfile.is_open()) {
        if (myfile.peek() != ifstream::traits_type::eof()) {
            myfile >> gender;
            myfile >> weight;
            myfile >> currentDrink[1];
            myfile >> currentDrink[0];
            myfile >> consumedDrinks;
            myfile >> time_start;
            myfile >> time_lastDrink;
        }
        myfile.close();
    }

    theEraser();
}

// Sets user weight and gender
string myDrink::setUser(int userGender, double userWeight) {
    gender = userGender;
    weight = userWeight;
    return "you're all set";
}

// Stores user input into currentDrink
// currentDrink[0] is ABV (percent) and currentDrink[1] is volume (mL)
string myDrink::setDrink(double ABV, double thevolume) {
    currentDrink[0] = ABV;
    currentDrink[1] = thevolume; // In mL
    return to_string(weight);
}

// Returns the hours since 00:00, Jan 1, 1970 (i.e. 1.23 hours)
double myDrink::timeCalc() {
    time_t mytimer;
    time(&mytimer);  // Gets current time and stores in mytimer
    double thetime = mytimer;
    thetime = thetime/3600; // Converts from seconds to hours
    return thetime;
}

// Returns how many standard drinks in selected beverage
double myDrink::standardDrink() {
    double alcWeight;
    alcWeight = (currentDrink[0]/100)*currentDrink[1]*0.7893; // 0.7893 is g/mL of ethanol (alcohol)
    return (alcWeight/10.0);
}

// Adds standard drinks consumed to consumedDrinks and updates time_lastdrink
string myDrink::addDrink() {
    double time_current;
    if (consumedDrinks==0) { // Stores start time of alcohol consumption
        time_start = timeCalc();
    }
    time_current = timeCalc();
    time_lastDrink = time_current - time_start;
    if (time_lastDrink<0.000001) {
        time_lastDrink = 0;
    }
    consumedDrinks = consumedDrinks + standardDrink();
    return to_string(time_current);
}

// Calculates blood alcohol content
string myDrink::CalcMyBAC() {
    double myBAC;
    string thenewbac;
    double time_current,time_elapsed;
    time_current = timeCalc();
    time_elapsed = time_current - time_start;
    if (time_elapsed<0.000001) {
        time_elapsed = 0;
    }
    double waterConst;
    double metabolism;
    if (gender==0) {        // If user is female
        waterConst = 0.49;  // body water constant is 0.49
        metabolism = 0.017; // metabolism constant is 0.017
    }
    else {                  // If user is male
        waterConst = 0.58;  // body water constant is 0.58
        metabolism = 0.015; // metabolism constant is 0.015
    }
    myBAC = ((0.806*consumedDrinks*1.2)/((weight/2.205)*waterConst))-(metabolism*time_elapsed);
    thenewbac = to_string(myBAC);
    return thenewbac;
}

void myDrink::theWriter() {
    double ABV = currentDrink[0];
    double myvolume = currentDrink[1];
    ofstream myfile(path);
    if (myfile.is_open()) {
        myfile << setprecision(12);
        myfile << gender << endl;
        myfile << weight << endl;
        myfile << myvolume << endl;
        myfile << ABV << endl;
        myfile << consumedDrinks << endl;
        myfile << time_start << endl;
        myfile << time_lastDrink << endl;
        myfile.close();
    } else {
        cout << "Unable to open file";
    }
}

void myDrink::theEraser(){
    ofstream myeraser;
    myeraser.open(path, std::ofstream::out | std::ofstream::trunc);
    myeraser.close();
}

void myDrink::newSession(){
    time_start = 0;
    time_lastDrink = 0; // Time (hours) since last drink
    currentDrink[0] = 0.0;
    currentDrink[1] = 0.0;
    consumedDrinks = 0;
}