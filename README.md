# Recruitment task for Ocado Technology - Basket Splitter

## Description

This project is a simple implementation of a library for splitting products between carriers within shopping basket.
The main goal is to minimize the number of carriers used to deliver the products. 
The other goal is to make the carriers that offers th best range of products to deliver the most of the products in the basket.

## Dependencies used

- `Jackson` - working with JSON files
- `Lombok` - avoid boilerplate code(getters, setters, constructors, etc.)
- `JUnit` - unit testing

## Build the project

To build the project, run the following command in the root directory of the project:

### Linux / Mac
```bash
  ./gradlew clean build
```

### Windows
```bash
  gradlew.bat clean build
```

## Run the project

To run the project, run the following command in the root directory of the project:

### Linux / Mac / Windows
```bash
  java -jar build/libs/basket-splitter-1.0-SNAPSHOT.jar
```

## Usage

The project is a library, so it can be used in other projects.
The main class is `BasketSplitter` which has a method `splitBasket` that takes a list of products `List<String>` and returns products mapped to the  to  `Map<String, List<String>>`.
