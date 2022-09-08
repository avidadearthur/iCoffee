# IB2 - Desktop App

Project done for the class of Engineering Experience II @ Group T - Faculty of Engineering Technology / academic year 2021-22
Alarm Clock & Coffee machine connected with Raspberry Pi.

## Description

Students typically drink a lot of coffee but preparing it in the morning while your housemates try to start their day in the kitchen can be cumbersome. 
If a student could schedule when they want their coffee and just have it there, ready, waiting for them when they wake up, life would be much easier. 
That is exactly what ICoffee proposes to do. This projects has two sides: The alarm clock part, described [here](https://github.com/avidadearthur/IB2) 
and the desktop application part that can be found in this repo.

The app had simple register and login functionalities with basic error checking of the hashed user inputs. Note that since the project's database has been cleared out, the app will not work as expected.

<img src="https://github.com/avidadearthur/iCoffee/blob/master/screenshots/login_with_error.png" width=48% height=48%> <img src="https://github.com/avidadearthur/iCoffee/blob/master/screenshots/register_with_error.png" width=48% height=48%>

On the app's main page, the user can see their last week's coffee consuption on a bar chart and schedule future alarms with desired coffee temperature and volume. When the database was working the panel would update and display the future alarms as editable blocks on the scroll-panel on the left. Each alarm was a JPanel object that could be changed or deleted. Note that since the project's database has been cleared out, no alarms will show up upon creation.

<img src="https://github.com/avidadearthur/iCoffee/blob/master/screenshots/home.png">
