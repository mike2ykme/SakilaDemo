This is a test project that I created to learn Java 8, Spring MVC, Spring Data, Spring Security, Apache Tiles, log4j, and Eclipse.

- The DataSource for Spring comes from the container so you will need to set that up or modify the database config file with your own. It's /src/main/webapp/config/data-contextConfig.xml
There are a couple things that have to be done in order to use these files as the standard Sakila Demo DB that comes with MySQL contained some issues that I had to handle.
- The checkout dates for some of the movies were far beyond what the built in functions for the DB allowed and this caused errors in both the database and when using my program.
- There was no authorities table so I had to create this.
- I have wrapped the entire website in a security filter as I built this with the idea that only authorized personel should be allowed to access an internal movie store's systems.
- In the DaoImpl file there are scripts at the bottom that I have created into functions that should be able to run either in Java or by taking the SQL queries out to create and update the Sakila DB so very little will have to be done if you want to use this.
- There is not much in the way of styling on this as I created it to be functional but I have tried to apply some generic styles and have
pasted where I received the files for images if I found any online using only images that were shown as free. For example the background image is literally flooring.
