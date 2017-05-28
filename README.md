# AgE 3 Carbon Footprint

This repository contains an implementation of the [AgE3](https://gitlab.com/age-agh/age3)-compatible solver for 
optimizing Carbon Footprint. Technology stack: Java, Spring framework.

## Prerequisites
You need to have the following tools installed and configured:
 - Java 1.8
 - Gradle

## Installation and Commissioning

This repository is compatible with the `0.5` version of the [AgE3](https://gitlab.com/age-agh/age3) project, which is
not deployed yet. In order to run this repository, follow these steps:
 1. Clone the latest version of the [AgE3 develop branch](https://gitlab.com/age-agh/age3/tree/develop).
 2. Enter the main directory of cloned repository.
 3. Navigate to the `settings.gradle` file and append the following line:
    ```
    include 'AgE3-CarbonFootprint'
    ```
     Then close the text editor.
 4. Add this repository as a submodule and navigate to it:
    ```
    git submodule add https://github.com/bgrochal/AgE3-CarbonFootprint.git
    cd AgE3-CarbonFootprint
    ```
 5. Build and run the code included in this repository by executing:
    ```
    gradle node -PappArgs="['pl/edu/agh/footprint/age/carbon-footprint-config.xml,pl/edu/agh/footprint/age/carbon-footprint-config.properties']"
    ```

## Links

* [AgE 3 reference](https://www.age.agh.edu.pl/)
* [AgE 3 Repository on GitLab](https://gitlab.com/age-agh/age3)
