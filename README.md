## Project Structure
### MVC 
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


### MVP
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


### MVVM
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



### MVI
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
