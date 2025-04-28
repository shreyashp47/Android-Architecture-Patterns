# Android Architecture Patterns

This project demonstrates the implementation of four major Android architecture patterns using a simple task management application. Each module showcases a different architectural approach to help developers understand the differences, advantages, and use cases for each pattern.

## About the Project

This project contains four separate modules, each implementing the same task management functionality using a different architecture pattern:
- MVC (Model-View-Controller)
- MVP (Model-View-Presenter)
- MVVM (Model-View-ViewModel)
- MVI (Model-View-Intent)

All modules share a common data model and similar UI, allowing for direct comparison of how the same functionality is implemented across different architectural approaches.

## Architecture Patterns Explained

### MVC (Model-View-Controller)
The traditional pattern where the Controller acts as the mediator between Model and View.

**Key Characteristics:**
- **Model**: Represents data and business logic
- **View**: UI elements that display data to the user
- **Controller**: Handles user input, updates the Model, and refreshes the View

**Advantages:**
- Simple and easy to understand
- Quick to implement for small applications

**Disadvantages:**
- Controllers often become bloated with logic
- Testing is difficult due to tight coupling
- UI logic mixed with business logic


### Project Structure MVC
```
mvc/
├── adapter/
│   └── TaskAdapter.kt
├── model/
│   ├── Task.kt
│   └── TaskRepository.kt
├── MVCMainActivity.kt
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   └── item_task.xml
```

---

### MVP (Model-View-Presenter)
An evolution of MVC where the Presenter handles the UI logic and updates the View.

**Key Characteristics:**
- **Model**: Represents data and business logic
- **View**: Passive interface that displays data and routes user events to the Presenter
- **Presenter**: Acts upon the Model and the View, retrieves data from the Model, and formats it for display in the View

**Advantages:**
- Better separation of concerns
- Improved testability through interfaces
- Reduced activity/fragment code

**Disadvantages:**
- Requires more interfaces and classes
- Can lead to tight coupling between View and Presenter


### Project Structure MVP
```
mvp/
├── contract/
│   └── MainContract.kt (View + Presenter interfaces)
├── model/
│   ├── Task.kt
│   └── TaskRepository.kt
├── presenter/
│   └── MainPresenter.kt
├── adapter/
│   └── TaskAdapter.kt
├── MVPMainActivity.kt (View)
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   └── item_task.xml
```

---

### MVVM (Model-View-ViewModel)
A pattern that leverages data binding to separate UI from business logic.

**Key Characteristics:**
- **Model**: Represents data and business logic
- **View**: Observes and reacts to ViewModel's state changes
- **ViewModel**: Exposes data streams to the View and handles user interactions

**Advantages:**
- Clear separation of UI from business logic
- Data binding reduces boilerplate code
- ViewModels survive configuration changes
- Highly testable

**Disadvantages:**
- Learning curve for data binding
- Can be overengineered for simple apps


### Project Structure MVVM
```
mvvm/
├── model/
│   ├── Task.kt
│   └── TaskRepository.kt
├── view/
│   ├── MVVMainActivity.kt (View)
│   └── adapter/
│       └── TaskAdapter.kt
├── viewmodel/
│   └── MainViewModel.kt
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   └── item_task.xml
```

---

### MVI (Model-View-Intent)
A unidirectional data flow pattern inspired by functional programming.

**Key Characteristics:**
- **Model**: Represents state rather than just data
- **View**: Renders state and sends user intents
- **Intent**: Represents user actions that can change state
- **State**: Immutable data that represents the entire UI state

**Advantages:**
- Predictable state management
- Easier debugging with immutable states
- Clear data flow
- Great for complex, reactive applications

**Disadvantages:**
- Steeper learning curve
- More verbose for simple applications
- Requires understanding of reactive programming


### Project Structure MVI
```
mvi/
├── model/
│   ├── Task.kt
│   └── TaskRepository.kt
├── intent/
│   └── TaskIntent.kt
├── state/
│   └── TaskState.kt
├── view/
│   ├── MVIMainActivity.kt
│   └── adapter/
│       └── TaskAdapter.kt
├── viewmodel/
│   └── TaskViewModel.kt
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   └── item_task.xml
```

---