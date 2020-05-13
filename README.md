# Patta Android App

Patta Android App is an Android App for Patta App Project assigned by DSC VU. Basically Patta is a location sharing app. It will generate a unique 10 digit code for every building which will further be used to share location via sharing that 10 digit code in the form of a QR Code. So in simple, a user can generate QR of his location, other can scan that QR and get his exact pin location.

## Tools

- Android Studio
- Java
- Firebase

## Installation

- Clone this project to your computer.
- Open project in Android Studio.


## Directory Structure

|Directory       |Purpose                          
|----------------|-------------------------------
|ui              |It contains all UI code including activities, fragments, their relevant viewmodels, repositories & listeners
|viewmodels      |Its functions are get called by UI and it further calls relevant repository class' function.
|repositories    |These are classes which contain business logic and its methods are called by viewmodels
|listeners       |These are interfaces which are used for callbacks.
|database        |It contains ROOM database to have some data locally.   
|models          |It contains classes to get interaction with Database/API
|utils           |It contains helping classes
|worker          |It contains WorkManagers to work in background.

## Note

Make sure to make debuggable false in Gradle Module before publishing app to PlayStore.

```
buildTypes {
        release {
            debuggable false
        }
    }
```