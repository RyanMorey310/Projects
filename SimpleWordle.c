#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "utils.h"
#define TRUE 1
#define FALSE 0
#define ALPHABET_SIZE 26
//Name:         Ryan Morey
//Student ID:   R00218530
//Class Group:  CS2

//Function for the task of reading in a guess
//whether its capital letters of lowercase.
void makeLower(char* change) {
    int i = 0;
    while(i<=WORD_SIZE) {
        if(change[i] >= 'A' && change[i] <= 'Z') {
            change[i] += 32;
        }
        i++;
    }
}

//Function for copying a string to another string.
void copyString(char* copy, char* to) {
    int i = 0;
    while(i < WORD_SIZE+1) {
        to[i] = copy[i];
        i++;
    }
}

//Function for getting the length of a string.
int getLength(char* word) {
    int length =0;
    int i = 0;
    while(i <= WORD_SIZE) {
        if (word[i] == '\0') {
            length = i;
            break;
        }
        i++;
    }
    return length;
}

//Function for find a certain character in a string.
char* findChar(char* string, int character) {
    while(*string != '\0') {
        if (*string == character) {
            return (char*) string;
        }
        string++;
    }
    if (character == '\0') {
        return (char*) string;
    }
    return NULL;
}

//Function for comparing 2 strings against each other.
//Used for identifying the guess and answer accuracy and 
//if the user guessed it correctly.
int compare(const char* firstString, const char* secondString) {
    while(*firstString != '\0' || *secondString != '\0') {
        if(*firstString != *secondString) {
            return (*firstString - *secondString);
        }
        firstString++;
        secondString++;
    }
    return 0;
}

int main() {
    //Used to ask the user to play again.
    int repeat = 1;

    //Variables for statistics
    int letterFreq[ALPHABET_SIZE] = {0};
    int totalGames = 0;
    int totalGuesses = 0;
    int totalCorrect = 0;
    float winPercentage = 0.0;
    float letterPercentage = 0.0;

    //Loop asking if the user wants to use hardmode
    int hardmode = 2;
    do {
    printf("Before we begin, would you like to play in hard mode? 1(yes)0(no):");
    scanf("%d", &hardmode);
    if ( hardmode != 1  && hardmode != 0) {
        printf("Please enter either 1 or 0.");
    }
    } while(hardmode != 1 && hardmode != 0);

    //Main do while loop for the game
    do {
        //Variables for dictionary, game answer, and user guess
        char dictionary[DICT_SIZE][WORD_SIZE+1];
        char answer[WORD_SIZE+1];
        char guess[WORD_SIZE+1];
        
        //word bank variable
        char guessedLetters[WORD_SIZE+1] = ""; 
        
        //Variables for the index of the word in dictionary
        // i global for for loops etc.
        // j global for for loops etc.
        // numGuesses to keep track of guesses, user has 6
        int wordIndex, i, j, numGuesses;

        //Variable for user's guess accuracy and progress of the user's guesses
        float accuracy;
        char progress[WORD_SIZE+1];

        //Getting the answer randomly from the dictionary
        load_word_list(dictionary);
        numGuesses = 0;
        //Using time as a random seed
        srand(time(NULL));
        //Word index variable being used
        wordIndex = rand() % DICT_SIZE;
        copyString(dictionary[wordIndex], answer);

        //Progress being initialized with dashes
        for (i = 0; i < WORD_SIZE; i++) {
            progress[i] = '-';
        }
        progress[WORD_SIZE] = '\0';

        //Starting the game
        printf("Can you guess the %d letter word?\n", WORD_SIZE);
        do {
            //Output the current user guess progress
            printf("Progress %s\n", progress);

            do {
                //Take user input as guess
                printf("\nEnter guess %d: ", numGuesses+1);
                scanf("%s", guess);
                makeLower(guess);

                //Checking guess size
                if (getLength(guess) != WORD_SIZE) {
                    printf("Your guess isn't %d letters long.\n", WORD_SIZE);
                }
                //Hardmode check for a letter that was already entered
                else if (hardmode && findChar(guessedLetters, guess[0]) != NULL) {
                   printf("You entered a letter you already guessed (hardmode)\n");
                }
                //Checking if the user correctly entered a guess from the dictionary
                else {
                    for (i = 0; i < DICT_SIZE; i++) {
                        if (compare(dictionary[i], guess) == 0) {
                            break;
                        }
                    }
                    if (i == DICT_SIZE) {
                        printf("Your guess is not in the dictionary, guess again.\n");
                    }
                }
             } while(getLength(guess) != WORD_SIZE || i == DICT_SIZE || (hardmode && findChar(guessedLetters, guess[0])!= NULL));
            // This is for the already guessed letter hardmode feature but its not working correctly
            // I didn't go about this right, I should've used a different way to store the letters
            // Instead of using guessedLetter (letter bank)
            if (hardmode) {
                guessedLetters[getLength(guessedLetters)] = guess[0];
                guessedLetters[getLength(guessedLetters)+1] = '\0';
            }
            //For loop for the Letter frequency statistic which is also not working properly
            for (i = 0; i < DICT_SIZE; i++) {
                int letterIndex = guess[i] - 'a';
                if (letterIndex >= 0 && letterIndex < ALPHABET_SIZE) {
                    letterFreq[letterIndex] += 1;
                }
            }

            //Checking the accuracy of the guess (% of correct letters in the correct position)
            accuracy = 0.0;
            for(i = 0; i < WORD_SIZE; i++) {
                if (guess[i] == answer[i]) {
                    accuracy += 1.0;
                    //Progress gets updated if the user guess a letter correctly
                    progress[i] = answer[i];  
                    //This is meant to remove letters from the letter bank if correctly guessed
                    //It works but can be a bit dodgy at times
                    for (j = 0; j < getLength(guessedLetters); j++) {
                        if (guessedLetters[j] == guess[i]) {
                        for (int k = j; k < getLength(guessedLetters); k++) {
                            guessedLetters[k] = guessedLetters[k+1];
                        }
                        guessedLetters[getLength(guessedLetters)-1] = '\0';
                        break;
                        }
                    }
                }
                //Else if for when the letter is in the answer but not in the correct position
                //Adds it to the word bank
                else if (findChar(answer, guess[i])!= NULL) {
                    //If letter is not in the word bank yet
                    if (findChar(guessedLetters, guess[i]) == NULL) {
                        //Letter is added to the letter bank then the letter bank length is increased 
                        guessedLetters[getLength(guessedLetters)] = guess[i]; 
                        guessedLetters[getLength(guessedLetters)+1] = '\0';
                    }
                }
            }

            //Using accuracy to see if the guess is correct
            accuracy = accuracy / WORD_SIZE;

            if (accuracy == 1.0) {
                printf("You guessed the word!\n");
                totalCorrect += 1;
                break;
            }
            else if (numGuesses == 5 ) {
                printf("Thank you for playing!\n");
                printf("The answer was: %s\n", answer);
            }
            else {
                printf("Your guess %d accurate.\n", (int)(accuracy *100));
                printf("Your letter bank(letters that are in the answer): %s\n\n", guessedLetters);
            }
            numGuesses += 1;
            
         } while (numGuesses != 6);
        totalGames += 1;
        totalGuesses += numGuesses;
        printf("Play again? 0 for no, 1 for yes: ");
        scanf("%d", &repeat);

    } while (repeat != 0);

    float totalLetterPer = 0.0;
    for (int i = 0; i <ALPHABET_SIZE; i++) {
        if (letterFreq[i]>0) {
            totalLetterPer += ((float) letterFreq[i] /totalGames);
        } 
    }

    letterPercentage = totalLetterPer * 100 /ALPHABET_SIZE;
    winPercentage = (totalCorrect /totalGames) * 100;
    printf("Statistics Report\n");
    printf("------------------\n");
    printf("Win Percentage: %.2f\n", winPercentage);
    printf("Average percentage of letters used: %.2f\n", letterPercentage);
    return 0;
}