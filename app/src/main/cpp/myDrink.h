#ifndef ALCONTROLV2_MYDRINK_H
#define ALCONTROLV2_MYDRINK_H
#include <string.h>
#include <iostream>

using namespace std;

class myDrink {
  private:
    const char *path = "/data/user/0/com.example.luke.alcontrolv2/files/file.txt";
    double gender; // Defaults to female so inherently overestimates BAC instead of underestimating
    double weight; // In pounds
    double time_start = 0;
    double time_lastDrink = 0; // Time (hours) since last drink
    double currentDrink[2] = {0.0,0.0};
    double consumedDrinks = 0;
  public:
    myDrink(int hasRun);

    void newSession();

    // Sets user weight and gender
    string setUser(int userGender, double userWeight);

    // Stores user input into currentDrink
    // currentDrink[0] is ABV (percent) and currentDrink[1] is volume (mL)
    string setDrink(double ABV, double thevolume);

    // Returns the hours since 00:00, Jan 1, 1970 (i.e. 1.23 hours)
    double timeCalc();

    // Returns how many standard drinks in selected beverage
    double standardDrink();

    // Adds standard drinks consumed to consumedDrinks and updates time_lastdrink
    string addDrink();

    // Calculates blood alcohol content
    string CalcMyBAC();

    void theWriter();

    void theEraser();
};

#endif //ALCONTROLV2_MYDRINK_H
