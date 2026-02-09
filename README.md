# Design Patterns Sudoku

A modular, industry-standard implementation of the Sudoku game engineered to demonstrate strict **MVC architecture** and advanced **Gang of Four (GoF)** design patterns.

## Technical Overview

### 1. Architecture: Strict MVC
The application enforces a rigid separation of concerns using **Facade Interfaces** to decouple layers:
* **Model (Logic):** Encapsulates the 9x9 grid, rules, and validation engine.
* **View (UI):** Observes the model via the `Viewable` interface (read-only access).
* **Controller:** Processes user input via the `Controllable` interface, preventing direct mutation of the internal state.

### 2. Design Patterns Implemented

#### A. Creational Patterns
* **Factory Method:** Used to abstract the board generation process. The `PuzzleFactory` dynamically creates different board configurations based on the selected difficulty (Easy, Medium, Hard).
* **Singleton Pattern:** Ensures that the `GameEngine` has only one active instance throughout the application lifecycle.

#### B. Structural Patterns
* **Flyweight Pattern:** Optimizes heap memory during the recursive solving process. Instead of deep-copying the board for every permutation, the solver shares common state (immutable cell references) between instances.

#### C. Behavioral Patterns
* **Command Pattern:** Encapsulates every user move as an object, enabling a stack-based **Undo/Redo** system.
* **Iterator Pattern:** Implements a custom iterator for "lazy evaluation," traversing board coordinates without exposing the internal array structure.



## Functional Specifications

### Core Features
* **Backtracking Solver:** A recursive algorithm capable of resolving any valid incomplete board.
* **Difficulty Generator:** Algorithmic generation of unique puzzles via the Factory implementation.
* **Move History:** Stack-based Undo/Redo functionality powered by the Command pattern.
* **Persistence:** Support for serializing board states to disk and loading from CSV.

### Game Logic
* **Incomplete State Handling:** Detects and manages "empty" cells (zeros) and prevents validation until filled.
* **Sequential Verification:** Uses a highly optimized single-threaded algorithm for real-time board validation.

## Contributors
* Mohamed Bahig
* Sara Hany
* Hayat Tarek
* Shaden Rafik
