# Android Architecture Patterns

This project demonstrates the implementation of five major Android architecture patterns using a simple task management application. Each module showcases a different architectural approach to help developers understand the differences, advantages, and use cases for each pattern.

## About the Project

This project contains five separate modules, each implementing the same task management functionality using a different architecture pattern:
- MVC (Model-View-Controller)
- MVP (Model-View-Presenter)
- MVVM (Model-View-ViewModel)
- MVI (Model-View-Intent)
- Clean Architecture

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
│   └── TaskAdapter.kt                 # Part of View (RecyclerView adapter)
├── model/                             # Model
│   ├── Task.kt                        # Data class representing a task
│   └── TaskRepository.kt              # Business logic and data operations
├── MVCMainActivity.kt                 # Controller + View (Activity)
├── res/                               # View
│   ├── layout/
│   │   ├── activity_main.xml          # Main UI layout
│   │   └── item_task.xml              # Task item layout
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
│   └── MainContract.kt                # Interfaces defining View and Presenter
├── model/                             # Model
│   ├── Task.kt                        # Data class representing a task
│   └── TaskRepository.kt              # Business logic and data operations
├── presenter/                         # Presenter
│   └── MainPresenter.kt               # Handles UI logic and updates View
├── adapter/
│   └── TaskAdapter.kt                 # Part of View (RecyclerView adapter)
├── MVPMainActivity.kt                 # View (implements View interface)
├── res/                               # View
│   ├── layout/
│   │   ├── activity_main.xml          # Main UI layout
│   │   └── item_task.xml              # Task item layout
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
├── model/                             # Model
│   ├── Task.kt                        # Data class representing a task
│   └── TaskRepository.kt              # Business logic and data operations
├── view/                              # View
│   ├── MVVMainActivity.kt             # Activity that observes ViewModel
│   └── adapter/
│       └── TaskAdapter.kt             # RecyclerView adapter
├── viewmodel/                         # ViewModel
│   └── MainViewModel.kt               # Exposes data streams to View
├── res/                               # View
│   ├── layout/
│   │   ├── activity_main.xml          # Main UI layout with data binding
│   │   └── item_task.xml              # Task item layout
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
├── model/                             # Model (represents state)
│   ├── Task.kt                        # Data class representing a task
│   └── TaskRepository.kt              # Business logic and data operations
├── intent/                            # Intent
│   └── TaskIntent.kt                  # Represents user actions that can change state
├── state/                             # State
│   └── TaskState.kt                   # Immutable data representing UI state
├── view/                              # View
│   ├── MVIMainActivity.kt             # Activity that renders state and sends intents
│   └── adapter/
│       └── TaskAdapter.kt             # RecyclerView adapter
├── viewmodel/                         # ViewModel
│   └── TaskViewModel.kt               # Processes intents and updates state
├── res/                               # View
│   ├── layout/
│   │   ├── activity_main.xml          # Main UI layout
│   │   └── item_task.xml              # Task item layout
```

---

### Clean Architecture
A layered architectural pattern that emphasizes separation of concerns and dependency rules to create more maintainable and testable applications.

**Key Characteristics:**
- **Domain Layer**: Contains business logic and rules, completely independent of other layers
- **Data Layer**: Implements repositories and manages data sources
- **Presentation Layer**: Handles UI and user interactions
- **Dependency Rule**: Dependencies always point inward (outer layers depend on inner layers)

**Advantages:**
- Clear separation of concerns
- Highly testable due to layer independence
- Business logic is isolated from framework details
- Easier to maintain and extend
- Flexible for changing requirements or technologies

**Disadvantages:**
- More complex initial setup
- More boilerplate code
- Steeper learning curve
- Can be overengineered for simple applications

### Project Structure Clean Architecture
```
cleanarchitecture/
├── domain/                            # Domain Layer (innermost layer)
│   ├── model/                         
│   │   └── Task.kt                    # Domain entities
│   ├── repository/                    
│   │   └── TaskRepository.kt          # Repository interfaces
│   └── usecase/                       # Business logic
│       ├── GetTasksUseCase.kt         
│       ├── AddTaskUseCase.kt          
│       ├── DeleteTaskUseCase.kt       
│       └── ToggleTaskCompleteUseCase.kt
├── data/                              # Data Layer
│   ├── datasource/                    
│   │   ├── TaskDataSource.kt          # Data source interface
│   │   └── InMemoryTaskDataSource.kt  # Implementation of data source
│   └── repository/                    
│       └── TaskRepositoryImpl.kt      # Implementation of domain repository
├── presentation/                      # Presentation Layer (outermost layer)
│   ├── view/                          
│   │   ├── CAMainActivity.kt          # Activity that observes ViewModel
│   │   └── adapter/                   
│   │       └── TaskAdapter.kt         # RecyclerView adapter
│   ├── viewmodel/                     
│   │   ├── TaskViewModel.kt           # Exposes state to the View
│   │   └── TaskViewModelFactory.kt    # Factory for creating ViewModel
│   └── state/                         
│       └── TaskState.kt               # UI state representation
├── res/                               
│   ├── layout/                        
│   │   ├── activity_camain.xml        # Main UI layout
│   │   └── item_task.xml              # Task item layout
```

---