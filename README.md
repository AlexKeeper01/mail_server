# Почтовый сервер
 

1.  Проблема с отображением кириллицы в консоли gradle
     
2.  Запуск тестов с покрытием
     
3.  Общая информация
     
    

## 1\. Проблема с отображением кириллицы в консоли gradle
 
Для решения проблемы надо перейти в ***Settings -> Build, Execution, Deployment -> Build Tools -> Gradle***
 
В пунктах ***Build and run using*** и ***Run test using*** указать ***IntelliJ IDEA***
 

## 2\. Запуск тестов с покрытием
 
Для этого нужно нажать на папку ***test*** правой кнопкой мыши и в выпадающем меню выбрать ***More Run/Debug -> Run 'All Tests' with Coverage***
 

## 3\. Общая информация
 
Покрытие тестами всех классов кроме Main составляет 100%. В классе Main реализована основная логика работы консольного интерфейса и взаимодействие классов, всё остальное выполнено по ТЗ. Проект реализован на ***OpenJDK 21***.
