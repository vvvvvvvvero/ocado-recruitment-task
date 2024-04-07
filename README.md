# Recruitment task for Ocado Technology - Basket Splitter

## Description

This project is a simple implementation of a library for splitting products between carriers within shopping basket.
The main class is `BasketSplitter` which has a method `splitBasket` that takes a list of products `List<String>`
and returns carriers mapped to the list of products `Map<String, List<String>>`.

### Objectives

1. **Minimize the Number of Carriers**: The optimizer seeks to reduce the total number of carriers utilized for delivering products.

2. **Optimize Carrier Product Range Utilization**: It also aims to ensure that carriers offering the best selection of products are preferred for deliveries.


### Implementation Details

The `ShoppingCartOptimizer` is the main class for computing result. It is instantiated with the number of products and carriers, a map specifying which carriers are allowed for each product, and a scoring system for carriers.
It then computes the optimal distribution of products to carriers.
The class uses a Dynamic Programming approach to solve the problem. It computes the optimal distribution of products to carriers by iterating over all possible combinations of products and carriers.
The `DPState` class is used to store the state of the dynamic programming algorithm. It stores the `count`,
which is representing minimal number of carrier used so far `usedColumns` boolean array, which is representing the carriers used so far and `score` which is representing the score of the current state, which is determined by the rank of the carrier.

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

### Project structure

To include a local JAR file in a Gradle project place it in a libs directory at the root level of your project.

```
.
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── libs  <--- You should create this directory
│   └── your-library.jar  <--- Place your JAR file here
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── github
    │   │           └── Main.java
    │   └── resources
    └── test
        ├── java
        └── resources


```

### build.gradle

In your build gradle file add the following dependencies and repositories:

```
dependencies {
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
    implementation('org.example:ocado-task')

}

repositories {
    flatDir {
        dirs 'libs'
    }
    // Include other repositories here if necessary
}
```

### Refresh Gradle project

```bash
  ./gradlew clean build
```

### Example usage

```
BasketSplitter basketSplitter = new BasketSplitter("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/config.json");
List<String> products = basketSplitter.getFileService().readCartFile("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/basket-1.json");
System.out.println(basketSplitter.split(products));
```




